/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree;

import org.junit.Test;

import java.util.List;

import static de.befrish.testdatamt.tree.util.TestTreeNodeType.TEST_TYPE;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

/**
 * @author Benno Müller
 */
public class OrderedTreeTest {

    private static final String ROOT_NODE_NAME = "root";
    private static final String PARENT_NODE_NAME = "parent";
    private static final String NODE_1_NAME = "node1";
    private static final String NODE_2_NAME = "node2";
    private static final TreePath NODE_1_PATH = TreePath.get(ROOT_NODE_NAME, PARENT_NODE_NAME, NODE_1_NAME);
    private static final TreePath NODE_2_PATH = TreePath.get(ROOT_NODE_NAME, PARENT_NODE_NAME, NODE_2_NAME);
    private static final TreeNodeType NODE_TYPE = TEST_TYPE;

    @Test
    public void sortiertNodesEntsprechendDerVorgabe() throws Exception {
        final TreeNode rootNode = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode1 = new DefaultTreeNode(NODE_1_NAME, NODE_TYPE);
        final TreeNode treeNode2 = new DefaultTreeNode(NODE_2_NAME, NODE_TYPE);
        rootNode.addChild(parentNode);
        parentNode.addChild(treeNode1);
        parentNode.addChild(treeNode2);

        final OrderedTree orderedTree = new OrderedTree(rootNode);
        orderedTree.setOrder(NODE_1_PATH, 1);
        orderedTree.setOrder(NODE_2_PATH, 2);

        final List<TreeNode> sortedNodes = orderedTree.getAllNodesSorted();
        final int node1ListIndex = sortedNodes.indexOf(treeNode1);
        final int node2ListIndex = sortedNodes.indexOf(treeNode2);

        assertThat(node1ListIndex, is(lessThan(node2ListIndex)));
    }

    @Test
    public void sortiertNodesOhneVorgabeHintenEin() throws Exception {
        final TreeNode rootNode = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode1 = new DefaultTreeNode(NODE_1_NAME, NODE_TYPE);
        rootNode.addChild(parentNode);
        parentNode.addChild(treeNode1);

        final OrderedTree orderedTree = new OrderedTree(rootNode);
        orderedTree.setOrder(NODE_1_PATH, 1);

        final List<TreeNode> sortedNodes = orderedTree.getAllNodesSorted();

        final int node1ListIndex = sortedNodes.indexOf(treeNode1);
        final int rootNodeListIndex = sortedNodes.indexOf(rootNode);
        final int parentNodeListIndex = sortedNodes.indexOf(parentNode);

        assertThat(rootNodeListIndex, is(greaterThan(node1ListIndex)));
        assertThat(parentNodeListIndex, is(greaterThan(node1ListIndex)));
    }

}
