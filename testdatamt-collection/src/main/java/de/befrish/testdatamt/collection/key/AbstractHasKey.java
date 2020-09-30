package de.befrish.testdatamt.collection.key;

import de.befrish.testdatamt.util.ParameterAssert;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author Benno Müller
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AbstractHasKey<K> implements HasKey<K> {

    @NonNull
    @Getter
    @EqualsAndHashCode.Include
    private final K key;

    protected AbstractHasKey(final K key) {
        ParameterAssert.notNull(key, "key");
        this.key = key;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "key=" + this.key +
                '}';
    }
}
