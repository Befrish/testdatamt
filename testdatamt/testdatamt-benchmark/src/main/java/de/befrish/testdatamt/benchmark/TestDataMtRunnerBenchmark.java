package de.befrish.testdatamt.benchmark;

import de.befrish.testdatamt.api.DefaultTestDataMtRunner;
import de.befrish.testdatamt.api.processor.IdentityTestDataProcessor;
import de.befrish.testdatamt.api.sink.NoOpTestDataSink;
import de.befrish.testdatamt.api.source.EmptyTestDataSource;
import io.vavr.collection.List;
import org.openjdk.jmh.annotations.Benchmark;

/**
 * @author Benno Müller
 */
public class TestDataMtRunnerBenchmark extends AbstractBenchmark {

    private DefaultTestDataMtRunner testDataMtRunner;

    @Override
    public void setUpInvocation() {
        this.testDataMtRunner = new DefaultTestDataMtRunner(
                List.of(new EmptyTestDataSource()),
                new NoOpTestDataSink()
        );
        this.testDataMtRunner.addProcessor(new IdentityTestDataProcessor());
    }

    @Benchmark
    @Override
    public void run() {
        this.testDataMtRunner.run();
    }

}
