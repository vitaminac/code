package code.adt.queue.simulation;

import collections.list.List;

public interface ChooseOut {
    Component choose(List<Component> components); // return null if no one available
}