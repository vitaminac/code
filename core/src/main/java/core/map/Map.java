package core.map;

import core.functional.Enumerable;

public interface Map<Domain, CoDomain>
        extends Enumerable<Domain> {
    boolean isEmpty();

    int size();

    void put(Domain key, CoDomain value);

    CoDomain get(Domain key);

    void remove(Domain key);

    void clear();
}
