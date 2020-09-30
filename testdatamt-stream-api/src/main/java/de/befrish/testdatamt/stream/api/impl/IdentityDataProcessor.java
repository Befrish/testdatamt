package de.befrish.testdatamt.stream.api.impl;

import de.befrish.testdatamt.stream.api.DataProcessor;

/**
 * @author Benno Müller
 */
public class IdentityDataProcessor implements DataProcessor {

    @Override
    public Object process(final Object data) {
        return data;
    }

}
