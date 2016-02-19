# jmh-benches
### JMH benchmarks set to measure and compare interesting cases and pitfalls

## How to run
Simply clone and run gradle commands from below

- **gradle benchMvel** or **gradle bM** bench MVEL scripted execution vs. direct calls
- **gradle benchBoxingUnboxing** or **gradle bBU** evaluate boxing/unboxing efforts for primitives
- **gradle benchFloatingPoint** or **gradle bFP** compare performance for primitive math actions for double and BigDecimal