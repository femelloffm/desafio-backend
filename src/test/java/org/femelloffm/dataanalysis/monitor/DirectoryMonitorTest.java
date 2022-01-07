package org.femelloffm.dataanalysis.monitor;

import org.femelloffm.dataanalysis.models.OutputData;
import org.femelloffm.dataanalysis.services.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.nio.file.*;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = DirectoryMonitor.class)
public class DirectoryMonitorTest {
    private static final int minNumberOfReports = 1;
    @Autowired
    private DirectoryMonitor directoryMonitor;
    @MockBean
    private WatchEvent<Path> fileCreatedEvent;
    @MockBean
    private WatchKey watchKey;
    @MockBean
    private WatchService watchService;
    @MockBean
    private ReportService reportService;

    @BeforeEach
    public void setup() throws InterruptedException {
        when(reportService.generateReport()).thenReturn(Optional.of(new OutputData()));
        when(watchService.take()).thenReturn(watchKey).thenThrow(new ClosedWatchServiceException());
    }

    @Test
    public void shouldGenerateReportAfterStartup() {
        when(watchKey.pollEvents()).thenReturn(Collections.emptyList());

        directoryMonitor.monitorInputDirectory();
        verify(reportService, times(minNumberOfReports)).generateReport();
    }

    @Test
    public void shouldGenerateNewReportIfNewFlatFileIsAvailable() {
        when(fileCreatedEvent.context()).thenReturn(Paths.get("file1.dat"));
        when(watchKey.pollEvents()).thenReturn(Collections.singletonList(fileCreatedEvent));

        directoryMonitor.monitorInputDirectory();
        verify(reportService, times(minNumberOfReports + 1)).generateReport();
    }

    @Test
    public void shouldNotGenerateNewReportIfNewFileExtensionIsNotDat() {
        when(fileCreatedEvent.context()).thenReturn(Paths.get("file1.txt"));
        when(watchKey.pollEvents()).thenReturn(Collections.singletonList(fileCreatedEvent));

        directoryMonitor.monitorInputDirectory();
        verify(reportService, times(minNumberOfReports)).generateReport();
    }
}