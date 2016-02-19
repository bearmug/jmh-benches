# jmh-benches
### JMH benchmarks set to measure and compare interesting cases and pitfalls

## How to run
Simply clone repo and run gradle tasks from below:

- **benchMvel** or **bM** bench MVEL scripted execution vs. direct calls
- **benchBoxingUnboxing** or **bBU** evaluate boxing/unboxing efforts for primitives
- **benchFloatingPoint** or **bFP** compare performance for primitive math actions for double and BigDecimal
- **benchStreams** or **bS** compare streams performance over classic loop processing