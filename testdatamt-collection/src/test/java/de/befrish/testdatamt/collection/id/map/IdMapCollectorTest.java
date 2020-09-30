package de.befrish.testdatamt.collection.id.map;

import de.befrish.testdatamt.collection.id.util.DummyIdentifiable;
import io.vavr.collection.List;
import org.junit.Test;

import static de.befrish.testdatamt.collection.id.util.TestUtils.containsIds;
import static org.hamcrest.MatcherAssert.assertThat;

public class IdMapCollectorTest {

    @Test
    public void canCollectValuesToIdMap() {
        final List<DummyIdentifiable> valuesAsList = List.of(
                new DummyIdentifiable("1"),
                new DummyIdentifiable("2"),
                new DummyIdentifiable("3")
        );

        final IdMap<DummyIdentifiable> valuesAsIdMap = valuesAsList.toJavaStream().collect(IdMapCollector.toIdMap());

        assertThat(valuesAsIdMap, containsIds("1", "2", "3"));
    }

}

