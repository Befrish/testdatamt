/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.api;

import de.befrish.testdatamt.api.dummy.NoOpTestDataSink;
import de.befrish.testdatamt.api.dummy.EmptyTestDataSource;
import de.befrish.testdatamt.api.dummy.IdentityTestDataProcessor;
import de.befrish.testdatamt.api.runner.TestDataMtException;
import de.befrish.testdatamt.api.runner.TestDataMtRunner;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThrows;

/**
 * @author Benno Müller
 */
public class TestDataMtRunnerTest {

    private TestDataMtRunner testDataMtRunner;

    @Before
    public void setUp() {
        this.testDataMtRunner = new TestDataMtRunner(
                List.of(new EmptyTestDataSource()),
                new NoOpTestDataSink()
        );
        this.testDataMtRunner.addProcessor(new IdentityTestDataProcessor());
    }

    @Test
    public void readProcessAndConsume() {
        this.testDataMtRunner.run();
    }

    @Test
    public void throwsExceptionOnFailure() {
        this.testDataMtRunner = new TestDataMtRunner(
                List.of(new EmptyTestDataSource()),
                testData -> {
                    throw new IllegalStateException("expected error");
                }
        );
        final TestDataMtException exception = assertThrows(TestDataMtException.class, () -> this.testDataMtRunner.run());

        assertThat(exception.getMessage(), containsString("error runnning class de.befrish.testdatamt.api.runner.TestDataMtRunner"));
        assertThat(exception.getCause(), instanceOf(IllegalStateException.class));
        assertThat(exception.getCause().getMessage(), containsString("expected error"));
    }

    // TODO Multiple sources
    // TODO Multiple processors

}