package org.bearmug.jmh.conversions;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 2)
@Measurement(iterations = 1, time = 2)
@Fork(1)
@Threads(1)
@State(Scope.Benchmark)
public class UuidValidationBenchmark {

    private static final Pattern PATTERN = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");

    @Param({"hello-inmvalid-uuid-here-O_o"})
    private String invalidUuid;

    @Param({"4164c802-f449-4c45-bd54-f1dcaf8b9e18"})
    private String validUuid;

    boolean validateWithPattern(String maybeUuid) {
        return PATTERN.matcher(maybeUuid).matches();
    }

    boolean validateWithParsing(String maybeUuid) {
        try {
            UUID.fromString(maybeUuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Benchmark
    public boolean validWithPattern() {
        return validateWithPattern(validUuid);
    }

    @Benchmark
    public boolean validWithParsing() {
        return validateWithParsing(validUuid);
    }

    @Benchmark
    public boolean invalidWithPattern() {
        return validateWithPattern(invalidUuid);
    }

    @Benchmark
    public boolean invalidWithParsing() {
        return validateWithParsing(invalidUuid);
    }

    public static void main(String[] args) throws RunnerException {
        Options res = new OptionsBuilder()
                .include(UuidValidationBenchmark.class.getName() + ".*").build();
        new Runner(res).run();
    }

}
