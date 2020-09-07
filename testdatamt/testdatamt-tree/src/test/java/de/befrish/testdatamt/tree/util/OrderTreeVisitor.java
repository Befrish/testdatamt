/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.util;

import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreePath;
import de.befrish.testdatamt.tree.TreeVisitor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Benno Müller
 */
public class OrderTreeVisitor implements TreeVisitor<Map<TreePath, Integer>> {

    private final Map<TreePath, Integer> visitedNodes = new HashMap<>();
    private final AtomicInteger visitCounter = new AtomicInteger();

    @Override
    public void visit(final TreeNode node) {
        visitedNodes.put(node.getPath(), visitCounter.incrementAndGet());
    }

    @Override
    public Map<TreePath, Integer> getResults() {
        return visitedNodes;
    }

}
