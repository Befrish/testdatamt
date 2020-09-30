package de.befrish.testdatamt.api.dummy;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

/**
 * @author Benno Müller
 */
public class EmptyTestDataSourceTest {

    private EmptyTestDataSource testDataSource;

    @Before
    public void setUp() throws Exception {
        testDataSource = new EmptyTestDataSource();
    }

    @Test
    public void testReadEmptyTestData() {
        final Object testData = testDataSource.readTestData();

        assertThat(testData, is(nullValue()));
    }

}