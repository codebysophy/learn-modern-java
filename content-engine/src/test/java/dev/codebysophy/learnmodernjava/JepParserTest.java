package dev.codebysophy.learnmodernjava;

import dev.codebysophy.learnmodernjava.jepparse.JepParser;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JepParserTest {
    @Test
    void parsesJep444Metadata() throws Exception {
        var document = JepParser.parse(Path.of("sources/openjdk/jeps/444.html"), 444);

        assertEquals("JEP 444: Virtual Threads", document.metadata().title());
        assertEquals(Optional.of("21"), document.metadata().release());
        assertEquals(Optional.of("loom dash dev at openjdk dot org"), document.metadata().discussion());
    }
}
