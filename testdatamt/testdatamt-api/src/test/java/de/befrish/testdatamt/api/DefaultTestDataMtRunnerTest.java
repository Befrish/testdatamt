/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.api;

import de.befrish.testdatamt.api.processor.IdentityTestDataProcessor;
import de.befrish.testdatamt.api.sink.NoOpTestDataSink;
import de.befrish.testdatamt.api.source.EmptyTestDataSource;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;

import javax.swing.tree.TreeNode;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Benno Müller
 */
public class DefaultTestDataMtRunnerTest {

    private DefaultTestDataMtRunner testDataMtRunner;

    @Before
    public void setUp() {
        this.testDataMtRunner = new DefaultTestDataMtRunner(
                List.of(new EmptyTestDataSource()),
                new NoOpTestDataSink()
        );
        this.testDataMtRunner.addProcessor(new IdentityTestDataProcessor());
    }

    @Test
    public void readProcessAndConsume() {
        this.testDataMtRunner.run();
    }

    // TODO Multiple sources
    // TODO Multiple processors

}