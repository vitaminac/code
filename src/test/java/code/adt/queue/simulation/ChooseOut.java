package code.adt.queue.simulation;

import java.util.List; // TODO

public interface ChooseOut {
    Component choose(List<Component> components); // return null if no one available
}