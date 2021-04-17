package core.functional;

public class MaxReducer<E extends Comparable<E>> extends Reducer<E, E> {
    public MaxReducer(final E identity) {
        super(identity, (e, ac) -> ac.compareTo(e) > 0 ? ac : e);
    }
}
