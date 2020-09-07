/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.nodefinder;

import de.befrish.testdatamt.tree.DefaultTreeNode;
import de.befrish.testdatamt.tree.NoSuchTreeNodeException;
import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreeNodeType;
import de.befrish.testdatamt.tree.TreePath;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static de.befrish.testdatamt.tree.util.TestTreeNodeType.TEST_TYPE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Benno Müller
 */
public class DefaultTreeNodeFinderTest {

    private static final String ROOT_NODE_NAME = "root";
    private static final String PARENT_NODE_NAME = "parent";
    private static final String NODE_NAME = "node";
    private static final String OTHER_NODE_NAME = "other";
    private static final TreePath ROOT_NODE_PATH = TreePath.get(ROOT_NODE_NAME);
    private static final TreePath NODE_PATH = TreePath.get(ROOT_NODE_NAME, PARENT_NODE_NAME, NODE_NAME);
    private static final TreePath OTHER_NODE_PATH = TreePath.get(ROOT_NODE_NAME, PARENT_NODE_NAME, OTHER_NODE_NAME);
    private static final TreeNodeType NODE_TYPE = TEST_TYPE;

    private TreeNodeFinder treeNodeFinder;

    private TreeNode rootNode;
    private TreeNode parentNode;
    private TreeNode treeNode;

    @Before
    public void setUp() throws Exception {
        rootNode = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        rootNode.addChild(parentNode);
        parentNode.addChild(treeNode);

        treeNodeFinder = new DefaultTreeNodeFinder(rootNode);
    }

    @Test
    public void findsRootNode() throws Exception {
        final Optional<TreeNode> foundNode = treeNodeFinder.findNode(ROOT_NODE_PATH);

        assertThat(foundNode, is(Optional.of(rootNode)));
    }

    @Test
    public void findsRootNode2() throws Exception {
        final TreeNode foundNode = treeNodeFinder.findExistingNode(ROOT_NODE_PATH);

        assertThat(foundNode, is(rootNode));
    }

    @Test
    public void findsChildNode() throws Exception {
        final Optional<TreeNode> foundNode = treeNodeFinder.findNode(NODE_PATH);

        assertThat(foundNode, is(Optional.of(treeNode)));
    }

    @Test
    public void findsChildNode2() throws Exception {
        final TreeNode foundNode = treeNodeFinder.findExistingNode(NODE_PATH);

        assertThat(foundNode, is(treeNode));
    }

    @Test
    public void findsNotOtherChildNode() throws Exception {
        final Optional<TreeNode> foundNode = treeNodeFinder.findNode(OTHER_NODE_PATH);

        assertThat(foundNode, is(Optional.empty()));
    }

    @Test(expected = NoSuchTreeNodeException.class)
    public void findsNotOtherChildNode2() throws Exception {
        treeNodeFinder.findExistingNode(OTHER_NODE_PATH);
    }

}
