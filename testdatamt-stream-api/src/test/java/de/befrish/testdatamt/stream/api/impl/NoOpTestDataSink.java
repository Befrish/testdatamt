package de.befrish.testdatamt.stream.api.impl;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.WithNull;

/**
 * @author Benno Müller
 */
class NoOpTestDataSink {

    @Property
    void testConsumeTestData_WithoutErrors(@ForAll @WithNull final String testData) {
        new NoOpDataSink().consume(testData);
    }

}