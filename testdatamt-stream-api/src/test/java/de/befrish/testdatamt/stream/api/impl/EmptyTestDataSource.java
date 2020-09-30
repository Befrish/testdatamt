package de.befrish.testdatamt.stream.api.impl;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

/**
 * @author Benno Müller
 */
class EmptyTestDataSource {

    @Test
    void testReadEmptyTestData() {
        final Object testData = new EmptyDataSource().read();

        assertThat(testData, is(nullValue()));
    }

}