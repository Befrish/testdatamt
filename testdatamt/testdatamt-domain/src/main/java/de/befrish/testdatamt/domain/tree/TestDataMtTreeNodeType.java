/*
 * Created: 08.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.domain.tree;

import de.befrish.testdatamt.tree.TreeNodeType;

/**
 * @author Benno Müller
 */
public enum TestDataMtTreeNodeType implements TreeNodeType {

    PAKET,
    BENANNTER_DATEN_TYP,
    BENANNTES_DATEN_OBJEKT,
    EIGENSCHAFTS_DEKLARATION,
    EIGENSCHAFTS_DEFINITION,
    EIGENSCHAFTS_TYP,
    EIGENSCHAFTS_WERT,
    LISTEN_ELEMENT

}
