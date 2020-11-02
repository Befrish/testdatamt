package de.befrish.testdatamt.test.util;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.function.Executable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @see de.befrish.testdatamt.util.ParameterAssert
 *
 * @author Benno Müller
 */
public final class ParameterAssertions {

    private ParameterAssertions() {
        super();
    }

    public static final void assertThrowsParameterNullException(final String parameterName, final Executable executable) {
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, executable);

        assertThat(illegalArgumentException.getMessage(), containsString(parameterName));
        assertThat(illegalArgumentException.getMessage(), containsString("must not be null"));
    }

    public static final void assertThrowsParameterBlankException(final String parameterName, final Executable executable) {
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, executable);

        assertThat(illegalArgumentException.getMessage(), containsString(parameterName));
        assertThat(illegalArgumentException.getMessage(), containsString("must not be blank"));
    }

    public static final void assertThrowsParameterEmptyException(final String parameterName, final Executable executable) {
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, executable);

        assertThat(illegalArgumentException.getMessage(), containsString(parameterName));
        assertThat(illegalArgumentException.getMessage(), containsString("must not be empty"));
    }

}
