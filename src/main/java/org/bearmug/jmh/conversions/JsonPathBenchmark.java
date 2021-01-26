package org.bearmug.jmh.conversions;

import com.jayway.jsonpath.JsonPath;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@Threads(1)
@State(Scope.Benchmark)
public class JsonPathBenchmark {
    @Param({"{\"details\":{\"structure\":{\"nestedStructure\":{\"optionalLevel\":{\"email\":\"secret@domain.com\",\"anotherField\":\"regularValue\"}}}}}"})
    private String jsonInput;

    @Benchmark
    public String wildcardReplacement() {
        return JsonPath.parse(jsonInput).set("$..email", "***").jsonString();
    }

    @Benchmark
    public String exactReplacement() {
        return JsonPath.parse(jsonInput).set("$.details[?(@.structure.nestedStructure.optionalLevel.email)].structure.nestedStructure.optionalLevel.email", "***").jsonString();
    }

    public static void main(String[] args) throws RunnerException {
        Options res = new OptionsBuilder()
                .include(JsonPathBenchmark.class.getName() + ".*").build();
        new Runner(res).run();
    }
}
