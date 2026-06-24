package dev.codebysophy.learnmodernjava.jepmodel;

import java.util.Optional;

public record JepMetadata(
        String title,
        Optional<String> author,
        Optional<String> owner,
        Optional<String> type,
        Optional<String> scope,
        Optional<String> status,
        Optional<String> release,
        Optional<String> component,
        Optional<String> created,
        Optional<String> updated
) {
}
