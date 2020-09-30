package de.befrish.testdatamt.test.util;

import io.vavr.control.Option;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Objects;

/**
 * @author Benno Müller
 */
public final class OptionMatchers {

    private OptionMatchers() {
        super();
    }

    public static <T> Matcher<Option<T>> none() {
        return new TypeSafeDiagnosingMatcher<>() {
            @Override
            protected boolean matchesSafely(final Option<T> item, final Description mismatchDescription) {
                mismatchDescription.appendValue(item);
                return item.isEmpty();
            }

            @Override
            public void describeTo(final Description description) {
                description.appendValue(Option.none());
            }
        };
    }

    public static <T> Matcher<Option<T>> some(final T value) {
        return new TypeSafeDiagnosingMatcher<>() {
            @Override
            protected boolean matchesSafely(final Option<T> item, final Description mismatchDescription) {
                mismatchDescription.appendValue(item);
                return Objects.equals(item, Option.some(value));
            }

            @Override
            public void describeTo(final Description description) {
                description.appendValue(Option.some(value));
            }
        };
    }

    public static <T> Matcher<Option<T>> some(final Matcher<T> matcher) {
        return new TypeSafeDiagnosingMatcher<>() {
            @Override
            protected boolean matchesSafely(final Option<T> item, final Description mismatchDescription) {
                mismatchDescription.appendValue(item);
                return item.isDefined() && matcher.matches(item.get());
            }

            @Override
            public void describeTo(final Description description) {
                description
                        .appendText("Some(")
                        .appendDescriptionOf(matcher)
                        .appendText(")");
            }
        };
    }

}
