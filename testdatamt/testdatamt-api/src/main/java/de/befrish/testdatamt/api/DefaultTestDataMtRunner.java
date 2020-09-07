/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.api;

import de.befrish.testdatamt.id.util.Assert;
import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.merger.TreeMerger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Benno Müller
 */
public class DefaultTestDataMtRunner implements TestDataMtRunner {

    private final List<TestDataSource> testDataSources;
    private final List<TestDataProcessor> testDataProcessors = new ArrayList<>();
    private final TestDataSink testDataSink;

    public DefaultTestDataMtRunner(
            final Collection<TestDataSource> testDataSources,
            final TestDataSink testDataSink) {
        Assert.notEmpty(testDataSources, "testDataSources");
        Assert.notNull(testDataSink, "testDataSink");
        this.testDataSources = new ArrayList<>(testDataSources);
        this.testDataSink = testDataSink;
    }

    public void addProcessor(final TestDataProcessor testDataProcessor) {
        Assert.notNull(testDataProcessor, "testDataProcessor");
        testDataProcessors.add(testDataProcessor);
    }

    @Override
    public void run() throws TestDataMtException {
        final TreeNode source = testDataSources.parallelStream()
                .map(TestDataSource::readTestData)
                .reduce(null, ((treeNode1, treeNode2) -> new TreeMerger().merge(treeNode1, treeNode2)
                        .orElseThrow(() -> new TestDataMtException("cannot merge trees with different roots"))));
        final TreeNode processed = io.vavr.collection.List.ofAll(testDataProcessors)
                .foldLeft(source, ((treeNode, testDataProcessor) -> testDataProcessor.processTestData(treeNode)));

        testDataSink.consumeTestData(processed);
    }

}
