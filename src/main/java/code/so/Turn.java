package code.so;

class Turn {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    private volatile boolean left = false;
    private volatile boolean right = false;

    private volatile int turn = 0;

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
