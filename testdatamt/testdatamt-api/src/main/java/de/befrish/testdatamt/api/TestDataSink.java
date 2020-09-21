/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.api;

/**
 * @author Benno Müller
 */
public interface TestDataSink {

    void consumeTestData(Void testData);

}
