package de.befrish.testdatamt.util;

import io.vavr.collection.List;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThrows;

/**
 * @author Benno Müller
 */
public class AssertTest {

    @Test
    public void notNull_nonNull() {
        Assert.notNull("not null", "parameter42");
        // no exception
    }

    @Test
    public void notNull_null() {
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> Assert.notNull(null, "parameter42"));

        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("parameter42"));
        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("must not be null"));
    }

    @Test
    public void notEmpty_nonEmptyJavaCollection() {
        Assert.notEmpty(Arrays.asList(1, 2, 5), "parameter42");
        // no exception
    }

    @Test
    public void notEmpty_emptyJavaCollection() {
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> Assert.notEmpty(Collections.emptyList(), "parameter42"));

        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("parameter42"));
        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("must not be empty"));
    }

    @Test
    public void notEmpty_nonEmptyVavrTraversable() {
        Assert.notEmpty(io.vavr.collection.List.of(1, 2, 5), "parameter42");
        // no exception
    }

    @Test
    public void notEmpty_emptyVavrTraversable() {
        final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> Assert.notEmpty(io.vavr.collection.List.empty(), "parameter42"));

        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("parameter42"));
        MatcherAssert.assertThat(illegalArgumentException.getMessage(), containsString("must not be empty"));
    }

}
