/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.resolver;

import de.befrish.testdatamt.id.util.Assert;
import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreePath;
import de.befrish.testdatamt.tree.TreeVisitor;

import java.util.Optional;

/**
 * @author Benno Müller
 */
public class TreeDescendantResolver implements TreeVisitor<Optional<TreeNode>> {

    private final TreePath path;

    private Optional<TreeNode> descendantNode;

    public TreeDescendantResolver(final TreePath path) {
        Assert.notNull(path, "path");
        this.path = path;
    }

    @Override
    public Optional<TreeNode> getResults() {
        return descendantNode;
    }

    @Override
    public void visit(final TreeNode node) {
        this.descendantNode = resolveDescendant(node, path);
    }

    private Optional<TreeNode> resolveDescendant(final TreeNode node, final TreePath path) {
        if (path.isEmpty()) {
            return Optional.of(node);
        } else {
            final Optional<TreeNode> foundChild = path.getHead().flatMap(node::getChild);
            return foundChild.flatMap(child -> resolveDescendant(child, path.getTailPath()));
        }
    }

}
