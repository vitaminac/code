package code.adt.queue.simulation;

import java.util.LinkedList;
import java.util.Queue;

public class InfiniteQueue extends Component {
    private final Queue<Atom> queue = new LinkedList<>(); // TODO

    public InfiniteQueue(Clock clock, ChooseOut chooseOut) {
        super(chooseOut);
        clock.addListeners(new Runnable() {
            @Override
            public void run() {
                if (!InfiniteQueue.this.queue.isEmpty()) {
                    if (InfiniteQueue.this.tryExit(InfiniteQueue.this.queue.peek())) {
                        InfiniteQueue.this.queue.remove();
                    }
                }
            }
        });
    }

    public InfiniteQueue(Clock clock) {
        this(clock, AnyAvailableChoose.instance);
    }

    @Override
    public void enter(Atom atom) {
        this.onEnter(atom);
        if (!this.tryExit(atom)) {
            this.queue.add(atom);
        }
    }

    @Override
    public boolean canEnter() {
        return true;
    }
}