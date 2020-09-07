/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree;

/**
 * @author Benno Müller
 */
public interface TreeNodeName {

    String name();

    default boolean matchesNode(final TreeNode node) {
        return node.getName().name().equals(this.name());
    }

    static TreeNodeName valueOf(final String name) {
        return new DefaultTreeNodeName(name);
    }

}
