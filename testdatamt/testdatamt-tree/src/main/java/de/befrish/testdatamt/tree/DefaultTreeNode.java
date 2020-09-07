/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree;

import de.befrish.testdatamt.id.map.IdMap;
import de.befrish.testdatamt.id.map.MutableIdMap;
import de.befrish.testdatamt.id.util.Assert;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static de.befrish.testdatamt.tree.util.TreeNodeUtils.nodeLabel;
import static de.befrish.testdatamt.tree.util.TreeNodeUtils.nodeReferenceType;
import static de.befrish.testdatamt.tree.util.TreeNodeUtils.nodeType;
import static java.util.Collections.unmodifiableCollection;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

/**
 * @author Benno Müller
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class DefaultTreeNode implements TreeNode {

    @EqualsAndHashCode.Include
    @ToString.Include
    @Getter
    private final TreeNodeName name;

    @Getter
    private final TreeNodeType type;

    private Supplier<Object> content;

    @Getter
    private TreePath path;
    private TreeNode parent;
    private final IdMap<TreeNode> children;

    private final Set<TreeNodeReference> references;
    private final Set<TreeNodeLabel> labels;


    public DefaultTreeNode(final TreeNodeName name, final TreeNodeType type) {
        Assert.notNull(name, "name");
        Assert.notNull(type, "type");
        this.name = name;
        this.type = type;
        this.parent = null;
        this.children = new MutableIdMap<>();
        this.references = new LinkedHashSet<>();
        this.labels = new LinkedHashSet<>();

        this.path = resolvePath(Optional.empty(), name);
    }

    public DefaultTreeNode(final String name, final TreeNodeType type) {
        this(TreeNodeName.valueOf(name), type);
    }

    @Override
    public Object getContent() {
        return content.get();
    }

    @Override
    public void setContentProvider(final Supplier<Object> content) {
        this.content = content;
    }

    @Override
    public Optional<TreeNode> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public void setParent(final TreeNode parent) {
        this.parent = parent;
        updatePath();
    }

    @Override
    public Collection<TreeNode> getChildren() {
        return unmodifiableCollection(children.values());
    }

    @Override
    public Collection<TreeNodeReference> getReferences() {
        return unmodifiableCollection(references);
    }

    @Override
    public Collection<TreeNodeLabel> getLabels() {
        return unmodifiableCollection(labels);
    }

    @Override
    public boolean isRoot() {
        return isNull(parent);
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public void updatePath() {
        this.path = resolvePath(getParent(), getName());
        children.forEach(TreeNode::updatePath);
    }

    @Override
    public Collection<TreeNode> getChildren(final Predicate<TreeNode> childPredicate) {
        return children.stream()
                .filter(childPredicate::test)
                .collect(toList());
    }

    @Override
    public Collection<TreeNode> getChildren(final TreeNodeType childType) {
        return getChildren(nodeType(childType));
    }

    @Override
    public Collection<TreeNode> getChildren(final TreeNodeLabel childLabel) {
        return getChildren(nodeLabel(childLabel));
    }

    @Override
    public Optional<TreeNode> getChild(final Predicate<TreeNode> childPredicate) {
        return children.stream()
                .filter(childPredicate::test)
                .findAny();
    }

    @Override
    public Optional<TreeNode> getChild(final TreeNodeName childName) {
        return Optional.ofNullable(children.get(childName));
    }

    @Override
    public Optional<TreeNode> getChild(final TreeNodeType childType) {
        return getChild(nodeType(childType));
    }

    @Override
    public boolean addChild(final TreeNode node) {
        node.getParent().ifPresent(parentNode -> parentNode.removeChild(node));
        node.setParent(this);
        return children.add(node);
    }

    @Override
    public boolean removeChild(final TreeNode node) {
        node.setParent(null);
        return children.remove(node.getId());
    }

    @Override
    public Collection<TreeNodeReference> getReferences(final TreeNodeReferenceType type) {
        return references.stream()
                .filter(nodeReferenceType(type))
                .collect(toList());
    }

    @Override
    public Optional<TreeNodeReference> getReference(final TreeNodeReferenceType type) {
        return references.stream()
                .filter(nodeReferenceType(type))
                .findAny();
    }

    @Override
    public boolean addReference(final TreeNodeReference reference) {
        return references.add(reference);
    }

    @Override
    public boolean addReferenceTo(final TreePath target, final TreeNodeReferenceType type) {
        return addReference(new TreeNodeReference(target, type));
    }

    @Override
    public boolean removeReference(final TreeNodeReference reference) {
        return references.remove(reference);
    }

    @Override
    public boolean removeReferenceTo(final TreePath target, final TreeNodeReferenceType type) {
        return removeReference(new TreeNodeReference(target, type));
    }

    @Override
    public boolean hasLabel(final TreeNodeLabel label) {
        return labels.contains(label);
    }

    @Override
    public boolean addLabel(final TreeNodeLabel label) {
        return labels.add(label);
    }

    @Override
    public boolean removeLabel(final TreeNodeLabel label) {
        return labels.remove(label);
    }

    private static TreePath resolvePath(final Optional<TreeNode> parentNode, final TreeNodeName nodeName) {
        return parentNode
                .map(TreeNode::getPath)
                .map(parentPath -> parentPath.resolve(nodeName))
                .orElse(TreePath.getByNames(nodeName));
    }

    @Override
    public void accept(final TreeVisitor visitor) {
        visitor.visit(this);
    }

}
