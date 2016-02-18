package org.bearmug.jmh;


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
@Warmup(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@Threads(1)
@State(Scope.Benchmark)

public class MVELBenchmark {

    public final Serializable EXPR_DOUBLE = MVEL.compileExpression("java.math.BigDecimal.valueOf(x).doubleValue()");
    public final Serializable EXPR_STRING = MVEL.compileExpression("x.replaceAll(y,z)");
    private final Map<String, Object> vars = new HashMap<String, Object>();

    @Param({"1.4"})
    public Double paramDouble;

    @Param({"qwertyuiopasdfghjklzxcvbnmasdferghugklgggd"})
    public String paramString;

    @Benchmark
    public Double benchDoubleMvel() {
        vars.put("x", paramDouble);
        return (Double) MVEL.executeExpression(EXPR_DOUBLE, vars);
    }

    @Benchmark
    public Double benchDoubleDirect() {
        return java.math.BigDecimal.valueOf(paramDouble).doubleValue();
    }

    @Benchmark
    public String benchStringMvel() {
        vars.put("x", paramString);
        vars.put("y", "g");
        vars.put("z", "O");
        return (String) MVEL.executeExpression(EXPR_STRING, vars);
    }

    @Benchmark
    public String benchStringDirect() {
        return paramString.replaceAll("g", "O");
    }

    public static void main(String[] args) throws RunnerException {
        Options res = new OptionsBuilder()
                .include(MVELBenchmark.class.getName() + ".*").build();

        new Runner(res).run();
    }
}
