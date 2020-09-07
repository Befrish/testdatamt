/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static de.befrish.testdatamt.tree.util.TestTreeNodeType.TEST_TYPE;
import static de.befrish.testdatamt.tree.util.TreeVisitorUtils.resolveDescendant;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Benno Müller
 */
public class AliasTreeNodeTest {

    private static final String ROOT_NODE_NAME = "root";
    private static final String PARENT_NODE_NAME = "parent";
    private static final String NODE_NAME = "node";
    private static final String ALIAS_NODE_NAME = "alias";
    private static final TreePath RELATIVE_NODE_PATH = TreePath.get(PARENT_NODE_NAME, NODE_NAME);
    private static final TreePath RELATIVE_ALIAS_NODE_PATH = TreePath.get(PARENT_NODE_NAME, ALIAS_NODE_NAME);
    private static final TreeNodeType NODE_TYPE = TEST_TYPE;

    private TreeNode rootNode;
    private TreeNode parentNode;
    private TreeNode treeNode;
    private TreeNode aliasNode;

    @Before
    public void setUp() throws Exception {
        rootNode = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        aliasNode = new AliasTreeNode(ALIAS_NODE_NAME, treeNode);
        rootNode.addChild(parentNode);
        parentNode.addChild(treeNode);
        parentNode.addChild(aliasNode);
    }

    @Test
    public void findsNodeByAliasName() throws Exception {
        final Optional<TreeNode> result = resolveDescendant(rootNode, RELATIVE_ALIAS_NODE_PATH);

        assertThat(result, is(Optional.of(aliasNode)));
    }

    @Test
    public void findsNodeByOriginName() throws Exception {
        final Optional<TreeNode> result = resolveDescendant(rootNode, RELATIVE_NODE_PATH);

        assertThat(result, is(Optional.of(treeNode)));
    }

}
