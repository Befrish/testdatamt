package de.befrish.testdatamt.id.map;

import de.befrish.testdatamt.id.Identifiable;
import de.befrish.testdatamt.id.util.DummyIdentifiable;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static de.befrish.testdatamt.id.util.TestUtils.assertEquals;
import static de.befrish.testdatamt.id.util.TestUtils.assertHashCode;
import static de.befrish.testdatamt.id.util.TestUtils.containsIds;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * @author Benno Müller
 */
public class MutableIdMapTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private static final String TEST_ID = "key";
    private static final String TEST_ID_2 = "keyX";
    private static final Identifiable<String> TEST_VALUE = new DummyIdentifiable(TEST_ID);
    private static final Identifiable<String> TEST_VALUE_2 = new DummyIdentifiable(TEST_ID_2);

    private MutableIdMap<Identifiable<String>> idMap;

    @Before
    public void setUp() {
        idMap = new MutableIdMap<>();
    }

    @Test
    public void wirftIllegalArgumentExceptionBeiGetNullKey() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("id");

        idMap.get(null);
    }

    @Test
    public void erstelltLeereIdMapWennKonstruktorArgumentNullArray() {
        assertThat(new MutableIdMap<Identifiable<?>>((Identifiable[]) null), is(emptyIterable()));
    }

    @Test
    public void erstelltLeereIdMapWennKonstruktorArgumentNullIterable() {
        assertThat(new MutableIdMap<>((Iterable<Identifiable<?>>) null), is(emptyIterable()));
    }

    @Test
    public void fuegtSchluesselWertPaarHinzu() {
        assertThat(idMap.containsId(TEST_ID), is(false));
        assertThat(idMap.contains(TEST_VALUE), is(false));
        assertThat(idMap.get(TEST_ID), is(nullValue()));

        idMap.add(TEST_VALUE);

        assertThat(idMap.containsId(TEST_ID), is(true));
        assertThat(idMap.contains(TEST_VALUE), is(true));
        assertThat(idMap.get(TEST_ID), is(TEST_VALUE));
    }

    @Test
    public void fuegtSchluesselWertPaareHinzu() {
        assertThat(idMap.containsId(TEST_ID), is(false));
        assertThat(idMap.contains(TEST_VALUE), is(false));
        assertThat(idMap.get(TEST_ID), is(nullValue()));
        assertThat(idMap.containsId(TEST_ID_2), is(false));
        assertThat(idMap.contains(TEST_VALUE_2), is(false));
        assertThat(idMap.get(TEST_ID_2), is(nullValue()));
        assertThat(idMap.size(), is(0));
        assertThat(idMap.isEmpty(), is(true));

        idMap.addAll(IdMaps.immutable(TEST_VALUE, TEST_VALUE_2));

        assertThat(idMap.containsId(TEST_ID), is(true));
        assertThat(idMap.contains(TEST_VALUE), is(true));
        assertThat(idMap.get(TEST_ID), is(TEST_VALUE));
        assertThat(idMap.containsId(TEST_ID_2), is(true));
        assertThat(idMap.contains(TEST_VALUE_2), is(true));
        assertThat(idMap.get(TEST_ID_2), is(TEST_VALUE_2));
        assertThat(idMap.size(), is(2));
        assertThat(idMap.isEmpty(), is(false));
    }

    @Test
    public void fuegtSchluesselWertPaareInReihenfolgeHinzu() throws Exception {
        final String[] ids = new String[] {
                TEST_ID + "4",
                TEST_ID + "3",
                TEST_ID + "2",
                TEST_ID + "0",
                TEST_ID + "1",
        }; // in der HashSet werden diese in eine andere Reihenfolge gebracht

        final IdMap<DummyIdentifiable> unorderedIds = IdMaps.immutable(
                stream(ids).map(DummyIdentifiable::new).collect(toList()));

        idMap.addAll(unorderedIds);

        assertThat(idMap, containsIds(ids));
    }

    @Test
    public void wirftIllegalArgumentExceptionBeiAddNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("value");

        idMap.add(null);
    }

    @Test
    public void loeschtSchluesselWertPaar() {
        idMap.add(TEST_VALUE);
        idMap.remove(TEST_ID);

        assertThat(idMap.containsId(TEST_ID), is(false));
        assertThat(idMap.contains(TEST_VALUE), is(false));
        assertThat(idMap.get(TEST_ID), is(nullValue()));
    }

    @Test
    public void loeschtSchluesselWertPaare() {
        idMap.addAll(IdMaps.immutable(TEST_VALUE, TEST_VALUE_2));
        idMap.removeAll(Arrays.asList(TEST_ID, TEST_ID_2));

        assertThat(idMap.containsId(TEST_ID), is(false));
        assertThat(idMap.contains(TEST_VALUE), is(false));
        assertThat(idMap.get(TEST_ID), is(nullValue()));
        assertThat(idMap.containsId(TEST_ID_2), is(false));
        assertThat(idMap.contains(TEST_VALUE_2), is(false));
        assertThat(idMap.get(TEST_ID_2), is(nullValue()));
        assertThat(idMap.size(), is(0));
        assertThat(idMap.isEmpty(), is(true));
    }

    @Test
    public void wirftIllegalArgumentExceptionBeiRemoveNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("id");

        idMap.remove(null);
    }

    @Test
    public void gibtIdsUndWerteZurueck() {
        idMap.add(TEST_VALUE);

        assertThat(idMap.ids(), hasItem(TEST_ID));
        assertThat(idMap.ids(), not(hasItem(TEST_ID_2)));
        assertThat(idMap.values(), hasItem(TEST_VALUE));
        assertThat(idMap.values(), not(hasItem(TEST_VALUE_2)));
    }

    @Test
    public void testeEquals() {
        assertEquals(new MutableIdMap<>(TEST_VALUE), new MutableIdMap<>(TEST_VALUE), Integer.valueOf(0),
                new MutableIdMap<>(), new MutableIdMap<>(TEST_VALUE_2), new MutableIdMap<>(TEST_VALUE, TEST_VALUE_2));
    }

    @Test
    public void testeHashCode() {
        assertHashCode(new MutableIdMap<>(TEST_VALUE), new MutableIdMap<>(TEST_VALUE), new MutableIdMap<>(),
                new MutableIdMap<>(TEST_VALUE_2), new MutableIdMap<>(TEST_VALUE, TEST_VALUE_2));
    }

}
