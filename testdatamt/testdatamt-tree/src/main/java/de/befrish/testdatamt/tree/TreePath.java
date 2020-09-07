package de.befrish.testdatamt.tree;

import de.befrish.testdatamt.id.util.Assert;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * @author Benno Müller
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class TreePath {

    public static final String TREE_PATH_SEPARATOR = "/";

    @EqualsAndHashCode.Include
    private final List<String> nodeNames;

    private TreePath(final List<String> nodeNames) {
        Assert.notNull(nodeNames, "nodeNames");
        nodeNames.forEach(nodeName -> Assert.notNull(nodeName, "nodeName"));
        this.nodeNames = unmodifiableList(new ArrayList<>(nodeNames));
    }

    private TreePath(final String... nodeNames) {
        Assert.notNull(nodeNames, "nodeNames");
        Arrays.stream(nodeNames).forEach(nodeName -> Assert.notNull(nodeName, "nodeName"));
        this.nodeNames = asList(nodeNames);
    }

    public static TreePath get(final String... nodeNames) {
        return new TreePath(nodeNames);
    }

    public static TreePath getByNames(final TreeNodeName... nodeNames) {
        return new TreePath(nodeNames == null ? null
                : Arrays.stream(nodeNames)
                        .map(nodeName -> nodeName == null ? null : nodeName.name())
                        .collect(toList()));
    }

    public boolean isEmpty() {
        return nodeNames.isEmpty();
    }

    public Optional<TreeNodeName> getHead() {
        return nodeNames.isEmpty()
                ? Optional.empty()
                : Optional.of(TreeNodeName.valueOf(nodeNames.get(0)));
    }

    public TreePath getTailPath() {
        return nodeNames.isEmpty()
                ? this
                : new TreePath(nodeNames.subList(1, nodeNames.size()));
    }

    public TreePath getInitPath() {
        return nodeNames.isEmpty()
                ? this
                : new TreePath(nodeNames.subList(0, nodeNames.size() - 1));
    }

    public Optional<TreeNodeName> getLast() {
        return nodeNames.isEmpty()
                ? Optional.empty()
                : Optional.of(TreeNodeName.valueOf(nodeNames.get(nodeNames.size() - 1)));
    }

    public int getDepth() {
        return nodeNames.size();
    }

    public TreePath resolve(final TreePath relativePath) {
        if (relativePath == null || relativePath.nodeNames.isEmpty()) {
            return this;
        }
        final List<String> absoluteNodeIds = new ArrayList<>(
                nodeNames.size() + relativePath.nodeNames.size());
        absoluteNodeIds.addAll(nodeNames);
        absoluteNodeIds.addAll(relativePath.nodeNames);
        return new TreePath(absoluteNodeIds);
    }

    public TreePath resolve(final TreeNodeName... relativePathNodeNames) {
        if (relativePathNodeNames == null) {
            return this;
        }
        return resolve(TreePath.getByNames(relativePathNodeNames));
    }

    public TreePath resolveByRaw(final String... relativePathNodeNames) {
        if (relativePathNodeNames == null) {
            return this;
        }
        return resolve(TreePath.get(relativePathNodeNames));
    }

    public TreePath relativize(final TreePath path) {
        TreePath relativePath = path;
        TreePath rootPath = this;
        while (relativePath.getHead().equals(rootPath.getHead())) {
            relativePath = relativePath.getTailPath();
            rootPath = rootPath.getTailPath();
        }
        return relativePath;
    }

    public TreePath filterEmptyNames() {
        return new TreePath(nodeNames.stream().filter(nodeName -> !nodeName.isEmpty()).collect(toList()));
    }

    @Override
    public String toString() {
        return toString(TREE_PATH_SEPARATOR);
    }

    public String toString(final String separator) {
        return nodeNames.stream().map(Object::toString).collect(joining(separator));
    }

}
