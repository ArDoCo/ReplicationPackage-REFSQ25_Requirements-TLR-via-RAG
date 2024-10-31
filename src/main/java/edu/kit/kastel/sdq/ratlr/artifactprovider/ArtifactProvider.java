package edu.kit.kastel.sdq.ratlr.artifactprovider;

import java.util.List;

import edu.kit.kastel.sdq.ratlr.Configuration;
import edu.kit.kastel.sdq.ratlr.knowledge.Artifact;

/**
 * Provides artifacts for LiSSA approach.
 */
public abstract class ArtifactProvider {

    /**
     * Returns the artifacts provided by this provider.
     */
    public abstract List<Artifact> getArtifacts();

    public abstract Artifact getArtifact(String identifier);

    public static ArtifactProvider createArtifactProvider(Configuration.ModuleConfiguration configuration) {
        return switch (configuration.name()) {
            case "text" -> new TextArtifactProvider(configuration);
            default -> throw new IllegalStateException("Unexpected value: " + configuration.name());
        };
    }
}
