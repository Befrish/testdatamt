/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.id.util;

import java.util.Collection;

/**
 * TODO extra module
 *
 * @author Benno Müller
 */
public final class Assert {

    private Assert() {
        super();
    }

    public static void notNull(Object object, String parameterName) throws IllegalArgumentException {
        if (object == null) {
            throw new IllegalArgumentException(
                    new StringBuilder(50)
                    .append("The parameter [")
                    .append(parameterName)
                    .append("] must not be null!")
                    .toString());
        }
    }

    public static void notEmpty(Collection<?> collection, String parameterName) throws IllegalArgumentException {
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
}
