package de.befrish.testdatamt.collection.id.map;

import de.befrish.testdatamt.collection.id.Identifiable;
import de.befrish.testdatamt.collection.id.util.DummyIdentifiable;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static de.befrish.testdatamt.collection.id.util.TestUtils.assertEquals;
import static de.befrish.testdatamt.collection.id.util.TestUtils.assertHashCode;
import static de.befrish.testdatamt.collection.id.util.TestUtils.containsIds;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

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
        this.idMap = new MutableIdMap<>();
    }

    @Test
    public void wirftIllegalArgumentExceptionBeiGetNullKey() {
        this.exception.expect(IllegalArgumentException.class);
        this.exception.expectMessage("id");

        this.idMap.get(null);
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
        assertThat(this.idMap.containsId(TEST_ID), is(false));
        assertThat(this.idMap.contains(TEST_VALUE), is(false));
        assertThat(this.idMap.get(TEST_ID), is(nullValue()));

        this.idMap.add(TEST_VALUE);

        assertThat(this.idMap.containsId(TEST_ID), is(true));
        assertThat(this.idMap.contains(TEST_VALUE), is(true));
        assertThat(this.idMap.get(TEST_ID), is(TEST_VALUE));
    }

    @Test
    public void fuegtSchluesselWertPaareHinzu() {
        assertThat(this.idMap.containsId(TEST_ID), is(false));
        assertThat(this.idMap.contains(TEST_VALUE), is(false));
        assertThat(this.idMap.get(TEST_ID), is(nullValue()));
        assertThat(this.idMap.containsId(TEST_ID_2), is(false));
        assertThat(this.idMap.contains(TEST_VALUE_2), is(false));
        assertThat(this.idMap.get(TEST_ID_2), is(nullValue()));
        assertThat(this.idMap.size(), is(0));
        assertThat(this.idMap.isEmpty(), is(true));

        this.idMap.addAll(IdMaps.immutable(TEST_VALUE, TEST_VALUE_2));

        assertThat(this.idMap.containsId(TEST_ID), is(true));
        assertThat(this.idMap.contains(TEST_VALUE), is(true));
        assertThat(this.idMap.get(TEST_ID), is(TEST_VALUE));
        assertThat(this.idMap.containsId(TEST_ID_2), is(true));
        assertThat(this.idMap.contains(TEST_VALUE_2), is(true));
        assertThat(this.idMap.get(TEST_ID_2), is(TEST_VALUE_2));
        assertThat(this.idMap.size(), is(2));
        assertThat(this.idMap.isEmpty(), is(false));
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

        this.idMap.addAll(unorderedIds);

        assertThat(this.idMap, containsIds(ids));
    }

    @Test
    public void wirftIllegalArgumentExceptionBeiAddNull() {
        this.exception.expect(IllegalArgumentException.class);
        this.exception.expectMessage("value");

        this.idMap.add(null);
    }

    @Test
    public void loeschtSchluesselWertPaar() {
        this.idMap.add(TEST_VALUE);
        this.idMap.remove(TEST_ID);

        assertThat(this.idMap.containsId(TEST_ID), is(false));
        assertThat(this.idMap.contains(TEST_VALUE), is(false));
        assertThat(this.idMap.get(TEST_ID), is(nullValue()));
    }

    @Test
    public void loeschtSchluesselWertPaare() {
        this.idMap.addAll(IdMaps.immutable(TEST_VALUE, TEST_VALUE_2));
        this.idMap.removeAll(Arrays.asList(TEST_ID, TEST_ID_2));

        assertThat(this.idMap.containsId(TEST_ID), is(false));
        assertThat(this.idMap.contains(TEST_VALUE), is(false));
        assertThat(this.idMap.get(TEST_ID), is(nullValue()));
        assertThat(this.idMap.containsId(TEST_ID_2), is(false));
        assertThat(this.idMap.contains(TEST_VALUE_2), is(false));
        assertThat(this.idMap.get(TEST_ID_2), is(nullValue()));
        assertThat(this.idMap.size(), is(0));
        assertThat(this.idMap.isEmpty(), is(true));
    }

    @Test
    public void wirftIllegalArgumentExceptionBeiRemoveNullId() {
        this.exception.expect(IllegalArgumentException.class);
        this.exception.expectMessage("id");

        this.idMap.remove(null);
    }

    @Test
    public void gibtIdsUndWerteZurueck() {
        this.idMap.add(TEST_VALUE);

        assertThat(this.idMap.ids(), hasItem(TEST_ID));
        assertThat(this.idMap.ids(), not(hasItem(TEST_ID_2)));
        assertThat(this.idMap.values(), hasItem(TEST_VALUE));
        assertThat(this.idMap.values(), not(hasItem(TEST_VALUE_2)));
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
