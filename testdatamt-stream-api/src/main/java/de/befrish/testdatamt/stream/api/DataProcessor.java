package de.befrish.testdatamt.stream.api;

/**
 * @author Benno Müller
 */
@FunctionalInterface
public interface DataProcessor {

    Object process(Object data);

}
