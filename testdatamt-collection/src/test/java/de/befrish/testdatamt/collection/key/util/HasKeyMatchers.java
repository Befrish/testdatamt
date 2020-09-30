package de.befrish.testdatamt.collection.key.util;

import de.befrish.testdatamt.collection.key.HasKey;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Objects;

/**
 * @author Benno Müller
 */
public final class HasKeyMatchers {

    private HasKeyMatchers() {
        super();
    }

    public static <K> Matcher<? extends HasKey<K>> hasKey(final K key) {
        return new TypeSafeDiagnosingMatcher<>() {
            @Override
            protected boolean matchesSafely(final HasKey<K> item, final Description mismatchDescription) {
                mismatchDescription
                        .appendText("object with key ")
                        .appendValue(item.getKey());
                return Objects.equals(item.getKey(), key);
            }

            @Override
            public void describeTo(final Description description) {
                description
                        .appendText("object with key ")
                        .appendValue(key);
            }
        };
    }
}
