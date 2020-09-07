package de.befrish.testdatamt.tree;

/**
 * @author Benno Müller
 */
public interface TreeVisitor<R> {

    void visit(TreeNode node);

    R getResults();

}
