package code.adt.queue.simulation;

import org.apache.commons.math3.distribution.ExponentialDistribution;

public class SingleServer extends Component {
    private final Runnable process;
    private Atom processing;

    public SingleServer(Clock clock, double mu, ChooseOut chooseOut) {
        super(chooseOut);
        var distribution = new ExponentialDistribution(1 / mu);
        this.process = new Runnable() {
            @Override
            public void run() {
                var interval = distribution.sample();
                clock.addEvent(new Event((clock.getTime() + interval), new Runnable() {
                    @Override
                    public void run() {
                        SingleServer.this.tryExit(SingleServer.this.processing);
                        SingleServer.this.processing = null;
                    }
                }));
            }
        };
    }

    public SingleServer(Clock clock, double mu) {
        this(clock, mu, RandomChooseOutput.instance);
    }

    @Override
    public void enter(Atom atom) {
        if (this.processing == null) {
            this.onEnter(atom);
            this.processing = atom;
            this.process.run();
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean canEnter() {
        return this.processing == null;
    }
}