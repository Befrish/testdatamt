/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * @author Benno Müller
 */
@RequiredArgsConstructor
public class DefaultTreeNodeName implements TreeNodeName {

    private final String name;

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof TreeNodeName)) {
            return false;
        }
        final TreeNodeName that = (TreeNodeName) o;
        return Objects.equals(name, that.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

}
