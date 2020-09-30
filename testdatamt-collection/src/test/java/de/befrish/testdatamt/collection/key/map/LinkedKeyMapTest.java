package de.befrish.testdatamt.collection.key.map;

import de.befrish.testdatamt.collection.key.AbstractHasKey;
import de.befrish.testdatamt.collection.key.HasKey;
import de.befrish.testdatamt.collection.key.domain.StringKeyObject;
import de.befrish.testdatamt.test.util.CollectionsMatchers;
import io.vavr.PartialFunction;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashSet;
import io.vavr.collection.Iterator;
import io.vavr.collection.LinkedHashSet;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.collection.Traversable;
import io.vavr.control.Option;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.From;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Negative;
import net.jqwik.api.constraints.NotEmpty;
import net.jqwik.api.constraints.Positive;
import net.jqwik.api.constraints.Size;
import net.jqwik.api.constraints.Unique;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Stream;

import static de.befrish.testdatamt.test.util.CollectionsMatchers.containsInOrder;
import static de.befrish.testdatamt.test.util.CollectionsMatchers.containsInOrderWith;
import static de.befrish.testdatamt.test.util.CollectionsMatchers.hasItems;
import static de.befrish.testdatamt.test.util.OptionMatchers.none;
import static de.befrish.testdatamt.test.util.OptionMatchers.some;
import static de.befrish.testdatamt.test.util.ParameterAssertions.assertThrowsParameterNullException;
import static java.util.stream.Collectors.joining;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Benno Müller
 */
class LinkedKeyMapTest {

    @Property
    void testCollector(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = keyObjects.toJavaStream()
                .collect(LinkedKeyMap.collector());

        assertThat(linkedKeyMap, containsInOrder(keyObjects));
    }

    @Property
    void testNarrow(@ForAll final List<StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        final LinkedKeyMap<String, ? extends HasKey<String>> narrowedKeyMap = LinkedKeyMap.narrow(linkedKeyMap);

        assertThat(narrowedKeyMap, is(sameInstance(linkedKeyMap)));
    }

    @Test
    void testNarrow_null() {
        final LinkedKeyMap<String, ? extends HasKey<String>> narrowedKeyMap = LinkedKeyMap.narrow(null);

        assertThat(narrowedKeyMap, is(nullValue()));
    }

    @Property
    void testOf(@ForAll final StringKeyObject keyObject) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.of(keyObject);

