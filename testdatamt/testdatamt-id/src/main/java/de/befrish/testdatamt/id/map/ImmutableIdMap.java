package de.befrish.testdatamt.id.map;

import de.befrish.testdatamt.id.Identifiable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Benno Müller
 */
public class ImmutableIdMap<E extends Identifiable<?>> extends AbstractImmutableIdMap<E> implements IdMap<E> {

    private final IdMap<E> delegate;

    ImmutableIdMap(final IdMap<? extends E> delegate) {
        this.delegate = (IdMap<E>) delegate;
    }

    @Override
    public boolean containsId(final Object id) {
        return delegate.containsId(id);
    }

    @Override
    public boolean contains(final E value) {
        return delegate.contains(value);
    }

    @Override
    public E get(final Object id) {
        return delegate.get(id);
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public Set<Object> ids() {
        return delegate.ids();
    }

    @Override
    public Collection<E> values() {
        return delegate.values();
    }

    @Override
    public Map<Object, E> valuesWithIds() {
        return delegate.valuesWithIds();
    }

    @Override
    public Stream<E> stream() {
        return delegate.stream();
    }

    @Override
    public Iterator<E> iterator() {
        return delegate.iterator();
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        return delegate.equals(o);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

}
