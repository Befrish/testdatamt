package de.befrish.testdatamt.collection.key.map;

import de.befrish.testdatamt.collection.key.HasKey;
import de.befrish.testdatamt.util.ParameterAssert;
import io.vavr.Function1;
import io.vavr.PartialFunction;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.collection.Iterator;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import io.vavr.collection.Traversable;
import io.vavr.control.Option;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <h1>Set of objects with key</h1>
 * <p>
 * The Set has map methods in order to get fast access to an object by its key.
 * </p>
 *
 * @see io.vavr.collection.Set
 * @see io.vavr.collection.Map
 *
 * @author Benno Müller
 */
public interface KeyMap<K, V extends HasKey<K>> extends Traversable<V>, Function1<V, Boolean>, Serializable {

    long serialVersionUID = 1L;

    static <K, V extends HasKey<K>> Tuple2<K, V> entry(final V element) {
        ParameterAssert.notNull(element, "element");
        return Tuple.of(element.getKey(), element);
    }

    /*
     * Map methods
     */

    /**
     * Returns <code>true</code> if this map contains a mapping for the specified key.
     *
     * @param key key whose presence in this map is to be tested
     * @return <code>true</code> if this map contains a mapping for the specified key
     */
    boolean containsKey(K key);

    /**
     * Returns the {@code Some} of value to which the specified key
     * is mapped, or {@code None} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the {@code Some} of value to which the specified key
     * is mapped, or {@code None} if this map contains no mapping
     * for the key
     */
    Option<V> get(K key);

    /**
     * Returns the value associated with a key, or a default value if the key is not contained in the map.
     *
     * @param key          the key
     * @param defaultValue a default value
     * @return the value associated with key if it exists, otherwise the default value.
     */
    V getOrElse(K key, V defaultValue);

    /**
     * Returns the keys contained in this map.
     *
     * @return {@code Set} of the keys contained in this map.
     */
    io.vavr.collection.Set<K> keySet();

    /**
     * Returns the values contained in this map.
     *
     * @return {@code Set} of the values contained in this map.
     */
    Set<V> valueSet();

    /**
     * Removes the mapping for a key from this map if it is present.
     *
     * @param key key whose mapping is to be removed from the map
     * @return A new Map containing these elements without the entry
     * specified by that key.
     */
    KeyMap<K, V> removeByKey(K key);

    /**
     * Removes the mapping for a key from this map if it is present.
     *
     * @param keys keys are to be removed from the map
     * @return A new Map containing these elements without the entries
     * specified by that keys.
     */
    KeyMap<K, V> removeAllByKeys(Iterable<? extends K> keys);

    /**
     * Converts this Vavr {@code Map} to a {@code java.util.Map} while preserving characteristics
     * like insertion order ({@code LinkedHashMap}) and sort order ({@code SortedMap}).
     *
     * @return a new {@code java.util.Map} instance
     */
    java.util.Map<K, V> toJavaMap();

    // -- from io.vavr.collection.Set

    /**
     * Narrows a widened {@code Set<? extends V>} to {@code IdMap<K, V>}
     * by performing a type-safe cast. This is eligible because immutable/read-only
     * collections are covariant.
     *
     * @param keyMap A {@code Set}.
     * @param <V> Component type of the {@code Set}.
     * @return the given {@code idMap} instance as narrowed type {@code IdMap<K, V>}.
     */
    @SuppressWarnings("unchecked")
    static <K, V extends HasKey<K>> KeyMap<K, V> narrow(final KeyMap<? extends K, ? extends V> keyMap) {
        return (KeyMap<K, V>) keyMap;
    }

    /**
     * Add the given element to this set, if it is not already contained.
     *
     * @param element The element to be added.
     * @return A new set containing all elements of this set and also {@code element}.
     */
    KeyMap<K, V> add(V element);

    /**
     * Adds all of the given elements to this set, if not already contained.
     *
     * @param elements The elements to be added.
     * @return A new set containing all elements of this set and the given {@code elements}, if not already contained.
     */
    KeyMap<K, V> addAll(Iterable<? extends V> elements);

    /**
     * Tests if a given {@code element} is contained in this {@code Set}.
     * <p>
     * This method is equivalent to {@link #contains(V)}.
     *
     * @param element the element to test for membership.
     * @return {@code true} if the given {@code element} is contained, {@code false} otherwise.
     * @deprecated Will be removed
     */
    @Override
    @Deprecated
    default Boolean apply(final V element) {
        return contains(element);
    }

