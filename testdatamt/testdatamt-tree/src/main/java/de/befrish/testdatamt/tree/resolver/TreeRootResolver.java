/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.resolver;

import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.visitor.AscendingTreeVisitor;

/**
 * @author Benno Müller
 */
public class TreeRootResolver extends AscendingTreeVisitor<TreeNode> {

    private TreeNode root;

    @Override
    public TreeNode getResults() {
        return root;
    }

    @Override
    protected boolean preVisit(final TreeNode node) {
        if (node.isRoot()) {
            root = node;
        }
        return false;
    }

}
