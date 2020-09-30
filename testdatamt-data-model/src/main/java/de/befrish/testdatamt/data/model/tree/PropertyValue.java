/*
 * Created: 08.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.data.model.tree;

import de.befrish.testdatamt.data.model.Reference;
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

/**
 * @author Benno Müller
 */
@Deprecated
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum PropertyValue {

    LEERER_WERT(Object.class, null),
    ZEICHENKETTEN_WERT(String.class, PropertyType.ZEICHENKETTEN_TYP),
    WAHRHEITS_WERT(Boolean.class, PropertyType.WAHRHEITSWERT_TYP),
    GANZZAHL_WERT(BigInteger.class, PropertyType.GANZZAHL_TYP),
    DEZIMALZAHL_WERT(BigDecimal.class, PropertyType.DEZIMALZAHL_TYP),
    DATUMS_WERT(LocalDate.class, PropertyType.DATUMS_TYP),
    UHRZEIT_WERT(LocalTime.class, PropertyType.UHRZEIT_TYP),
    UHRZEIT_MIT_ZEITZONE_WERT(OffsetTime.class, PropertyType.UHRZEIT_MIT_ZEITZONE_TYP),
    ZEITSTEMPEL_WERT(LocalDateTime.class, PropertyType.ZEITSTEMPEL_TYP),
    ZEITSTEMPEL_MIT_ZEITZONE_WERT(OffsetDateTime.class, PropertyType.ZEITSTEMPEL_MIT_ZEITZONE_TYP),
    DATEI_WERT(LocalDate.class, PropertyType.DATEI_TYP),
    VERWEIS_WERT(Reference.class, PropertyType.VERWEIS_TYP),
    LISTEN_WERT(Object.class, PropertyType.LISTEN_TYP), // TODO valueClass
    ANONYMES_DATEN_OBJEKT(Object.class, PropertyType.ANONYMER_DATEN_TYP); // TODO valueClass

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
