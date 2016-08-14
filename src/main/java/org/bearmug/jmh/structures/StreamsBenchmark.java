package org.bearmug.jmh.structures;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Compare streams performance over classic loop processing. Simple combination used:
 * filter by module 1000 / sort / convert to long. Observations:
 * <li>
 *     <ul>- there is almost no performance boost from simple streaming usage. Although
 *     code really start looking much cleaner. Moreover, there is some penalty fr streams
 *     API usage this case</ul>
 *     <ul>- parallel streaming could dramatically improve performance.It is depends on
 *     underlying code, but multiplier could be ~2-4</ul>
 *     <ul>- simple or parallel streaming should be used with caution. There could be
 *     x200 penalty and more for wrong stream processing sequence. Sort before filter is a
 *     classic example</ul>
 * </li>
 */

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@Threads(1)
@State(Scope.Benchmark)

public class StreamsBenchmark {

    private static final List<Integer> src = new ArrayList<>();

    static {
        final Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 1000000; i++) {
            src.add(random.nextInt(Integer.MAX_VALUE));
        }
    }

    @Benchmark
    public List<Long> simpleLoop() {
        List<Long> res = new ArrayList<>(src.size() / 100);
        for (Integer element : src) {
            if (element % 1000 == 0) {
                res.add(element.longValue());
            }
        }
        Collections.sort(res);
        return res;
    }

    @Benchmark
    public List<Long> simpleStream() {
        return src.stream()
                .filter(it -> it % 1000 == 0)
                .sorted()
                .map(Integer::longValue)
                .collect(Collectors.toList());
    }

    @Benchmark
    public List<Long> parallelStream() {
        return src.parallelStream()
                .filter(it -> it % 1000 == 0)
                .sorted()
                .map(Integer::longValue)
                .collect(Collectors.toList());
    }

    @Benchmark
    public List<Long> simpleStreamNaive() {
        return src.stream()
                .map(Integer::longValue)
                .sorted()
                .filter(it -> it % 1000 == 0)
                .collect(Collectors.toList());
    }

    @Benchmark
    public List<Long> parallelStreamNaive() {
        return src.parallelStream()
                .map(Integer::longValue)
                .sorted()
                .filter(it -> it % 1000 == 0)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws RunnerException {
        Options res = new OptionsBuilder()
                .include(StreamsBenchmark.class.getName() + ".*").build();

        new Runner(res).run();
    }
}
