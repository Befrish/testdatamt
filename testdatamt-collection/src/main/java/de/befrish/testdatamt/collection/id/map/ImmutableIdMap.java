package de.befrish.testdatamt.collection.id.map;

import de.befrish.testdatamt.collection.id.Identifiable;

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

    @SuppressWarnings("unchecked")
    ImmutableIdMap(final IdMap<? extends E> delegate) {
        this.delegate = (IdMap<E>) delegate;
    }

    @Override
    public boolean containsId(final Object id) {
        return this.delegate.containsId(id);
    }

    @Override
    public boolean contains(final E value) {
        return this.delegate.contains(value);
    }

    @Override
    public E get(final Object id) {
        return this.delegate.get(id);
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public Set<Object> ids() {
        return this.delegate.ids();
    }

    @Override
    public Collection<E> values() {
        return this.delegate.values();
    }

    @Override
    public Map<Object, E> valuesWithIds() {
        return this.delegate.valuesWithIds();
    }

    @Override
    public Stream<E> stream() {
        return this.delegate.stream();
    }

    @Override
    public Iterator<E> iterator() {
        return this.delegate.iterator();
    }

    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        return this.delegate.equals(o);
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }

}
