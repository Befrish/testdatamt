package de.befrish.testdatamt.util;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThrows;

/**
 * @author Benno Müller
 */
public class ParameterAssertTest {

    @Test
    public void notNull_nonNull() {
        ParameterAssert.notNull("not null", "parameter42");
        // no exception
    }

    @Test
    public void notNull_null() {
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> ParameterAssert.notNull(null, "parameter42"));

        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("parameter42"));
        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("must not be null"));
    }

    @Test
    public void notBlank_nonWhitespace() {
        ParameterAssert.notBlank("not blank", "parameter42");
        // no exception
    }

    @Test
    public void notBlank_null() {
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> ParameterAssert.notBlank(null, "parameter42"));

        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("parameter42"));
        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("must not be null"));
    }

    @Test
    public void notBlank_emptyString() {
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> ParameterAssert.notBlank("", "parameter42"));

        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("parameter42"));
        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("must not be blank"));
    }

    @Test
    public void notBlank_onlyWhitespace() {
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> ParameterAssert.notBlank(" \t\r\n", "parameter42"));

        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("parameter42"));
        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("must not be blank"));
    }

    @Test
    public void notEmpty_nonEmptyJavaCollection() {
        ParameterAssert.notEmpty(Arrays.asList(1, 2, 5), "parameter42");
        // no exception
    }

    @Test
    public void notEmpty_emptyJavaCollection() {
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> ParameterAssert.notEmpty(Collections.emptyList(), "parameter42"));

        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("parameter42"));
        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("must not be empty"));
    }

    @Test
    public void notEmpty_nonEmptyVavrTraversable() {
        ParameterAssert.notEmpty(io.vavr.collection.List.of(1, 2, 5), "parameter42");
        // no exception
    }

    @Test
    public void notEmpty_emptyVavrTraversable() {
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> ParameterAssert.notEmpty(io.vavr.collection.List.empty(), "parameter42"));

        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("parameter42"));
        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("must not be empty"));
    }

}
