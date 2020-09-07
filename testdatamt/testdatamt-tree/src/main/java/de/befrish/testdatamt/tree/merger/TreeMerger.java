/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.merger;

import de.befrish.testdatamt.tree.TreeNode;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Benno Müller
 */
public class TreeMerger {

    /**
     * Die Knoten von Baum 1 werden ergänzt durch Knoten von Baum 2, falls die Wurzel gleich ist (gleicher Name des
     * Knotens).
     *
     * @param tree1 Baum 1 (gleichzeitig auch Ergebnis)
     * @param tree2 Baum 2 (kann ebenfalls geändert werden, wenn Knoten in Baum 1 umgehangen werden)
     * @return Änderungen bei Merge?
     */
    public boolean merge(final TreeNode tree1, final TreeNode tree2) {
        if (Objects.equals(tree1, tree2)) {
            new ArrayList<>(tree2.getChildren())
                    .forEach(child2 -> {
                        final Optional<TreeNode> child1 = tree1.getChild(child2.getName());
                        if (child1.isPresent()) {
                            merge(child1.get(), child2);
                        } else {
                            tree1.addChild(child2);
                        }
                    });
            return true;
        }
        return false;
    }

}
