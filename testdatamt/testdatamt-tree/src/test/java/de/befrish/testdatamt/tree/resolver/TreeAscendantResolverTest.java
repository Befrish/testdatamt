/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.resolver;

import de.befrish.testdatamt.tree.DefaultTreeNode;
import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreeNodeType;
import org.junit.Test;

import java.util.Optional;

import static de.befrish.testdatamt.tree.util.TestTreeNodeType.TEST_TYPE;
import static de.befrish.testdatamt.tree.util.TreeNodeUtils.nodeName;
import static de.befrish.testdatamt.tree.util.TreeVisitorUtils.applyVisitor;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Benno Müller
 */
public class TreeAscendantResolverTest {

    private static final String ROOT_NODE_NAME = "root";
    private static final String PARENT_NODE_NAME = "parent";
    private static final String NODE_NAME = "node";
    private static final String OTHER_NODE_NAME = "other";
    private static final String NON_EXISTING_NODE_NAME = "nodeX";
    private static final TreeNodeType NODE_TYPE = TEST_TYPE;

    @Test
    public void findsAscendant() throws Exception {
        final TreeNode rootNode = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        rootNode.addChild(parentNode);
        parentNode.addChild(treeNode);

        final TreeAscendantResolver visitor = new TreeAscendantResolver(nodeName(ROOT_NODE_NAME));
        final Optional<TreeNode> resultNode = applyVisitor(visitor, treeNode);

        assertThat(resultNode, is(Optional.of(rootNode)));
    }

    @Test
    public void findsNoAscendantWhenNoSuchPathExists() throws Exception {
        final TreeNode rootNode = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        rootNode.addChild(parentNode);
        parentNode.addChild(treeNode);

        final TreeAscendantResolver visitor = new TreeAscendantResolver(nodeName(NON_EXISTING_NODE_NAME));
        final Optional<TreeNode> resultNode = applyVisitor(visitor, treeNode);

        assertThat(resultNode, is(Optional.empty()));
    }

    @Test
    public void findsNoAscendantWhenNoSuchAscendentExists() throws Exception {
        final TreeNode rootNode = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        final TreeNode otherNode = new DefaultTreeNode(OTHER_NODE_NAME, NODE_TYPE);
        rootNode.addChild(parentNode);
        rootNode.addChild(otherNode);
        parentNode.addChild(treeNode);

        final TreeAscendantResolver visitor = new TreeAscendantResolver(nodeName(OTHER_NODE_NAME));
        final Optional<TreeNode> resultNode = applyVisitor(visitor, treeNode);

        assertThat(resultNode, is(Optional.empty()));
    }

}
