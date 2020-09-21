package de.befrish.testdatamt.api.sink;

import de.befrish.testdatamt.api.TestDataSink;

/**
 * @author Benno Müller
 */
public class NoOpTestDataSink implements TestDataSink {

    @Override
    public void consumeTestData(final Void testData) {
        // do nothing
    }

}
