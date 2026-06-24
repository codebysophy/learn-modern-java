package dev.codebysophy.learnmodernjava.jepmodel;

import java.util.List;

public record JepDocument(JepSource source, JepMetadata metadata, List<JepSection> sections) {
}
