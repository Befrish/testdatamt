package de.befrish.testdatamt.tree;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

import static de.befrish.testdatamt.tree.util.TestTreeNodeLabel.TEST_LABEL;
import static de.befrish.testdatamt.tree.util.TestTreeNodeReferenceType.TEST_REFERENCE_TYPE;
import static de.befrish.testdatamt.tree.util.TestTreeNodeReferenceType.TEST_REFERENCE_TYPE_2;
import static de.befrish.testdatamt.tree.util.TestTreeNodeType.TEST_TYPE;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * @author Benno Müller
 */
public class DefaultTreeNodeTest {

    private static final String ROOT_NODE_NAME = "root";
    private static final String ROOT_2_NODE_NAME = "root2";
    private static final String PARENT_NODE_NAME = "parent";
    private static final String NODE_NAME = "node";
    private static final String REFERENCE_NODE_NAME = "reference";
    private static final String REFERENCE_2_NODE_NAME = "reference2";
    private static final TreePath ROOT_NODE_PATH = TreePath.get(ROOT_NODE_NAME);
    private static final TreePath PARENT_NODE_PATH = TreePath.get(ROOT_NODE_NAME, PARENT_NODE_NAME);
    private static final TreePath PARENT_NODE_PATH_2 = TreePath.get(ROOT_2_NODE_NAME, PARENT_NODE_NAME);
    private static final TreePath NODE_PATH = TreePath.get(ROOT_NODE_NAME, PARENT_NODE_NAME, NODE_NAME);
    private static final TreePath NODE_PATH_2 = TreePath.get(ROOT_2_NODE_NAME, PARENT_NODE_NAME, NODE_NAME);
    private static final TreePath REFERENCE_NODE_PATH = TreePath.get(REFERENCE_NODE_NAME);
    private static final TreePath REFERENCE_2_NODE_PATH = TreePath.get(REFERENCE_2_NODE_NAME);
    private static final TreeNodeType NODE_TYPE = TEST_TYPE;
    private static final TreeNodeReferenceType REFERENCE_TYPE = TEST_REFERENCE_TYPE;
    private static final TreeNodeReferenceType REFERENCE_TYPE_2 = TEST_REFERENCE_TYPE_2;
    private static final TreeNodeLabel NODE_LABEL = TEST_LABEL;

    @Test
    public void hasName() throws Exception {
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);

