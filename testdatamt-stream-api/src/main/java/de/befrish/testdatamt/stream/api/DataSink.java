/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.stream.api;

/**
 * @author Benno Müller
 */
@FunctionalInterface
public interface DataSink {

    void consume(Object data);

}
