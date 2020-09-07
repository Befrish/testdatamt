package de.befrish.testdatamt.tree.nodefinder;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreePath;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * @author Benno Müller
 */
public class CachedTreeNodeFinder implements TreeNodeFinder {

    private final TreeNodeFinder nodeFinder;
    private Cache<TreePath, TreeNode> cache = CacheBuilder.newBuilder()
            .initialCapacity(10)
            .maximumSize(1000)
            .build();

    public CachedTreeNodeFinder(final TreeNodeFinder nodeFinder) {
        this.nodeFinder = nodeFinder;
    }

    @Override
    public Optional<TreeNode> findNode(final TreePath path) {
        try {
            return Optional.ofNullable(cache.get(path, () -> nodeFinder.findNode(path).orElse(null)));
        } catch (final ExecutionException e) {
            return Optional.empty();
        }
    }

}
