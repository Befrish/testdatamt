/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.visitor;

import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreeVisitor;

import java.util.ArrayList;

/**
 * @author Benno Müller
 */
public abstract class PreOrderTreeVisitor<R> implements TreeVisitor<R> {

    @Override
    public void visit(final TreeNode node) {
        final boolean finished = preVisit(node);
        if (!finished && !node.isLeaf()) {
            new ArrayList<>(node.getChildren()).forEach(childNode -> childNode.accept(this));
        }
    }

    /**
     *
     * @param node
     * @return is finished? (can skip child nodes?)
     */
    protected abstract boolean preVisit(TreeNode node);

}
