package code.adt.map;

import code.adt.Enumerable;
import code.adt.Relation;

public interface Map<Domain, CoDomain> extends Enumerable<Relation<Domain, CoDomain>> {
    int size();

    boolean isEmpty();

    void link(Domain key, CoDomain value);

    CoDomain map(Domain key);

    CoDomain remove(Domain key);
}
