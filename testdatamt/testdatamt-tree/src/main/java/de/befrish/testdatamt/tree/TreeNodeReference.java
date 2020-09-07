/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.tree;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Benno Müller
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public final class TreeNodeReference {

    private final TreePath target;
    private final TreeNodeReferenceType type;

}
