package de.befrish.testdatamt.collection.key.map;

import de.befrish.testdatamt.collection.key.HasKey;
import de.befrish.testdatamt.collection.key.domain.StringKeyObject;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import net.jqwik.api.ForAll;
import net.jqwik.api.From;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Test;

import static de.befrish.testdatamt.test.util.ParameterAssertions.assertThrowsParameterNullException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;

/**
 * @author Benno Müller
 */
class KeyMapTest {

    /*
     * static methods
     */

    @Property
    void testEntry(@ForAll final StringKeyObject keyObject) {
        final Tuple2<String, StringKeyObject> entry = KeyMap.entry(keyObject);

        assertThat(entry._1(), is(keyObject.getKey()));
        assertThat(entry._2(), is(sameInstance(keyObject)));
    }

    @Test
    void testEntry_null() {
        assertThrowsParameterNullException("element", () -> KeyMap.entry(null));
    }

    @Property
    void testNarrow(@ForAll final List<StringKeyObject> keyObjects) {
        final KeyMap<String, StringKeyObject> keyMap = LinkedKeyMap.ofAll(keyObjects);

        final KeyMap<String, ? extends HasKey<String>> narrowedKeyMap = KeyMap.narrow(keyMap);

        assertThat(narrowedKeyMap, is(sameInstance(keyMap)));
    }

    @Test
    void testNarrow_null() {
        final KeyMap<String, ? extends HasKey<String>> narrowedKeyMap = KeyMap.narrow(null);

        assertThat(narrowedKeyMap, is(nullValue()));
    }

}
