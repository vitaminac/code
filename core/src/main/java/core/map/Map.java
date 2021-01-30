package core.map;

import core.Collection;

public interface Map<Domain, CoDomain>
        extends Collection<Domain> {
    boolean isEmpty();

    void put(Domain key, CoDomain value);

    CoDomain get(Domain key);

    void remove(Domain key);

    void clear();
}
