package de.befrish.testdatamt.tree.nodefinder;

import de.befrish.testdatamt.tree.NoSuchTreeNodeException;
import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreePath;

import java.util.Optional;

/**
 * @author Benno Müller
 */
public interface TreeNodeFinder {

    Optional<TreeNode> findNode(final TreePath path);

    default TreeNode findExistingNode(final TreePath path) throws NoSuchTreeNodeException {
        return findNode(path).orElseThrow(() -> new NoSuchTreeNodeException(path));
    }

}
