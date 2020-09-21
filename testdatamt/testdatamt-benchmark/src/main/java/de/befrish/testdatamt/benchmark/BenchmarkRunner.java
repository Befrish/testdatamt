package de.befrish.testdatamt.benchmark;

/**
 * @author Benno Müller
 */
public final class BenchmarkRunner {

    private BenchmarkRunner() {
        super();
    }

    public static void main(final String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

}
