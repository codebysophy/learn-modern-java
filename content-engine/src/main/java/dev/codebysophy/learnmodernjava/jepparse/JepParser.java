package dev.codebysophy.learnmodernjava.jepparse;

import dev.codebysophy.learnmodernjava.jepmodel.JepDocument;
import dev.codebysophy.learnmodernjava.jepmodel.JepMetadata;
import dev.codebysophy.learnmodernjava.jepmodel.JepSource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class JepParser {
    private JepParser() {
    }

    public static JepDocument parse(Path snapshot, int jepNumber) throws IOException {
        var html = Jsoup.parse(snapshot.toFile(), "UTF-8");

        var heading = html.selectFirst("#main h1");
        if (heading == null) {
            throw new JepParseException("JEP " + jepNumber + " snapshot has no title heading.");
        }

        var metadata = readHeadTable(html.select("table.head tr"), heading.text());
        return new JepDocument(JepSource.official(jepNumber), metadata, List.of());
    }

    private static JepMetadata readHeadTable(List<Element> rows, String title) {
        Map<String, String> values = new LinkedHashMap<>();
        for (var row : rows) {
            var cells = row.select("td");
            if (cells.size() < 2 || cells.get(0).text().isBlank()) {
                continue;
            }
            values.putIfAbsent(cells.get(0).text(), cells.get(1).text());
        }

        return new JepMetadata(
                title,
                value(values, "Author"),
                value(values, "Owner"),
                value(values, "Type"),
                value(values, "Scope"),
                value(values, "Status"),
                value(values, "Release"),
                value(values, "Component"),
                value(values, "Discussion"),
                value(values, "Created"),
                value(values, "Updated"));
    }

    private static Optional<String> value(Map<String, String> values, String label) {
        return Optional.ofNullable(values.get(label));
    }
}
