package de.befrish.testdatamt.benchmark;

import de.befrish.testdatamt.api.dummy.EmptyTestDataSource;
import de.befrish.testdatamt.api.dummy.IdentityTestDataProcessor;
import de.befrish.testdatamt.api.dummy.NoOpTestDataSink;
import de.befrish.testdatamt.api.runner.TestDataMtRunner;
import io.vavr.collection.List;
import org.openjdk.jmh.annotations.Benchmark;

/**
 * @author Benno Müller
 */
public class TestDataMtRunnerBenchmark extends AbstractBenchmark {

    private TestDataMtRunner testDataMtRunner;

    @Override
    public void setUpInvocation() {
        this.testDataMtRunner = new TestDataMtRunner(
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
