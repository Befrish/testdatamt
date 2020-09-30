package de.befrish.testdatamt.api.dummy;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Benno Müller
 */
public class NoOpTestDataSinkTest {

    private NoOpTestDataSink testDataSink;

    @Before
    public void setUp() {
        testDataSink = new NoOpTestDataSink();
    }

    @Test
    public void consumeTestData_WithoutErrors() {
        testDataSink.consumeTestData("foo bar");
    }

}