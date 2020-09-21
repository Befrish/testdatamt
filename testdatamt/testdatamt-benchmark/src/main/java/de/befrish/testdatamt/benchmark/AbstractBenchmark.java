package de.befrish.testdatamt.benchmark;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

import static de.befrish.testdatamt.benchmark.BenchmarkConstants.DEFAULT_FORK_COUNT;
import static de.befrish.testdatamt.benchmark.BenchmarkConstants.DEFAULT_MEASUREMENT_ITERATION_COUNT;
import static de.befrish.testdatamt.benchmark.BenchmarkConstants.DEFAULT_WARMUP_ITERATION_COUNT;

/**
 * @author Benno Müller
 */
@State(Scope.Benchmark)
@Threads(1)
//@BenchmarkMode(Mode.AverageTime) // s/op
@BenchmarkMode(Mode.Throughput) // op/s
@Fork(DEFAULT_FORK_COUNT)
@Warmup(iterations = DEFAULT_WARMUP_ITERATION_COUNT)
@Measurement(iterations = DEFAULT_MEASUREMENT_ITERATION_COUNT)
public abstract class AbstractBenchmark {

    @Setup(Level.Trial)
    public void setUpTrial() throws Exception {
        // kann überschrieben werden
    }

    @Setup(Level.Iteration)
    public void setUpIteration() throws Exception {
        // kann überschrieben werden
    }

    @Setup(Level.Invocation)
    public void setUpInvocation() throws Exception {
        // kann überschrieben werden
    }

    public abstract void run() throws Exception;

}
