# Assumptions

## 1. Low-stock threshold storage

**What was unclear:** The legacy inventory data has no field indicating a
low-stock threshold, but the system must display low-stock warnings.

**Decision:** The threshold is stored as a single configurable value, either
as a static constant in a Config class or loaded from a simple config.txt
file (e.g. `lowStockThreshold=10`), so it can be adjusted without
recompiling the application.

**Why reasonable:** The coursework does not specify per-category or
per-part thresholds, so a single global threshold is the simplest
interpretation that still satisfies "configurable."

**Limitation:** All parts share the same threshold regardless of category
or typical stock levels, which may not reflect real depot operations
where fast-moving parts (e.g. spark plugs) need higher thresholds than
slow-moving ones (e.g. canopy covers).

## 2. Default value for missing supplier/phone fields

**What was unclear:** Several legacy records have missing fields — e.g.
P003 and P010 have no supplier, and D103/D107 have no phone number.

**Decision:** Missing supplier or phone fields default to the string
"Unknown" rather than null or an empty string.

**Why reasonable:** Using "Unknown" avoids NullPointerExceptions when the
GUI displays these fields, and is more informative to the user than a
blank cell in a table.

**Limitation:** If "Unknown" is ever used in search/filter logic (e.g.
filtering by supplier), it could group unrelated records together under
one fake supplier name, which may need separate handling later.