    /**
     * Calculates the difference between this set and another set.
     * <p>
     * See also {@link #removeAll(Iterable)}.
     *
     * @param elements Elements to be removed from this set.
     * @return A new Set containing all elements of this set which are not located in {@code that} set.
     */
    default KeyMap<K, V> diff(final Set<? extends V> elements) {
        ParameterAssert.notNull(elements, "elements");
        return isEmpty() || elements.isEmpty() ? this : removeAll(elements);
    }

    /**
     * Computes the intersection between this set and another set.
     * <p>
     * See also {@link #retainAll(Iterable)}.
     *
     * @param elements the set to intersect with.
     * @return A new Set consisting of all elements that are both in this set and in the given set {@code that}.
     */
    KeyMap<K, V> intersect(Set<? extends V> elements);

    /**
     * Removes a specific element from this set, if present.
     *
     * @param element The element to be removed from this set.
     * @return A new set consisting of the elements of this set, without the given {@code element}.
     */
    default KeyMap<K, V> remove(final V element) {
        return element == null ? this : removeByKey(element.getKey());
    }

    /**
     * Removes all of the given elements from this set, if present.
     *
     * @param elements The elements to be removed from this set.
     * @return A new set consisting of the elements of this set, without the given {@code elements}.
     */
    default KeyMap<K, V> removeAll(final Iterable<? extends V> elements) {
        ParameterAssert.notNull(elements, "elements");
        return removeAllByKeys(Stream.ofAll(elements).map(HasKey::getKey));
    }

    /**
     * Converts this Vavr {@code Set} to a {@code java.util.Set} while preserving characteristics
     * like insertion order ({@code LinkedHashSet}) and sort order ({@code SortedSet}).
     *
     * @return a new {@code java.util.Set} instance
     */

    @Override
    default java.util.Set<V> toJavaSet() {
        return valueSet().toJavaSet();
    }

    /**
     * Adds all of the elements of {@code that} set to this set, if not already present.
     * <p>
     * See also {@link #addAll(Iterable)}.
     *
     * @param elements The set to form the union with.
     * @return A new set that contains all distinct elements of this and {@code that} set.
     */
    KeyMap<K, V> union(Set<? extends V> elements);

    // -- Adjusted return types of Traversable methods

    @Override
    default <R> Set<R> collect(final PartialFunction<? super V, ? extends R> partialFunction) {
        ParameterAssert.notNull(partialFunction, "partialFunction");
        return valueSet().collect(partialFunction);
    }

    @Override
    default boolean contains(final V element) {
        return element != null && containsKey(element.getKey());
    }

    @Override
    default KeyMap<K, V> distinct() {
        return this; // always distinct because of the key set
    }

    @Override
    KeyMap<K, V> distinctBy(Comparator<? super V> comparator);

    @Override
    <U> KeyMap<K, V> distinctBy(Function<? super V, ? extends U> keyExtractor);

    @Override
    KeyMap<K, V> drop(int n);

    @Override
    KeyMap<K, V> dropRight(int n);

    @Override
    KeyMap<K, V> dropUntil(Predicate<? super V> predicate);

    @Override
    KeyMap<K, V> dropWhile(Predicate<? super V> predicate);

    @Override
    KeyMap<K, V> filter(Predicate<? super V> predicate);

    @Override
    default <U> Set<U> flatMap(final Function<? super V, ? extends Iterable<? extends U>> mapper) {
        ParameterAssert.notNull(mapper, "mapper");
        return valueSet().flatMap(mapper);
    }

    @Override
    default <U> U foldLeft(final U zero, final BiFunction<? super U, ? super V, ? extends U> function) {
        ParameterAssert.notNull(function, "function");
        return iterator().foldLeft(zero, function);
    }

    @Override
    default  <U> U foldRight(final U zero, final BiFunction<? super V, ? super U, ? extends U> function) {
        ParameterAssert.notNull(function, "function");
        return iterator().foldRight(zero, function);
    }

    @Override
    default V head() {
        return this.iterator().head();
    }

    @Override
    KeyMap<K, V> reject(Predicate<? super V> predicate);

    @Override
    <C> Map<C, ? extends KeyMap<K, V>> groupBy(Function<? super V, ? extends C> classifier);

    @Override
    default Iterator<? extends KeyMap<K, V>> grouped(final int size) {
        return sliding(size, size);
    }

    @Override
    default boolean hasDefiniteSize() {
        return true;
    }

    @Override
    KeyMap<K, V> init();

    @Override
    default Option<? extends KeyMap<K, V>> initOption() {
        return isEmpty() ? Option.none() : Option.some(init());
    }

