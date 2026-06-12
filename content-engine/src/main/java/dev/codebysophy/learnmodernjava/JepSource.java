package dev.codebysophy.learnmodernjava;

import java.net.URI;

public record JepSource(int number, URI url) {
    public JepSource {
        if (number <= 0) {
            throw new IllegalArgumentException("JEP number must be positive.");
        }
        if (url == null) {
            throw new IllegalArgumentException("JEP source URL is required.");
        }
        if (!"https".equals(url.getScheme()) || !"openjdk.org".equals(url.getHost())) {
            throw new IllegalArgumentException("JEP source must come from https://openjdk.org.");
        }
        if (!url.getPath().equals("/jeps/" + number)) {
            throw new IllegalArgumentException("JEP source URL path must match the JEP number.");
        }
    }

    public static JepSource official(int number) {
        return new JepSource(number, URI.create("https://openjdk.org/jeps/" + number));
    }
}
