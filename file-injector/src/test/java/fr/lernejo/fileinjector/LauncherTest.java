package fr.lernejo.fileinjector;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LauncherTest {

    @Test
    void main_terminates_before_20_sec() {
        assertTimeoutPreemptively(
            Duration.ofSeconds(20L),
            () -> Launcher.main(new String[]{"src/test/resources/games.json"}));
    }

    @Test
    void testSendingMessageWithNoFile () {
        Assertions.assertThatExceptionOfType(Exception.class)
            .isThrownBy(() -> Launcher.main(new String[]{}));
    }
}
