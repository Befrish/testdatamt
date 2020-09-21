package de.befrish.testdatamt.api.source;

import de.befrish.testdatamt.api.TestDataSource;

/**
 * @author Benno Müller
 */
public class EmptyTestDataSource implements TestDataSource {

    @Override
    public Void readTestData() {
        return null;
    }

}
