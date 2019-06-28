package code.adt.queue.simulation;

import java.util.LinkedList;
import java.util.List;

public class Consumer extends Component {
    public Consumer() {
        super(null);
    }

    private List<Atom> bag = new LinkedList<>(); // TODO

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

    public List<Atom> getBag() {
        return this.bag;
    }
}