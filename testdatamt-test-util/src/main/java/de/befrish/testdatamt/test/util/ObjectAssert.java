package de.befrish.testdatamt.test.util;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Benno Müller
 */
public final class ObjectAssert {

    private ObjectAssert() {
        super();
    }

    public static void assertEquals(final Object o1_1, final Object o1_2, final Object... os2) {
        assertThat(o1_1.equals(o1_2), is(true));
//        assertThat(o1_1.equals(null), is(false));
        for (final Object o2 : os2) {
            assertThat(o1_1.equals(o2), is(false));
        }
    }

    public static void assertHashCode(final Object o1_1, final Object o1_2, final Object... os2) {
        assertThat(o1_1.hashCode(), is(o1_2.hashCode()));
        assertThat(o1_1.hashCode(), is(not(Objects.hashCode(null))));
        for (final Object o2 : os2) {
            assertThat(o1_1.hashCode(), is(not(o2.hashCode())));
        }
    }

}
