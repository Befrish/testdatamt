package de.befrish.testdatamt.collection.id.util;

/**
 * @author Benno Müller
 */
public interface Builder<T> {

    T build() throws IllegalStateException;

}
