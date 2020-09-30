package de.befrish.testdatamt.collection.id.map;

import de.befrish.testdatamt.collection.id.Identifiable;
import de.befrish.testdatamt.util.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Benno Müller
 */
public class MutableIdMap<E extends Identifiable<?>> implements IdMap<E> {

    private final Map<Object, E> elements;

    public MutableIdMap() {
        this.elements = new LinkedHashMap<>();
    }

    public MutableIdMap(final E... elements) {
        this();
        if (elements != null) {
            for (final E element : elements) {
                add(element);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public MutableIdMap(final Iterable<? extends E> elements) {
        this();
        // Wenn es eine MutableIdMap ist, brauchen wir keine Prüfung durchführen und müssen auch
        // die Elemente nicht einzeln hinzufügen. Wir können direkt kopieren und die effizientere
        // Map.putAll()-Methode verwenden.
        if (elements instanceof MutableIdMap) {
            final MutableIdMap<E> otherMap = (MutableIdMap<E>) elements;
            this.elements.putAll(otherMap.elements);
        } else if (elements != null) {
            for (final E element : elements) {
                add(element);
            }
        }
    }

    @Override
    public boolean containsId(final Object id) {
        return this.elements.containsKey(id);
    }

    @Override
    public boolean contains(final E value) {
        return this.elements.containsValue(value);
    }

    @Override
    public E get(final Object id) {
        Assert.notNull(id, "id");
        return this.elements.get(id);
    }

    @Override
    public final boolean add(final E value) {
        Assert.notNull(value, "value");
        final Object id = value.getId();
        return this.elements.put(id, value) == value;
    }

    @Override
    public void addAll(final IdMap<? extends E> c) {
        this.elements.putAll(c.valuesWithIds());
    }

    @Override
    public boolean remove(final Object id) {
        Assert.notNull(id, "id");
        return this.elements.remove(id) != null;
    }

    @Override
    public void removeAll(final Collection<?> ids) {
        this.elements.keySet().removeAll(ids);
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public boolean isEmpty() {
        return this.elements.isEmpty();
    }

    @Override
    public Set<Object> ids() {
        return this.elements.keySet();
    }

    @Override
    public Collection<E> values() {
        return this.elements.values();
    }

    @Override
    public Map<Object, E> valuesWithIds() {
        return Collections.unmodifiableMap(this.elements);
    }

    @Override
    public Iterator<E> iterator() {
        return values().iterator();
    }

    @Override
    public Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.elements);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof IdMap)) {
            return false;
        }
        final IdMap<E> other = (IdMap<E>) obj;
        return Arrays.equals(this.values().toArray(), other.values().toArray());
    }

    @Override
    public String toString() {
        return this.elements.values().toString();
    }

}
