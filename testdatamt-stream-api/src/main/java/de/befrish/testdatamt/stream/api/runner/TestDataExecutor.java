package de.befrish.testdatamt.stream.api.runner;

import de.befrish.testdatamt.stream.api.DataSink;
import de.befrish.testdatamt.stream.api.DataSource;
import de.befrish.testdatamt.stream.api.DataProcessor;
import de.befrish.testdatamt.util.ParameterAssert;
import io.vavr.collection.List;
import io.vavr.collection.Traversable;

/**
 * @author Benno Müller
 */
public class TestDataExecutor {

    private final List<DataSource> testDataSources;
    private final List<DataProcessor> testDataTransformers;
    private final DataSink dataSink;

    public TestDataExecutor(
            final Traversable<DataSource> testDataSources,
            final List<DataProcessor> testDataTransformers,
            final DataSink dataSink) {
        ParameterAssert.notEmpty(testDataSources, "testDataSources");
        ParameterAssert.notNull(testDataTransformers, "testDataTransformers");
        ParameterAssert.notNull(dataSink, "testDataSink");
        this.testDataSources = List.ofAll(testDataSources);
        this.testDataTransformers = List.ofAll(testDataTransformers);
        this.dataSink = dataSink;
    }

    public void execute() throws TestDataExecutionException {
        try {
            final Object sourceData = this.testDataSources.toJavaParallelStream()
                    .map(DataSource::read)
                    .reduce(null, ((data1, data2) -> null));
            final Object processedData = io.vavr.collection.List.ofAll(this.testDataTransformers)
                    .foldLeft(sourceData, ((data, dataProcessor) -> dataProcessor.process(data)));

            this.dataSink.consume(processedData);
        } catch (final Exception e) {
            throw new TestDataExecutionException("error runnning " + getClass(), e);
        }
    }

}
