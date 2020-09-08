/*
 * Created: 08.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.domain.tree;

import de.befrish.testdatamt.tree.TreeNodeName;

/**
 * @author Benno Müller
 */
public enum PropertyType implements TreeNodeName {

    ZEICHENKETTEN_TYP,
    WAHRHEITSWERT_TYP,
    GANZZAHL_TYP,
    DEZIMALZAHL_TYP,
    DATUMS_TYP,
    UHRZEIT_TYP,
    UHRZEIT_MIT_ZEITZONE_TYP,
    ZEITSTEMPEL_TYP,
    ZEITSTEMPEL_MIT_ZEITZONE_TYP,
    DATEI_TYP,
    VERWEIS_TYP,
    LISTEN_TYP,
    ANONYMER_DATEN_TYP,

    GANZZAHL_SCHLUESSEL_TYP,
    ZEICHENKETTEN_SCHLUESSEL_TYP,
    KOMMENTAR_TYP

}
