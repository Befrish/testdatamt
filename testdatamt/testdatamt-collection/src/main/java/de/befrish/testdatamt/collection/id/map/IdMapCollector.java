package de.befrish.testdatamt.collection.id.map;

import de.befrish.testdatamt.collection.id.Identifiable;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * {@link Collector} for {@link IdMap} (creates a {@link MutableIdMap}).
 *
 * @author Benno Müller
 */
public class IdMapCollector<E extends Identifiable<?>> implements Collector<E, MutableIdMap<E>, IdMap<E>> {

    public static <E extends Identifiable<?>> IdMapCollector<E> toIdMap() {
        return new IdMapCollector<>();
    }

    @Override
    public Supplier<MutableIdMap<E>> supplier() {
        return MutableIdMap::new;
    }

    @Override
    public BiConsumer<MutableIdMap<E>, E> accumulator() {
        return IdMap::add;
    }

    @Override
    public BinaryOperator<MutableIdMap<E>> combiner() {
        return (left, right) -> {
            left.addAll(right);
            return left;
        };
    }

    @Override
    public Function<MutableIdMap<E>, IdMap<E>> finisher() {
        return m -> m;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.IDENTITY_FINISH);
    }

}
