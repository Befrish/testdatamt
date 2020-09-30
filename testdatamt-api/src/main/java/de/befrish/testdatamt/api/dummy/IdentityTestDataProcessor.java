package de.befrish.testdatamt.api.dummy;

import de.befrish.testdatamt.api.TestDataProcessor;

/**
 * @author Benno Müller
 */
public class IdentityTestDataProcessor implements TestDataProcessor {

    @Override
    public Object processTestData(final Object testData) {
        return testData;
    }

}
