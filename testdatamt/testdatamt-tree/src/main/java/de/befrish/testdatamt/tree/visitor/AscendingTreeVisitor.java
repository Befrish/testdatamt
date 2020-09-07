/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.visitor;

import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreeVisitor;

/**
 * @author Benno Müller
 */
public abstract class AscendingTreeVisitor<R> implements TreeVisitor<R> {

    @Override
    public void visit(final TreeNode node) {
        final boolean finished = preVisit(node);
        if (!finished) {
            node.getParent().ifPresent(this::visit);
        }
    }

    protected abstract boolean preVisit(final TreeNode node);
}
