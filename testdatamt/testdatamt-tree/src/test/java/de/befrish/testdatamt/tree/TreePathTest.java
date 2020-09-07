/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Benno Müller
 */
public class TreePathTest {

    private static final String NODE_NAME_1 = "node1";
    private static final String NODE_NAME_2 = "node2";
    private static final String NODE_NAME_3 = "node3";

    @Test
    public void canBeEmpty() throws Exception {
        final TreePath emptyTreePath = TreePath.getByNames();

        assertThat(emptyTreePath.isEmpty(), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void supportsNotNullNodeNames() throws Exception {
        TreePath.getByNames((TreeNodeName[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void supportsNotNullNodeNames2() throws Exception {
        TreePath.get((String[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void supportsNotNullNodeName() throws Exception {
        TreePath.getByNames((TreeNodeName) null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void supportsNotNullNodeName2() throws Exception {
        TreePath.get((String) null);
    }

    @Test
    public void hasNodeNames() throws Exception {
        final TreePath treePath = TreePath.get(NODE_NAME_1, NODE_NAME_2, NODE_NAME_3);

        assertThat(treePath.isEmpty(), is(false));
    }

    @Test
    public void canAccessHeadNodeName() throws Exception {
        final TreePath treePath = TreePath.get(NODE_NAME_1, NODE_NAME_2, NODE_NAME_3);

        assertThat(treePath.getHead(), is(Optional.of(TreeNodeName.valueOf(NODE_NAME_1))));
    }

    @Test
    public void noHeadWhenEmpty() throws Exception {
        final TreePath emptyTreePath = TreePath.getByNames();

        assertThat(emptyTreePath.getHead(), is(Optional.empty()));
    }

    @Test
    public void canAccessTailNodeNamesAsPath() throws Exception {
        final TreePath treePath = TreePath.get(NODE_NAME_1, NODE_NAME_2, NODE_NAME_3);

        assertThat(treePath.getTailPath(), is(TreePath.get(NODE_NAME_2, NODE_NAME_3)));
    }

    @Test
    public void emptyTailPathWhenEmpty() throws Exception {
        final TreePath emptyTreePath = TreePath.getByNames();

        assertThat(emptyTreePath.getTailPath(), is(TreePath.getByNames()));
    }

    @Test
    public void canAccessLastNodeName() throws Exception {
        final TreePath treePath = TreePath.get(NODE_NAME_1, NODE_NAME_2, NODE_NAME_3);

        assertThat(treePath.getLast(), is(Optional.of(TreeNodeName.valueOf(NODE_NAME_3))));
    }

    @Test
    public void noLastWhenEmpty() throws Exception {
        final TreePath emptyTreePath = TreePath.getByNames();

        assertThat(emptyTreePath.getLast(), is(Optional.empty()));
    }

    @Test
    public void canAccessInitNodeNamesAsPath() throws Exception {
        final TreePath treePath = TreePath.get(NODE_NAME_1, NODE_NAME_2, NODE_NAME_3);

        assertThat(treePath.getInitPath(), is(TreePath.get(NODE_NAME_1, NODE_NAME_2)));
    }

    @Test
    public void emptyInitPathWhenEmpty() throws Exception {
        final TreePath emptyTreePath = TreePath.getByNames();

        assertThat(emptyTreePath.getInitPath(), is(TreePath.getByNames()));
    }

    @Test
    public void canResolvePathWithPath() throws Exception {
        final TreePath treePath = TreePath.get(NODE_NAME_1, NODE_NAME_2);

        assertThat(treePath.resolve(TreePath.get(NODE_NAME_3)),
                is(TreePath.get(NODE_NAME_1, NODE_NAME_2, NODE_NAME_3)));
    }

    @Test
    public void canResolvePathWithEmptyPath() throws Exception {
        final TreePath treePath = TreePath.get(NODE_NAME_1, NODE_NAME_2);

        assertThat(treePath.resolve(TreePath.getByNames()), is(TreePath.get(NODE_NAME_1, NODE_NAME_2)));
    }

    @Test
    public void canResolvePathWithNodeNames() throws Exception {
        final TreePath treePath = TreePath.get(NODE_NAME_1, NODE_NAME_2);

        assertThat(treePath.resolveByRaw(NODE_NAME_3),
                is(TreePath.get(NODE_NAME_1, NODE_NAME_2, NODE_NAME_3)));
    }

    @Test
    public void canResolvePathWithNoNodeNames() throws Exception {
        final TreePath treePath = TreePath.get(NODE_NAME_1, NODE_NAME_2);

        assertThat(treePath.resolve(), is(TreePath.get(NODE_NAME_1, NODE_NAME_2)));
    }

    @Test
    public void canRelativizePathWithEmptyPath() throws Exception {
        final TreePath treePath1 = TreePath.getByNames();
        final TreePath treePath2 = TreePath.get(NODE_NAME_1, NODE_NAME_2);

        assertThat(treePath1.relativize(treePath2), is(TreePath.get(NODE_NAME_1, NODE_NAME_2)));
    }

    @Test
    public void canRelativizePathWithNonMatchingPath() throws Exception {
        final TreePath treePath1 = TreePath.get(NODE_NAME_1);
        final TreePath treePath2 = TreePath.get(NODE_NAME_2, NODE_NAME_3);

        assertThat(treePath1.relativize(treePath2), is(TreePath.get(NODE_NAME_2, NODE_NAME_3)));
    }

    @Test
    public void canRelativizePathWithMatchingPath() throws Exception {
        final TreePath treePath1 = TreePath.get(NODE_NAME_1);
        final TreePath treePath2 = TreePath.get(NODE_NAME_1, NODE_NAME_2, NODE_NAME_3);

        assertThat(treePath1.relativize(treePath2), is(TreePath.get(NODE_NAME_2, NODE_NAME_3)));
    }

    @Test
    public void canFilterEmptyNodeNames() throws Exception {
        final TreePath treePath = TreePath.get("", NODE_NAME_1, "");

        assertThat(treePath.filterEmptyNames(), is(TreePath.get(NODE_NAME_1)));
    }

}
