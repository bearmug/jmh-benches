package org.bearmug.jmh.conversions;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@Threads(1)
@State(Scope.Benchmark)

/**
 * Measures overhead from long/double boxing/unboxing. Now it`s obvious that:
 * <li>
 *     <ol>unboxing is almost free</ol>
 *     <ol>char -> Character boxing boosted with symbols cache and also free</ol>
 * </li>
 */
public class BoxingUnboxingBenchmark {

    @Param({"99999999999999999"})
    private long paramLongPrimitive;

    @Param({"99999999999999999"})
    private Long paramLongObject;

    @Param({"99999999999.00000000999999999"})
    private double paramDoublePrimitive;

    @Param({"99999999999.00000000999999999"})
    private Double paramDoubleObject;

    @Param({"R"})
    private char paramCharPrimitive;

    @Param({"R"})
    private Character paramCharObject;

    @Benchmark
    public long longUnboxing() {
        return paramLongObject;
    }

    @Benchmark
    public Long longBoxing() {
        return paramLongPrimitive;
    }

    @Benchmark
    public double doubleUnboxing() {
        return paramDoubleObject;
    }

    @Benchmark
    public Double doubleBoxing() {
        return paramDoublePrimitive;
    }

    @Benchmark
    public char charUnboxing() {
        return paramCharObject;
    }

    @Benchmark
    public Character charBoxing() {
        return paramCharPrimitive;
    }

    @Benchmark
    public double noConversion() {
        return paramDoublePrimitive;
    }

    public static void main(String[] args) throws RunnerException {
        Options res = new OptionsBuilder()
                .include(BoxingUnboxingBenchmark.class.getName() + ".*").build();
        new Runner(res).run();
    }
}
