package code.adt.queue.simulation;

import code.algorithm.Statistic;
import collections.list.List;

public class RandomChooseOutput implements ChooseOut {
    public static final ChooseOut instance = new RandomChooseOutput();

    private RandomChooseOutput() {
    }

    @Override
    public Component choose(List<Component> components) {
        if (components.isEmpty()) {
            return null;
        }
        return components.get(Statistic.sampleUniform(0, components.size() - 1));
    }
}