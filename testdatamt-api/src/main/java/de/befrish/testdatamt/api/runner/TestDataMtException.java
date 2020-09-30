/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.api.runner;

/**
 * @author Benno Müller
 */
public class TestDataMtException extends RuntimeException {

    private static final long serialVersionUID = -1884540876508468377L;

    public TestDataMtException(final String message, final Throwable error) {
        super(message, error);
    }

}
