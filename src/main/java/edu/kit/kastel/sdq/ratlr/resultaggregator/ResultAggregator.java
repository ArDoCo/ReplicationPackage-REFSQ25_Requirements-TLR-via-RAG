package edu.kit.kastel.sdq.ratlr.resultaggregator;

import java.util.List;
import java.util.Set;

import edu.kit.kastel.sdq.ratlr.Configuration;
import edu.kit.kastel.sdq.ratlr.classifier.ClassificationResult;
import edu.kit.kastel.sdq.ratlr.knowledge.Element;
import edu.kit.kastel.sdq.ratlr.knowledge.TraceLink;

public abstract class ResultAggregator {
    public abstract Set<TraceLink> aggregate(
            List<Element> sourceElements,
            List<Element> targetElements,
            List<ClassificationResult> classificationResults);

    public static ResultAggregator createResultAggregator(Configuration.ModuleConfiguration configuration) {
        return switch (configuration.name()) {
            case "any_connection" -> new AnyResultAggregator(configuration);
            default -> throw new IllegalStateException("Unexpected value: " + configuration.name());
        };
    }
}
