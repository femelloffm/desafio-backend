package org.femelloffm.dataanalysis.services;

import org.femelloffm.dataanalysis.exceptions.FailedAnalysisException;
import org.femelloffm.dataanalysis.models.*;
import org.femelloffm.dataanalysis.repositories.FlatFileRepository;
import org.femelloffm.dataanalysis.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class FileAnalysisService {
    private FlatFileRepository flatFileRepository;
    private ReportRepository reportRepository;

    @Autowired
    public FileAnalysisService(FlatFileRepository flatFileRepository, ReportRepository reportRepository) {
        this.flatFileRepository = flatFileRepository;
        this.reportRepository = reportRepository;
    }

    public boolean analyseAllFlatFiles() {
        try {
            List<Path> allFlatFiles = flatFileRepository.readInputDirectoryContent();
            if (allFlatFiles.isEmpty()) return false;
            int processedFiles = 0;
            for (Path file : allFlatFiles) {
                if (analyseFile(file)) processedFiles++;
            }
            if (processedFiles == 0) throw new FailedAnalysisException("Analysis of every file has failed");
            return true;
        } catch (IOException ex) {
            throw new FailedAnalysisException("Could not read directory content", ex);
        }
    }

    private boolean analyseFile(Path filename) {
        try {
            FlatFileData fileData = flatFileRepository.readFile(filename);
            reportRepository.addCustomerAmount(fileData.getCustomers().size());
            reportRepository.addSalesmanAmount(fileData.getSalesmen().size());
            fileData.getSales().forEach(sale -> reportRepository.addSale(sale.getId(),
                    sale.getSalesmanName(), getSaleCost(sale)));
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    private Double getSaleCost(Sale sale) {
        return sale.getItems().stream()
                .map(item -> item.getPrice() * item.getQuantity())
                .reduce(0.0, Double::sum);
    }
}