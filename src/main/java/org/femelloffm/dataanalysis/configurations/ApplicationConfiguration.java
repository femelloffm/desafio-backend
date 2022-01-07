package org.femelloffm.dataanalysis.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.*;
import java.time.Clock;
import java.time.format.DateTimeFormatter;


@Configuration
public class ApplicationConfiguration {
    @Value("${user.home}")
    private String homePath;

    @Bean(name = "inputPath")
    public Path inputPath() {
        return Paths.get(homePath, "data", "in");
    }

    @Bean(name = "outputPath")
    public Path outputPath() {
        return Paths.get(homePath, "data", "out");
    }

    @Bean
    public WatchService directoryWatcher() throws IOException {
        return FileSystems.getDefault().newWatchService();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy--HH-mm-ss");
    }
}