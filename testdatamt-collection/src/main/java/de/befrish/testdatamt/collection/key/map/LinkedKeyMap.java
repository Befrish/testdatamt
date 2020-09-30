package de.befrish.testdatamt.collection.key.map;

import de.befrish.testdatamt.collection.key.HasKey;
import de.befrish.testdatamt.util.ParameterAssert;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Iterator;
import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.LinkedHashSet;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import io.vavr.control.Option;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * <h1>{@link KeyMap} with insertion order.</h1>
 *
 * @see io.vavr.collection.LinkedHashSet
 * @see io.vavr.collection.LinkedHashMap
 *
 * @author Benno Müller
 */
public class LinkedKeyMap<K, V extends HasKey<K>> implements KeyMap<K, V> {

    private static final long serialVersionUID = -6579801974666393689L;

    private static final LinkedKeyMap<?, ?> EMPTY = new LinkedKeyMap(LinkedHashMap.empty());

    private final LinkedHashMap<K, V> map;

    private LinkedKeyMap(final LinkedHashMap<K, V> map) {
        this.map = map;
    }

    private static <K, V extends HasKey<K>> LinkedHashMap<K, V> addAll(
            final LinkedHashMap<K, V> initial,
            final Iterable<? extends V> additional) {
        LinkedHashMap<K, V> that = initial;
        for (final V v : additional) {
            that = that.put(v.getKey(), v);
        }
        return that;
    }

    @SuppressWarnings("unchecked")
    public static <K, V extends HasKey<K>> LinkedKeyMap<K, V> empty() {
        return (LinkedKeyMap<K, V>) EMPTY;
    }

    public static <K, V extends HasKey<K>> Collector<V, ArrayList<V>, LinkedKeyMap<K, V>> collector() {
        final Supplier<ArrayList<V>> supplier = ArrayList::new;
        final BiConsumer<ArrayList<V>, V> accumulator = ArrayList::add;
        final BinaryOperator<ArrayList<V>> combiner = (left, right) -> {
            left.addAll(right);
            return left;
        };
        final Function<ArrayList<V>, LinkedKeyMap<K, V>> finisher = LinkedKeyMap::ofAll;
        return Collector.of(supplier, accumulator, combiner, finisher);
    }

    @SuppressWarnings("unchecked")
    public static <K, V extends HasKey<K>> LinkedKeyMap<K, V> narrow(final LinkedKeyMap<? extends K, ? extends V> linkedHashMap) {
        return (LinkedKeyMap<K, V>) linkedHashMap;
    }

    public static <K, V extends HasKey<K>> LinkedKeyMap<K, V> of(final V element) {
        ParameterAssert.notNull(element, "element");
        return LinkedKeyMap.<K, V>empty().add(element);
    }

    @SafeVarargs
    public static <K, V extends HasKey<K>> LinkedKeyMap<K, V> of(final V... elements) {
        ParameterAssert.notNull(elements, "elements");
        LinkedKeyMap<K, V> result = LinkedKeyMap.empty();
        for (final V element : elements) {
            result = result.add(element);
        }
        return result;
    }

    /**
     * Creates a LinkedHashSet of the given elements.
     *
     * @param elements Set elements
     * @param <V>      The value type
     * @return A new LinkedHashSet containing the given entries
     */
    @SuppressWarnings("unchecked")
    public static <K, V extends HasKey<K>> LinkedKeyMap<K, V> ofAll(final Iterable<? extends V> elements) {
        ParameterAssert.notNull(elements, "elements");
        final LinkedHashMap<K, V> mao = addAll(LinkedHashMap.empty(), elements);
        return mao.isEmpty() ? empty() : new LinkedKeyMap<>(mao);
    }

    /**
     * Creates a LinkedHashSet that contains the elements of the given {@link java.util.stream.Stream}.
     *
     * @param javaStream A {@link java.util.stream.Stream}
     * @param <V>        Component type of the Stream.
     * @return A LinkedHashSet containing the given elements in the same order.
     */
    public static <K, V extends HasKey<K>> LinkedKeyMap<K, V> ofAll(final java.util.stream.Stream<? extends V> javaStream) {
        ParameterAssert.notNull(javaStream, "javaStream");
        return ofAll(Iterator.ofAll(javaStream.iterator()));
    }

    // -- Map

    @Override
    public boolean containsKey(final K key) {
        return this.map.containsKey(key);
    }

    @Override
    public Option<V> get(final K key) {
        return this.map.get(key);
    }

