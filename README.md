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
