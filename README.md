# Campus Smart-Parking System — Assignment 1

Name: R SARVESH

Register number: 192521143

Java version used: Oracle JDK 21.0.12.1, compiled for Java 17.

GitHub: https://github.com/192521143simats-sketch/CSA09-192521143-Assignment1

## Compile

Run these commands in PowerShell from the repository root. A JDK version of 17 or later is required. There are no third-party Java dependencies.

```powershell
New-Item -ItemType Directory -Force build | Out-Null
$sourceFiles = (Get-ChildItem .\src -Recurse -Filter *.java).FullName
javac --release 17 -d build $sourceFiles

1..12 | ForEach-Object {
    $task = 'Task{0:D2}' -f $_
    New-Item -ItemType Directory -Force "build\$task" | Out-Null
    $taskSources = (Get-ChildItem ".\$task" -Filter *.java).FullName
    javac --release 17 -cp build -d "build\$task" $taskSources
}
```

Each task compiles separately because the class names in the supplied exercises overlap the integrated vehicle model. Task03 and Task08 retain the supplied program behaviour; their public entry classes are named Task03 and Task08.

## Run each task

| Task | Command | Result |
|---|---|---|
| 1 | `java -cp "build/Task01;build" Task01` | Creates different vehicle objects and finds a registration |
| 2 | `java -cp "build/Task02;build" Task02` | Shows unequal floor capacities and correct 61-minute billing |
| 3 | `java -cp "build/Task03;build" Task03` | Matches all six supplied output lines |
| 4 | `java -cp "build/Task04;build" Task04` | Exercises short and full ticket constructors |
| 5 | `java -cp "build/Task05;build" Task05` | Confirms distinct ticket identities across two facilities |
| 6 | `java -cp "build/Task06;build" Task06` | Prints the superclass-to-subclass constructor trace |
| 7 | `java -cp "build/Task07;build" Task07` | Confirms registration equality and consistent hashing |
| 8 | `java -cp "build/Task08;build" Task08` | Matches all four supplied output lines |
| 9 | `java -cp "build/Task09;build" Task09` | Exercises abstract-class and interface behaviour |
| 10 | `java -cp "build/Task10;build" Task10` | Rejects invalid state changes and compares wrapper values |
| 11 | `java -cp "build/Task11;build" Task11` | Passes 43 assertions, including the Rs 330 collection check |
| 12 | `java -cp "build/Task12;build" Task12` | Exercises the new categories and day pass; collects Rs 110 |

Additional required comparisons and tests:

```powershell
java -cp "build/Task08;build" Task08Overloading
java -cp "build/Task11;build" Task11Benchmark
java -cp "build/Task12;build" Task12Tests
```

Task12Tests passes 31 assertions. Together, Tasks 11 and 12 contain 74 passing assertions. Benchmark timings vary between runs.

The integrated baseline application can also be run with:

```powershell
java -cp build parking.app.Main
java -cp build parking.app.Main CAR TN01A1
```

## Files

- `Task01`–`Task12`: Java source and output screenshots; Task06 also contains the UML diagram.
- `src/parking`: shared model, service, pass, pricing and application classes.
- `docs/Assignment_Report.pdf` and `docs/Assignment_Report.docx`: answers, pseudocode, UML, test tables and complexity analysis.

Inputs are fixed in the task source files so the runs can be repeated. No external input files are needed. Main accepts optional vehicle-type and registration arguments. PNG output screenshots are browser captures of actual Java process output at 2880-pixel width; longer outputs are split into numbered images. The generated `build` folder is excluded from Git.

## Assumptions

Two-wheelers cost Rs 10 per started hour; cars and EVs cost Rs 30. Zero duration is free. Electricity costs Rs 12/kWh and is not waived by a pass. The last slot on a floor with at least two slots is reserved for charging; other slots are general. Monthly passes cover staff/students for a specified registration and calendar month. Visitor day passes cover one registration and one calendar date, excluding the next midnight.

E-bikes cost Rs 20 for any positive stay. Delivery vans cost Rs 60/hour during 09:00–17:00 and Rs 30/hour otherwise; actual overlap is prorated and the final total is rounded upward to a whole rupee. Pass purchase prices and a daily parking cap were not specified and are not added.

The application uses in-memory state and one thread. Ticket IDs combine a process UUID and shared counter; permanent guaranteed uniqueness and recovery after restart would require persistent storage.
