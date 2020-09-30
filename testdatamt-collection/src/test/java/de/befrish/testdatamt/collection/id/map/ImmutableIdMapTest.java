package de.befrish.testdatamt.collection.id.map;

import de.befrish.testdatamt.collection.id.Identifiable;
import de.befrish.testdatamt.collection.id.util.DummyIdentifiable;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static de.befrish.testdatamt.collection.id.util.TestUtils.assertEquals;
import static de.befrish.testdatamt.collection.id.util.TestUtils.assertHashCode;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

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
        this.idMap = new ImmutableIdMap<>(new MutableIdMap<>(TEST_VALUE));
    }

    private void assertIdMapIsUnchanged() {
        assertThat(this.idMap.containsId(TEST_ID), is(true));
        assertThat(this.idMap.contains(TEST_VALUE), is(true));
        assertThat(this.idMap.get(TEST_ID), is(TEST_VALUE));

        assertThat(this.idMap.containsId(TEST_ID_2), is(false));
        assertThat(this.idMap.contains(TEST_VALUE_2), is(false));
        assertThat(this.idMap.get(TEST_ID_2), is(nullValue()));

        assertThat(this.idMap.size(), is(1));
        assertThat(this.idMap.isEmpty(), is(false));
        assertThat(this.idMap, is(not(emptyIterable())));
    }

    @Test
    public void ignoriertHinzufuegenVonSchluesselWertPaar() {
        this.idMap.add(TEST_VALUE);

        assertIdMapIsUnchanged();
    }

    @Test
    public void ignoriertHinzufuegenVonSchluesselWertPaaren() {
        this.idMap.addAll(IdMaps.immutable(TEST_VALUE, TEST_VALUE_2));

        assertIdMapIsUnchanged();
    }

    @Test
    public void ignoriertHinzufuegenVonNullAlsWert() {
        this.idMap.add(null);

        assertIdMapIsUnchanged();
    }

    @Test
    public void ignoriertLoeschenVonSchluesselWertPaar() {
        this.idMap.remove(TEST_ID);

        assertIdMapIsUnchanged();
    }

    @Test
    public void ignoriertLoeschenVonSchluesselWertPaaren() {
        this.idMap.removeAll(Arrays.asList(TEST_ID, TEST_ID_2));

        assertIdMapIsUnchanged();
    }

    @Test
    public void ignoriertLoeschenVonNullId() {
        this.idMap.remove(null);

        assertIdMapIsUnchanged();
    }

    @Test
    public void gibtIdsUndWerteZurueck() {
        assertThat(this.idMap.ids(), hasItem(TEST_ID));
        assertThat(this.idMap.ids(), not(hasItem(TEST_ID_2)));
        assertThat(this.idMap.values(), hasItem(TEST_VALUE));
        assertThat(this.idMap.values(), not(hasItem(TEST_VALUE_2)));
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
