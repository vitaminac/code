package collections.map;

public interface MutableMap<Domain, CoDomain>
        extends Map<Domain, CoDomain> {
    void put(Domain key, CoDomain value);

    void remove(Domain key);

    void clear();
}
