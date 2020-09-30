package de.befrish.testdatamt.test.util;

import io.vavr.collection.LinkedHashSet;
import io.vavr.collection.List;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;

/**
 * @author Benno Müller
 */
public final class CollectionsMatchers {

    private CollectionsMatchers() {
        super();
    }

    public static <E> Matcher<Iterable<? extends E>> containsInOrder(final Iterable<E> items) {
        return items.iterator().hasNext()
                ? logActual(Matchers.contains(List.ofAll(items).map(Matchers::equalTo).toJavaArray(Matcher[]::new)))
                : Matchers.emptyIterable();
    }

    public static <E> Matcher<Iterable<? extends E>> containsInOrderWith(final Iterable<Matcher<E>> itemMatchers) {
        return itemMatchers.iterator().hasNext()
                ? logActual(Matchers.contains(List.ofAll(itemMatchers).toJavaArray(Matcher[]::new)))
                : Matchers.emptyIterable();
    }

    public static <E> Matcher<Iterable<E>> hasItems(final Iterable<E> items) {
        return Matchers.hasItems(List.ofAll(items).map(Matchers::equalTo).toJavaArray(Matcher[]::new));
    }


    public static <E> Matcher<Iterable<? extends E>> logActual(final Matcher<Iterable<? extends E>> matcher) {
        return new TypeSafeDiagnosingMatcher<>() {
            @Override
            protected boolean matchesSafely(final Iterable<? extends E> actual, final Description mismatchDescription) {
                matcher.describeMismatch(actual, mismatchDescription);
                mismatchDescription
                        .appendText(" - Iterable=")
                        .appendValue(actual);
                return matcher.matches(actual);
            }

            @Override
            public void describeTo(final Description description) {
                matcher.describeTo(description);
            }
        };
    }

}
