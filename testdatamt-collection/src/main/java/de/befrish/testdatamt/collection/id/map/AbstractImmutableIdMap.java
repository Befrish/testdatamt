package de.befrish.testdatamt.collection.id.map;

import de.befrish.testdatamt.collection.id.Identifiable;

import java.util.Collection;

/**
 * @author Benno Müller
 */
public abstract class AbstractImmutableIdMap<E extends Identifiable<?>> implements IdMap<E> {

    @Override
    public final boolean add(final E value) {
        // nichts tun
        return false;
    }

    @Override
    public final void addAll(final IdMap<? extends E> c) {
        // nichts tun
    }

    @Override
    public final boolean remove(final Object id) {
        // nichts tun
        return false;
    }

    @Override
    public final void removeAll(final Collection<?> ids) {
        // nichts tun
    }

}
