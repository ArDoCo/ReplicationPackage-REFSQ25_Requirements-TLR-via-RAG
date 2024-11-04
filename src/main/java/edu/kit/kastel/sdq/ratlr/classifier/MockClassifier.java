package edu.kit.kastel.sdq.ratlr.classifier;

import java.util.List;

import edu.kit.kastel.sdq.ratlr.knowledge.Element;

public class MockClassifier extends Classifier {
    public MockClassifier() {
        super(1);
    }

    @Override
    protected List<ClassificationResult> classify(Element source, List<Element> targets) {
        return targets.stream()
                .map(target -> ClassificationResult.of(source, target))
                .toList();
    }

    @Override
    protected Classifier copyOf() {
        return new MockClassifier();
    }
}
