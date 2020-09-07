package de.befrish.testdatamt.tree.nodefinder;

import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreePath;

import java.util.Optional;

import static de.befrish.testdatamt.tree.util.TreeVisitorUtils.resolveDescendant;
import static de.befrish.testdatamt.tree.util.TreeVisitorUtils.resolveRoot;

/**
 * @author Benno Müller
 */
public class DefaultTreeNodeFinder implements TreeNodeFinder {

    private final TreeNode rootNode;

    public DefaultTreeNodeFinder(final TreeNode node) {
        this.rootNode = resolveRoot(node);
    }

    public Optional<TreeNode> findNode(final TreePath path) {
        return path.getHead().map(rootNode.getName()::equals).orElse(false)
                ? resolveDescendant(rootNode, path.getTailPath())
                : Optional.empty();
    }

}
