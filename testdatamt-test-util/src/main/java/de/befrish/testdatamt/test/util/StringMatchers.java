package de.befrish.testdatamt.test.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * @author Benno Müller
 */
public final class StringMatchers {

    private StringMatchers() {
        super();
    }

    public static Matcher<String> equalsRegex(final String regex) {
        return new RegexMatcher(regex);
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class RegexMatcher extends TypeSafeDiagnosingMatcher<String> {

        private final String regex;

        @Override
        protected boolean matchesSafely(final String item, final Description mismatchDescription) {
            return item.matches(this.regex);
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText(String.format("string matching regex \"%s\"", this.regex));
        }

    }

}
