package de.befrish.testdatamt.id.map;

import de.befrish.testdatamt.id.Identifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Benno Müller
 */
public class EmptyIdMap<E extends Identifiable<?>> extends AbstractImmutableIdMap<E> implements IdMap<E> {

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean containsId(final Object id) {
        return false;
    }

    @Override
    public boolean contains(final E value) {
        return false;
    }

    @Override
    public E get(final Object id) {
        return null;
    }

    @Override
    public Set<Object> ids() {
        return Collections.emptySet();
    }

    @Override
    public Collection<E> values() {
        return Collections.emptyList();
    }

    @Override
    public Map<Object, E> valuesWithIds() {
        return Collections.emptyMap();
    }

    @Override
    public Iterator<E> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public Stream<E> stream() {
        return Stream.empty();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof IdMap)) {
            return false;
        }
        final IdMap<E> other = (IdMap<E>) o;
        return other.isEmpty();
    }

    @Override
    public String toString() {
        return Collections.emptyList().toString();
    }

}