        assertThat(treeNode.getName(), is(TreeNodeName.valueOf(NODE_NAME)));
    }

    @Test
    public void hasType() throws Exception {
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);

        assertThat(treeNode.getType(), is(NODE_TYPE));
    }
    @Test
    public void canHaveContent() throws Exception {
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        treeNode.setContent(BigInteger.ONE);

        assertThat(treeNode.getContent(), is(BigInteger.ONE));
    }

    @Test
    public void rootHasNoParent() throws Exception {
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);

        assertThat(treeNode.getParent(), is(Optional.empty()));
    }

    @Test
    public void nonRootHasParent() throws Exception {
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        parentNode.addChild(treeNode);

        assertThat(treeNode.getParent(), is(Optional.of(parentNode)));
    }

    @Test
    public void leafHasNoChildren() throws Exception {
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);

        assertThat(treeNode.getChildren(), is(empty()));
    }

    @Test
    public void nonLeafHasChildren() throws Exception {
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        parentNode.addChild(treeNode);

        assertThat(parentNode.getChildren(), hasSize(1));
        assertThat(parentNode.getChild(NODE_NAME), is(not(Optional.empty())));
    }

    @Test
    public void canRemoveChild() throws Exception {
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        parentNode.addChild(treeNode);
        parentNode.removeChild(treeNode);

        assertThat(parentNode.getChildren(), is(empty()));
        assertThat(treeNode.getParent(), is(Optional.empty()));
    }

    @Test
    public void hasPath() throws Exception {
        final TreeNode rootNode = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        rootNode.addChild(parentNode);
        parentNode.addChild(treeNode);

        assertThat("root node path", rootNode.getPath(), is(ROOT_NODE_PATH));
        assertThat("parent node path", parentNode.getPath(), is(PARENT_NODE_PATH));
        assertThat("child node path", treeNode.getPath(), is(NODE_PATH));
    }

    @Test
    public void updatesPathAndPathOfAllDescendant() throws Exception {
        final TreeNode rootNode = new DefaultTreeNode(ROOT_NODE_NAME, NODE_TYPE);
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        rootNode.addChild(parentNode);
        parentNode.addChild(treeNode);

        final TreeNode root2Node = new DefaultTreeNode(ROOT_2_NODE_NAME, NODE_TYPE);

        root2Node.addChild(parentNode);

        assertThat("old root node is leaf", rootNode.isLeaf(), is(true));
        assertThat("new root node is not leaf", root2Node.isLeaf(), is(false));
        assertThat("new parent node path", parentNode.getPath(), is(PARENT_NODE_PATH_2));
        assertThat("new child node path", treeNode.getPath(), is(NODE_PATH_2));
    }

    @Test
    public void findsChild() throws Exception {
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        parentNode.addChild(treeNode);

        assertThat(parentNode.getChild(NODE_NAME), is(Optional.of(treeNode)));
    }

    @Test
    public void findsChildWhenNoChildWithThisNameExists() throws Exception {
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        parentNode.addChild(treeNode);

        assertThat(parentNode.getChild(NODE_NAME + "x"), is(Optional.empty()));
    }

    @Test
    public void canHaveReferences() throws Exception {
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        parentNode.addChild(treeNode);
        treeNode.addReference(new TreeNodeReference(REFERENCE_NODE_PATH, REFERENCE_TYPE));

        assertThat(treeNode.getReferences(), hasSize(1));
        assertThat(treeNode.getReferences(), contains(new TreeNodeReference(REFERENCE_NODE_PATH, REFERENCE_TYPE)));
    }

    @Test
    public void canHaveReferencesTo() throws Exception {
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        parentNode.addChild(treeNode);
        treeNode.addReferenceTo(REFERENCE_NODE_PATH, REFERENCE_TYPE);

        assertThat(treeNode.getReferences(), hasSize(1));
        assertThat(treeNode.getReferences(), contains(new TreeNodeReference(REFERENCE_NODE_PATH, REFERENCE_TYPE)));
    }

    @Test
    public void canRemoveReference() throws Exception {
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        treeNode.addReference(new TreeNodeReference(REFERENCE_NODE_PATH, REFERENCE_TYPE));
        treeNode.removeReference(new TreeNodeReference(REFERENCE_NODE_PATH, REFERENCE_TYPE));

        assertThat(treeNode.getReferences(), is(empty()));
    }

    @Test
    public void canRemoveReferenceTo() throws Exception {
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        treeNode.addReferenceTo(REFERENCE_NODE_PATH, REFERENCE_TYPE);
        treeNode.removeReferenceTo(REFERENCE_NODE_PATH, REFERENCE_TYPE);

        assertThat(treeNode.getReferences(), is(empty()));
    }

    @Test
    public void findsReferencesOfType() throws Exception {
        final TreeNode parentNode = new DefaultTreeNode(PARENT_NODE_NAME, NODE_TYPE);
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        parentNode.addChild(treeNode);
        treeNode.addReferenceTo(REFERENCE_NODE_PATH, REFERENCE_TYPE);
        treeNode.addReferenceTo(REFERENCE_2_NODE_PATH, REFERENCE_TYPE_2);

        final Collection<TreeNodeReference> references = treeNode.getReferences(REFERENCE_TYPE);

        assertThat(references, hasSize(1));
        assertThat(references, contains(new TreeNodeReference(REFERENCE_NODE_PATH, REFERENCE_TYPE)));
    }

    @Test
    public void canHaveLabels() throws Exception {
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        treeNode.addLabel(NODE_LABEL);

        assertThat(treeNode.getLabels(), hasSize(1));
        assertThat(treeNode.getLabels(), contains(NODE_LABEL));
    }

    @Test
    public void canRemoveLabel() throws Exception {
        final TreeNode treeNode = new DefaultTreeNode(NODE_NAME, NODE_TYPE);
        treeNode.addLabel(NODE_LABEL);
        treeNode.removeLabel(NODE_LABEL);

        assertThat(treeNode.getReferences(), is(empty()));
    }

}
