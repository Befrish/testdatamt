package de.befrish.testdatamt.api.processor;

import de.befrish.testdatamt.api.TestDataProcessor;

/**
 * @author Benno Müller
 */
public class IdentityTestDataProcessor implements TestDataProcessor {

    @Override
    public Void processTestData(final Void testData) {
        return testData;
    }

}
