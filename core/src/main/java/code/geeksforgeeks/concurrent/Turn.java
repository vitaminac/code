package code.geeksforgeeks.concurrent;

class Turn {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    // https://stackoverflow.com/a/38636062/9980245
    private volatile boolean left;
    private volatile boolean right;

    private volatile int turn;

    public Turn() {
        // Initialize lock by reseting the desire of
        // both the threads to acquire the locks.
        // And, giving turn to one of them.
        left = false;
        right = false;
        turn = 0;
    }

    public void setTurn(int thread) {
        this.turn = thread;
    }

    public int getTurn() {
        return turn;
    }

    public void setIntent(int thread, boolean intent) {
        if (thread == LEFT) {
            right = intent;
        } else {
            left = intent;
        }
    }

    public boolean getIntent(int thread) {
        if (thread == LEFT) {
            return right;
        } else {
            return left;
        }
    }
}
