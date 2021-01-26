[![Codacy Badge](https://api.codacy.com/project/badge/Grade/bd0d630ac1784c819523e1505bd6339d)](https://www.codacy.com/app/pavel-fadeev/jmh-benches?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=bearmug/jmh-benches&amp;utm_campaign=Badge_Grade)

# jmh-benches
JMH benchmarks collection to evaluate interesting cases and pitfalls

## How to run
Simply clone repo and run gradle tasks from below:

- **benchMvel** or **bM** bench MVEL scripted execution vs. direct calls
- **benchBoxingUnboxing** or **bBU** evaluate boxing/unboxing efforts for primitives
- **benchFloatingPoint** or **bFP** compare performance for primitive math actions for double and BigDecimal
- **benchStreams** or **bS** compare streams performance over classic loop processing
- **benchMemoryContention** or **bMC** validate memory contention impact with primitive and concurrent atomic
- **benchUuidValidation** or **bUV** compare uuid strings validation performance in case of precompiled pattern and
`UUID.fromString(...)` 
- **benchJsonPath** or **bJP** compare json-path nodes replacement performance for exact optional path and generic 
recursive '..' pattern 
