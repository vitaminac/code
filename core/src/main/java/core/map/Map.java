package core.map;

import core.Enumerable;

public interface Map<Domain, CoDomain> extends Enumerable<Domain> {
    boolean isEmpty();

    void link(Domain key, CoDomain value);

    CoDomain map(Domain key);

    CoDomain remove(Domain key);

    void clear();
}
