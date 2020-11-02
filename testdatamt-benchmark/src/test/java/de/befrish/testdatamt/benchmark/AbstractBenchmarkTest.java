/*
 * Created: 25.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.benchmark;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Benno Müller
 */
public abstract class AbstractBenchmarkTest {

    private AbstractBenchmark benchmark;

    protected abstract AbstractBenchmark createBenchmark();

    @BeforeEach
    public void setUp() throws Exception {
        this.benchmark = createBenchmark();

        this.benchmark.setUpTrial();
    }

    @Test
    public void canRunBenchmarkWithoutErrors() throws Exception {
        this.benchmark.setUpIteration();
        this.benchmark.setUpInvocation();

        this.benchmark.run();
    }

}
