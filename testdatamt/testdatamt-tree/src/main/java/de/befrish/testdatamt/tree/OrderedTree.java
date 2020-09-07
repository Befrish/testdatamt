package de.befrish.testdatamt.tree;

import de.befrish.testdatamt.id.util.Assert;
import de.befrish.testdatamt.tree.visitor.CollectingTreeVisitor;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.befrish.testdatamt.tree.util.TreeVisitorUtils.applyVisitor;

/**
 * Bietet zusätzlich eine Methode an, welche alle Knoten und Unterknoten in einer geordneten Liste zurückgibt.
 *
 * @author Benno Müller
 */
public class OrderedTree {

    private static final int DEFAULT_NODE_ORDER_VALUE = Integer.MAX_VALUE;

    private final TreeNode rootNode;
    private final Map<TreePath, Integer> childOrder = new HashMap<>();

    public OrderedTree(final TreeNode rootNode) {
        Assert.notNull(rootNode, "rootNode");
        this.rootNode = rootNode;
    }

    public TreeNode getRootNode() {
        return rootNode;
    }

    public List<TreeNode> getAllNodesSorted() {
        final List<TreeNode> nodes = applyVisitor(new CollectingTreeVisitor(), rootNode);
        Collections.sort(nodes, Comparator.comparing(n -> getOrder(n.getPath())));
        return nodes;
    }

    public int getOrder(final TreePath childPath) {
        return childOrder.getOrDefault(childPath, DEFAULT_NODE_ORDER_VALUE);
    }

    public void setOrder(final TreePath childPath, final int orderValue) {
        Assert.notNull(childPath, "childPath");
        childOrder.put(childPath, orderValue);
    }

    public void removeOrder(final TreePath childPath) {
        childOrder.remove(childPath);
    }

}

