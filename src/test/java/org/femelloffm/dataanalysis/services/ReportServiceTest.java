package org.femelloffm.dataanalysis.services;

import org.femelloffm.dataanalysis.configurations.ApplicationConfigurationTest;
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
import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ReportService.class, FileAnalysisService.class, ReportRepository.class, ApplicationConfigurationTest.class})
public class ReportServiceTest {
    @Autowired
    private ReportService reportService;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private Clock clock;
    @Autowired
    private DateTimeFormatter dateTimeFormatter;
    @Autowired
    private FileAnalysisService fileAnalysisService;
    @MockBean
    private FlatFileRepository flatFileRepository;

    @Test
    public void shouldGenerateReport() throws IOException {
        Item item1 = new Item(2, 3, 14.5);
        FlatFileData flatFileData1 = new FlatFileData();
        flatFileData1.addSalesman(new Salesman("12345678912", "Jose", 1500.0));
        flatFileData1.addCustomer(new Customer("12345678912345", "Katiane", "Rural"));
        flatFileData1.addSale(new Sale(1, "Jose", Collections.singletonList(item1)));

        Item item2 = new Item(5, 20, 0.1);
        FlatFileData flatFileData2 = new FlatFileData();
        flatFileData2.addSalesman(new Salesman("12345678977", "Fernanda", 1850.5));
        flatFileData1.addSale(new Sale(2, "Fernanda", Collections.singletonList(item2)));

        Path path1 = Paths.get("file1.dat");
        Path path2 = Paths.get("file2.dat");
        List<Path> pathList = Arrays.asList(path1, path2);

        when(flatFileRepository.readInputDirectoryContent()).thenReturn(pathList);
        when(flatFileRepository.readFile(path1)).thenReturn(flatFileData1);
        when(flatFileRepository.readFile(path2)).thenReturn(flatFileData2);

        String expectedReportFilename = "28-01-2022--00-00-00.done.dat";
        Optional<OutputData> outputData = reportService.generateReport();
        verify(flatFileRepository).writeFile(eq(expectedReportFilename), any(OutputData.class));

        assertTrue(outputData.isPresent());
        assertEquals(1, outputData.get().getClientAmount());
        assertEquals(2, outputData.get().getSalesmanAmount());
        assertEquals(1, outputData.get().getMostExpensiveSaleId());
        assertEquals("Fernanda", outputData.get().getWorstSalesman());
    }

    @Test
    public void shouldReturnEmptyOptionalIfReportWritingFailed() throws IOException {
        doThrow(new IOException("error")).when(flatFileRepository).writeFile(anyString(), any(OutputData.class));

        Optional<OutputData> outputData = reportService.generateReport();
        assertFalse(outputData.isPresent());
    }

    @Test
    public void shouldReturnEmptyOptionalIfAnalysisDidNotHappen() throws IOException {
        when(flatFileRepository.readInputDirectoryContent()).thenReturn(Collections.emptyList());

        Optional<OutputData> outputData = reportService.generateReport();
        assertFalse(outputData.isPresent());
    }

    @Test
    public void shouldReturnEmptyOptionalIfAnalysisThrewException() throws IOException {
        when(flatFileRepository.readInputDirectoryContent()).thenThrow(new IOException("error"));

        Optional<OutputData> outputData = reportService.generateReport();
        assertFalse(outputData.isPresent());
    }
}