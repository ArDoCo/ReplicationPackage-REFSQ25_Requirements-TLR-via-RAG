package edu.kit.kastel.sdq.ratlr.preprocessor;

import java.util.List;

import edu.kit.kastel.sdq.ratlr.Configuration;
import edu.kit.kastel.sdq.ratlr.knowledge.Artifact;
import edu.kit.kastel.sdq.ratlr.knowledge.Element;

/**
 * A preprocessor extracts elements based on the given artifacts.
 */
public abstract class Preprocessor {
    public static final String SEPARATOR = "$";

    public abstract List<Element> preprocess(List<Artifact> artifacts);

    public static Preprocessor createPreprocessor(Configuration.ModuleConfiguration configuration) {
        return switch (configuration.name()) {
            case "artifact" -> new SingleArtifactPreprocessor();
            default -> throw new IllegalStateException("Unexpected value: " + configuration.name());
        };
    }
}
