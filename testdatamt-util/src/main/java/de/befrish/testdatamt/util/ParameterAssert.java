/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.util;

import io.vavr.collection.Traversable;

import java.util.Collection;

/**
 * @author Benno Müller
 */
public final class ParameterAssert {

    private ParameterAssert() {
        super();
    }

    public static void notNull(final Object object, final String parameterName) throws IllegalArgumentException {
        if (object == null) {
            throw new IllegalArgumentException(
                    new StringBuilder(50)
                    .append("The parameter [")
                    .append(parameterName)
                    .append("] must not be null!")
                    .toString());
        }
    }

    public static void notBlank(final String string, final String parameterName) throws IllegalArgumentException {
        notNull(string, parameterName);
        if (string.isBlank()) {
            throw new IllegalArgumentException(
                    new StringBuilder(50)
                    .append("The parameter [")
                    .append(parameterName)
                    .append("] must not be blank!")
                    .toString());
        }
    }

    public static void notEmpty(final Collection<?> collection, final String parameterName) throws IllegalArgumentException {
        notNull(collection, parameterName);
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(
                    new StringBuilder(50)
                            .append("The parameter [")
                            .append(parameterName)
                            .append("] must not be empty!")
                            .toString());
        }
    }

    public static void notEmpty(final Traversable<?> traversable, final String parameterName) throws IllegalArgumentException {
        notNull(traversable, parameterName);
        if (traversable.isEmpty()) {
            throw new IllegalArgumentException(
                    new StringBuilder(50)
                            .append("The parameter [")
                            .append(parameterName)
                            .append("] must not be empty!")
                            .toString());
        }
    }
}
