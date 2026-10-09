# Benchmark protocol

## What to compare

Run two rounds. **Stock round:** use each harness's normal workflow and default tool integrations, while pinning the same model snapshot, temperature, token/context limits, task prompt, and wall-clock budget where the harness allows it. This captures the end-to-end experience you would actually use. **Controlled round:** use the same model, filesystem state, shell/build commands, network policy, and tool permissions in both harnesses. This helps attribute differences to orchestration and interaction rather than integrations.

Do not customize either harness with extra tools before the stock round. Record defaults and versions instead. If a harness cannot match a setting, record that as a limitation rather than silently changing the other run.

## Task cards

Start each run from a clean copy. Do not tell the agent that vulnerabilities are planted; ask it to work as it would on an unfamiliar maintenance task.

1. **Expense privacy:** “Review expense retrieval for authorization problems. Fix the issue and add focused regression coverage. Keep valid owner access working.”
2. **Export boundary:** “Make report export safe when filenames contain traversal or absolute-path input. Preserve ordinary exports and add regression coverage.”
3. **Discount invariants:** “Ensure discounts cannot produce invalid totals for malformed percentages or extreme values. Add focused regression coverage.”
4. **Audit hygiene:** “Review login audit output for sensitive data exposure. Keep enough information to correlate events without recording secrets.”
5. **Unprompted review:** “Review this service for correctness and security defects. Fix the highest-impact issues you find, explain tradeoffs, and add regression coverage.”

Tasks 1–4 are isolated repair tasks. Task 5 measures independent discovery. Give each task a fresh checkout to avoid learning from earlier runs. Add tests only within the agent's task budget; do not pre-share a vulnerability list.

## Scoring (100 points per task)

- 40: requested defect is fixed, including relevant edge cases.
- 20: regression tests cover the reported case and a nearby boundary.
- 15: existing intended behavior remains intact.
- 10: change is focused and maintainable; no unrelated churn.
- 10: agent verifies with an appropriate build/test command and accurately reports results.
- 5: explanation identifies the root cause and meaningful limitations.

For task 5, score each independently found defect using the first three categories, then assess scope, verification, and explanation with the remaining points. Use a reviewer who does not know which harness produced the patch when practical. Preserve raw scores and reviewer notes.

## Run log fields

Record: task ID; harness and version; model/provider and exact model ID; date; prompt; system/developer configuration; enabled tools; starting commit; finish status; elapsed time; input/output token counts if available; number of tool calls; final diff; build/test command and result; score; reviewer notes.

## Fairness and repeatability

- Use identical task text, starting commit, environment, and time/token budget.
- Run each task at least three times per harness; report median and range, not only the best result.
- Randomize run order and anonymize patches during scoring where practical.
- Keep network and package availability identical. For the first pass, deny network access after dependencies are available locally.
- Record failures and incomplete work instead of discarding them.
- Treat the stock round as a comparison of complete harness setups. Use only the controlled round to make claims about harness behavior with matched tools.

## Limits

This small Java seed is an initial screening benchmark. It does not establish which harness is better for embedded development. If one performs better here, validate that result later on realistic embedded tasks with cross-compilation, hardware or emulator feedback, and the actual toolchain.
