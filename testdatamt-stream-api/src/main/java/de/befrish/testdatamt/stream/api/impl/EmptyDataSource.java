package de.befrish.testdatamt.stream.api.impl;

import de.befrish.testdatamt.stream.api.DataSource;

/**
 * @author Benno Müller
 */
public class EmptyDataSource implements DataSource {

    @Override
    public Object read() {
        return null;
    }

}
