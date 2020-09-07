package de.befrish.testdatamt.tree;

import de.befrish.testdatamt.id.util.Assert;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Benno Müller
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class AliasTreeNode implements TreeNode {

    @EqualsAndHashCode.Include
    @ToString.Include
    private final TreeNodeName aliasName;

    private final TreeNode delegate;

    public AliasTreeNode(final TreeNodeName aliasName, final TreeNode delegateNode) {
        Assert.notNull(aliasName, "aliasName");
        Assert.notNull(delegateNode, "delegateNode");
        this.aliasName = aliasName;
        this.delegate = delegateNode;

        updatePath();
    }

    public AliasTreeNode(final String aliasName, final TreeNode delegateNode) {
        this(TreeNodeName.valueOf(aliasName), delegateNode);
    }

    public TreeNodeName getDelegateName() {
        return delegate.getName();
    }

    @Override
    public TreeNodeName getName() {
        return aliasName;
    }

    @Override
    public TreeNodeType getType() {
        return delegate.getType();
    }

    @Override
    public Object getContent() {
        return delegate.getContent();
    }

    @Override
    public void setContent(final Object content) {
        delegate.setContent(content);
    }

    @Override
    public void setContentProvider(final Supplier<Object> content) {
        delegate.setContentProvider(content);
    }

    @Override
    public TreePath getPath() {
        return delegate.getPath();
    }

    @Override
    public Optional<TreeNode> getParent() {
        return delegate.getParent();
    }

    @Override
    public void setParent(final TreeNode parent) {
        delegate.setParent(parent);
    }

    @Override
    public Collection<TreeNode> getChildren() {
        return delegate.getChildren();
    }

    @Override
    public Collection<TreeNodeReference> getReferences() {
        return delegate.getReferences();
    }

    @Override
    public Collection<TreeNodeLabel> getLabels() {
        return delegate.getLabels();
    }

    @Override
    public boolean isRoot() {
        return delegate.isRoot();
    }

    @Override
    public boolean isLeaf() {
        return delegate.isLeaf();
    }

    @Override
    public final void updatePath() {
        delegate.updatePath();
    }

    @Override
    public Collection<TreeNode> getChildren(final Predicate<TreeNode> childPredicate) {
        return delegate.getChildren(childPredicate);
    }

    @Override
    public Collection<TreeNode> getChildren(final TreeNodeType childType) {
        return delegate.getChildren(childType);
    }

    @Override
    public Collection<TreeNode> getChildren(final TreeNodeLabel childLabel) {
        return delegate.getChildren(childLabel);
    }

    @Override
    public Optional<TreeNode> getChild(final Predicate<TreeNode> childPredicate) {
        return delegate.getChild(childPredicate);
    }

    @Override
    public Optional<TreeNode> getChild(final TreeNodeName childName) {
        return delegate.getChild(childName);
    }

    @Override
    public Optional<TreeNode> getChild(final TreeNodeType childType) {
        return delegate.getChild(childType);
    }

    @Override
    public boolean addChild(final TreeNode node) {
        return delegate.addChild(node);
    }

    @Override
    public boolean removeChild(final TreeNode node) {
        return delegate.removeChild(node);
    }

    @Override
    public Collection<TreeNodeReference> getReferences(final TreeNodeReferenceType type) {
        return delegate.getReferences(type);
    }

    @Override
    public Optional<TreeNodeReference> getReference(final TreeNodeReferenceType type) {
        return delegate.getReference(type);
    }

    @Override
    public boolean addReference(final TreeNodeReference reference) {
        return delegate.addReference(reference);
    }

    @Override
    public boolean addReferenceTo(final TreePath target, final TreeNodeReferenceType type) {
        return delegate.addReferenceTo(target, type);
    }

    @Override
    public boolean removeReference(final TreeNodeReference reference) {
        return delegate.removeReference(reference);
    }

    @Override
    public boolean removeReferenceTo(final TreePath target, final TreeNodeReferenceType type) {
        return delegate.removeReferenceTo(target, type);
    }

    @Override
    public boolean hasLabel(final TreeNodeLabel label) {
        return delegate.hasLabel(label);
    }

    @Override
    public boolean addLabel(final TreeNodeLabel label) {
        return delegate.addLabel(label);
    }

    @Override
    public boolean removeLabel(final TreeNodeLabel label) {
        return delegate.removeLabel(label);
    }

    @Override
    public void accept(final TreeVisitor visitor) {
        visitor.visit(this);
    }

}
