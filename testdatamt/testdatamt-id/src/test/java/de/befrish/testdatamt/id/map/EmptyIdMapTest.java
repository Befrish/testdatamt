package de.befrish.testdatamt.id.map;

import de.befrish.testdatamt.id.Identifiable;
import de.befrish.testdatamt.id.util.DummyIdentifiable;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static de.befrish.testdatamt.id.util.TestUtils.assertEquals;
import static de.befrish.testdatamt.id.util.TestUtils.assertHashCode;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Benno Müller
 */
public class EmptyIdMapTest {

    private static final String TEST_ID = "key";

    private static final Identifiable<String> TEST_VALUE = new DummyIdentifiable(TEST_ID);

    private EmptyIdMap<Identifiable<String>> idMap;

    @Before
    public void setUp() {
        idMap = new EmptyIdMap<>();
    }

    private void assertIdMapIsEmpty() {
        assertThat(idMap.containsId(TEST_ID), is(false));
        assertThat(idMap.contains(TEST_VALUE), is(false));
        assertThat(idMap.get(TEST_ID), is(nullValue()));

        assertThat(idMap.size(), is(0));
        assertThat(idMap.isEmpty(), is(true));
        assertThat(idMap.ids(), hasSize(0));
        assertThat(idMap.ids(), is(empty()));
        assertThat(idMap.values(), hasSize(0));
        assertThat(idMap.values(), is(empty()));
        assertThat(idMap, is(emptyIterable()));
    }

    @Test
    public void erstelltLeereIdMap() {
        assertIdMapIsEmpty();
    }

    @Test
    public void ignoriertHinzufuegenVonSchluesselWertPaar() {
        idMap.add(TEST_VALUE);

        assertIdMapIsEmpty();
    }

    @Test
    public void ignoriertHinzufuegenVonSchluesselWertPaaren() {
        idMap.addAll(IdMaps.immutable(TEST_VALUE, TEST_VALUE));

        assertIdMapIsEmpty();
    }

    @Test
    public void ignoriertLoeschenVonSchluesselWertPaar() {
        idMap.remove(TEST_ID);

        assertIdMapIsEmpty();
    }

    @Test
    public void ignoriertLoeschenVonSchluesselWertPaaren() {
        idMap.removeAll(Arrays.asList(TEST_ID, TEST_ID));

        assertIdMapIsEmpty();
    }

    @Test
    public void testeEquals() {
        assertEquals(new EmptyIdMap<>(), new EmptyIdMap<>(), Integer.valueOf(0),
                new ImmutableIdMap<>(new MutableIdMap<>(TEST_VALUE)), new MutableIdMap<>(TEST_VALUE));
    }

    @Test
    public void testeHashCode() {
        assertHashCode(new EmptyIdMap<>(), new EmptyIdMap<>(),
                new ImmutableIdMap<>(new MutableIdMap<>(TEST_VALUE)), new MutableIdMap<>(TEST_VALUE));
    }

}
