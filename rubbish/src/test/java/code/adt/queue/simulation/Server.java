package code.adt.queue.simulation;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.RealDistribution;

public class Server extends Component {
    private final Runnable process;
    private Atom processing;

    public Server(Clock clock, RealDistribution distribution, ChooseOut chooseOut) {
        super(chooseOut);
        this.process = new Runnable() {
            @Override
            public void run() {
                var interval = distribution.sample();
                clock.addEvent(new Event((clock.getTime() + interval), new Runnable() {
                    @Override
                    public void run() {
                        Server.this.tryExit(Server.this.processing);
                        Server.this.processing = null;
                    }
                }));
            }
        };
    }

    public Server(Clock clock, double mu, ChooseOut chooseOut) {
        this(clock, new ExponentialDistribution(1 / mu), chooseOut);
    }

    public Server(Clock clock, double mu) {
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