package de.befrish.testdatamt.data.model;

import de.befrish.testdatamt.test.util.ObjectAssert;
import de.befrish.testdatamt.test.util.ParameterAssertions;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.NotEmpty;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Benno Müller
 */
class PropertyNameTest {

    // TODO test all allowed and not allowed characters
    // TODO create Arbitraries

    @Test
    void testConstructor_null() {
        ParameterAssertions.assertThrowsParameterNullException("name", () -> new PropertyName(null));
    }

    @Test
    void testConstructor_blank() {
        ParameterAssertions.assertThrowsParameterBlankException("name", () -> new PropertyName(""));
    }

    @Property
    void testGetName(@ForAll @NotEmpty @AlphaChars final String name) {
        assertThat(new PropertyName(name).getName(), is(name.toLowerCase()));
    }

    @Property
    void testEquals(@ForAll @NotEmpty final String name) {
        ObjectAssert.assertEquals(
                new PropertyName(name),
                new PropertyName(name),
                new PropertyName(name + "x"),
                name,
                new Object()
        );
    }

    @Property
    void testHashCode(@ForAll @NotEmpty @AlphaChars final String name) {
        ObjectAssert.assertHashCode(
                new PropertyName(name),
                new PropertyName(name),
                new PropertyName(name + "x"),
                name,
                new Object()
        );
    }

    @Property
    void testToString(@ForAll @NotEmpty @AlphaChars final String name) {
        assertThat(new PropertyName(name).toString(), is(name.toLowerCase()));
    }

}