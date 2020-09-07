/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.util;

import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreeNodeLabel;
import de.befrish.testdatamt.tree.TreeNodeName;
import de.befrish.testdatamt.tree.TreeNodeReference;
import de.befrish.testdatamt.tree.TreeNodeReferenceType;
import de.befrish.testdatamt.tree.TreeNodeType;
import de.befrish.testdatamt.tree.TreePath;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Objects;

import static org.hamcrest.Matchers.not;

/**
 * @author Benno Müller
 */
public final class TreeMatchers {

    private TreeMatchers() {
        super();
    }

    public static Matcher<TreeNode> hasName(final TreeNodeName nodeName) {
        return new TypeSafeDiagnosingMatcher<TreeNode>() {
            @Override
            protected boolean matchesSafely(final TreeNode node, final Description mismatchDescription) {
                mismatchDescription.appendText("node with name [")
                        .appendValue(node.getName())
                        .appendText("]");
                return Objects.equals(node.getName(), nodeName);
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("node with name [")
                        .appendValue(nodeName)
                        .appendText("]");
            }
        };
    }

    public static Matcher<TreeNode> hasName(final String nodeName) {
        return hasName(TreeNodeName.valueOf(nodeName));
    }

    public static Matcher<TreeNode> hasType(final TreeNodeType nodeType) {
        return new TypeSafeDiagnosingMatcher<TreeNode>() {
            @Override
            protected boolean matchesSafely(final TreeNode node, final Description mismatchDescription) {
                mismatchDescription.appendText("node of type [")
                        .appendValue(node.getType())
                        .appendText("]");
                return Objects.equals(node.getType(), nodeType);
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("node of type [")
                        .appendValue(nodeType)
                        .appendText("]");
            }
        };
    }

    public static Matcher<TreeNode> hasContent(final Object nodeContent) {
        return new TypeSafeDiagnosingMatcher<TreeNode>() {
            @Override
            protected boolean matchesSafely(final TreeNode node, final Description mismatchDescription) {
                mismatchDescription.appendText("node with content [")
                        .appendValue(node.getContent())
                        .appendText("]");
                return Objects.equals(node.getContent(), nodeContent);
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("node with content [")
                        .appendValue(nodeContent)
                        .appendText("]");
            }
        };
    }

    public static Matcher<TreeNode> root() {
        return new TypeSafeDiagnosingMatcher<TreeNode>() {
            @Override
            protected boolean matchesSafely(final TreeNode node, final Description mismatchDescription) {
                mismatchDescription.appendText("no root node");
                return node.isRoot();
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("root node");
            }
        };
    }

    public static Matcher<TreeNode> leaf() {
        return new TypeSafeDiagnosingMatcher<TreeNode>() {
            @Override
            protected boolean matchesSafely(final TreeNode node, final Description mismatchDescription) {
                mismatchDescription.appendText("no leaf node");
                return node.isLeaf();
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("leaf node");
            }
        };
    }

    public static Matcher<TreeNode> hasChildrenCount(final int childrenCount) {
        return new TypeSafeDiagnosingMatcher<TreeNode>() {
            @Override
            protected boolean matchesSafely(final TreeNode node, final Description mismatchDescription) {
                mismatchDescription
                        .appendText("node with ")
                        .appendValue(node.getChildren().size())
                        .appendText(" children");
                return node.getChildren().size() == childrenCount;
            }

            @Override
            public void describeTo(final Description description) {
                description
                        .appendText("node with ")
                        .appendValue(childrenCount)
                        .appendText(" children");
            }
        };
    }

    public static Matcher<TreeNode> hasParent(final Matcher<TreeNode> parentNodeMatcher) {
        return new TypeSafeDiagnosingMatcher<TreeNode>() {
            @Override
            protected boolean matchesSafely(final TreeNode node, final Description mismatchDescription) {
                final boolean matches = node.getParent().map(parentNodeMatcher::matches).orElse(false);
                if (!matches) {
                    mismatchDescription
                            .appendText("node with parent [")
                            .appendValue(node.getParent())
                            .appendText("]");
                }
                return matches;
            }

            @Override
            public void describeTo(final Description description) {
                description
                        .appendText("parent node [")
                        .appendDescriptionOf(parentNodeMatcher)
                        .appendText("]");
            }
        };
    }

    public static Matcher<TreeNode> hasChild(final Matcher<TreeNode> childNodeMatcher) {
        return new TypeSafeDiagnosingMatcher<TreeNode>() {
            @Override
            protected boolean matchesSafely(final TreeNode node, final Description mismatchDescription) {
                final boolean matches = node.getChildren().stream().anyMatch(childNodeMatcher::matches);
                if (!matches) {
                    mismatchDescription
                            .appendText("node with children ")
                            .appendValueList("[", ",", "]", node.getChildren());
                }
                return matches;
            }

            @Override
            public void describeTo(final Description description) {
                description
                        .appendText("child node [")
                        .appendDescriptionOf(childNodeMatcher)
                        .appendText("]");
            }
        };
    }

    public static Matcher<TreeNode> hasChild(final TreeNodeName childNodeName) {
        return hasChild(hasName(childNodeName));
    }

    public static Matcher<TreeNode> hasChild(final String childNodeName) {
        return hasChild(TreeNodeName.valueOf(childNodeName));
    }

    public static Matcher<TreeNode> hasNoChildOfType(final TreeNodeType childNodeType) {
        return not(hasChild(hasType(childNodeType)));
    }

    public static Matcher<TreeNode> hasReference(
            final TreePath referenceTarget,
            final TreeNodeReferenceType referenceType) {
        final TreeNodeReference nodeReference = new TreeNodeReference(referenceTarget, referenceType);
        return new TypeSafeDiagnosingMatcher<TreeNode>() {
            @Override
            protected boolean matchesSafely(final TreeNode node, final Description mismatchDescription) {
                final boolean matches = node.getReferences().contains(nodeReference);
                if (!matches) {
                    mismatchDescription
                            .appendText("node with references ")
                            .appendValueList("[", ",", "]", node.getReferences());
                }
                return matches;
            }

            @Override
            public void describeTo(final Description description) {
                description
                        .appendText("node with reference [")
                        .appendValue(nodeReference)
                        .appendText("]");
            }
        };
    }

    public static Matcher<TreeNode> hasNoReferenceOfType(final TreeNodeReferenceType referenceType) {
        return new TypeSafeDiagnosingMatcher<TreeNode>() {
            @Override
            protected boolean matchesSafely(final TreeNode node, final Description mismatchDescription) {
                final boolean matches = !node.getReference(referenceType).isPresent();
                if (!matches) {
                    mismatchDescription
                            .appendText("node with references ")
                            .appendValueList("[", ",", "]", node.getReferences());
                }
                return matches;
            }

            @Override
            public void describeTo(final Description description) {
                description
                        .appendText("node with no reference [type=")
                        .appendValue(referenceType)
                        .appendText("]");
            }
        };
    }

    public static Matcher<TreeNode> hasLabel(final TreeNodeLabel nodeLabel) {
        return new TypeSafeDiagnosingMatcher<TreeNode>() {
            @Override
            protected boolean matchesSafely(final TreeNode node, final Description mismatchDescription) {
                final boolean matches = node.hasLabel(nodeLabel);
                if (!matches) {
                    mismatchDescription
                            .appendText("node with labels ")
                            .appendValueList("[", ",", "]", node.getLabels());
                }
                return matches;
            }

            @Override
            public void describeTo(final Description description) {
                description
                        .appendText("node with label [")
                        .appendValue(nodeLabel)
                        .appendText("]");
            }
        };
    }

}
