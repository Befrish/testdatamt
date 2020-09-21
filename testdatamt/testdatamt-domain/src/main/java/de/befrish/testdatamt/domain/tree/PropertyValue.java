/*
 * Created: 08.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.domain.tree;

import de.befrish.testdatamt.domain.Reference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static de.befrish.testdatamt.domain.tree.PropertyType.ANONYMER_DATEN_TYP;
import static de.befrish.testdatamt.domain.tree.PropertyType.DATEI_TYP;
import static de.befrish.testdatamt.domain.tree.PropertyType.DATUMS_TYP;
import static de.befrish.testdatamt.domain.tree.PropertyType.DEZIMALZAHL_TYP;
import static de.befrish.testdatamt.domain.tree.PropertyType.GANZZAHL_TYP;
import static de.befrish.testdatamt.domain.tree.PropertyType.LISTEN_TYP;
import static de.befrish.testdatamt.domain.tree.PropertyType.UHRZEIT_MIT_ZEITZONE_TYP;
import static de.befrish.testdatamt.domain.tree.PropertyType.UHRZEIT_TYP;
import static de.befrish.testdatamt.domain.tree.PropertyType.VERWEIS_TYP;
import static de.befrish.testdatamt.domain.tree.PropertyType.WAHRHEITSWERT_TYP;
import static de.befrish.testdatamt.domain.tree.PropertyType.ZEICHENKETTEN_TYP;
import static de.befrish.testdatamt.domain.tree.PropertyType.ZEITSTEMPEL_MIT_ZEITZONE_TYP;
import static de.befrish.testdatamt.domain.tree.PropertyType.ZEITSTEMPEL_TYP;

/**
 * @author Benno Müller
 */
@Deprecated
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum PropertyValue {

    LEERER_WERT(Object.class, null),
    ZEICHENKETTEN_WERT(String.class, ZEICHENKETTEN_TYP),
    WAHRHEITS_WERT(Boolean.class, WAHRHEITSWERT_TYP),
    GANZZAHL_WERT(BigInteger.class, GANZZAHL_TYP),
    DEZIMALZAHL_WERT(BigDecimal.class, DEZIMALZAHL_TYP),
    DATUMS_WERT(LocalDate.class, DATUMS_TYP),
    UHRZEIT_WERT(LocalTime.class, UHRZEIT_TYP),
    UHRZEIT_MIT_ZEITZONE_WERT(OffsetTime.class, UHRZEIT_MIT_ZEITZONE_TYP),
    ZEITSTEMPEL_WERT(LocalDateTime.class, ZEITSTEMPEL_TYP),
    ZEITSTEMPEL_MIT_ZEITZONE_WERT(OffsetDateTime.class, ZEITSTEMPEL_MIT_ZEITZONE_TYP),
    DATEI_WERT(LocalDate.class, DATEI_TYP),
    VERWEIS_WERT(Reference.class, VERWEIS_TYP),
    LISTEN_WERT(Object.class, LISTEN_TYP), // TODO valueClass
    ANONYMES_DATEN_OBJEKT(Object.class, ANONYMER_DATEN_TYP); // TODO valueClass

    @NonNull
    private final Class<?> valueClass;

//    @NonNull
    @Getter
    private final PropertyType propertyType;

    public static Optional<PropertyValue> findByValueClass(final Class<?> valueClass) {
        return Arrays.stream(PropertyValue.values())
                .filter(value -> Objects.equals(valueClass, value.valueClass))
                .findAny();
    }

}
