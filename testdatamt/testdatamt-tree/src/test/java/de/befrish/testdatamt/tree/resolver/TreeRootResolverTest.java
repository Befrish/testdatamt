/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.resolver;

import de.befrish.testdatamt.tree.DefaultTreeNode;
import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreeNodeType;
import org.junit.Before;
import org.junit.Test;

import static de.befrish.testdatamt.tree.util.TestTreeNodeType.TEST_TYPE;
import static de.befrish.testdatamt.tree.util.TreeVisitorUtils.applyVisitor;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Benno Müller
 */
public class TreeRootResolverTest {

    private static final String ROOT_NODE_NAME = "root";
    private static final String PARENT_NODE_NAME = "parent";
    private static final String NODE_NAME = "node";
    private static final TreeNodeType NODE_TYPE = TEST_TYPE;

    private TreeRootResolver visitor;

    @Before
    public void setUp() throws Exception {
        visitor = new TreeRootResolver();
    }

    @Test
    public void findsRootWhenRoot() throws Exception {
        final TreeNode rootNode = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);

        final TreeNode resultNode = applyVisitor(visitor, rootNode);

        assertThat(resultNode, is(rootNode));
    }

    @Test
    public void findsRootWhenNoRoot() throws Exception {
        final TreeNode rootNode = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        rootNode.addChild(parentNode);
        parentNode.addChild(treeNode);

        final TreeNode resultNode = applyVisitor(visitor, treeNode);

        assertThat(resultNode, is(rootNode));
    }

}
