package de.befrish.testdatamt.util;

/**
 * @author Benno Müller
 */
public interface Builder<T> {

    T build() throws IllegalStateException;

}