    @Override
    public V getOrElse(final K key, final V defaultValue) {
        return this.map.getOrElse(key, defaultValue);
    }

    @Override
    public Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public Set<V> valueSet() {
        return this.map.values().toLinkedSet();
    }

    @Override
    public LinkedKeyMap<K, V> removeByKey(final K key) {
        return key == null ? this : new LinkedKeyMap<>(this.map.remove(key));
    }

    @Override
    public LinkedKeyMap<K, V> removeAllByKeys(final Iterable<? extends K> keys) {
        return keys == null ? this : new LinkedKeyMap<>(this.map.removeAll(keys));
    }

    @Override
    public java.util.Map<K, V> toJavaMap() {
        return this.map.toJavaMap();
    }

    // -- Set

    @Override
    public LinkedKeyMap<K, V> add(final V element) {
        ParameterAssert.notNull(element, "element");
        return containsKey(element.getKey()) ? this : new LinkedKeyMap<>(this.map.put(element.getKey(), element));
    }

    @Override
    public LinkedKeyMap<K, V> addAll(final Iterable<? extends V> elements) {
        ParameterAssert.notNull(elements, "elements");
        return new LinkedKeyMap<>(addAll(this.map, elements));
    }

    @Override
    public LinkedKeyMap<K, V> intersect(final Set<? extends V> elements) {
        ParameterAssert.notNull(elements, "elements");
        return isEmpty() || elements.isEmpty() ? empty() : retainAll(elements);
    }

    @Override
    @SuppressWarnings("unchecked")
    public LinkedKeyMap<K, V> union(final Set<? extends V> elements) {
        ParameterAssert.notNull(elements, "elements");
        if (isEmpty()) {
            return LinkedKeyMap.ofAll(elements);
        } else if (elements.isEmpty()) {
            return this;
        } else {
            final LinkedHashMap<K, V> that = addAll(this.map, elements);
            if (that.size() == this.map.size()) {
                return this;
            } else {
                return new LinkedKeyMap<>(that);
            }
        }
    }

    // -- Adjusted return types of Traversable methods

    @Override
    public LinkedKeyMap<K, V> distinctBy(final Comparator<? super V> comparator) {
        ParameterAssert.notNull(comparator, "comparator");
        return ofAll(iterator().distinctBy(comparator));
    }

    @Override
    public <U> LinkedKeyMap<K, V> distinctBy(final Function<? super V, ? extends U> keyExtractor) {
        ParameterAssert.notNull(keyExtractor, "keyExtractor");
        return ofAll(iterator().distinctBy(keyExtractor));
    }

    @Override
    public LinkedKeyMap<K, V> drop(final int n) {
        return new LinkedKeyMap<>(this.map.drop(n));
    }

    @Override
    public LinkedKeyMap<K, V> dropRight(final int n) {
        return new LinkedKeyMap<>(this.map.dropRight(n));
    }

    @Override
    public LinkedKeyMap<K, V> dropUntil(final Predicate<? super V> predicate) {
        ParameterAssert.notNull(predicate, "predicate");
        return ofAll(iterator().dropUntil(predicate));
    }

    @Override
    public LinkedKeyMap<K, V> dropWhile(final Predicate<? super V> predicate) {
        ParameterAssert.notNull(predicate, "predicate");
        return ofAll(iterator().dropWhile(predicate));
    }

    @Override
    public LinkedKeyMap<K, V> filter(final Predicate<? super V> predicate) {
        ParameterAssert.notNull(predicate, "predicate");
        return ofAll(iterator().filter(predicate));
    }

    @Override
    public LinkedKeyMap<K, V> reject(final Predicate<? super V> predicate) {
        ParameterAssert.notNull(predicate, "predicate");
        return ofAll(iterator().reject(predicate));
    }

    @Override
    public <C> Map<C, ? extends LinkedKeyMap<K, V>> groupBy(final Function<? super V, ? extends C> classifier) {
        ParameterAssert.notNull(classifier, "classifier");
        return iterator().<C>groupBy(classifier).mapValues(LinkedKeyMap::ofAll);
    }

