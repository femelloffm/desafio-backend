package org.femelloffm.dataanalysis.monitor;

import org.femelloffm.dataanalysis.models.OutputData;
import org.femelloffm.dataanalysis.services.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.*;
import java.util.Optional;

@Component
public class DirectoryMonitor {
    private static final Logger logger = LoggerFactory.getLogger(DirectoryMonitor.class);
    private static final String FLAT_FILE_EXTENSION = ".dat";
    private WatchService directoryWatcher;
    private ReportService reportService;

    @Autowired
    public DirectoryMonitor(WatchService directoryWatcher, ReportService reportService) {
        this.directoryWatcher = directoryWatcher;
        this.reportService = reportService;
    }

    public void monitorInputDirectory() {
        callReportGenerator();
        while (true) {
            WatchKey key;
            try {
                key = directoryWatcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    Path createdFile = (Path) event.context();
                    if (createdFile.toString().endsWith(FLAT_FILE_EXTENSION)) callReportGenerator();
                }
                key.reset();
            } catch (InterruptedException | ClosedWatchServiceException ex) {
                logger.warn("Stopping directory monitoring", ex);
                break;
            }
        }
    }

    private void callReportGenerator() {
        Optional<OutputData> outputData = reportService.generateReport();
        outputData.ifPresent(data -> logger.info("Wrote new report: " + data));
    }
}