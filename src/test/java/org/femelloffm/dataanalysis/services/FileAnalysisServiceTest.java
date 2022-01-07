package org.femelloffm.dataanalysis.services;

import org.femelloffm.dataanalysis.exceptions.FailedAnalysisException;
import org.femelloffm.dataanalysis.models.*;
import org.femelloffm.dataanalysis.repositories.FlatFileRepository;
import org.femelloffm.dataanalysis.repositories.ReportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = FileAnalysisService.class)
public class FileAnalysisServiceTest {
    @Autowired
    private FileAnalysisService fileAnalysisService;
    @MockBean
    private FlatFileRepository flatFileRepository;
    @MockBean
    private ReportRepository reportRepository;

    @Test
    public void shouldAnalyseFilesIfDirectoryHasFiles() throws IOException {
        Item item = new Item(2, 3, 14.5);
        FlatFileData flatFileData = new FlatFileData();
        flatFileData.addSalesman(new Salesman("12345678912", "Jose", 1500.0));
        flatFileData.addCustomer(new Customer("12345678912345", "Katiane", "Rural"));
        flatFileData.addSale(new Sale(1, "Jose", Collections.singletonList(item)));
        flatFileData.addSale(new Sale(2, "Jose", Collections.singletonList(item)));

        Path filePath = Paths.get("file.dat");
        when(flatFileRepository.readInputDirectoryContent()).thenReturn(Collections.singletonList(filePath));
        when(flatFileRepository.readFile(filePath)).thenReturn(flatFileData);

        assertTrue(fileAnalysisService.analyseAllFlatFiles());
        verify(reportRepository).addSalesmanAmount(1);
        verify(reportRepository).addCustomerAmount(1);
        verify(reportRepository).addSale(1, "Jose", 43.5);
        verify(reportRepository).addSale(2, "Jose", 43.5);
    }

    @Test
    public void shouldNotAnalyseFilesIfDirectoryIsEmpty() throws IOException {
        when(flatFileRepository.readInputDirectoryContent()).thenReturn(Collections.emptyList());

        assertFalse(fileAnalysisService.analyseAllFlatFiles());
        verifyNoInteractions(reportRepository);
    }

    @Test
    public void shouldThrowExceptionIfEveryFileWasSkipped() throws IOException {
        Path filePath1 = Paths.get("file1.dat");
        Path filePath2 = Paths.get("file2.dat");
        when(flatFileRepository.readInputDirectoryContent()).thenReturn(Arrays.asList(filePath1, filePath2));
        when(flatFileRepository.readFile(any(Path.class))).thenThrow(new IOException("error"));

        assertThrows(FailedAnalysisException.class, () -> fileAnalysisService.analyseAllFlatFiles());
        verifyNoInteractions(reportRepository);
    }

    @Test
    public void shouldThrowExceptionIfDirectoryReadingFailed() throws IOException {
        when(flatFileRepository.readInputDirectoryContent()).thenThrow(new IOException("error"));

        assertThrows(FailedAnalysisException.class, () -> fileAnalysisService.analyseAllFlatFiles());
        verifyNoInteractions(reportRepository);
    }
}