package de.befrish.testdatamt.api.dummy;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

/**
 * @author Benno Müller
 */
public class IdentityTestDataProcessorTest {

    private static final String FOO_BAR = "foo bar";

    private IdentityTestDataProcessor testDataProcessor;

    @Before
    public void setUp() throws Exception {
        testDataProcessor = new IdentityTestDataProcessor();
    }

    @Test
    public void testProcessTestData_ReturnsSameObject() {
        final Object testData = testDataProcessor.processTestData(FOO_BAR);

        assertThat(testData, is(sameInstance(FOO_BAR)));
    }

}