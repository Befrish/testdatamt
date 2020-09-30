/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.stream.api.runner;

import de.befrish.testdatamt.stream.api.impl.EmptyDataSource;
import de.befrish.testdatamt.stream.api.impl.IdentityDataProcessor;
import de.befrish.testdatamt.stream.api.impl.NoOpDataSink;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Benno Müller
 */
class TestDataExecutorTest {

    @Test
    void readProcessAndConsume() {
        final TestDataExecutor testDataExecutor = new TestDataExecutor(
                List.of(new EmptyDataSource()),
                List.of(new IdentityDataProcessor()),
                new NoOpDataSink()
        );

        testDataExecutor.execute();
    }

    @Test
    void throwsExceptionOnFailure() {
        final TestDataExecutor testDataExecutor = new TestDataExecutor(
                List.of(new EmptyDataSource()),
                List.of(new IdentityDataProcessor()),
                testData -> {
                    throw new IllegalStateException("expected error");
                }
        );
        final TestDataExecutionException exception = assertThrows(TestDataExecutionException.class, testDataExecutor::execute);

        assertThat(exception.getMessage(), containsString("error runnning class " + TestDataExecutor.class.getName()));
        assertThat(exception.getCause(), instanceOf(IllegalStateException.class));
        assertThat(exception.getCause().getMessage(), containsString("expected error"));
    }

    // TODO Multiple sources
    // TODO Multiple processors

}