package core.map;

import core.Enumerable;

public interface Map<Domain, CoDomain> extends Enumerable<Domain> {
    boolean isEmpty();

    void put(Domain key, CoDomain value);

    CoDomain get(Domain key);

    void remove(Domain key);

    void clear();
}
