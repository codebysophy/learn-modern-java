package dev.codebysophy.learnmodernjava;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.codebysophy.learnmodernjava.jepmodel.JepSource;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DependencySmokeTest {
    @Test
    void parsesHtmlWithJsoup() {
        var document = Jsoup.parse("""
                <html>
                  <body>
                    <h1>JEP 444: Virtual Threads</h1>
                  </body>
                </html>
                """);

        assertEquals("JEP 444: Virtual Threads", document.selectFirst("h1").text());
    }

    @Test
    void serializesRecordsWithJackson() throws Exception {
        var source = JepSource.official(444);
        var json = new ObjectMapper().writeValueAsString(source);

        assertEquals("""
                {"number":444,"url":"https://openjdk.org/jeps/444"}\
                """, json);
    }

    @Test
    void buildsOfficialJepUrls() {
        assertEquals("https://openjdk.org/jeps/444", JepSource.official(444).url().toString());
    }

    @Test
    void rejectsJepUrlsWithQueryStrings() {
        assertThrows(IllegalArgumentException.class,
                () -> new JepSource(444, URI.create("https://openjdk.org/jeps/444?view=raw")));
    }

    @Test
    void rejectsJepUrlsWithFragments() {
        assertThrows(IllegalArgumentException.class,
                () -> new JepSource(444, URI.create("https://openjdk.org/jeps/444#summary")));
    }
}
