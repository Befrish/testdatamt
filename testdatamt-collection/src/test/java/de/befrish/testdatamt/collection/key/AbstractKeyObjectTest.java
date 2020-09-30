package de.befrish.testdatamt.collection.key;

import de.befrish.testdatamt.collection.key.domain.StringKeyObject;
import de.befrish.testdatamt.test.util.ObjectAssert;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Test;

import static de.befrish.testdatamt.collection.key.util.HasKeyMatchers.hasKey;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Benno Müller
 */
public class AbstractKeyObjectTest {

    @Test
    public void testConstructor_nullKey() {
        final IllegalArgumentException illegalArgumentException
                = assertThrows(IllegalArgumentException.class, () -> new StringKeyObject(null));

        assertThat(illegalArgumentException.getMessage(), containsString("key"));
    }

    @Property
    public void testGetKey(@ForAll final String key) {
        final StringKeyObject keyObject = new StringKeyObject(key);
        assertThat(keyObject, hasKey(key));
    }

    @Property
    public void testEquals(@ForAll final String key) {
        ObjectAssert.assertEquals(
                new StringKeyObject(key),
                new AbstractHasKey<>(key) {},
                new StringKeyObject(key + "x"),
                key,
                new Object()
        );
    }

    @Property
    public void testHashCode(@ForAll final String key) {
        ObjectAssert.assertHashCode(
                new StringKeyObject(key),
                new AbstractHasKey<>(key) {},
                new StringKeyObject(key + "x"),
                key,
                new Object()
        );
    }

    @Property
    public void testToString(@ForAll final String key) {
        final StringKeyObject keyObject = new StringKeyObject(key);
        assertThat(keyObject.toString(), is(StringKeyObject.class.getSimpleName() + "{key=" + key + '}'));
    }

}