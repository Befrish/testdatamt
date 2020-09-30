package de.befrish.testdatamt.api.dummy;

import de.befrish.testdatamt.api.TestDataSink;

/**
 * @author Benno Müller
 */
public class NoOpTestDataSink implements TestDataSink {

    @Override
    public void consumeTestData(final Object testData) {
        // do nothing
    }

}
