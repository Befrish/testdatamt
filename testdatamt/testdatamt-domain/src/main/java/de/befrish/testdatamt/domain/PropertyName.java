package de.befrish.testdatamt.domain;

import de.befrish.testdatamt.id.util.Assert;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

/**
 * @author Benno Müller
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PropertyName {

    @Getter
    @EqualsAndHashCode.Include
    private final String name;

    /**
     * Temporäre Speicherung des HashCodes zur Performance-Optimierung.
     */
    private int hashCode;

    public PropertyName(final String name) {
        Assert.notNull(name, "name");
        this.name = NameConfiguration.getCurrent().getPropertyName(name);
        this.hashCode = Objects.hashCode(this.name);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return name;
    }

}