        assertThat(linkedKeyMap, contains(keyObject));
    }

    @Test
    void testOf_null() {
        assertThrowsParameterNullException("element", () -> LinkedKeyMap.of((StringKeyObject) null));
    }

    @Property
    void testOfArray(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .of(keyObjects.toJavaArray(StringKeyObject[]::new));

        assertThat(linkedKeyMap, containsInOrder(keyObjects));
    }

    @Test
    void testOfArray_null() {
        assertThrowsParameterNullException("elements", () -> LinkedKeyMap.of((StringKeyObject[]) null));
    }

    @Property
    void testOfAllIterable(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap, containsInOrder(keyObjects));
    }

    @Test
    void testOfAllIterable_null() {
        assertThrowsParameterNullException("elements",
                () -> LinkedKeyMap.ofAll((Iterable<? extends StringKeyObject>) null));
    }

    @Property
    void testOfAllStream(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects.toJavaStream());

        assertThat(linkedKeyMap, containsInOrder(keyObjects));
    }

    @Test
    void testOfAllStream_null() {
        assertThrowsParameterNullException("javaStream",
                () -> LinkedKeyMap.ofAll((Stream<? extends StringKeyObject>) null));
    }

    @Property
    void testContainsKey_containing(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.append(keyObject).distinct());

        assertThat(linkedKeyMap.containsKey(keyObject.getKey()), is(true));
    }

    @Property
    void testContainsKey_notContaining(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.remove(keyObject));

        assertThat(linkedKeyMap.containsKey(keyObject.getKey()), is(false));
    }

    @Property
    void testContainsKey_null(@ForAll final List<StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.containsKey(null), is(false));
    }

    @Property
    void testGet_containing(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.append(keyObject).distinct());

        assertThat(linkedKeyMap.get(keyObject.getKey()), is(some(keyObject)));
    }

    @Property
    void testGet_notContaining(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.remove(keyObject));

        assertThat(linkedKeyMap.get(keyObject.getKey()), is(none()));
    }

    @Property
    void testGet_null(@ForAll final List<StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.get(null), is(none()));
    }

    @Property
    void testGetOrElse_containing(
            @ForAll final StringKeyObject keyObject,
            @ForAll final StringKeyObject defaultKeyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.append(keyObject).distinct());

        assertThat(linkedKeyMap.getOrElse(keyObject.getKey(), defaultKeyObject), is(keyObject));
    }

    @Property
    void testGetOrElse_notContaining(
            @ForAll final StringKeyObject keyObject,
            @ForAll final StringKeyObject defaultKeyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.remove(keyObject));

        assertThat(linkedKeyMap.getOrElse(keyObject.getKey(), defaultKeyObject), is(defaultKeyObject));
    }

    @Property
    void testGetOrElse_null(
            @ForAll final StringKeyObject defaultKeyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.getOrElse(null, defaultKeyObject), is(defaultKeyObject));
    }

    @Property
    void testGetOrElse_notContaining_nullDefault(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.remove(keyObject));

        assertThat(linkedKeyMap.getOrElse(keyObject.getKey(), null), is(nullValue()));
    }

    @Property
    void testKeySet(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.keySet(), containsInOrder(keyObjects.map(HasKey::getKey)));
    }

    @Property
    void testValueSet(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.valueSet(), containsInOrder(keyObjects));
    }

    @Property
    void testRemoveByKey_containing(
            @ForAll final StringKeyObject keyObjectToRemove,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final List<StringKeyObject> allKeyObjects = keyObjects
                .append(keyObjectToRemove).distinct();
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(allKeyObjects);

        assertThat(linkedKeyMap.removeByKey(keyObjectToRemove.getKey()), is(not(hasItem(keyObjectToRemove))));
        assertThat(linkedKeyMap, containsInOrder(allKeyObjects)); // not changed - immutable
    }

    @Property
    void testRemoveByKey_notContaining(
            @ForAll final StringKeyObject keyObjectToRemove,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final List<StringKeyObject> allKeyObjects = keyObjects.remove(keyObjectToRemove);
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(allKeyObjects);

        assertThat(linkedKeyMap.removeByKey(keyObjectToRemove.getKey()), is(not(hasItem(keyObjectToRemove))));
        assertThat(linkedKeyMap, containsInOrder(allKeyObjects)); // not changed
    }

    @Property
    void testRemoveByKey_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.removeByKey(null), containsInOrder(keyObjects));
    }

    @Property
    void testRemoveAllByKeys_containing(
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjectsToRemove,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final List<StringKeyObject> allKeyObjects = keyObjects.appendAll(keyObjectsToRemove).distinct();
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(allKeyObjects);

        assertThat(linkedKeyMap.removeAllByKeys(keyObjectsToRemove.map(HasKey::getKey)),
                not(hasItems(keyObjectsToRemove)));
        assertThat(linkedKeyMap, containsInOrder(allKeyObjects)); // not changed - immutable
    }

    @Property
    void testRemoveAllByKeys_notContaining(
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjectsToRemove,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final List<StringKeyObject> allKeyObjects = keyObjects.removeAll(keyObjectsToRemove);
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(allKeyObjects);

        assertThat(linkedKeyMap.removeAllByKeys(keyObjectsToRemove.map(HasKey::getKey)), not(hasItems(keyObjectsToRemove)));
        assertThat(linkedKeyMap, containsInOrder(allKeyObjects)); // not changed
    }

    @Property
    void testRemoveAllByKeys_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.removeAllByKeys(null), containsInOrder(keyObjects));
    }

    @Property
    void testToJavaMap(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.toJavaMap().keySet(), containsInOrder(keyObjects.map(HasKey::getKey)));
        assertThat(linkedKeyMap.toJavaMap().values(), containsInOrder(keyObjects));
    }

    @Property
    void testAdd_notContaining(
            @ForAll final StringKeyObject keyObjectToAdd,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final List<StringKeyObject> allKeyObjects = keyObjects.remove(keyObjectToAdd);
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(allKeyObjects);

        assertThat(linkedKeyMap.add(keyObjectToAdd), hasItem(keyObjectToAdd));
        assertThat(linkedKeyMap, containsInOrder(allKeyObjects)); // not changed - immutable
    }

    @Property
    void testAdd_containing(
            @ForAll final StringKeyObject keyObjectToAdd,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final List<StringKeyObject> allKeyObjects = keyObjects.prepend(keyObjectToAdd).distinct();
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(allKeyObjects);

        assertThat(linkedKeyMap.add(keyObjectToAdd), hasItem(keyObjectToAdd));
        assertThat(linkedKeyMap.add(keyObjectToAdd), containsInOrder(allKeyObjects)); // not changed
    }

    @Property
    void testAdd_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);
        assertThrowsParameterNullException("element", () -> linkedKeyMap.add(null));
    }

    @Property
    void testAddAll_notContaining(
            @ForAll final List<@Unique StringKeyObject> keyObjectsToAdd,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final List<StringKeyObject> allKeyObjects = keyObjects.removeAll(keyObjectsToAdd);
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(allKeyObjects);

        assertThat(linkedKeyMap.addAll(keyObjectsToAdd), hasItems(keyObjectsToAdd));
        assertThat(linkedKeyMap, containsInOrder(allKeyObjects)); // not changed - immutable
    }

    @Property
    void testAddAll_containing(
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjectsToAdd,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final List<StringKeyObject> allKeyObjects = keyObjects.prependAll(keyObjectsToAdd).distinct();
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(allKeyObjects);

        final LinkedKeyMap<String, StringKeyObject> keyObjectsResult = linkedKeyMap.addAll(keyObjectsToAdd);
        assertThat(keyObjectsResult, hasItems(keyObjectsToAdd));
        assertThat(keyObjectsResult, containsInOrder(allKeyObjects)); // not changed
    }

    @Property
    void testAddAll_empty(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.addAll(List.empty()), containsInOrder(keyObjects)); // not changed
    }

    @Property
    void testAddAll_null(@ForAll final List<StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("element", () -> linkedKeyMap.addAll(null));
    }

    @Property
    void testApply_containing(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.append(keyObject).distinct());

        assertThat(linkedKeyMap.apply(keyObject), is(true));
    }

    @Property
    void testApply_notContaining(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.remove(keyObject));

        assertThat(linkedKeyMap.apply(keyObject), is(false));
    }

    @Property
    void testApply_null(@ForAll final List<StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.apply(null), is(false));
    }

    @Property
    void testDiff(
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjectsToDiff,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.diff(HashSet.ofAll(keyObjectsToDiff)), not(hasItems(keyObjectsToDiff)));
    }

    @Property
    void testDiff_empty(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.diff(HashSet.ofAll(List.empty())), containsInOrder(keyObjects)); // not changed
    }

    @Property
    void testDiff_null(@ForAll final List<StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);
        assertThrowsParameterNullException("elements", () -> linkedKeyMap.diff(null));
    }

    @Property
    void testIntersect(
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjectsToIntersect,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        final Set<StringKeyObject> intersectionKeyObjects = LinkedHashSet.ofAll(keyObjects)
                .intersect(HashSet.ofAll(keyObjectsToIntersect));
        assertThat(linkedKeyMap.intersect(HashSet.ofAll(keyObjectsToIntersect)), containsInOrder(intersectionKeyObjects));
    }

    @Property
    void testIntersect_empty(@ForAll final List<StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.intersect(HashSet.ofAll(List.empty())), is(emptyIterable()));
    }

    @Property
    void testIntersect_null(@ForAll final List<StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("elements", () -> linkedKeyMap.intersect(null));
    }

    @Property
    void testRemove_containing(
            @ForAll final StringKeyObject keyObjectToRemove,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final List<StringKeyObject> allKeyObjects = keyObjects.append(keyObjectToRemove).distinct();
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(allKeyObjects);

        assertThat(linkedKeyMap.remove(keyObjectToRemove), is(not(hasItem(keyObjectToRemove))));
        assertThat(linkedKeyMap, containsInOrder(allKeyObjects)); // not changed - immutable
    }

    @Property
    void testRemove_notContaining(
            @ForAll final StringKeyObject keyObjectToRemove,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final List<StringKeyObject> allKeyObjects = keyObjects.remove(keyObjectToRemove);
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(allKeyObjects);

        assertThat(linkedKeyMap.remove(keyObjectToRemove), is(not(hasItem(keyObjectToRemove))));
        assertThat(linkedKeyMap, containsInOrder(allKeyObjects)); // not changed
    }

    @Property
    void testRemove_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.remove(null), containsInOrder(keyObjects));
    }

    @Property
    void testRemoveAll(
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjectsToRemove,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.removeAll(HashSet.ofAll(keyObjectsToRemove)), not(hasItems(keyObjectsToRemove)));
    }

    @Property
    void testRemoveAll_empty(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.removeAll(List.empty()), containsInOrder(keyObjects)); // not changed
    }

    @Property
    void testRemoveAll_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);
        assertThrowsParameterNullException("elements", () -> linkedKeyMap.removeAll(null));
    }

    @Property
    void testToJavaSet(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.toJavaMap().keySet(), containsInOrder(keyObjects.map(HasKey::getKey)));
        assertThat(linkedKeyMap.toJavaSet(), containsInOrder(keyObjects));
    }

    @Property
    void testUnion(
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjectsToUnion,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        final Set<StringKeyObject> unionKeyObjects = LinkedHashSet.ofAll(keyObjects)
                .union(LinkedHashSet.ofAll(keyObjectsToUnion));
        assertThat(linkedKeyMap.union(LinkedHashSet.ofAll(keyObjectsToUnion)), containsInOrder(unionKeyObjects));
    }

    @Property
    void testUnion_empty(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.union(LinkedHashSet.ofAll(LinkedHashSet.empty())), containsInOrder(keyObjects));
    }

    @Property
    void testUnion_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("elements", () -> linkedKeyMap.union(null));
    }

    @Property
    void testCollect(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        final PartialFunction<StringKeyObject, Integer> toIndex = new PartialFunction<>() {
            private static final long serialVersionUID = -5442752620964600628L;

            @Override
            public Integer apply(final StringKeyObject keyObject) {
                return keyObjects.indexOf(keyObject);
            }

            @Override
            public boolean isDefinedAt(final StringKeyObject keyObject) {
                return keyObjects.contains(keyObject);
            }
        };

        assertThat(linkedKeyMap.collect(toIndex), containsInOrder(List.range(0, keyObjects.size())));
    }

    @Property
    void testCollect_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("partialFunction",
                () -> linkedKeyMap.collect((PartialFunction<StringKeyObject, Integer>) null));
    }

    @Property
    void testContains_containing(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.append(keyObject));

        assertThat(linkedKeyMap.contains(keyObject), is(true));
    }

    @Property
    void testContains_notContaining(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.remove(keyObject));

        assertThat(linkedKeyMap.contains(keyObject), is(false));
    }

    @Property
    void testContains_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.contains(null), is(false));
    }

    @Property
    void testDistinct(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.distinct(), is(containsInOrder(keyObjects)));
    }

    @Property
    void testDistinctByComparator(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.distinctBy(Comparator.comparing(HasKey::getKey)), containsInOrder(keyObjects.distinct()));
    }

    @Property
    void testDistinctByComparator_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("comparator",
                () -> linkedKeyMap.distinctBy((Comparator<? super StringKeyObject>) null));
    }

    @Property
    void testDistinctByKeyExtractor(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.distinctBy(HasKey::getKey), containsInOrder(keyObjects.distinct()));
    }

    @Property
    void testDistinctByKeyExtractor_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("keyExtractor",
                () -> linkedKeyMap.distinctBy((Function<? super StringKeyObject, ?>) null));
    }

    @Property
    void testDrop(
            @ForAll @Positive final int n,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.drop(n), containsInOrder(keyObjects.drop(n)));
    }

    @Property
    void testDrop_negativeOrZero(
            @ForAll @IntRange(min = Integer.MIN_VALUE, max = 0) final int n,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.drop(n), containsInOrder(keyObjects));
    }

    @Property
    void testDropRight(
            @ForAll @Positive final int n,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.dropRight(n), containsInOrder(keyObjects.dropRight(n)));
    }

    @Property
    void testDropRight_negativeOrZero(
            @ForAll @IntRange(min = Integer.MIN_VALUE, max = 0) final int n,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.dropRight(n), containsInOrder(keyObjects));
    }

    @Property
    void testDropUntil(@ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.dropUntil(keyObjects.last()::equals), contains(keyObjects.last()));
    }

    @Property
    void testDropUntil_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("predicate", () -> linkedKeyMap.dropUntil(null));
    }

    @Property
    void testDropWhile(@ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.dropWhile(keyObjects.init()::contains), contains(keyObjects.last()));
    }

    @Property
    void testDropWhile_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("predicate", () -> linkedKeyMap.dropWhile(null));
    }

    @Property
    void testFilter_containing(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.append(keyObject));

        assertThat(linkedKeyMap.filter(keyObject::equals), everyItem(is(keyObject)));
    }

    @Property
    void testFilter_notContaining(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.remove(keyObject));

        assertThat(linkedKeyMap.filter(keyObject::equals), is(emptyIterable()));
    }

    @Property
    void testFilter_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("predicate", () -> linkedKeyMap.filter(null));
    }

    @Property
    void testFlatMap(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.flatMap(HashSet::of), containsInOrder(linkedKeyMap));
    }

    @Property
    void testFlatMap_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("mapper", () -> linkedKeyMap.flatMap(null));
    }

    @Property
    void testFoldLeft(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.foldLeft(0, (sum, keyObject) -> sum + 1), is(keyObjects.size()));
    }

    @Property
    void testFoldLeft_zero_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.foldLeft(null, (result, keyObject) -> result), is(nullValue()));
    }

    @Property
    void testFoldLeft_function_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("function", () -> linkedKeyMap.foldLeft(0, null));
    }

    @Property
    void testFoldRight(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.foldRight(0, (keyObject, sum) -> sum + 1), is(keyObjects.size()));
    }

    @Property
    void testFoldRight_zero_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.foldRight(null, (keyObject, result) -> result), is(nullValue()));
    }

    @Property
    void testFoldRight_function_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("function", () -> linkedKeyMap.foldRight(0, null));
    }

    @Property
    void testHead_notEmpty(
            @ForAll final StringKeyObject headKeyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(List.of(headKeyObject).appendAll(keyObjects));

        assertThat(linkedKeyMap.head(), is(headKeyObject));
    }

    @Property
    void testHead_empty() {
        final NoSuchElementException noSuchElementException = assertThrows(NoSuchElementException.class,
                () -> LinkedKeyMap.empty().head());

        assertThat(noSuchElementException.getMessage(), containsString("empty"));
    }

    @Property
    void testReject_containing(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.append(keyObject));

        assertThat(linkedKeyMap.reject(keyObject::equals), everyItem(is(not(keyObject))));
    }

    @Property
    void testReject_notContaining(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.remove(keyObject));

        assertThat(linkedKeyMap.reject(keyObject::equals), containsInOrder(linkedKeyMap));
    }

    @Property
    void testReject_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("predicate", () -> linkedKeyMap.reject(null));
    }

    @Property
    void testGroupBy(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        final Map<String, ? extends LinkedKeyMap<String, StringKeyObject>> resultMap = linkedKeyMap.groupBy(HasKey::getKey);

        assertThat(resultMap.keySet(), containsInOrder(keyObjects.map(HasKey::getKey)));
        assertThat(resultMap.values(), containsInOrder(keyObjects.map(LinkedKeyMap::of)));
    }

    @Property
    void testGroupBy_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("classifier", () -> linkedKeyMap.groupBy(null));
    }

    @Property
    void testGrouped_notEmpty(
            @ForAll @IntRange(min = 1, max = 1024)  final int size,
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        final List<? extends KeyMap<String, StringKeyObject>> result = linkedKeyMap.grouped(size).toList();

        assertThat(result.init(), everyItem(iterableWithSize(size)));
        if (keyObjects.size() % size != 0) {
            assertThat(result.last(), is(iterableWithSize(keyObjects.size() % size)));
        }
    }

    @Property
    void testGrouped_empty(@ForAll @IntRange(min = 1, max = 1024) final int size) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.empty();

        final List<? extends KeyMap<String, StringKeyObject>> result = linkedKeyMap.grouped(size).toList();

        assertThat(result, emptyIterable());
    }

    @Property
    void testHasDefiniteSize(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.hasDefiniteSize(), is(true));
    }

    @Property
    void testInit_notEmpty(
            @ForAll final StringKeyObject lastKeyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final List<StringKeyObject> keyObjectsWithoutLast = keyObjects.remove(lastKeyObject);
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjectsWithoutLast.append(lastKeyObject));

        assertThat(linkedKeyMap.init(), containsInOrder(keyObjectsWithoutLast));
    }

    @Property
    void testInit_empty() {
        final UnsupportedOperationException unsupportedOperationException = assertThrows(
                UnsupportedOperationException.class,
                () -> LinkedKeyMap.empty().init());

        assertThat(unsupportedOperationException.getMessage(), containsString("empty"));
    }

    @Property
    void testInitOption_notEmpty(
            @ForAll final StringKeyObject lastKeyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final List<StringKeyObject> keyObjectsWithoutLast = keyObjects.remove(lastKeyObject);
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjectsWithoutLast.append(lastKeyObject));

        final Option<? extends KeyMap<String, StringKeyObject>> linkedKeyMapOption = linkedKeyMap.initOption();
        assertThat(linkedKeyMapOption.isDefined(), is(true));
        assertThat(linkedKeyMapOption.get(), containsInOrder(keyObjectsWithoutLast));
    }

    @Property
    void testInitOption_empty() {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.empty();

        assertThat(linkedKeyMap.initOption(), is(Option.none()));
    }

    @Property
    void testIsDistinct(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.isDistinct(), is(true));
    }

    @Property
    void testIsTraversableAgain(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.isTraversableAgain(), is(true));
    }

    @Property
    void testIterator(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.iterator(), is(iterableWithSize(keyObjects.size())));
    }

    @Property
    void testMap(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.map(HasKey::getKey), containsInOrder(linkedKeyMap.keySet()));
    }

    @Property
    void testMap_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("mapper", () -> linkedKeyMap.map(null));
    }

    @Property
    void testLast_notEmpty(
            @ForAll final StringKeyObject lastKeyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.remove(lastKeyObject).append(lastKeyObject));

        assertThat(linkedKeyMap.last(), is(lastKeyObject));
    }

    @Property
    void testLast_empty() {
        final NoSuchElementException noSuchElementException = assertThrows(NoSuchElementException.class,
                () -> LinkedKeyMap.empty().last());

        assertThat(noSuchElementException.getMessage(), containsString("empty"));
    }

    @Property
    void testLength(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.length(), is(keyObjects.size()));
    }

    @Property
    void testOrElse_notEmpty(
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjectsElse,
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.orElse(keyObjectsElse), containsInOrder(keyObjects));
    }

    @Property
    void testOrElse_empty(
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjectsElse) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.empty();

        assertThat(linkedKeyMap.orElse(keyObjectsElse), containsInOrder(keyObjectsElse));
    }

    @Property
    void testOrElseSupplier_notEmpty(
            @ForAll final List<@Unique StringKeyObject> keyObjectsElse,
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.orElse(() -> keyObjectsElse), containsInOrder(keyObjects));
    }

    @Property
    void testOrElseSupplier_empty(
            @ForAll final List<@Unique StringKeyObject> keyObjectsElse) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.empty();

        assertThat(linkedKeyMap.orElse(() -> keyObjectsElse), containsInOrder(keyObjectsElse));
    }

    @Property
    void testPartition_notEmpty(
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        final var partitionedLinkedKeyMap = linkedKeyMap.partition(keyObject -> keyObject.getKey().length() == 3);
        assertThat(partitionedLinkedKeyMap._1().keySet(), everyItem(hasLength(3)));
        assertThat(partitionedLinkedKeyMap._2().keySet(), everyItem(not(hasLength(3))));
    }

    @Property
    void testPartition_empty() {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.empty();

        final var partitionedLinkedKeyMap = linkedKeyMap.partition(keyObject -> keyObject.getKey().length() == 3);

        assertThat(partitionedLinkedKeyMap._1(), is(emptyIterable()));
        assertThat(partitionedLinkedKeyMap._2(), is(emptyIterable()));
    }

    @Property
    void testPartition_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("predicate", () -> linkedKeyMap.partition(null));
    }

    @Property
    void testPeek_notEmpty(@ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        final var consumedKeyObjects = new AtomicReference<>(List.<StringKeyObject>empty());
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMapResult = linkedKeyMap
                .peek(keyObject -> consumedKeyObjects.updateAndGet(list -> list.append(keyObject)));

        assertThat(linkedKeyMapResult, containsInOrder(keyObjects));
        assertThat(consumedKeyObjects.get(), containsInOrder(keyObjects));
    }

    @Test
    void testPeek_empty() {
        LinkedKeyMap.empty().peek(keyObject -> fail()); // fail() should not be executed
    }

    @Property
    void testReplace_containing(
            @ForAll final StringKeyObject keyObject,
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.append(keyObject));

        final LinkedKeyMap<String, StringKeyObject> linkedKeyMapResult = linkedKeyMap
                .replace(keyObject, new StringKeyObject(keyObject.getKey()));

        assertThat(linkedKeyMapResult, hasItem(keyObject));
    }

    @Property
    void testReplace_notContaining(
            @ForAll final StringKeyObject keyObject,
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.remove(keyObject));

        final LinkedKeyMap<String, StringKeyObject> linkedKeyMapResult = linkedKeyMap
                .replace(keyObject, new StringKeyObject(keyObject.getKey()));

        assertThat(linkedKeyMapResult, not(hasItem(keyObject)));
    }

    @Property
    void testReplace_currentElement_null(
            @ForAll final StringKeyObject keyObject,
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("currentElement", () -> linkedKeyMap.replace(null, keyObject));
    }

    @Property
    void testReplace_newElement_null(
            @ForAll final StringKeyObject keyObject,
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("newElement", () -> linkedKeyMap.replace(keyObject, null));
    }

    @Property
    void testReplace_different_keys(
            @ForAll final StringKeyObject keyObject,
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> linkedKeyMap.replace(keyObject, new StringKeyObject(keyObject.getKey() + "x")));

        assertThat(illegalArgumentException.getMessage(), containsString("cannot replace values with different keys"));
    }

    @Property
    void testReplaceAll_containing(
            @ForAll final StringKeyObject keyObject,
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.append(keyObject));

        final KeyMap<String, StringKeyObject> linkedKeyMapResult = linkedKeyMap
                .replaceAll(keyObject, new StringKeyObject(keyObject.getKey()));

        assertThat(linkedKeyMapResult, hasItem(keyObject));
    }

    @Property
    void testReplaceAll_notContaining(
            @ForAll final StringKeyObject keyObject,
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(keyObjects.remove(keyObject));

        final KeyMap<String, StringKeyObject> linkedKeyMapResult = linkedKeyMap
                .replaceAll(keyObject, new StringKeyObject(keyObject.getKey()));

        assertThat(linkedKeyMapResult, not(hasItem(keyObject)));
    }

    @Property
    void testReplaceAll_currentElement_null(
            @ForAll final StringKeyObject keyObject,
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("currentElement", () -> linkedKeyMap.replaceAll(null, keyObject));
    }

    @Property
    void testReplaceAll_newElement_null(
            @ForAll final StringKeyObject keyObject,
            @ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("newElement", () -> linkedKeyMap.replaceAll(keyObject, null));
    }

    @Property
    void testRetainAll(
            @ForAll final List<@Unique StringKeyObject> keyObjectToRetain,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.retainAll(keyObjectToRetain), everyItem(is(in(keyObjectToRetain.toJavaSet()))));
    }

    @Property
    void testRetainAll_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("elements", () -> linkedKeyMap.retainAll(null));
    }

    @Property
    void testScan(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);
        final StringKeyObject zero = new StringKeyObject("foobar");

        assertThat(linkedKeyMap.scan(zero, (result, keyObject) -> keyObject), is(LinkedKeyMap.ofAll(keyObjects.prepend(zero))));
    }

    @Property
    void testScan_zero_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("zero", () -> linkedKeyMap.scan(null, (result, keyObject) -> keyObject));
    }

    @Property
    void testScan_operation_null(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("operation", () -> linkedKeyMap.scan(keyObject, null));
    }

    @Property
    void testScanLeft(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.scanLeft(0, (sum, keyObject) -> sum + 1),
                containsInOrder(List.rangeClosed(0, keyObjects.size())));
    }

    @Property
    void testScanLeft_zero_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
            final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.scanLeft(null, (result, keyObject) -> result), everyItem(is(nullValue())));
    }

    @Property
    void testScanLeft_operation_null(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("operation", () -> linkedKeyMap.scanLeft(keyObject, null));
    }

    @Property
    void testScanRight(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.scanRight(0, (keyObject, sum) -> sum + 1),
                containsInOrder(List.rangeClosed(0, keyObjects.size()).reverse()));
    }

    @Property
    void testScanRight_zero_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.scanRight(null, (keyObject, result) -> result), everyItem(is(nullValue())));
    }

    @Property
    void testScanRight_operation_null(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("operation", () -> linkedKeyMap.scan(keyObject, null));
    }

    @Property
    void testSlideBy(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.slideBy(keyObject -> keyObject.getKey().length()), everyItem(hasSameKeyLength()));
    }

    private static Matcher<Traversable<StringKeyObject>> hasSameKeyLength() {
        return new TypeSafeDiagnosingMatcher<>() {
            @Override
            protected boolean matchesSafely(final Traversable<StringKeyObject> traversable, final Description mismatchDescription) {
                final List<Integer> stringLengths = List.ofAll(traversable).map(HasKey::getKey).map(String::length);
                mismatchDescription.appendText("Traversable of StringKeyObject with string lengths")
                        .appendValue(stringLengths);
                return stringLengths.isEmpty() || stringLengths.distinct().size() == 1;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("Traversable of StringKeyObject with same string length of key");
            }
        };
    }

    @Property
    void testSlideBy_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("classifier", () -> linkedKeyMap.slideBy(null));
    }

    @Property
    void testSliding_size(
            @ForAll @IntRange(min = 1, max = 1024) final int size,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        final List<? extends KeyMap<String, StringKeyObject>> result = linkedKeyMap.sliding(size).toList();

        assertThat(result, containsInOrderWith(keyObjects.sliding(size).map(CollectionsMatchers::containsInOrder)));
    }

    @Property
    void testSliding_size_negative(@ForAll @Negative final int size) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.empty();

        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> linkedKeyMap.sliding(size).toList());

        assertThat(illegalArgumentException.getMessage(), containsString("size"));
        assertThat(illegalArgumentException.getMessage(), containsString("be positive"));
    }

    @Property
    void testSliding_size_step_notEmpty(
            @ForAll @IntRange(min = 1, max = 1024) final int size,
            @ForAll @IntRange(min = 1, max = 1024) final int step,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        final List<? extends KeyMap<String, StringKeyObject>> result = linkedKeyMap.sliding(size, step).toList();

        assertThat(result, containsInOrderWith(keyObjects.sliding(size, step).map(CollectionsMatchers::containsInOrder)));
    }

    @Property
    void testSliding_size_step_negative_size(
            @ForAll @Negative final int size,
            @ForAll @IntRange(min = 1, max = 1024) final int step) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.empty();

        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> linkedKeyMap.sliding(size, step).toList());

        assertThat(illegalArgumentException.getMessage(), containsString("size"));
        assertThat(illegalArgumentException.getMessage(), containsString("be positive"));
    }

    @Property
    void testSliding_size_step_negative_step(
            @ForAll @IntRange(min = 1, max = 1024) final int size,
            @ForAll @Negative final int step) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.empty();

        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> linkedKeyMap.sliding(size, step).toList());

        assertThat(illegalArgumentException.getMessage(), containsString("step"));
        assertThat(illegalArgumentException.getMessage(), containsString("be positive"));
    }

    @Property
    void testSpan_notEmpty(@ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        final var result = linkedKeyMap.span(keyObjects.init()::contains);

        assertThat(result._1(), is(containsInOrder(keyObjects.init())));
        assertThat(result._2(), is(containsInOrder(List.of(keyObjects.last()))));
    }

    @Test
    void testSpan_empty() {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.empty();

        final var result = linkedKeyMap.span(keyObject -> keyObject.getKey().isEmpty());

        assertThat(result._1(), is(emptyIterable()));
        assertThat(result._2(), is(emptyIterable()));
    }

    @Property
    void testSpan_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("predicate", () -> linkedKeyMap.span(null));
    }

    @Property
    void testTail_notEmpty(
            @ForAll final StringKeyObject headKeyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final List<StringKeyObject> keyObjectsWithoutHead = keyObjects.remove(headKeyObject);
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(List.of(headKeyObject).appendAll(keyObjectsWithoutHead));

        assertThat(linkedKeyMap.tail(), containsInOrder(keyObjectsWithoutHead));
    }

    @Property
    void testTail_empty() {
        final UnsupportedOperationException unsupportedOperationException = assertThrows(UnsupportedOperationException.class,
                () -> LinkedKeyMap.empty().tail());

        assertThat(unsupportedOperationException.getMessage(), containsString("empty"));
    }

    @Property
    void testTailOption_notEmpty(
            @ForAll final StringKeyObject headKeyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final List<StringKeyObject> keyObjectsWithoutHead = keyObjects.remove(headKeyObject);
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap
                .ofAll(List.of(headKeyObject).appendAll(keyObjectsWithoutHead));

        final Option<? extends KeyMap<String, StringKeyObject>> linkedKeyMapOption = linkedKeyMap.tailOption();
        assertThat(linkedKeyMapOption.isDefined(), is(true));
        assertThat(linkedKeyMapOption.get(), containsInOrder(keyObjectsWithoutHead));
    }

    @Property
    void testTailOption_empty() {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.empty();

        assertThat(linkedKeyMap.tailOption(), is(Option.none()));
    }

    @Property
    void testTake(
            @ForAll @Positive final int n,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.take(n), containsInOrder(keyObjects.take(n)));
    }

    @Property
    void testTake_negativeOrZero(
            @ForAll @IntRange(min = Integer.MIN_VALUE, max = 0) final int n,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.take(n), is(emptyIterable()));
    }

    @Property
    void testTakeRight(
            @ForAll @Positive final int n,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.takeRight(n), containsInOrder(keyObjects.takeRight(n)));
    }

    @Property
    void testTakeRight_negativeOrZero(
            @ForAll @IntRange(min = Integer.MIN_VALUE, max = 0) final int n,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.takeRight(n), is(emptyIterable()));
    }

    @Property
    void testTakeUntil_notEmpty(@ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.takeUntil(keyObjects.last()::equals), containsInOrder(keyObjects.init()));
    }

    @Property
    void testTakeUntil_empty(@ForAll final boolean until_condition) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.empty();

        assertThat(linkedKeyMap.takeUntil(keyObject -> until_condition), is(emptyIterable()));
    }

    @Property
    void testTakeUntil_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("predicate", () -> linkedKeyMap.takeUntil(null));
    }

    @Property
    void testTakeWhile_notEmpty(@ForAll @NotEmpty final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.takeWhile(keyObjects.init()::contains), containsInOrder(keyObjects.init()));
    }

    @Property
    void testTakeWhile_empty(@ForAll final boolean while_condition) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.empty();

        assertThat(linkedKeyMap.takeWhile(keyObject -> while_condition), is(emptyIterable()));
    }

    @Property
    void testTakeWhile_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("predicate", () -> linkedKeyMap.takeWhile(null));
    }

    @Property
    void testUnzip(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        final var result = linkedKeyMap.unzip(keyObject -> Tuple.of(keyObject.getKey(), keyObject));

        assertThat(result._1(), containsInOrder(linkedKeyMap.keySet()));
        assertThat(result._2(), containsInOrder(linkedKeyMap.valueSet()));
    }

    @Property
    void testUnzip_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("unzipper", () -> linkedKeyMap.unzip(null));
    }

    @Property
    void testUnzip3(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        final var result = linkedKeyMap.unzip3(keyObject -> Tuple.of(keyObject.getKey(), keyObject, keyObject.getKey().length()));

        assertThat(result._1(), containsInOrder(linkedKeyMap.keySet()));
        assertThat(result._2(), containsInOrder(linkedKeyMap.valueSet()));
        assertThat(result._3(), containsInOrder(linkedKeyMap.keySet().map(String::length)));
    }

    @Property
    void testUnzip3_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("unzipper", () -> linkedKeyMap.unzip(null));
    }

    @Property
    void testZip(
            @ForAll @IntRange(max = 1024) final int offset,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);
        final List<Integer> toZip = List.range(offset, offset + keyObjects.size());

        final Set<Tuple2<StringKeyObject, Integer>> result = linkedKeyMap.zip(toZip);

        final Set<Tuple2<StringKeyObject, Integer>> expectedResult = linkedKeyMap.zipWithIndex()
                .map(keyObjectWithIndex -> Tuple.of(keyObjectWithIndex._1(), keyObjectWithIndex._2() + offset));
        assertThat(result, containsInOrder(expectedResult));
    }

    @Property
    void testZip_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("that", () -> linkedKeyMap.zip(null));
    }

    @Property
    void testZipWith(
            @ForAll @IntRange(max = 1024) final int offset,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);
        final List<Integer> toZip = List.range(offset, offset + keyObjects.size());

        final Set<String> result = linkedKeyMap.zipWith(toZip, (keyObject, indexWithOffset) -> keyObject.getKey() + indexWithOffset);

        final Set<String> expectedResult = linkedKeyMap.zipWithIndex()
                .map(keyObjectWithIndex -> Tuple.of(keyObjectWithIndex._1(), keyObjectWithIndex._2() + offset))
                .map(keyObjectWithIndex -> keyObjectWithIndex._1().getKey() + keyObjectWithIndex._2());
        assertThat(result, containsInOrder(expectedResult));
    }

    @Property
    void testZipWith_that_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("that", () -> linkedKeyMap.zipWith(null,
                (keyObject, indexWithOffset) -> keyObject.getKey() + indexWithOffset));
    }

    @Property
    void testZipWith_that_null(
            @ForAll @IntRange(max = 1024) final int offset,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);
        final List<Integer> toZip = List.range(offset, offset + keyObjects.size());

        assertThrowsParameterNullException("mapper", () -> linkedKeyMap.zipWith(toZip, null));
    }

    @Property
    void testZipAll(
            @ForAll @IntRange(max = 1024) final int offset,
            @ForAll @IntRange(max = 1024) final int lengthToZip,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);
        final List<Integer> toZip = List.range(offset, offset + lengthToZip);
        final StringKeyObject thisElem = new StringKeyObject("foo");
        final int thatElem = 0;

        final var result = linkedKeyMap.zipAll(toZip, thisElem, thatElem);

        final int expectedResultSize = Math.max(keyObjects.size(), lengthToZip);
        assertThat(result, is(iterableWithSize(expectedResultSize)));
        assertThat(result.toList().map(Tuple2::_1), containsInOrder(
                keyObjects.appendAll(List.fill(expectedResultSize - keyObjects.size(), thisElem))));
        assertThat(result.toList().map(Tuple2::_2), containsInOrder(
                toZip.appendAll(List.fill(expectedResultSize - lengthToZip, thatElem))));
    }

    @Property
    void testZipAll_that_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("that", () -> linkedKeyMap.zipAll(null, new StringKeyObject("foo"), 0));
    }

    @Property
    void testZipAll_thisElem_null(
            @ForAll @IntRange(max = 1024) final int offset,
            @ForAll @IntRange(max = 1024) final int lengthToZip) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.empty();
        final List<Integer> toZip = List.range(offset, offset + lengthToZip);

        final var result = linkedKeyMap.zipAll(toZip, null, 0);

        assertThat(result, is(iterableWithSize(lengthToZip)));
        assertThat(result.map(Tuple2::_1), everyItem(is(nullValue())));
    }

    @Property
    void testZipAll_thatElem_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);
        final List<Integer> toZip = List.empty();

        final var result = linkedKeyMap.zipAll(toZip, new StringKeyObject("foo"), null);

        assertThat(result, is(iterableWithSize(keyObjects.size())));
        assertThat(result.map(Tuple2::_2), everyItem(is(nullValue())));
    }

    @Property
    void testZipWithIndex(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        final var result = linkedKeyMap.zipWithIndex();

        assertThat(result, containsInOrder(keyObjects.zipWithIndex()));
    }

    @Property
    void testZipWithIndex_mapper(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        final Set<StringKeyObject> result = linkedKeyMap
                .zipWithIndex((keyObject, index) -> keyObject.equals(keyObjects.get(index)) ? keyObject : null);

        assertThat(result, containsInOrder(keyObjects));
    }

    @Property
    void testZipWithIndex_mapper_null(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThrowsParameterNullException("mapper", () -> linkedKeyMap.zipWithIndex(null));
    }

    @Property
    void testIsAsync(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.isAsync(), is(false));
    }

    @Property
    void testIsLazy(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.isLazy(), is(false));
    }

    @Property
    void testIsSequential(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.isSequential(), is(true));
    }

    @Property
    void testEquals_notEquals(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMapWithKeyObject = LinkedKeyMap
                .ofAll(keyObjects.remove(keyObject));
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMapWithoutKeyObject = LinkedKeyMap
                .ofAll(keyObjects.append(keyObject));

        assertThat(linkedKeyMapWithKeyObject.equals(linkedKeyMapWithoutKeyObject), is(false));
    }

    @Property
    void testEquals_differentInstanceSameElements(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap1 = LinkedKeyMap.ofAll(keyObjects);
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap2 = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap1.equals(linkedKeyMap2), is(true));
    }

    @Property
    void testEquals_equalInstance(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.equals(linkedKeyMap), is(true));
    }

    @Property
    void testEquals_noKeyMap(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.equals(keyObjects), is(false));
    }

    @Property
    void testHashCode_notEquals(
            @ForAll final StringKeyObject keyObject,
            @ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMapWithKeyObject = LinkedKeyMap
                .ofAll(keyObjects.remove(keyObject));
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMapWithoutKeyObject = LinkedKeyMap
                .ofAll(keyObjects.append(keyObject));

        assertThat(linkedKeyMapWithKeyObject.hashCode(), is(not(linkedKeyMapWithoutKeyObject.hashCode())));
    }

    @Property
    void testHashCode_differentInstanceSameElements(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap1 = LinkedKeyMap.ofAll(keyObjects);
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap2 = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap1.hashCode(), is(linkedKeyMap2.hashCode()));
    }

    @Property
    void testHashCode_equalInstance(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.hashCode(), is(linkedKeyMap.hashCode()));
    }

    @Property
    void testToString(@ForAll final List<@Unique StringKeyObject> keyObjects) {
        final LinkedKeyMap<String, StringKeyObject> linkedKeyMap = LinkedKeyMap.ofAll(keyObjects);

        assertThat(linkedKeyMap.toString(), is("LinkedKeyMap"
                + keyObjects.map(StringKeyObject::toString).toJavaStream().collect(joining(", ", "(", ")"))));
    }

}