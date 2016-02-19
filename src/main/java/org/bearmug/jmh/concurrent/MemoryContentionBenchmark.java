package org.bearmug.jmh.concurrent;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@Threads(4)
public class MemoryContentionBenchmark {

    @State(Scope.Benchmark)
    public static class StateBenchmark {
        volatile int primitiveCounter = 0;
        final AtomicInteger atomicCounter = new AtomicInteger(0);
    }

    @State(Scope.Thread)
    public static class StateThread {
        volatile int primitiveCounter = 0;
        final AtomicInteger atomicCounter = new AtomicInteger(0);
    }

    @Benchmark
    public int unsharedPrimitive(StateThread state) {
        return ++state.primitiveCounter;
    }

    @Benchmark
    public int sharedPrimitive(StateBenchmark state) {
        return ++state.primitiveCounter;
    }

    @Benchmark
    public int unsharedAtomic(StateThread state) {
        return state.atomicCounter.incrementAndGet();
    }

    @Benchmark
    public int sharedAtomic(StateBenchmark state) {
        return state.atomicCounter.incrementAndGet();
    }

    public static void main(String[] args) throws RunnerException {
        Options res = new OptionsBuilder()
                .include(MemoryContentionBenchmark.class.getName() + ".*").build();
        new Runner(res).run();
    }
}
