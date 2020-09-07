/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.visitor;

import de.befrish.testdatamt.tree.DefaultTreeNode;
import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreeNodeType;
import de.befrish.testdatamt.tree.TreePath;
import de.befrish.testdatamt.tree.util.OrderTreeVisitor;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static de.befrish.testdatamt.tree.util.TestTreeNodeType.TEST_TYPE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Benno Müller
 */
public class PostOrderTreeVisitorTest {

    private static final String PARENT_NODE_NAME = "parent";
    private static final String CHILD_NODE_NAME = "child";
    private static final TreePath PARENT_NODE_PATH = TreePath.get(PARENT_NODE_NAME);
    private static final TreePath CHILD_NODE_PATH = TreePath.get(PARENT_NODE_NAME, CHILD_NODE_NAME);
    private static final TreeNodeType NODE_TYPE = TEST_TYPE;

    private PostOrderTreeVisitor<Map<TreePath, Integer>> visitor;

    @Before
    public void setUp() throws Exception {
        visitor = new PostOrderTreeVisitor<Map<TreePath, Integer>>() {
            private final OrderTreeVisitor orderTreeVisitor = new OrderTreeVisitor();

            @Override
            protected void postVisit(final TreeNode node) {
                orderTreeVisitor.visit(node);
            }

            @Override
            public Map<TreePath, Integer> getResults() {
                return orderTreeVisitor.getResults();
            }
        };
    }

    @Test
    public void visitsNodeAfterChildren() throws Exception {
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode childNode = new DefaultTreeNode(CHILD_NODE_NAME, NODE_TYPE);
        parentNode.addChild(childNode);

        parentNode.accept(visitor);

        final Map<TreePath, Integer> visitOrder = visitor.getResults();

        assertThat(visitOrder.get(PARENT_NODE_PATH), is(2));
        assertThat(visitOrder.get(CHILD_NODE_PATH), is(1));
    }

}
