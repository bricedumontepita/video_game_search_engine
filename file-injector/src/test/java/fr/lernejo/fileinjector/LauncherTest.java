package fr.lernejo.fileinjector;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LauncherTest {

    /*@Test
    void main_terminates_before_1_sec() {
        assertTimeoutPreemptively(
            Duration.ofSeconds(0),
            () -> Launcher.main(new String[]{}));
    }*/

    @Test
    void testSendingMessageWithNoFile () {
        Assertions.assertThatExceptionOfType(Exception.class)
            .isThrownBy(() -> Launcher.main(new String[]{}));
    }

    @Test
    void testSendingMessage () {
        try {
            Launcher.main(new String[]{"src/test/resources/games.json"});
        } catch (Exception e) {

        }
    }
}
