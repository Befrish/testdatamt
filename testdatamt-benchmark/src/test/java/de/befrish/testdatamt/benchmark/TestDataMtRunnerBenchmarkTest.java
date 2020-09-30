/*
 * Created: 25.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.benchmark;

/**
 * @author Benno Müller
 */
public class TestDataMtRunnerBenchmarkTest extends AbstractBenchmarkTest {

    @Override
    protected AbstractBenchmark createBenchmark() {
        return new TestDataMtRunnerBenchmark();
    }

}