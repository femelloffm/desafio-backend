package org.femelloffm.dataanalysis.services;

import org.femelloffm.dataanalysis.builders.OutputDataBuilder;
import org.femelloffm.dataanalysis.exceptions.FailedAnalysisException;
import org.femelloffm.dataanalysis.exceptions.SalesNotFoundException;
import org.femelloffm.dataanalysis.models.OutputData;
import org.femelloffm.dataanalysis.repositories.FlatFileRepository;
import org.femelloffm.dataanalysis.repositories.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);
    private static final String REPORT_FILE_EXTENSION = ".done.dat";
    private FileAnalysisService fileAnalysisService;
    private FlatFileRepository flatFileRepository;
    private ReportRepository reportRepository;
    private Clock clock;
    private DateTimeFormatter dateTimeFormatter;

    @Autowired
    public ReportService(FileAnalysisService fileAnalysisService, FlatFileRepository flatFileRepository,
                         ReportRepository reportRepository, Clock clock, DateTimeFormatter dateTimeFormatter) {
        this.fileAnalysisService = fileAnalysisService;
        this.flatFileRepository = flatFileRepository;
        this.reportRepository = reportRepository;
        this.clock = clock;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public Optional<OutputData> generateReport() {
        try {
            try {
                boolean executedAnalysis = fileAnalysisService.analyseAllFlatFiles();
                if (!executedAnalysis) return Optional.empty();
            } catch (FailedAnalysisException ex) {
                logger.error("Unexpected error: " + ex.getMessage(), ex);
                return Optional.empty();
            }
            OutputData outputData = getReportResults();
            flatFileRepository.writeFile(createOutputFilename(), outputData);
            return Optional.of(outputData);
        } catch (IOException ex) {
            logger.error("Could not write report", ex);
            return Optional.empty();
        } finally {
            reportRepository.clearReport();
        }
    }

    private OutputData getReportResults() {
        OutputDataBuilder outputDataBuilder = OutputDataBuilder.builder()
                .withClientAmount(reportRepository.getCustomerCount())
                .withSalesmanAmount(reportRepository.getSalesmanCount());
        try {
            return outputDataBuilder.withMostExpensiveSale(reportRepository.findMostExpensiveSale())
                    .withWorstSalesman(reportRepository.findWorstSalesman()).build();
        } catch (SalesNotFoundException ex) {
            return outputDataBuilder.withoutSales().build();
        }
    }

    private String createOutputFilename() {
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        return dateTimeFormatter.format(currentDateTime) + REPORT_FILE_EXTENSION;
    }
}