package de.befrish.testdatamt.id.util;

/**
 * @author Benno Müller
 */
public interface Builder<T> {

    T build() throws IllegalStateException;

}
