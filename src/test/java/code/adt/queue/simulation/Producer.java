package code.adt.queue.simulation;

import org.apache.commons.math3.distribution.ExponentialDistribution;

public class Producer extends Component {
    public Producer(Clock clock, double lambda, ChooseOut chooseOut) {
        super(chooseOut);
        var distribution = new ExponentialDistribution(1 / lambda);
        Runnable produce = new Runnable() {
            @Override
            public void run() {
                var interval = distribution.sample();
                clock.addEvent(new Event(interval + clock.getTime(), this));
                var atom = new Atom();
                Producer.this.tryExit(atom);
            }
        };
        produce.run();
    }

    public Producer(Clock clock, double lambda) {
        this(clock, lambda, RandomChooseOutput.instance);
    }

    @Override
    public void enter(Atom atom) {
        throw new RuntimeException();
    }

    @Override
    public boolean canEnter() {
        return false;
    }
}