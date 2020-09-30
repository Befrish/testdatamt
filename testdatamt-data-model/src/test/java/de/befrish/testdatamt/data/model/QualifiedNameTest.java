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
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.NotEmpty;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Benno Müller
 */
class QualifiedNameTest {

    @Provide
    Arbitrary<String> qualifiedNames() {
        return Arbitraries.strings().ofMinLength(1).alpha().list().ofMinSize(1)
                .map(strings -> List.ofAll(strings).mkString(QualifiedName.SEPARATOR));
    }

    // TODO test all allowed and not allowed characters
    // TODO create Arbitraries

    @Test
    void testConstructor_null() {
        ParameterAssertions.assertThrowsParameterNullException("qualifiedName", () -> QualifiedName.fromString(null));
    }

    @Property
    void testGetFullQualifiedName(@ForAll @From("qualifiedNames") final String qualifiedName) {
        assertThat(QualifiedName.fromString(qualifiedName).getFullQualifiedName(), is(qualifiedName));
    }

    @Property
    void testGetName(
            @ForAll @From("qualifiedNames") final String packageName,
            @ForAll @NotEmpty @AlphaChars final String name) {
        assertThat(QualifiedName.fromString(packageName + QualifiedName.SEPARATOR + name).getName(),
                is(name));
    }

    @Property
    void testGetPackageName(
            @ForAll @From("qualifiedNames") final String packageName,
            @ForAll @NotEmpty @AlphaChars final String name) {
        assertThat(QualifiedName.fromString(packageName + QualifiedName.SEPARATOR + name).getPackageName(),
                is(packageName));
    }

    @Property
    void testEquals(@ForAll @From("qualifiedNames") final String qualifiedName) {
        ObjectAssert.assertEquals(
                QualifiedName.fromString(qualifiedName),
                QualifiedName.fromString(qualifiedName),
                QualifiedName.fromString(qualifiedName + "x"),
                qualifiedName,
                new Object()
        );
    }

    @Property
    void testHashCode(@ForAll @From("qualifiedNames") final String qualifiedName) {
        ObjectAssert.assertHashCode(
                QualifiedName.fromString(qualifiedName),
                QualifiedName.fromString(qualifiedName),
                QualifiedName.fromString(qualifiedName + "x"),
                qualifiedName,
                new Object()
        );
    }

    @Property
    void testToString(@ForAll @From("qualifiedNames") final String qualifiedName) {
        assertThat(QualifiedName.fromString(qualifiedName).toString(), is(qualifiedName));
    }

}