package org.bearmug.jmh.conversions;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@Threads(1)
@State(Scope.Benchmark)

/**
 * Let`s look into two floating point numbers arithmetics performance
 * <li>
 *     <ul>- add/subtract actions not much longer with BigDecimal. It is ~2 times slower</ul>
 *     <ul>- multiplication slower with BigDecimal at least with x10 multiplicator</ul>
 *     <ul>- and numbers division slower with BigDeciamal ~2000 (two THOusAND!!!) times, since there
 *     is NO_ROUNDING strategy choosen under the hood</ul>
 *     <ul>- RoundingMode != UNNECESSARY definitely save lives and decrease timing from x2000 to
 *     x10-15</ul>
 *     <ul>- BigDecimal.divide with MathContext usage could kick performance up to the double/double
 *     level. Surely, precision has to be choosen carefully this case.</ul>
 * </li>
 */
public class FloatingPointBenchmark {

    @Param({"1.0000000001"})
    private double doubleParam1;

    @Param({"1.0000000001"})
    private double doubleParam2;

    private BigDecimal bigDecimalParam1;

    private BigDecimal bigDecimalParam2;

    @Setup
    public void before() {
        bigDecimalParam1 = BigDecimal.valueOf(1.0000000001);
        bigDecimalParam2 = BigDecimal.valueOf(1.0000000001);
    }

    @Benchmark
    public double calcSumDouble() {
        return doubleParam1 + doubleParam2;
    }

    @Benchmark
    public BigDecimal calcSumBigDecimal() {
        return bigDecimalParam1.add(bigDecimalParam2);
    }

    @Benchmark
    public double calcSubtrDouble() {
        return doubleParam1 - doubleParam2;
    }

    @Benchmark
    public BigDecimal calcSubtrBigDecimal() {
        return bigDecimalParam1.subtract(bigDecimalParam2);
    }

    @Benchmark
    public double calcMultDouble() {
        return doubleParam1 * doubleParam2;
    }

    @Benchmark
    public BigDecimal calcMultBigDecimal() {
        return bigDecimalParam1.multiply(bigDecimalParam2);
    }

    @Benchmark
    public double calcDivDouble() {
        return doubleParam1 / doubleParam2;
    }

    @Benchmark
    public BigDecimal calcDivBigDecimal() {
        return bigDecimalParam1.divide(bigDecimalParam2);
    }

    @Benchmark
    public BigDecimal calcDivBigDecimalRound() {
        return bigDecimalParam1.divide(bigDecimalParam2, BigDecimal.ROUND_HALF_EVEN);
    }

    @Benchmark
    public BigDecimal calcDivBigDecimalMathContext() {
        return bigDecimalParam1.divide(bigDecimalParam2, MathContext.DECIMAL32);
    }

    public static void main(String[] args) throws RunnerException {
        Options res = new OptionsBuilder()
                .include(FloatingPointBenchmark.class.getName() + ".*").build();
        new Runner(res).run();
    }
}
