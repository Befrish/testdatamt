package de.befrish.testdatamt.collection.id.map;

import de.befrish.testdatamt.collection.id.Identifiable;
import de.befrish.testdatamt.collection.id.util.DummyIdentifiable;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static de.befrish.testdatamt.collection.id.util.TestUtils.assertEquals;
import static de.befrish.testdatamt.collection.id.util.TestUtils.assertHashCode;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Benno Müller
 */
public class EmptyIdMapTest {

    private static final String TEST_ID = "key";

    private static final Identifiable<String> TEST_VALUE = new DummyIdentifiable(TEST_ID);

    private EmptyIdMap<Identifiable<String>> idMap;

    @Before
    public void setUp() {
        this.idMap = new EmptyIdMap<>();
    }

    private void assertIdMapIsEmpty() {
        assertThat(this.idMap.containsId(TEST_ID), is(false));
        assertThat(this.idMap.contains(TEST_VALUE), is(false));
        assertThat(this.idMap.get(TEST_ID), is(nullValue()));

        assertThat(this.idMap.size(), is(0));
        assertThat(this.idMap.isEmpty(), is(true));
        assertThat(this.idMap.ids(), hasSize(0));
        assertThat(this.idMap.ids(), is(empty()));
        assertThat(this.idMap.values(), hasSize(0));
        assertThat(this.idMap.values(), is(empty()));
        assertThat(this.idMap, is(emptyIterable()));
    }

    @Test
    public void erstelltLeereIdMap() {
        assertIdMapIsEmpty();
    }

    @Test
    public void ignoriertHinzufuegenVonSchluesselWertPaar() {
        this.idMap.add(TEST_VALUE);

        assertIdMapIsEmpty();
    }

    @Test
    public void ignoriertHinzufuegenVonSchluesselWertPaaren() {
        this.idMap.addAll(IdMaps.immutable(TEST_VALUE, TEST_VALUE));

        assertIdMapIsEmpty();
    }

    @Test
    public void ignoriertLoeschenVonSchluesselWertPaar() {
        this.idMap.remove(TEST_ID);

        assertIdMapIsEmpty();
    }

    @Test
    public void ignoriertLoeschenVonSchluesselWertPaaren() {
        this.idMap.removeAll(Arrays.asList(TEST_ID, TEST_ID));

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
