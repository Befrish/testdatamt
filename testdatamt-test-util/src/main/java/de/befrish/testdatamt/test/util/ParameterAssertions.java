package de.befrish.testdatamt.test.util;

import org.hamcrest.MatcherAssert;
import org.junit.function.ThrowingRunnable;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThrows;

/**
 * @see de.befrish.testdatamt.util.ParameterAssert
 *
 * @author Benno Müller
 */
public final class ParameterAssertions {

    private ParameterAssertions() {
        super();
    }

    public static final void assertThrowsParameterNullException(final String parameterName, final ThrowingRunnable runnable) {
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, runnable);

        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString(parameterName));
        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("must not be null"));
    }

    public static final void assertThrowsParameterBlankException(final String parameterName, final ThrowingRunnable runnable) {
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, runnable);

        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString(parameterName));
        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("must not be blank"));
    }

    public static final void assertThrowsParameterEmptyException(final String parameterName, final ThrowingRunnable runnable) {
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, runnable);

        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString(parameterName));
        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("must not be empty"));
    }

}
