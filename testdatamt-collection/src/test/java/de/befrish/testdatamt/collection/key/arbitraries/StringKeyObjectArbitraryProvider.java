package de.befrish.testdatamt.collection.key.arbitraries;

import de.befrish.testdatamt.collection.key.domain.StringKeyObject;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.arbitraries.StringArbitrary;
import net.jqwik.api.providers.ArbitraryProvider;
import net.jqwik.api.providers.TypeUsage;

import java.util.Collections;
import java.util.Set;

/**
 * @author Benno Müller
 */
public class StringKeyObjectArbitraryProvider implements ArbitraryProvider {

    @Override
    public boolean canProvideFor(final TypeUsage targetType) {
        return targetType.isOfType(StringKeyObject.class);
    }

    @Override
    public Set<Arbitrary<?>> provideFor(final TypeUsage targetType, final SubtypeProvider subtypeProvider) {
        final Arbitrary<String> keys = Arbitraries.strings().alpha().ofMinLength(1);
        return Collections.singleton(keys.map(StringKeyObject::new));
    }

}
