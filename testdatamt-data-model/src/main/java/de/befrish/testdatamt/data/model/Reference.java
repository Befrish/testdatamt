package de.befrish.testdatamt.data.model;

import de.befrish.testdatamt.util.ParameterAssert;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Objects;

/**
 * @author Benno Müller
 */
public class Reference implements HasQualifiedName {

    private final QualifiedName qualifiedName;

    public Reference(final QualifiedName qualifiedName) {
        ParameterAssert.notNull(qualifiedName, "qualifiedName");
        this.qualifiedName = qualifiedName;
    }

    @Override
    public QualifiedName getQualifiedName() {
        return this.qualifiedName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Reference reference = (Reference) o;
        return Objects.equals(this.qualifiedName, reference.qualifiedName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.qualifiedName);
    }

    @Override
    public String toString() {
        return "Reference{" +
                "qualifiedName=" + this.qualifiedName +
                '}';
    }

}
