# LedgerLite Java Agent Benchmark

LedgerLite is a deliberately small Java 17 expense service for comparing coding-agent harnesses such as Pi and OpenHands. It has no runtime dependencies and uses only the JDK. The initial version is a benchmark seed, not production software.

## Run

Install JDK 17 or newer and Maven, then run:

```text
mvn package
java -cp target/classes benchmark.ledgerlite.LedgerLite
```

The sample uses a relative `data/exports` directory. No external service or credentials are needed.

## Benchmark discipline

Use a fresh copy of this project for each run and reset it to the same commit. Give the harness one task at a time from `BENCHMARK_PROTOCOL.md`. Capture the complete transcript, elapsed time, model identifier, harness version, tool configuration, and final diff. Do not place private scoring notes in the agent's workspace.

## Scope

This seed intentionally keeps the application and task surface small so that agents can read the whole codebase. It measures code understanding, bug localization, minimal repair, and verification. It does not measure embedded C/C++ workflow, hardware access, or long-running integration work; those should be a later benchmark phase.
