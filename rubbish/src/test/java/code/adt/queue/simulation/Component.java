package code.adt.queue.simulation;

import collections.list.ArrayList;
import collections.list.List;
import collections.hashtable.SeparateChainingHashTable;
import collections.map.MutableMap;
import collections.set.MutableSet;

import java.util.function.Consumer;

public abstract class Component {
    protected final List<Component> outs = new ArrayList<>();
    private final MutableSet<Consumer<Atom>> consumersOnEnter = MutableSet.fromHashTable(SeparateChainingHashTable::new);
    private final MutableSet<Consumer<Atom>> consumersOnExit = MutableSet.fromHashTable(SeparateChainingHashTable::new);
    private final ChooseOut chooseOut;

    public Component(ChooseOut chooseOut) {
        this.chooseOut = chooseOut;
    }

    abstract void enter(Atom atom);

    abstract boolean canEnter();

    protected boolean tryExit(Atom atom) {
        var out = this.chooseOut.choose(this.outs);
        if (out != null) {
            this.onExit(atom);
            out.enter(atom);
            return true;
        } else {
            return false;
        }
    }

    public void addTriggerOnEnter(Consumer<Atom> consumer) {
        this.consumersOnEnter.add(consumer);
    }

    public void addTriggerOnExit(Consumer<Atom> consumer) {
        this.consumersOnExit.add(consumer);
    }

    public void onEnter(Atom atom) {
        for (var consumer : this.consumersOnEnter)
            consumer.accept(atom);
    }

    public void onExit(Atom atom) {
        for (var consumer : this.consumersOnExit)
            consumer.accept(atom);
    }

    public void addOutput(Component component) {
        this.outs.add(this.outs.size(), component);
    }
}