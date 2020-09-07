package de.befrish.testdatamt.id;

/**
 * Object, that has an ID.
 *
 * @param <I> Type of the ID (use this for implementation of {@link Object#equals(java.lang.Object)} and
 * {@link Object#hashCode()}
 *
 * @author Benno Müller
 */
public interface Identifiable<I> {

    I getId();

}
