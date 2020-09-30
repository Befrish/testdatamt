package de.befrish.testdatamt.api.dummy;

import de.befrish.testdatamt.api.TestDataSource;

/**
 * @author Benno Müller
 */
public class EmptyTestDataSource implements TestDataSource {

    @Override
    public Object readTestData() {
        return null;
    }

}
