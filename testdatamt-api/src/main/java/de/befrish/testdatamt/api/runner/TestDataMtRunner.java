/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.api.runner;

import de.befrish.testdatamt.api.TestDataProcessor;
import de.befrish.testdatamt.api.TestDataSink;
import de.befrish.testdatamt.api.TestDataSource;
import de.befrish.testdatamt.util.Assert;
import io.vavr.collection.List;
import io.vavr.collection.Traversable;

/**
 * @author Benno Müller
 */
public class TestDataMtRunner {

    private final List<TestDataSource> testDataSources;
    private List<TestDataProcessor> testDataProcessors = List.empty();
    private final TestDataSink testDataSink;

    public TestDataMtRunner(
            final Traversable<TestDataSource> testDataSources,
            final TestDataSink testDataSink) {
        Assert.notEmpty(testDataSources, "testDataSources");
        Assert.notNull(testDataSink, "testDataSink");
        this.testDataSources = List.ofAll(testDataSources);
        this.testDataSink = testDataSink;
    }

    public void addProcessor(final TestDataProcessor testDataProcessor) {
        Assert.notNull(testDataProcessor, "testDataProcessor");
        this.testDataProcessors = this.testDataProcessors.append(testDataProcessor);
    }

    public void run() throws TestDataMtException {
        try {
            final Object sourceTestData = this.testDataSources.toJavaParallelStream()
                    .map(TestDataSource::readTestData)
                    .reduce(null, ((testData1, testData2) -> null));
            final Object processedTestData = io.vavr.collection.List.ofAll(this.testDataProcessors)
                    .foldLeft(sourceTestData, ((testData, testDataProcessor) -> testDataProcessor.processTestData(testData)));

            this.testDataSink.consumeTestData(processedTestData);
        } catch (final Exception e) {
            throw new TestDataMtException("error runnning " + getClass(), e);
        }
    }

}
