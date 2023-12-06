package collections.map;

import core.functional.Enumerable;

public interface Map<Domain, CoDomain>
        extends Enumerable<Domain> {
    boolean isEmpty();

    int size();

    CoDomain get(Domain key);

}
