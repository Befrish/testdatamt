/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.api;

import de.befrish.testdatamt.util.Assert;

import io.vavr.collection.List;
import io.vavr.collection.Traversable;

/**
 * @author Benno Müller
 */
public class DefaultTestDataMtRunner implements TestDataMtRunner {

    private final List<TestDataSource> testDataSources;
    private List<TestDataProcessor> testDataProcessors = List.empty();
    private final TestDataSink testDataSink;

    public DefaultTestDataMtRunner(
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

    @Override
    public void run() throws TestDataMtException {
        final Void source = this.testDataSources//.parallelStream()
                .map(TestDataSource::readTestData)
                .reduce(((testData1, testData2) -> null));
        final Void processed = io.vavr.collection.List.ofAll(this.testDataProcessors)
                .foldLeft(source, ((treeNode, testDataProcessor) -> testDataProcessor.processTestData(treeNode)));

        this.testDataSink.consumeTestData(processed);
    }

}