    @Override
    default boolean isDistinct() {
        return true;
    }

    @Override
    default boolean isTraversableAgain() {
        return true;
    }

    @Override
    Iterator<V> iterator();

    @Override
    int length();

    @Override
    default <U> Set<U> map(final Function<? super V, ? extends U> mapper) {
        ParameterAssert.notNull(mapper, "mapper");
        return valueSet().map(mapper);
    }

    @Override
    KeyMap<K, V> orElse(Iterable<? extends V> other);

    @Override
    KeyMap<K, V> orElse(Supplier<? extends Iterable<? extends V>> supplier);

    @Override
    Tuple2<? extends KeyMap<K, V>, ? extends KeyMap<K, V>> partition(Predicate<? super V> predicate);

    @Override
    KeyMap<K, V> peek(Consumer<? super V> action);

    @Override
    KeyMap<K, V> replace(V currentElement, V newElement);

    @Override
    default KeyMap<K, V> replaceAll(final V currentElement, final V newElement) {
        return replace(currentElement, newElement);
    }

    @Override
    KeyMap<K, V> retainAll(Iterable<? extends V> elements);

    @Override
    KeyMap<K, V> scan(V zero, BiFunction<? super V, ? super V, ? extends V> operation);

    @Override
    default <U> Set<U> scanLeft(final U zero, final BiFunction<? super U, ? super V, ? extends U> operation) {
        ParameterAssert.notNull(operation, "operation");
        return valueSet().scanLeft(zero, operation);
    }

    @Override
    default <U> Set<U> scanRight(final U zero, final BiFunction<? super V, ? super U, ? extends U> operation) {
        ParameterAssert.notNull(operation, "operation");
        return valueSet().scanRight(zero, operation);
    }

    @Override
    Iterator<? extends KeyMap<K, V>> slideBy(Function<? super V, ?> classifier);

    @Override
    default Iterator<? extends KeyMap<K, V>> sliding(final int size) {
        return sliding(size, 1);
    }

    @Override
    Iterator<? extends KeyMap<K, V>> sliding(int size, int step);

    @Override
    Tuple2<? extends KeyMap<K, V>, ? extends KeyMap<K, V>> span(Predicate<? super V> predicate);

    @Override
    KeyMap<K, V> tail();

    @Override
    default Option<? extends KeyMap<K, V>> tailOption() {
        return isEmpty() ? Option.none() : Option.some(tail());
    }

    @Override
    KeyMap<K, V> take(int n);

    @Override
    KeyMap<K, V> takeRight(int n);

    @Override
    KeyMap<K, V> takeUntil(Predicate<? super V> predicate);

    @Override
    KeyMap<K, V> takeWhile(Predicate<? super V> predicate);

    @Override
    default  <T1, T2> Tuple2<? extends Set<T1>, ? extends Set<T2>> unzip(final Function<? super V, Tuple2<? extends T1, ? extends T2>> unzipper) {
        ParameterAssert.notNull(unzipper, "unzipper");
        return valueSet().unzip(unzipper);
    }

    @Override
    default  <T1, T2, T3> Tuple3<? extends Set<T1>, ? extends Set<T2>, ? extends Set<T3>> unzip3(final Function<? super V, Tuple3<? extends T1, ? extends T2, ? extends T3>> unzipper) {
        ParameterAssert.notNull(unzipper, "unzipper");
        return valueSet().unzip3(unzipper);
    }

    @Override
    default <U> Set<Tuple2<V, U>> zip(final Iterable<? extends U> that) {
        ParameterAssert.notNull(that, "that");
        return valueSet().zip(that);
    }

    @Override
    default <U, R> Set<R> zipWith(final Iterable<? extends U> that, final BiFunction<? super V, ? super U, ? extends R> mapper) {
        ParameterAssert.notNull(that, "that");
        ParameterAssert.notNull(mapper, "mapper");
        return valueSet().zipWith(that, mapper);
    }

    @Override
    default <U> Set<Tuple2<V, U>> zipAll(final Iterable<? extends U> that, final V thisElem, final U thatElem) {
        ParameterAssert.notNull(that, "that");
        return valueSet().zipAll(that, thisElem, thatElem);
    }

    @Override
    default Set<Tuple2<V, Integer>> zipWithIndex() {
        return valueSet().zipWithIndex();
    }

    @Override
    default <U> Set<U> zipWithIndex(final BiFunction<? super V, ? super Integer, ? extends U> mapper) {
        ParameterAssert.notNull(mapper, "mapper");
        return valueSet().zipWithIndex(mapper);
    }

}
