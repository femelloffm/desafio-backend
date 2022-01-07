package org.femelloffm.dataanalysis.monitor;

import org.femelloffm.dataanalysis.repositories.FlatFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

@Component
public class MonitorLifecycleManager {
    private static final Logger logger = LoggerFactory.getLogger(MonitorLifecycleManager.class);
    private WatchService watchService;
    private DirectoryMonitor directoryMonitor;
    private Path inputPath;
    private FlatFileRepository flatFileRepository;

    @Autowired
    public MonitorLifecycleManager(WatchService watchService, DirectoryMonitor directoryMonitor, Path inputPath,
                                                                    FlatFileRepository flatFileRepository) {
        this.watchService = watchService;
        this.directoryMonitor = directoryMonitor;
        this.inputPath = inputPath;
        this.flatFileRepository = flatFileRepository;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void startDirectoryMonitor() {
        try {
            flatFileRepository.ensureDirectoriesAvailability();
            inputPath.register(watchService, ENTRY_CREATE);
            directoryMonitor.monitorInputDirectory();
        } catch (IOException ex) {
            logger.error("Could not start directory monitor", ex);
        }
    }

    @EventListener(ContextClosedEvent.class)
    public void stopDirectoryMonitor() {
        try {
            watchService.close();
        } catch (IOException ex) {
            logger.error("Could not stop directory monitor", ex);
        }
    }
}