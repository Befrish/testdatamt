package de.befrish.testdatamt.stream.api.impl;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.WithNull;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

/**
 * @author Benno Müller
 */
class IdentityTestDataProcessorTest {

    @Property
    void testProcessTestData_ReturnsSameObject(@ForAll @WithNull final String testData) {
        final Object testDataResult = new IdentityDataProcessor().process(testData);

        assertThat(testDataResult, is(sameInstance(testData)));
    }

}