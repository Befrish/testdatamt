/*
 * Created: 08.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.data.model;

import de.befrish.testdatamt.util.ParameterAssert;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;

/**
 * @author Benno Müller
 */
public final class QualifiedName {

    static final String SEPARATOR = ".";

    /**
     * Erlaubte Umlaute in Namen.
     */
    private static final String UMLAUTE = "ÄäÖöÜüß";
    /**
     * Regex für einfachen Name.
     */
    private static final String IDENTIFIER = "[\\p{Alpha}" + UMLAUTE + "][\\p{Alnum}_" + UMLAUTE + "]*";

    /**
     * Pattern zum Muster (Regex) für einfache Namen.
     */
    private static final Pattern IDENTIFIER_PATTERN = Pattern.compile(IDENTIFIER);
    /**
     * Regex für qualifizierten Namen mit Name.
     */
    private static final String QUALIFIED_IDENTIFIER = "((" + IDENTIFIER + "\\.)*)(" + IDENTIFIER + ")";

    /**
     * Pattern zum Muster (Regex) für qualifizierte Namen.
     */
    private static final Pattern QUALIFIED_IDENTIFIER_PATTERN = Pattern.compile(QUALIFIED_IDENTIFIER);
    /**
     * Position des letzten Namen im Pattern für einen qualifizierten Namen.
     */
    public static final int QUALIFIED_PATTERN_LAST_NAME_GROUP_INDEX = 3;

    private final String name;

    private final String packageName;

    /**
     * Gibt den voll qualifizierten Namen bestehend aus Paketnamen und Namen als String zurück. Wird zur eindeutigen
     * Identifizierung verwendet. Wird im Konstruktor generiert zur Performance-Optimierung.
     */
    private final String fullQualifiedName;

    /**
     * Temporäre Speicherung des HashCodes zur Performance-Optimierung.
     */
    private final int hashCode;

    private QualifiedName(final String name, final String... packageNames) {
        ParameterAssert.notNull(name, "name");
        if (!name.isEmpty() && !IDENTIFIER_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException(String.format("[%s] is no valid name", name));
        }
        this.name = name;
        if (packageNames != null && packageNames.length > 0) {
            this.packageName = Arrays.stream(packageNames)
                    .filter(paketName1 -> !isStandardPackage(paketName1))
                    .map(paketName1 -> {
                        if (!QUALIFIED_IDENTIFIER_PATTERN.matcher(paketName1).matches()) {
                            throw new IllegalArgumentException(
                                    String.format("[%s] is no valid qualified package name", paketName1));
                        }
                        return paketName1;
                    })
                    .collect(joining(SEPARATOR));
        } else {
            this.packageName = null;
        }

        this.fullQualifiedName = isStandardPackage(this.packageName) ? name : (this.packageName + SEPARATOR + name);
        this.hashCode = Objects.hash(this.fullQualifiedName.toLowerCase(NameConfiguration.NAME_LOCALE));
    }

    public static QualifiedName fromString(final String qualifiedName) {
        ParameterAssert.notNull(qualifiedName, "qualifiedName");
        if (IDENTIFIER_PATTERN.matcher(qualifiedName).matches()) {
            return new QualifiedName(qualifiedName, (String[]) null);
        } else {
            final Matcher matcher = QUALIFIED_IDENTIFIER_PATTERN.matcher(qualifiedName);
            if (matcher.matches()) {
                final String group1 = matcher.group(1);
                return new QualifiedName(matcher.group(QUALIFIED_PATTERN_LAST_NAME_GROUP_INDEX),
                        group1.substring(0, group1.length() - 1));
            } else {
                throw new IllegalArgumentException(String.format("[%s] is no valid qualified name",
                        qualifiedName));
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getFullQualifiedName() {
        return this.fullQualifiedName;
    }

    /**
     * Prüft, ob ein Paket gleich dem Standard-Paket ist.
     *
     * @param packageName (qualifizierter) Name des Pakets
     *
     * @return true, wenn gleich Standard-Paket; false anderfalls
     */
    public static boolean isStandardPackage(final String packageName) {
        return packageName == null || packageName.isEmpty();
    }

    /**
     * Prüft, ob ein Paket gesetzt ist oder es unter dem Standard-Paket untergeordnet ist.
     *
     * @return true, wenn in Standard-Paket (explizit nicht zu Paket zugeordnet); false anderfalls
     */
    public boolean isInStandardPackage() {
        return isStandardPackage(this.packageName);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final QualifiedName other = (QualifiedName) o;
        return Objects.equals(
                this.fullQualifiedName.toLowerCase(NameConfiguration.NAME_LOCALE),
                other.fullQualifiedName.toLowerCase(NameConfiguration.NAME_LOCALE));
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public String toString() {
        return getFullQualifiedName();
    }

}
