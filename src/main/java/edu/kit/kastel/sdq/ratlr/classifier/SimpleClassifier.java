package edu.kit.kastel.sdq.ratlr.classifier;

import java.util.ArrayList;
import java.util.List;

import dev.langchain4j.model.chat.ChatLanguageModel;
import edu.kit.kastel.sdq.ratlr.Configuration;
import edu.kit.kastel.sdq.ratlr.cache.Cache;
import edu.kit.kastel.sdq.ratlr.cache.CacheManager;
import edu.kit.kastel.sdq.ratlr.knowledge.Element;
import edu.kit.kastel.sdq.ratlr.utils.KeyGenerator;

public class SimpleClassifier extends Classifier {

    private static final String DEFAULT_TEMPLATE =
            """
            Question: Here are two parts of software development artifacts.

            {source_type}: '''{source_content}'''

            {target_type}: '''{target_content}'''
            Are they related?

            Answer with 'yes' or 'no'.
            """;

    private final Cache cache;
    private final ChatLanguageModelProvider provider;

    private final ChatLanguageModel llm;
    private final String template;

    public SimpleClassifier(Configuration.ModuleConfiguration configuration) {
        super(ChatLanguageModelProvider.supportsThreads(configuration) ? DEFAULT_THREAD_COUNT : 1);
        this.provider = new ChatLanguageModelProvider(configuration);
        this.template = configuration.argumentAsString("template", DEFAULT_TEMPLATE);
        this.cache = CacheManager.getDefaultInstance()
                .getCache(this.getClass().getSimpleName() + "_" + provider.modelName());
        this.llm = provider.createChatModel();
    }

    private SimpleClassifier(Cache cache, ChatLanguageModelProvider provider, String template) {
        super(provider.supportsThreads() ? DEFAULT_THREAD_COUNT : 1);
        this.cache = cache;
        this.provider = provider;
        this.template = template;
        this.llm = provider.createChatModel();
    }

    @Override
    protected final Classifier copyOf() {
        return new SimpleClassifier(cache, provider, template);
    }

    @Override
    protected final List<ClassificationResult> classify(Element source, List<Element> targets) {
        List<Element> relatedTargets = new ArrayList<>();

        for (var target : targets) {
            String llmResponse = classify(source, target);
            boolean isRelated = llmResponse.toLowerCase().contains("yes");
            if (isRelated) {
                relatedTargets.add(target);
            }
        }
        return relatedTargets.stream()
                .map(relatedTarget -> ClassificationResult.of(source, relatedTarget))
                .toList();
    }

    private String classify(Element source, Element target) {
        String request = template.replace("{source_type}", source.getType())
                .replace("{source_content}", source.getContent())
                .replace("{target_type}", target.getType())
                .replace("{target_content}", target.getContent());

        String key = KeyGenerator.generateKey(request);
        String cachedResponse = cache.get(key, String.class);
        if (cachedResponse != null) {
            return cachedResponse;
        } else {
            logger.info("Classifying: {} and {}", source.getIdentifier(), target.getIdentifier());
            String response = llm.generate(request);
            cache.put(key, response);
            return response;
        }
    }
}
