/*
 * Created: 08.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.domain.tree;

import de.befrish.testdatamt.tree.TreeNodeLabel;

/**
 * @author Benno Müller
 */
public enum TestDataMtTreeNodeLabel implements TreeNodeLabel {

    /**
     * Aufzählungs-Datentyp oder -Datenobjekt
     */
    AUFZAEHLUNG,
    /**
     * Vorlage-Datenobjekt
     */
    VORLAGE,
    /**
     * ID-Eigenschaft
     */
    ID,
    /**
     * Primitiver (nicht-zusammengesetzter) Eigenschaftstyp oder -wert
     */
    PRIMITIV,
    /**
     * Bei Verarbeitung erzeugte Verweise.
     */
    GENERIERTER_VERWEIS

}
