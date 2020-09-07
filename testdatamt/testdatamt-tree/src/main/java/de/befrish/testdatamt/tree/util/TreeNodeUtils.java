/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.util;

import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreeNodeLabel;
import de.befrish.testdatamt.tree.TreeNodeName;
import de.befrish.testdatamt.tree.TreeNodeReference;
import de.befrish.testdatamt.tree.TreeNodeReferenceType;
import de.befrish.testdatamt.tree.TreeNodeType;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Benno Müller
 */
public final class TreeNodeUtils {

    private TreeNodeUtils() {
        super();
    }

    public static Predicate<TreeNode> nodeName(final String nodeName) {
        return nodeName(TreeNodeName.valueOf(nodeName));
    }

    public static Predicate<TreeNode> nodeName(final TreeNodeName nodeName) {
        return node -> node.getName().equals(nodeName);
    }

    public static Predicate<TreeNode> nodeType(final TreeNodeType nodeType) {
        return node -> node.getType().equals(nodeType);
    }

    public static Predicate<TreeNode> nodeLabel(final TreeNodeLabel nodeLabel) {
        return node -> node.hasLabel(nodeLabel);
    }

    public static Predicate<TreeNodeReference> nodeReferenceType(final TreeNodeReferenceType nodeReferenceType) {
        return nodeReference -> nodeReference.getType().equals(nodeReferenceType);
    }

    public static void replaceNode(final TreeNode oldNode, final Supplier<TreeNode> newNodeSupplier) {
        oldNode.getParent().ifPresent(parentNode -> {
            parentNode.removeChild(oldNode);
            parentNode.addChild(newNodeSupplier.get());
        });
    }

}
