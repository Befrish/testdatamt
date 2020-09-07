/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.visitor;

import de.befrish.testdatamt.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Benno Müller
 */
public class CollectingTreeVisitor extends PreOrderTreeVisitor<List<TreeNode>> {

    private List<TreeNode> nodes = new ArrayList<>();

    @Override
    public List<TreeNode> getResults() {
        return nodes;
    }

    @Override
    protected boolean preVisit(final TreeNode node) {
        nodes.add(node);
        return false;
    }

}
