# Task 3

Allocation uses nested loops in floor order and slot order. In the integrated model continue skips occupied or unsuitable slots and return stops both loops on the first successful allocation. No-suitable-slot exhaustion reports an error; it never creates an unallocated active ticket. The supplied exercise returns null on exhaustion. release() validates the floor before indexing its row, validates the slot against that row length, and returns false when already free. break is useful when only the inner loop should stop; return is clearer for returning the first match from nested loops.

Pseudocode uses numbered algorithm-style steps, INPUT, OUTPUT, IF/ENDIF and FOR/ENDFOR. No official SIMATS pseudocode handout was supplied, so institutional typography cannot be verified.

ALGORITHM AllocateSuitableSlot
INPUT vehicle, needsCharging, slots
OUTPUT position or failure
BEGIN
  FOR floor FROM 0 TO length(slots) - 1 DO
    FOR slot FROM 0 TO length(slots[floor]) - 1 DO
      IF occupied OR NOT suitable(vehicle, needsCharging) THEN
        CONTINUE
      ENDIF
      store vehicle in slots[floor][slot]
      RETURN (floor, slot)
    ENDFOR
  ENDFOR
  RETURN failure
END

ALGORITHM ComputeFee
INPUT vehicle, entry, exit, pass, role, kWh
OUTPUT total fee
BEGIN
  IF exit < entry OR kWh < 0 THEN REJECT ENDIF
  minutes <- ceiling(duration(entry, exit) in minutes)
  hours <- minutes DIV 60
  IF minutes MOD 60 > 0 THEN hours <- hours + 1 ENDIF
  parking <- hours * vehicle.hourlyRate
  IF valid pass covers registration, role, entry and exit THEN
    parking <- 0
  ENDIF
  electricity <- 0
  IF charging vehicle AND valid charging booking THEN
    electricity <- 12 * kWh
  ENDIF
  RETURN parking + electricity
END

The selected hourly rule uses if for rounding and pass decisions; Main uses switch for input category selection. A different first-hour/additional-hour rule would use IF hours=0 THEN 0 ELSE firstRate + (hours-1)*extraRate. If a daily maximum C were specified, full-day blocks and the last partial day would each be capped at C; it is not silently introduced into this implementation.

Task03/ParkingFacility.java completes the two TODO regions, preserves the supplied main executable statements and matches all six expected lines. Source comments are omitted under the master prompt's no-comments rule. The integrated facility adds suitability and duplicate checks without altering the exercise.

Alternative: keep freeCount[f] and a stack of free indexes for each floor. allocate scans freeCount until a suitable nonempty floor, pops one index, marks occupied and decrements its count; release first validates that the slot is occupied, clears it, pushes once and increments. For F floors of S slots, the original exercise allocate is O(FS) worst-case and release O(1), with O(FS) storage and O(1) extra workspace. Per-floor stacks reduce allocate to O(F), release O(1), with O(FS+F) extra free-index/count storage. A global stack/set of nonempty floors reduces arbitrary allocation to O(1), but preserving first-floor order may need an ordered structure and separate suitability pools. A LIFO stack does not necessarily preserve the original first-free-slot order. Integrated duplicate detection and locating a free ticket-array cell remain O(N), even if slot selection is optimized.
