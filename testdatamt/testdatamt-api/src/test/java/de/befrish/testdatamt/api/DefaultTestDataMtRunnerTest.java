/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.api;

import de.befrish.testdatamt.tree.DefaultTreeNode;
import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.util.TestTreeNodeType;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static de.befrish.testdatamt.tree.util.TreeMatchers.hasName;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Benno Müller
 */
public class DefaultTestDataMtRunnerTest {

    private DefaultTestDataMtRunner testDataMtRunner;

    @Before
    public void setUp() throws Exception {
        testDataMtRunner = new DefaultTestDataMtRunner(
                Arrays.asList(new DummyTestDataSource()),
                new DummyTestDataSink()
        );
        testDataMtRunner.addProcessor(new DummyTestDataProcessor());
    }

    @Test
    public void readProcessAndConsume() throws Exception {
        testDataMtRunner.run();
    }

    // TODO Multiple sources
    // TODO Multiple processors

    private static class DummyTestDataSource implements TestDataSource {

        @Override
        public TreeNode readTestData() {
            return new DefaultTreeNode("test1", TestTreeNodeType.TEST_TYPE);
        }
    }

    private static class DummyTestDataProcessor implements TestDataProcessor {

        @Override
        public TreeNode processTestData(final TreeNode rootNode) {
            assertThat(rootNode, hasName("test1"));
            return new DefaultTreeNode("test2", TestTreeNodeType.TEST_TYPE);
        }
    }

    private static class DummyTestDataSink implements TestDataSink {

        @Override
        public void consumeTestData(final TreeNode rootNode) {
            assertThat(rootNode, hasName("test2"));
        }
    }

}