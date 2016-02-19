# jmh-benches
### JMH benchmarks set to measure and compare interesting cases and pitfalls

## How to run
Simply clone and run gradle commands from below

- *gradle benchMvel* benchmark MVEL framevork in terms of overhead for scripted calls
- *gradle benchBoxingUnboxing* evaluate boxing/unboxing efforts for long/double/char
- *benchFloatingPoint* compare performance of primitive math actions for double and BigDecimal