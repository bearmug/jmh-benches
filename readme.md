# jmh-benches
JMH benchmarks collection to evaluate interesting cases and pitfalls

## How to run
Simply clone repo and run gradle tasks from below:

- **benchMvel** or **bM** bench MVEL scripted execution vs. direct calls
- **benchBoxingUnboxing** or **bBU** evaluate boxing/unboxing efforts for primitives
- **benchFloatingPoint** or **bFP** compare performance for primitive math actions for double and BigDecimal
- **benchStreams** or **bS** compare streams performance over classic loop processing
- **benchMemoryContention** or **bMC** validate memory contention impact with primitive and concurrent atomic
