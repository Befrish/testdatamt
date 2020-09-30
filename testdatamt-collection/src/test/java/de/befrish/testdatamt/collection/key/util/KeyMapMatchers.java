package de.befrish.testdatamt.collection.key.util;

import de.befrish.testdatamt.collection.key.HasKey;
import de.befrish.testdatamt.collection.key.map.KeyMap;
import io.vavr.collection.List;
import io.vavr.collection.Traversable;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * @author Benno Müller
 */
public final class KeyMapMatchers {

    private KeyMapMatchers() {
        super();
    }

    public static <K, V extends HasKey<K>> Matcher<KeyMap<K, V>> hasValueWithKey(final K key) {
        return new TypeSafeDiagnosingMatcher<>() {
            @Override
            protected boolean matchesSafely(final KeyMap<K, V> item, final Description mismatchDescription) {
                mismatchDescription.appendValue(item);
                return item.containsKey(key);
            }

            @Override
            public void describeTo(final Description description) {
                description
                        .appendText(KeyMap.class.getSimpleName() + " containing item with key ")
                        .appendValue(key);
            }
        };
    }

    public static <K, V extends HasKey<K>> Matcher<KeyMap<K, V>> containsValuesWithKeys(final K... key) {
        return new TypeSafeDiagnosingMatcher<>() {
            @Override
            protected boolean matchesSafely(final KeyMap<K, V> item, final Description mismatchDescription) {
                mismatchDescription.appendValue(item);
                return Matchers.contains(List.of(key).map(HasKeyMatchers::hasKey)).matches(item);
            }

            @Override
            public void describeTo(final Description description) {
                description
                        .appendText(KeyMap.class.getSimpleName() + " containing items with keys ")
                        .appendValue(key);
            }
        };
    }

    public static <V> Matcher<Traversable<V>> hasSize(final int size) {
        return new TypeSafeDiagnosingMatcher<>() {
            @Override
            protected boolean matchesSafely(final Traversable<V> item, final Description mismatchDescription) {
                mismatchDescription.appendValue(item);
                return item.size() == size;
            }

            @Override
            public void describeTo(final Description description) {
                description
                        .appendText(Traversable.class.getSimpleName() + " with size ")
                        .appendValue(size);
            }
        };
    }

}
