/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.merger;

import de.befrish.testdatamt.tree.AliasTreeNode;
import de.befrish.testdatamt.tree.DefaultTreeNode;
import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreeNodeType;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static de.befrish.testdatamt.tree.util.TestTreeNodeType.TEST_TYPE;
import static de.befrish.testdatamt.tree.util.TreeAssert.assertChildNode;
import static de.befrish.testdatamt.tree.util.TreeMatchers.hasChild;
import static de.befrish.testdatamt.tree.util.TreeMatchers.hasName;
import static de.befrish.testdatamt.tree.util.TreeVisitorUtils.logTree;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * @author Benno Müller
 */
public class TreeMergerTest {

    private static final String ROOT_NODE_NAME = "root";
    private static final String ROOT_NODE_2_NAME = "root2";
    private static final String PARENT_NODE_NAME = "parent";
    private static final String NODE_NAME = "node";
    private static final String NODE_2_NAME = "node2";
    private static final String ALIAS_NODE_NAME = "alias";
    private static final TreeNodeType NODE_TYPE = TEST_TYPE;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private TreeMerger treeMerger;

    @Before
    public void setUp() {
        treeMerger = new TreeMerger();
    }

    @Test
    public void returnNothingWhenBothTreesAreNull() throws Exception {
        final Optional<TreeNode> merged = treeMerger.merge(null, null);

        assertThat(merged, is(Optional.empty()));
    }

    @Test
    public void returnFirstTreeWhenSecondIsNull() throws Exception {
        final TreeNode rootNode1 = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode1 = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        rootNode1.addChild(treeNode1);

        final Optional<TreeNode> merged = treeMerger.merge(rootNode1, null);
        assertThat(merged.isPresent(), is(true));

        assertThat(merged.get(), hasName(ROOT_NODE_NAME));
        assertThat(merged.get(), hasChild(NODE_NAME));
        assertThat(merged.get(), not(hasChild(NODE_2_NAME)));
    }

    @Test
    public void returnSecondTreeWhenFirstIsNull() throws Exception {
        final TreeNode rootNode2 = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode2 = new DefaultTreeNode(NODE_2_NAME, NODE_TYPE);
        rootNode2.addChild(treeNode2);

        final Optional<TreeNode> merged = treeMerger.merge(null, rootNode2);
        assertThat(merged.isPresent(), is(true));

        assertThat(merged.get(), hasName(ROOT_NODE_NAME));
        assertThat(merged.get(), hasChild(NODE_2_NAME));
        assertThat(merged.get(), not(hasChild(NODE_NAME)));
    }

    @Test
    public void mergeNoNodeWhenRootNodeNotEqual() {
        final TreeNode rootNode1 = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode1 = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        rootNode1.addChild(treeNode1);
        final TreeNode rootNode2 = new DefaultTreeNode(ROOT_NODE_2_NAME, NODE_TYPE);
        final TreeNode treeNode2 = new DefaultTreeNode(NODE_2_NAME, NODE_TYPE);
        rootNode2.addChild(treeNode2);

        final Optional<TreeNode> merged = treeMerger.merge(rootNode1, rootNode2);

        assertThat(merged, is(Optional.empty()));
    }

    @Test
    public void mergeNodeWhenRootNodeEqual() {
        final TreeNode rootNode1 = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode1 = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        rootNode1.addChild(treeNode1);
        final TreeNode rootNode2 = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode2 = new DefaultTreeNode(NODE_2_NAME, NODE_TYPE);
        rootNode2.addChild(treeNode2);

        final Optional<TreeNode> merged = treeMerger.merge(rootNode1, rootNode2);
        assertThat(merged.isPresent(), is(true));

        logTree(logger, merged.get());
        assertChildNode(NODE_NAME, merged.get());
        assertChildNode(NODE_2_NAME, merged.get());
    }

    @Test
    public void mergeNode2WhenRootNodeEqual() {
        // eine Zwischenebene
        final TreeNode rootNode1 = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode parentNode1 = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode1 = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        parentNode1.addChild(treeNode1);
        rootNode1.addChild(parentNode1);
        final TreeNode rootNode2 = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode parentNode2 = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode2 = new DefaultTreeNode(NODE_2_NAME, NODE_TYPE);
        parentNode2.addChild(treeNode2);
        rootNode2.addChild(parentNode2);

        final Optional<TreeNode> merged = treeMerger.merge(rootNode1, rootNode2);
        assertThat(merged.isPresent(), is(true));

        logTree(logger, merged.get());
        final TreeNode resultParentNode = assertChildNode(PARENT_NODE_NAME, merged.get());
        assertChildNode(NODE_NAME, resultParentNode);
        assertChildNode(NODE_2_NAME, resultParentNode);
    }

    @Test
    public void mergeAlias1() {
        final TreeNode rootNode1 = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode1 = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        final TreeNode aliasTreeNode1 = new AliasTreeNode(ALIAS_NODE_NAME, treeNode1);
        rootNode1.addChild(treeNode1);
        rootNode1.addChild(aliasTreeNode1);
        final TreeNode rootNode2 = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode2 = new DefaultTreeNode(NODE_2_NAME, NODE_TYPE);
        rootNode2.addChild(treeNode2);

        final Optional<TreeNode> merged = treeMerger.merge(rootNode1, rootNode2);
        assertThat(merged.isPresent(), is(true));

        logTree(logger, merged.get());
        assertChildNode(NODE_NAME, merged.get());
        assertChildNode(ALIAS_NODE_NAME, merged.get());
        assertChildNode(NODE_2_NAME, merged.get());
    }

    @Test
    public void mergeAlias2() {
        final TreeNode rootNode1 = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode1 = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        rootNode1.addChild(treeNode1);
        final TreeNode rootNode2 = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode2 = new DefaultTreeNode(NODE_2_NAME, NODE_TYPE);
        final TreeNode aliasTreeNode2 = new AliasTreeNode(ALIAS_NODE_NAME, treeNode1);
        rootNode2.addChild(treeNode2);
        rootNode2.addChild(aliasTreeNode2);

        final Optional<TreeNode> merged = treeMerger.merge(rootNode1, rootNode2);
        assertThat(merged.isPresent(), is(true));

        logTree(logger, merged.get());
        assertChildNode(NODE_NAME, merged.get());
        assertChildNode(NODE_2_NAME, merged.get());
        assertChildNode(ALIAS_NODE_NAME, merged.get());
    }

}
