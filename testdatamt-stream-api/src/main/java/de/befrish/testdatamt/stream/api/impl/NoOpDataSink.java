package de.befrish.testdatamt.stream.api.impl;

import de.befrish.testdatamt.stream.api.DataSink;

/**
 * @author Benno Müller
 */
public class NoOpDataSink implements DataSink {

    @Override
    public void consume(final Object data) {
        // do nothing
    }

}