    @Override
    public LinkedKeyMap<K, V> init() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("init of empty LinkedKeyMap");
        } else {
            return new LinkedKeyMap<>(this.map.init());
        }
    }

    @Override
    public Iterator<V> iterator() {
        return this.map.valuesIterator();
    }

    @Override
    public V last() {
        return this.map.last()._2;
    }

    @Override
    public int length() {
        return this.map.length();
    }

    @Override
    public LinkedKeyMap<K, V> orElse(final Iterable<? extends V> other) {
        return isEmpty() ? ofAll(other) : this;
    }

    @Override
    public LinkedKeyMap<K, V> orElse(final Supplier<? extends Iterable<? extends V>> supplier) {
        return isEmpty() ? ofAll(supplier.get()) : this;
    }

    @Override
    public Tuple2<? extends LinkedKeyMap<K, V>, ? extends LinkedKeyMap<K, V>> partition(final Predicate<? super V> predicate) {
        ParameterAssert.notNull(predicate, "predicate");
        final Tuple2<Iterator<V>, Iterator<V>> partitioned = iterator().partition(predicate);
        return Tuple.of(ofAll(partitioned._1), ofAll(partitioned._2));
    }

    @Override
    public LinkedKeyMap<K, V> peek(final Consumer<? super V> action) {
        return ofAll(iterator().peek(action));
    }

    @Override
    public LinkedKeyMap<K, V> replace(final V currentElement, final V newElement) {
        ParameterAssert.notNull(currentElement, "currentElement");
        ParameterAssert.notNull(newElement, "newElement");
        if (!Objects.equals(currentElement.getKey(), newElement.getKey()))  {
            throw new IllegalArgumentException("cannot replace values with different keys");
        }
        return containsKey(currentElement.getKey()) ? new LinkedKeyMap<>(this.map.replaceValue(currentElement.getKey(), newElement)) : this;
    }

    @Override
    public LinkedKeyMap<K, V> retainAll(final Iterable<? extends V> elements) {
        ParameterAssert.notNull(elements, "elements");
        return isEmpty() ? this : ofAll(iterator().retainAll(elements));
    }

    @Override
    public LinkedKeyMap<K, V> scan(final V zero, final BiFunction<? super V, ? super V, ? extends V> operation) {
        ParameterAssert.notNull(zero, "zero");
        ParameterAssert.notNull(operation, "operation");
        return isEmpty() ? LinkedKeyMap.of(zero) : ofAll(iterator().scan(zero, operation));
    }

    @Override
    public Iterator<? extends LinkedKeyMap<K, V>> slideBy(final Function<? super V, ?> classifier) {
        ParameterAssert.notNull(classifier, "classifier");
        return iterator().slideBy(classifier).map(LinkedKeyMap::ofAll);
    }

    @Override
    public Iterator<? extends LinkedKeyMap<K, V>> sliding(final int size, final int step) {
        return this.map.sliding(size, step).map(LinkedKeyMap::new);
    }

    @Override
    public Tuple2<? extends LinkedKeyMap<K, V>, ? extends LinkedKeyMap<K, V>> span(final Predicate<? super V> predicate) {
        ParameterAssert.notNull(predicate, "predicate");
        final Tuple2<Iterator<V>, Iterator<V>> partitioned = iterator().span(predicate);
        return Tuple.of(ofAll(partitioned._1), ofAll(partitioned._2));
    }

    @Override
    public LinkedKeyMap<K, V> tail() {
        return new LinkedKeyMap<>(this.map.tail());
    }

    @Override
    public LinkedKeyMap<K, V> take(final int n) {
        return ofAll(iterator().take(n));
    }

    @Override
    public LinkedKeyMap<K, V> takeRight(final int n) {
        return ofAll(iterator().takeRight(n));
    }

    @Override
    public LinkedKeyMap<K, V> takeUntil(final Predicate<? super V> predicate) {
        ParameterAssert.notNull(predicate, "predicate");
        return ofAll(iterator().takeUntil(predicate));
    }

    @Override
    public LinkedKeyMap<K, V> takeWhile(final Predicate<? super V> predicate) {
        ParameterAssert.notNull(predicate, "predicate");
        return ofAll(iterator().takeWhile(predicate));
    }

    // -- Value

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean isLazy() {
        return false;
    }

    @Override
    public boolean isSequential() {
        return true;
    }

    // -- Object

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof KeyMap)) {
            return false;
        } else {
            final KeyMap that = (KeyMap) object;
            return this.size() == that.size() && this.valueSet().equals(that.valueSet());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.valueSet());
    }

    @Override
    public String stringPrefix() {
        return "LinkedKeyMap";
    }

    @Override
    public String toString() {
        return mkString(stringPrefix() + "(", ", ", ")");
    }

}
