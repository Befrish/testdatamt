/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.util;

import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreeNodeName;
import org.hamcrest.Matcher;

import static de.befrish.testdatamt.tree.util.TreeMatchers.hasChild;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Benno Müller
 */
public final class TreeAssert {

    private TreeAssert() {
        super();
    }

    public static TreeNode assertChildNode(
            final TreeNodeName childNodeName,
            final TreeNode parentNode,
            final Matcher<TreeNode>... childNodeMatchers) {
        assertThat(parentNode, hasChild(childNodeName));
        final TreeNode child = parentNode.getChild(childNodeName).get();
        for (final Matcher<TreeNode> childNodeMatcher : childNodeMatchers) {
            assertThat(child, childNodeMatcher);
        }
        return child;
    }

    public static TreeNode assertChildNode(
            final String childNodeName,
            final TreeNode parentNode,
            final Matcher<TreeNode>... childNodeMatchers) {
        return assertChildNode(TreeNodeName.valueOf(childNodeName), parentNode, childNodeMatchers);
    }
}
