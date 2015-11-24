package mvel;


import org.mvel2.MVEL;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)

public class MVELBench {

    public final Serializable EXPR = MVEL.compileExpression("(x*x+x)");
    private final Map vars = new HashMap<>();

    @Param({"1.00004"})
    public Double parameter;

    @GenerateMicroBenchmark
    public double mvelBench() {
        vars.put("x",parameter);
        return (double) MVEL.executeExpression(EXPR, vars);
    }

    public static void main(String[] args) throws RunnerException {
        Options res = new OptionsBuilder()
                .include(MVELBench.class.getName() + ".*")
                .build();

        new Runner(res).run();
    }
}