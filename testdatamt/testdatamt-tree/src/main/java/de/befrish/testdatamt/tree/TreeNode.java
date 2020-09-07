/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree;

import de.befrish.testdatamt.id.Identifiable;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Benno Müller
 */
public interface TreeNode extends Identifiable<TreeNodeName> {

    @Override
    default TreeNodeName getId() {
        return getName();
    }

    TreeNodeName getName();

    TreeNodeType getType();

    Object getContent();

    default void setContent(final Object content) {
        setContentProvider(() -> content);
    }

    void setContentProvider(Supplier<Object> content);

    TreePath getPath();

    Optional<TreeNode> getParent();

    void setParent(TreeNode parent);

    Collection<TreeNode> getChildren();

    Collection<TreeNodeReference> getReferences();

    Collection<TreeNodeLabel> getLabels();

    boolean isRoot();

    boolean isLeaf();

    void updatePath();

    Collection<TreeNode> getChildren(Predicate<TreeNode> childPredicate);

    Collection<TreeNode> getChildren(TreeNodeType childType);

    Collection<TreeNode> getChildren(TreeNodeLabel childLabel);

    Optional<TreeNode> getChild(Predicate<TreeNode> childPredicate);

    Optional<TreeNode> getChild(TreeNodeName childName);

    default Optional<TreeNode> getChild(final String childName) {
        return getChild(TreeNodeName.valueOf(childName));
    }

    Optional<TreeNode> getChild(TreeNodeType childType);

    boolean addChild(TreeNode node);

    boolean removeChild(TreeNode node);

    Collection<TreeNodeReference> getReferences(TreeNodeReferenceType type);

    Optional<TreeNodeReference> getReference(TreeNodeReferenceType type);

    boolean addReference(TreeNodeReference reference);

    boolean addReferenceTo(TreePath target, TreeNodeReferenceType type);

    boolean removeReference(TreeNodeReference reference);

    boolean removeReferenceTo(TreePath target, TreeNodeReferenceType type);

    boolean hasLabel(TreeNodeLabel label);

    boolean addLabel(TreeNodeLabel label);

    boolean removeLabel(TreeNodeLabel label);

    void accept(final TreeVisitor visitor);

}
