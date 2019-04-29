package code.adt.map;

import java.util.Objects;

public class Relation<Domain, CoDomain> {
    public static final Relation SKIP = new Relation<>(null, null);

    private Domain key;
    private CoDomain value;

    public Relation(Domain key, CoDomain value) {
        this.key = key;
        this.value = value;
    }

    public Domain getKey() {
        return this.key;
    }

    public CoDomain getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relation<?, ?> relation = (Relation<?, ?>) o;
        return Objects.equals(key, relation.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
