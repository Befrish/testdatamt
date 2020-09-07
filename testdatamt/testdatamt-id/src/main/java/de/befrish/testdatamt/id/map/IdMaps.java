package de.befrish.testdatamt.id.map;

import de.befrish.testdatamt.id.Identifiable;

import java.util.Collection;

/**
 * @author Benno Müller
 */
public final class IdMaps {

    private IdMaps() {
        super();
    }

    public static <E extends Identifiable<?>> IdMap<E> immutable(final Collection<? extends E> items) {
        return new ImmutableIdMap<>(new MutableIdMap<>(items));
    }

    public static <E extends Identifiable<?>> IdMap<E> immutable(final E... items) {
        return new ImmutableIdMap<>(new MutableIdMap<>(items));
    }

    public static <E extends Identifiable<?>> IdMap<E> immutable(final IdMap<? extends E> idMap) {
        return new ImmutableIdMap<>(idMap);
    }

    public static <E extends Identifiable<?>> IdMap<E> empty() {
        return new EmptyIdMap<>();
    }

}
