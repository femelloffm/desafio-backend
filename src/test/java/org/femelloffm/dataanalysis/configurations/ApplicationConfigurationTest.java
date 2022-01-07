package org.femelloffm.dataanalysis.configurations;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@TestConfiguration
public class ApplicationConfigurationTest {
    @Bean
    public Clock clock() {
        return Clock.fixed(LocalDate.of(2022, 1, 28).atStartOfDay(ZoneId.systemDefault()).toInstant(),
                ZoneId.systemDefault());
    }

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy--HH-mm-ss");
    }
}