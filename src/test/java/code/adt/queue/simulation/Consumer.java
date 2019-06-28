package code.adt.queue.simulation;

import code.adt.Bag;
import code.adt.LinkedList;

public class Consumer extends Component {
    public Consumer() {
        super(null);
    }

    private Bag<Atom> bag = new LinkedList<>();

    @Override
    public void enter(Atom atom) {
        this.onEnter(atom);
        this.bag.add(atom);
    }

    @Override
    public boolean canEnter() {
        return true;
    }

    @Override
    public void addOutput(Component component) {
        throw new RuntimeException();
    }

    public Bag<Atom> getBag() {
        return this.bag;
    }
}