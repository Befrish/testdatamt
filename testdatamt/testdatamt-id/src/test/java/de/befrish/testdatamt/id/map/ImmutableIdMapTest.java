package de.befrish.testdatamt.id.map;

import de.befrish.testdatamt.id.Identifiable;
import de.befrish.testdatamt.id.util.DummyIdentifiable;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static de.befrish.testdatamt.id.util.TestUtils.assertEquals;
import static de.befrish.testdatamt.id.util.TestUtils.assertHashCode;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * Test für {@link ImmutableIdMap}.
 *
 * @author Benno Müller
 */
public class ImmutableIdMapTest {

    private static final String TEST_ID = "key";
    private static final String TEST_ID_2 = "keyX";
    private static final Identifiable<String> TEST_VALUE = new DummyIdentifiable(TEST_ID);
    private static final Identifiable<String> TEST_VALUE_2 = new DummyIdentifiable(TEST_ID_2);

    private ImmutableIdMap<Identifiable<String>> idMap;

    @Before
    public void setUp() {
        idMap = new ImmutableIdMap<>(new MutableIdMap<>(TEST_VALUE));
    }

    private void assertIdMapIsUnchanged() {
        assertThat(idMap.containsId(TEST_ID), is(true));
        assertThat(idMap.contains(TEST_VALUE), is(true));
        assertThat(idMap.get(TEST_ID), is(TEST_VALUE));

        assertThat(idMap.containsId(TEST_ID_2), is(false));
        assertThat(idMap.contains(TEST_VALUE_2), is(false));
        assertThat(idMap.get(TEST_ID_2), is(nullValue()));

        assertThat(idMap.size(), is(1));
        assertThat(idMap.isEmpty(), is(false));
        assertThat(idMap, is(not(emptyIterable())));
    }

    @Test
    public void ignoriertHinzufuegenVonSchluesselWertPaar() {
        idMap.add(TEST_VALUE);

        assertIdMapIsUnchanged();
    }

    @Test
    public void ignoriertHinzufuegenVonSchluesselWertPaaren() {
        idMap.addAll(IdMaps.immutable(TEST_VALUE, TEST_VALUE_2));

        assertIdMapIsUnchanged();
    }

    @Test
    public void ignoriertHinzufuegenVonNullAlsWert() {
        idMap.add(null);

        assertIdMapIsUnchanged();
    }

    @Test
    public void ignoriertLoeschenVonSchluesselWertPaar() {
        idMap.remove(TEST_ID);

        assertIdMapIsUnchanged();
    }

    @Test
    public void ignoriertLoeschenVonSchluesselWertPaaren() {
        idMap.removeAll(Arrays.asList(TEST_ID, TEST_ID_2));

        assertIdMapIsUnchanged();
    }

    @Test
    public void ignoriertLoeschenVonNullId() {
        idMap.remove(null);

        assertIdMapIsUnchanged();
    }

    @Test
    public void gibtIdsUndWerteZurueck() {
        assertThat(idMap.ids(), hasItem(TEST_ID));
        assertThat(idMap.ids(), not(hasItem(TEST_ID_2)));
        assertThat(idMap.values(), hasItem(TEST_VALUE));
        assertThat(idMap.values(), not(hasItem(TEST_VALUE_2)));
    }

    @Test
    public void testeEquals() {
        assertEquals(new ImmutableIdMap<>(new MutableIdMap<>(TEST_VALUE)),
                new ImmutableIdMap<>(new MutableIdMap<>(TEST_VALUE)), Integer.valueOf(0),
                new ImmutableIdMap<>(new MutableIdMap<>()), new ImmutableIdMap<>(new MutableIdMap<>(TEST_VALUE_2)),
                new ImmutableIdMap<>(new MutableIdMap<>(TEST_VALUE, TEST_VALUE_2)));
    }

    @Test
    public void testeHashCode() {
        assertHashCode(new ImmutableIdMap<>(new MutableIdMap<>(TEST_VALUE)),
                new ImmutableIdMap<>(new MutableIdMap<>(TEST_VALUE)), new ImmutableIdMap<>(new MutableIdMap<>()),
                new ImmutableIdMap<>(new MutableIdMap<>(TEST_VALUE_2)),
                new ImmutableIdMap<>(new MutableIdMap<>(TEST_VALUE, TEST_VALUE_2)));
    }

}
