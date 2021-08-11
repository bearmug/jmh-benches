package org.bearmug.jmh.conversions;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.bearmug.jmh.conversions.LongConditionBenchmark.MyEnum.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 2)
@Measurement(iterations = 2, time = 2)
@Fork(1)
@Threads(1)
@State(Scope.Benchmark)
public class LongConditionBenchmark {

    public enum MyEnum {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN
    }

    @Param({"TEN"})
    private MyEnum input;

    private static final Set<MyEnum> SET = Set.of(MyEnum.values());

    @Benchmark
    public boolean validateWithLongCondition() {
        return ONE == input || TWO == input || THREE == input
                || FOUR == input || FIVE == input || MyEnum.SIX == input
                || SEVEN == input || EIGHT == input || NINE == input
                || TEN == input;
    }

    @Benchmark
    public boolean validateWithSet() {
        return SET.contains(input);
    }

    public static void main(String[] args) throws RunnerException {
        Options res = new OptionsBuilder()
                .include(LongConditionBenchmark.class.getName() + ".*").build();
        new Runner(res).run();
    }

}
