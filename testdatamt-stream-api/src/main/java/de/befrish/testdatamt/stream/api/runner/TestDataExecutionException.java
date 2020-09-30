/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.stream.api.runner;

/**
 * @author Benno Müller
 */
public class TestDataExecutionException extends RuntimeException {

    private static final long serialVersionUID = -1884540876508468377L;

    public TestDataExecutionException(final String message, final Throwable error) {
        super(message, error);
    }

}
