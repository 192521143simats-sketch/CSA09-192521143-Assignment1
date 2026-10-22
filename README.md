# Campus Smart-Parking Assignment 1

Name: R SARVESH  
Register number: 192521143  
Executed JDK: Oracle Java 21.0.12.1; compiled with `--release 17`.  
Submission repository: https://github.com/192521143simats-sketch/CSA09-192521143-Assignment1  
Additional destination specified in the master prompt: https://github.com/192521143simats-sketch/CSA0920--JAVA-PROGRAMMING

## Compile, run and test

Open PowerShell in this repository. No Maven or third-party Java library is needed.

```powershell
.\compile.ps1
java -cp build parking.app.Main
java -cp build parking.app.Main CAR TN01A1
.
un-all.ps1
.\Task03
un.ps1
.\Task08
un.ps1
.\Task11
un.ps1
.\Task12
un.ps1
```

For any individual task run `.\TaskNN
un.ps1`; it compiles the shared code, compiles that task into `build/TaskNN`, then runs every main class in that task. Task03 and Task08 are kept in isolated classpaths because their supplied class names overlap the integrated model. Their main executable statements are unchanged; source comments are omitted to satisfy the master prompt.

## Files and results

| Task | Source/main | Result |
|---|---|---|
| 01 | Task01Demo.java | Vehicle objects and registration search demonstrated |
| 02 | Task02Demo.java | Jagged capacities and 61-minute billing precision demonstrated |
| 03 | ParkingFacility.java | All six supplied output lines match |
| 04 | Task04Demo.java | Short/full constructors and shared references demonstrated |
| 05 | Task05Demo.java | Two facilities issue distinct tickets; shared sequence demonstrated |
| 06 | Task06Demo.java | Constructor trace and UML hierarchy |
| 07 | Task07Demo.java | Registration equality, hashing and overriding demonstrated |
| 08 | FeeDemo.java, OverloadDemo.java | All four expected lines match; fee overloads demonstrated |
| 09 | Task09Demo.java | Abstract base, capability, default/static interface methods |
| 10 | Task10Demo.java | Validated state changes and wrapper value equality |
| 11 | SystemTests.java, LookupBenchmark.java | 43 assertions pass; measured lookup comparison |
| 12 | ChallengeDemo.java, ChallengeTests.java | 31 assertions pass; extended demo collects Rs 110 |

Each TaskNN folder contains Java source, deterministic input description, the executed output logs, genuine PNG screenshots of the Java process output, Answer.md and run.ps1. The screenshot viewer launches each Java main as a subprocess, captures its real output and exit status, and displays them in a Swing window; screenshots are native window captures, not rendered imitations of terminal output. Full output logs supplement screenshots where scrolling is needed. Task06 also contains UML source and a diagram.

Shared code is under `src/parking/{model,pass,pricing,service,app}`. `docs/Assignment_Report.pdf` and `.docx` contain only task numbers and answers, in 12-point Times New Roman; no cover page, repeated question text or unrelated introductory text is included, following the latest user instruction. The GitHub link is recorded here and in the Task11 answer because a cover page was explicitly excluded.

## Billing assumptions

General slots accept all categories. The final slot on each floor with capacity >1 is charging-only; a one-slot floor is general. Baseline rates: two-wheeler Rs 10/hour, car/EV Rs 30/hour; every started hour is charged, zero duration is free. Electricity is Rs 12/kWh and is never waived by a parking pass. No unspecified daily cap is invented.

Monthly passes are bound to registration and the calendar month and apply only to staff/students. Both endpoints must be covered. Visitor day passes cover a specified calendar date; exit at next midnight is outside validity. Pass purchase prices were not supplied and are not included in collection.

E-bikes cost a flat Rs 20 for any positive stay. Van rates are Rs 60/hour during [09:00,17:00), Rs 30/hour otherwise; actual time overlap is prorated and the final total rounded upward to a whole rupee. This documented changed van rule does not round each time band up to an hour. Test times are deterministic local campus times.

## Design evaluation and limits

Active tickets use a fixed-capacity array. Original tested pricing remains usable; new rules are selected with ChallengePricing in Task12. Four extension classes were added. Two existing classes were tightened in Task12: ParkingTicket now returns an immutable Position, and ParkingFacility resolves that position to its private Slot before release. This prevents a caller from releasing a live slot through a ticket reference. `verification/core_before_task12.json` and `core_change_summary.json` record the actual file comparison.

This is a single-threaded, in-memory academic model. It has no database, persistence, payment gateway or campus-wide duplicate registry across independently operated facilities. UUID run prefix plus shared counter offers practical cross-run uniqueness; mathematically permanent uniqueness needs durable issuance state. Array lookup is O(capacity); map lookup is average O(1), with more storage overhead. Benchmark timings are illustrative, not a proof of complexity or exact heap measurements.

All commits use their real creation times. Task commits are distinct and meaningful; no dates or multi-day development activity have been fabricated. The public submission repository is accessible by the faculty without a collaborator invitation. No faculty identity was supplied, so no invitation was sent. Uploading the final PDF to the university portal remains a student submission step.
