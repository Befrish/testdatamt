package de.befrish.testdatamt.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Benno Müller
 */
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Reference implements HasQualifiedName {

    @NonNull
    @Getter
    private final QualifiedName qualifiedName;

}
