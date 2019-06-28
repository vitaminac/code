package code.adt.queue.simulation;

import code.adt.List;

public interface ChooseOut {
    Component choose(List<Component> components); // return null if no one available
}