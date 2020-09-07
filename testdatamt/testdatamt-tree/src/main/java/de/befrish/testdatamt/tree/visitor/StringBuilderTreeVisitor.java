/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree.visitor;

import de.befrish.testdatamt.tree.AliasTreeNode;
import de.befrish.testdatamt.tree.TreeNode;
import de.befrish.testdatamt.tree.TreeNodeLabel;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

/**
 * @author Benno Müller
 */
public class StringBuilderTreeVisitor extends PreOrderTreeVisitor<String> {

    private static final String TAB = "  ";
    private static final String LEAF = "- ";
    private static final String BRANCH = "+ ";
    private static final String LIST_DELIMITER = ", ";

    private final StringBuilder resultBuilder = new StringBuilder();

    @Override
    public String getResults() {
        return resultBuilder.toString();
    }

    @Override
    protected boolean preVisit(final TreeNode node) {
        resultBuilder
                .append(IntStream.range(1, node.getPath().getDepth())
                        .mapToObj(i -> TAB)
                        .collect(joining()))
                .append(node.isLeaf() ? LEAF : BRANCH)
                .append(node.getName());
        if (node instanceof AliasTreeNode) {
            resultBuilder
                    .append('[')
                    .append(((AliasTreeNode) node).getDelegateName())
                    .append(']');
        }
        resultBuilder
                .append(" {")
                .append(node.getType())
                .append('}');
        if (!node.getLabels().isEmpty()) {
            resultBuilder
                    .append(" - {")
                    .append(node.getLabels().stream()
                            .map(TreeNodeLabel::name)
                            .collect(joining(LIST_DELIMITER)))
                    .append('}');
        }
        if (!node.getReferences().isEmpty()) {
            resultBuilder
                    .append(" -> ")
                    .append(node.getReferences().stream()
                            .map(reference -> "{" + reference.getType() + "} " + reference.getTarget().toString())
                            .collect(joining(LIST_DELIMITER)));
        }
        resultBuilder.append(System.lineSeparator());
        return false;
    }

}
