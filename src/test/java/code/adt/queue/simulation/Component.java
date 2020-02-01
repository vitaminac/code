package code.adt.queue.simulation;

import code.adt.ArrayList;
import code.adt.Bag;
import code.adt.DoublyLinkedList;
import code.adt.List;

import java.util.function.Consumer;

public abstract class Component {
    protected final List<Component> outs = new ArrayList<>();
    private final Bag<Consumer<Atom>> consumersOnEnter = new DoublyLinkedList<>();
    private final Bag<Consumer<Atom>> consumersOnExit = new DoublyLinkedList<>();
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
        this.outs.insert(this.outs.size(), component);
    }
}