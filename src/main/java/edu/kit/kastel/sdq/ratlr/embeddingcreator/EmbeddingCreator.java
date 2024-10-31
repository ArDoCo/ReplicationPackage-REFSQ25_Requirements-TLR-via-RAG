package edu.kit.kastel.sdq.ratlr.embeddingcreator;

import java.util.List;

import edu.kit.kastel.sdq.ratlr.Configuration;
import edu.kit.kastel.sdq.ratlr.knowledge.Element;

public abstract class EmbeddingCreator {
    public float[] calculateEmbedding(Element element) {
        return calculateEmbeddings(List.of(element)).getFirst();
    }

    public abstract List<float[]> calculateEmbeddings(List<Element> elements);

    public static EmbeddingCreator createEmbeddingCreator(Configuration.ModuleConfiguration configuration) {
        return switch (configuration.name()) {
            case "openai" -> new OpenAiEmbeddingCreator(configuration);
            default -> throw new IllegalStateException("Unexpected value: " + configuration.name());
        };
    }
}
