package code.adt.map;

import code.adt.Enumerable;

public interface Map<Domain, CoDomain> extends Enumerable<Domain> {
    int size();

    boolean isEmpty();

    void link(Domain key, CoDomain value);

    CoDomain map(Domain key);

    CoDomain remove(Domain key);
}
