package code.adt.queue.simulation;

import core.list.List;

public interface ChooseOut {
    Component choose(List<Component> components); // return null if no one available
}