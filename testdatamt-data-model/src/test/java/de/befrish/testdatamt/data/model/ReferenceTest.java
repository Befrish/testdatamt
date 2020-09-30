package de.befrish.testdatamt.data.model;

import de.befrish.testdatamt.test.util.ObjectAssert;
import de.befrish.testdatamt.test.util.ParameterAssertions;
import io.vavr.collection.List;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.From;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Benno Müller
 */
class ReferenceTest {

    @Provide
    Arbitrary<QualifiedName> qualifiedNames() {
        return Arbitraries.strings().ofMinLength(1).alpha().list().ofMinSize(1)
                .map(strings -> List.ofAll(strings).mkString(QualifiedName.SEPARATOR))
                .map(QualifiedName::fromString);
    }

    @Test
    void testConstructor_null() {
        ParameterAssertions.assertThrowsParameterNullException("", () -> new Reference(null));
    }

    @Property
    void testGetQualifiedName(@ForAll @From("qualifiedNames") final QualifiedName qualifiedName) {
        assertThat(new Reference(qualifiedName).getQualifiedName(), is(qualifiedName));
    }

    @Property
    void testEquals(@ForAll @From("qualifiedNames") final QualifiedName qualifiedName) {
        ObjectAssert.assertEquals(
                new Reference(qualifiedName),
                new Reference(qualifiedName),
                new Reference(QualifiedName.fromString(qualifiedName + "x")),
                qualifiedName,
                new Object()
        );
    }

    @Property
    void testHashCode(@ForAll @From("qualifiedNames") final QualifiedName qualifiedName) {
        ObjectAssert.assertHashCode(
                new Reference(qualifiedName),
                new Reference(qualifiedName),
                new Reference(QualifiedName.fromString(qualifiedName + "x")),
                qualifiedName,
                new Object()
        );
    }

    @Property
    void testToString(@ForAll @From("qualifiedNames") final QualifiedName qualifiedName) {
        assertThat(new Reference(qualifiedName).toString(), is("Reference{qualifiedName=" + qualifiedName + '}'));
    }

}