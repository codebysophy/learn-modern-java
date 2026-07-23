# Project Status — Learn Modern Java

Last updated: 2026-07-22

## Where things stand

Milestones 1–3 (scaffold, smoke tests, GitHub Pages deployment) are complete and
the site is live. Milestone 4 (JEP source pipeline) is in progress: metadata
parsing works end-to-end for one real JEP.

## Done this session (2026-07-22)

- Cloned repo into `~/Code/modern_java_site`; verified the deployed site returns 200.
- Saved first snapshot: `content-engine/sources/openjdk/jeps/444.html` (JEP 444, Virtual Threads).
- Added `discussion` field to `JepMetadata` (decision: keep the Discussion
  mailing-list row; store the raw obfuscated value, normalize later at
  export/render time).
- Installed Maven 3.9.16 via SDKMAN (machine setup, not repo).
- Wrote `JepParserTest` (test-first): asserts title, release, and discussion
  against the real 444 snapshot.
- Implemented `JepParser` (new `jepparse` package): jsoup parse → head-table
  row loop → label map → `JepMetadata`. Sections deliberately left empty
  (`List.of()`) for a later slice. Added `JepParseException`.
- All 6 tests green (`mvn -f content-engine/pom.xml test`).
- Verified template consistency empirically: JEP 1 (2011 process), 126 (Java 8),
  198 (withdrawn), 512 (Java 25) all share the same structure (`#main h1` +
  `table.head`); only field presence varies. JEP 187 is a 404 — numbers have holes.
- Inspected the JEP index (`https://openjdk.org/jeps/0`): seven tables grouped
  by category (Process, Informational, In-flight, Submitted, Draft, Delivered,
  Withdrawn). The Delivered table has 384 rows, 380 in releases 8–26, each row
  carrying number, title, release, type, status, component. The index is the
  catalog — no numeric iteration needed.

## Decisions made

- Snapshot-then-parse architecture (per README): store raw HTML, parse offline,
  deterministic tests/builds. No live fetching at build time.
- Strict template parser, lenient fields: structure (`#main h1`, `table.head`)
  is required; individual metadata rows are `Optional`. Unknown rows are skipped.
- Catalog source: parse the official index at `jeps/0` (Delivered table only)
  instead of iterating JEP numbers.
- Include Java 8 update-release JEPs (8u20/8u40/8u60, 16 JEPs) as Java 8-era
  features. (Provisional — revisit if desired.)
- Commit fetched snapshots to git (~300 pages ≈ 20MB working tree, ~5MB packed).
  Accepted trade for deterministic builds.

## Open decision (blocking the fetcher)

**Preview/incubator JEPs: include or exclude?** Currently leaning exclude-for-now,
add later. Things to figure out before deciding:

- Preview JEPs *are* "delivered" in OpenJDK terms (JEP 425 shipped in 19 as a
  preview), so exclusion means filtering title suffixes: "(Preview)",
  "(Second Preview)", "(Incubator)", etc.
- Excluding previews loses feature history (virtual threads = only JEP 444/
  Java 21, no 425/436 chain). Including them means one feature spans multiple
  JEP pages — raises the "is the site's unit a JEP or a feature?" question.
- The head table's "Relates to" rows link preview chains together; not currently
  modeled (would be a `List`, not an `Optional`).

## Next steps (planned, not started)

- A. Snapshot the index page to `content-engine/sources/openjdk/jeps/0.html`.
- B. `JepIndexParser`, test-first: parse the Delivered table from the snapshot
  into catalog entries (number, title, release), filtered to releases 8–26
  (+ preview filter, pending the decision above).
- C. Fetcher (`java.net.http.HttpClient`): download missing snapshots from the
  catalog, with a polite delay between requests.
- D. Corpus test: parse every stored snapshot; assert required fields across all.
- Then: section parsing (`div.markdown` h2 walk), JSON export to
  `site/src/content/generated/` (watch Jackson `Optional` handling — needs
  jackson-datatype-jdk8 module), Astro rendering.
- Also pending: required-fields validation (title + Type + Status present in
  all four sampled pages) and a reject-non-JEP-HTML test.

## Not yet committed

Nothing from this session is committed. Working tree has: modified
`JepMetadata.java`; new `444.html` snapshot, `JepParserTest.java`,
`JepParser.java`, `JepParseException.java`, and this file.
