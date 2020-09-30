package de.befrish.testdatamt.data.model;

import de.befrish.testdatamt.util.ParameterAssert;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

/**
 * @author Benno Müller
 */
public final class PropertyName {

    private final String name;

    /**
     * Temporäre Speicherung des HashCodes zur Performance-Optimierung.
     */
    private int hashCode;

    public PropertyName(final String name) {
        ParameterAssert.notBlank(name, "name");
        this.name = NameConfiguration.getCurrent().getPropertyName(name);
        this.hashCode = Objects.hash(this.name);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PropertyName that = (PropertyName) o;
        return Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
