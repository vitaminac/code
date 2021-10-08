package code.adt.queue.simulation;

import core.list.List;

public class AnyAvailableChoose implements ChooseOut {
    public static final ChooseOut instance = new AnyAvailableChoose();

    private AnyAvailableChoose() {

    }

    @Override
    public Component choose(List<Component> components) {
        for (var component : components) {
            if (component.canEnter()) {
                return component;
            }
        }
        return null;
    }
}