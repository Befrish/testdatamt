/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.util;

import de.befrish.testdatamt.id.util.Assert;
import de.befrish.testdatamt.tree.NoSuchTreeNodeException;
import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreeNodeType;
import de.befrish.testdatamt.tree.TreePath;
import de.befrish.testdatamt.tree.TreeVisitor;
import de.befrish.testdatamt.tree.resolver.TreeAscendantResolver;
import de.befrish.testdatamt.tree.resolver.TreeDescendantResolver;
import de.befrish.testdatamt.tree.resolver.TreeRootResolver;
import de.befrish.testdatamt.tree.visitor.StringBuilderTreeVisitor;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.function.Predicate;

import static de.befrish.testdatamt.tree.util.TreeNodeUtils.nodeType;

/**
 * @author Benno Müller
 */
public final class TreeVisitorUtils {

    private TreeVisitorUtils() {
        super();
    }

    public static <R> R applyVisitor(final TreeVisitor<R> visitor, final TreeNode node) {
        node.accept(visitor);
        return visitor.getResults();
    }

    public static void logTree(final Logger logger, final String message, final TreeNode root) {
        Assert.notNull(message, "message");
        if (logger.isDebugEnabled()) {
            logger.debug(message + System.lineSeparator() + "{}", applyVisitor(new StringBuilderTreeVisitor(), root));
        }
    }

    public static void logTree(final Logger logger, final TreeNode root) {
        logTree(logger, "tree:", root);
    }

    public static TreeNode resolveRoot(final TreeNode node) {
        return applyVisitor(new TreeRootResolver(), node);
    }

    public static Optional<TreeNode> resolveDescendant(final TreeNode node, final TreePath path) {
        return applyVisitor(new TreeDescendantResolver(path), node);
    }

    public static TreeNode resolveExistingDescendant(final TreeNode node, final TreePath path)
            throws NoSuchTreeNodeException {
        return resolveDescendant(node, path)
                .orElseThrow(() -> new NoSuchTreeNodeException(node.getPath().resolve(path)));
    }

    public static Optional<TreeNode> resolveAscendant(final TreeNode node, final Predicate<TreeNode> predicate) {
        return applyVisitor(new TreeAscendantResolver(predicate), node);
    }

    public static Optional<TreeNode> resolveAscendant(final TreeNode node, final TreeNodeType nodeType) {
        return resolveAscendant(node, nodeType(nodeType));
    }

    public static TreeNode resolveExistingAscendant(final TreeNode node, final TreeNodeType nodeType)
            throws NoSuchTreeNodeException {
        return resolveAscendant(node, nodeType)
                .orElseThrow(() -> new NoSuchTreeNodeException(
                        "ascendant of node [" + node.getPath() + "] with type [" + nodeType.name() + ']'));
    }

}
