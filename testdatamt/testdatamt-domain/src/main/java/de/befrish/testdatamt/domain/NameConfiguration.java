/*
 * Created: 08.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.domain;

import de.befrish.testdatamt.util.Assert;

import java.util.Locale;

/**
 * @author Benno Müller
 */
public class NameConfiguration {

    public static final Locale NAME_LOCALE = Locale.GERMAN;

    private static final NameConfiguration CURRENT = new NameConfiguration();

    private boolean propertyNamesCaseSensitive = false;

    private NameConfiguration() {
        super();
    }

    public static NameConfiguration getCurrent() {
        return CURRENT;
    }

    public boolean isPropertyNamesCaseSensitive() {
        return this.propertyNamesCaseSensitive;
    }

    public void setPropertyNamesCaseSensitive(final boolean propertyNamesCaseSensitive) {
        this.propertyNamesCaseSensitive = propertyNamesCaseSensitive;
    }

    public String getPropertyName(final String name) {
        Assert.notNull(name, "name");
        return isPropertyNamesCaseSensitive() ? name : name.toLowerCase(NAME_LOCALE);
    }

}
