package de.befrish.testdatamt.collection.id.map;

import de.befrish.testdatamt.collection.id.Identifiable;
import de.befrish.testdatamt.collection.id.util.DummyIdentifiable;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * @author Benno Müller
 */
public class IdMapsTest {

    @Test
    public void erstelltUnveraenderlicheIdMapAusIdMap() {
        final IdMap<Identifiable<?>> idMap = new MutableIdMap<>(
                new DummyIdentifiable("1"), new DummyIdentifiable("2"), new DummyIdentifiable("3"));

        final IdMap<Identifiable<?>> immutableIdMap = IdMaps.immutable(idMap);

        assertThat(immutableIdMap, is(instanceOf(ImmutableIdMap.class)));
        assertThat(immutableIdMap, is(idMap));
    }

    @Test
    public void erstelltUnveraenderlicheIdMapAusElementenAlsArray() {
        final IdMap<Identifiable<?>> immutableIdMap = IdMaps.immutable(
                new DummyIdentifiable("1"), new DummyIdentifiable("2"), new DummyIdentifiable("3"));

        assertThat(immutableIdMap, is(instanceOf(ImmutableIdMap.class)));
        assertThat(immutableIdMap.values(), hasItems((Identifiable<?>) new DummyIdentifiable("1"),
                new DummyIdentifiable("2"), new DummyIdentifiable("3")));
    }

    @Test
    public void erstelltUnveraenderlicheIdMapAusElementenAlsCollection() {
        final IdMap<Identifiable<?>> immutableIdMap = IdMaps.immutable(Arrays.asList(
                new DummyIdentifiable("1"), new DummyIdentifiable("2"), new DummyIdentifiable("3")));

        assertThat(immutableIdMap, is(instanceOf(ImmutableIdMap.class)));
        assertThat(immutableIdMap.values(), hasItems(new DummyIdentifiable("1"),
                new DummyIdentifiable("2"), new DummyIdentifiable("3")));
    }

    @Test
    public void erstelltLeereUndUnveränderlicheIdMap() {
        final IdMap<Identifiable<?>> emptyIdMap = IdMaps.empty();

        assertThat(emptyIdMap, is(instanceOf(EmptyIdMap.class)));
        assertThat(emptyIdMap.isEmpty(), is(true));
    }

}
