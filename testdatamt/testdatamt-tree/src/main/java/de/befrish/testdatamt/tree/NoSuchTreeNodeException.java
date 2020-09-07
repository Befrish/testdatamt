/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree;

/**
 * @author Benno Müller
 */
public class NoSuchTreeNodeException extends RuntimeException {

    private static final long serialVersionUID = 9019823159190240752L;

    public NoSuchTreeNodeException(final TreePath path) {
        super("path=" + path.toString());
    }

    public NoSuchTreeNodeException(final String message) {
        super(message);
    }
}
