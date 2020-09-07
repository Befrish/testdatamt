/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.resolver;

import de.befrish.testdatamt.id.util.Assert;
import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.visitor.AscendingTreeVisitor;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author Benno Müller
 */
public class TreeAscendantResolver extends AscendingTreeVisitor<Optional<TreeNode>> {

    private final Predicate<TreeNode> ascendantPredicate;

    private Optional<TreeNode> ascendantNode = Optional.empty();

    public TreeAscendantResolver(final Predicate<TreeNode> ascendantPredicate) {
        Assert.notNull(ascendantPredicate, "ascendantPredicate");
        this.ascendantPredicate = ascendantPredicate;
    }

    @Override
    public Optional<TreeNode> getResults() {
        return ascendantNode;
    }

    @Override
    protected boolean preVisit(final TreeNode node) {
        if (ascendantPredicate.test(node)) {
            ascendantNode = Optional.of(node);
            return true;
        } else {
            return false;
        }
    }

}
