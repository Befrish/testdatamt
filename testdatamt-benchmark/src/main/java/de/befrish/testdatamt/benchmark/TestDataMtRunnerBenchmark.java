package de.befrish.testdatamt.benchmark;

import de.befrish.testdatamt.stream.api.impl.EmptyDataSource;
import de.befrish.testdatamt.stream.api.impl.IdentityDataProcessor;
import de.befrish.testdatamt.stream.api.impl.NoOpDataSink;
import de.befrish.testdatamt.stream.api.runner.TestDataExecutor;
import io.vavr.collection.List;
import org.openjdk.jmh.annotations.Benchmark;

/**
 * @author Benno Müller
 */
public class TestDataMtRunnerBenchmark extends AbstractBenchmark {

    private TestDataExecutor testDataExecutor;

    @Override
    public void setUpInvocation() {
        this.testDataExecutor = new TestDataExecutor(
                List.of(new EmptyDataSource()),
                List.of(new IdentityDataProcessor()),
                new NoOpDataSink()
        );
    }

    @Benchmark
    @Override
    public void run() {
        this.testDataExecutor.execute();
    }

}
