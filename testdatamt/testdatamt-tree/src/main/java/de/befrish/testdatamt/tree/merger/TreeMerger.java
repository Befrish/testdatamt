/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.merger;

import de.befrish.testdatamt.tree.DefaultTreeNode;
import de.befrish.testdatamt.tree.TreeNode;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Benno Müller
 */
public final class TreeMerger {

    /**
     * Die Knoten von Baum 1 werden ergänzt durch Knoten von Baum 2, falls die Wurzel gleich ist (gleicher Name des
     * Knotens).
     *
     * @param tree1 Baum 1 (gleichzeitig auch Ergebnis)
     * @param tree2 Baum 2 (kann ebenfalls geändert werden, wenn Knoten in Baum 1 umgehangen werden)
     * @return Änderungen bei Merge?
     */
    public Optional<TreeNode> merge(final TreeNode tree1, final TreeNode tree2) {
        if (Objects.isNull(tree1)) {
            return Optional.ofNullable(tree2);
        }
        if (Objects.isNull(tree2)) {
            return Optional.of(tree1);
        }
        return Objects.equals(tree1, tree2) ? Optional.of(safeMerge(tree1, tree2)) : Optional.empty();
    }

    private TreeNode safeMerge(final TreeNode tree1, final TreeNode tree2) {
        final TreeNode resultTree = new DefaultTreeNode(tree1.getName(), tree1.getType()); // TODO type can be not equals
        final Seq<TreeNode> mergedChildren = List.ofAll(tree1.getChildren()).appendAll(tree2.getChildren())
                .groupBy(TreeNode::getName)
                .map((treeNodeName, treeNodes) -> treeNodes.size() == 1
                        ? new Tuple2<>(treeNodeName, treeNodes.get(0))
                        : new Tuple2<>(treeNodeName, treeNodes.reduce(this::safeMerge)))
                .values();
        mergedChildren.forEach(resultTree::addChild);
        return resultTree;
    }

}
