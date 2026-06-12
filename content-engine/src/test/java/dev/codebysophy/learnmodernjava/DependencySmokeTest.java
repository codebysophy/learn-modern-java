package dev.codebysophy.learnmodernjava;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
