# Learn Modern Java

Learn Modern Java is a learning site for modern Java features from Java 8 through Java 26.

The project starts with a simple, buildable architecture:

- `content-engine/`: Java 26 content engine for future OpenJDK JEP ingestion, parsing, validation, and JSON export.
- `site/`: Astro frontend for rendering the static learning site.
- `.github/workflows/pages.yml`: GitHub Pages deployment workflow.

The first deployed page is a scaffold smoke test. It proves the repository shape, dependency declarations, and GitHub Pages path before the first JEP ingestion slice is implemented.

## Source Rule

Future factual learning content must come from official OpenJDK JEP pages:

- `https://openjdk.org/jeps/0`
- `https://openjdk.org/jeps/{JEP_NUMBER}`

## Content Pipeline

The content engine should treat live fetching as a source update step, not as a
runtime dependency for the static site.

Planned flow:

1. Fetch selected official OpenJDK JEP pages.
2. Store the raw fetched HTML snapshots under `content-engine/sources/openjdk/jeps/`.
3. Parse stored snapshots into validated structured JEP records.
4. Export site-ready JSON under `site/src/content/generated/`.
5. Build the Astro site from generated JSON.

This keeps the official OpenJDK pages as the source of truth while making normal
tests and site builds deterministic. A later refresh command or scheduled
workflow can re-fetch OpenJDK sources, store changed snapshots, and regenerate
JSON when new or updated JEP content appears.

## Milestones

1. Project scaffold: Java content engine, Astro site, README, and Pages workflow.
2. Smoke tests: prove Maven dependencies, JSON serialization, and Astro build path.
3. Deployment path: build `site/dist` and deploy it to GitHub Pages.
4. JEP source pipeline: fetch official JEP pages, store raw snapshots, refresh
   changed/new sources, parse snapshots, validate records, and export JSON.
5. Learning frontend: render real Java feature pages from generated JEP JSON.

## Local Commands

Java content engine:

```bash
mvn -f content-engine/pom.xml test
```

Astro frontend:

```bash
cd site
npm install
npm run build
```

Node, npm, and Maven dependencies are declared but not vendored in this repository.
