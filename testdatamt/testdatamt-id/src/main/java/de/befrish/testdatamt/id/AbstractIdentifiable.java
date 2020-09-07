package de.befrish.testdatamt.id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * @author Benno Müller
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AbstractIdentifiable<I> implements Identifiable<I> {

    @NonNull
    @EqualsAndHashCode.Include
    private final I id;

    @Override
    public String toString() {
        return Objects.toString(id);
    }

}
