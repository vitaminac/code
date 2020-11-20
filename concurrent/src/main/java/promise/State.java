package promise;

public enum State {
    Pending, // initial state, neither fulfilled nor rejected.
    Fulfilled, // meaning that the operation completed successfully.
    Rejected // meaning that the operation failed.
}
