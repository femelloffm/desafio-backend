package org.femelloffm.dataanalysis.repositories;

import org.femelloffm.dataanalysis.exceptions.MissingDirectoryException;
import org.femelloffm.dataanalysis.utils.FileParser;
import org.femelloffm.dataanalysis.exceptions.ObjectParsingException;
import org.femelloffm.dataanalysis.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class FlatFileRepository {
    private static final Logger logger = LoggerFactory.getLogger(FlatFileRepository.class);
    private static final String FLAT_FILE_EXTENSION = ".dat";
    private static final String DATA_IN_LINE_SEPARATOR = "(?=00[1-3]+)";
    private Path inputPath;
    private Path outputPath;

    @Autowired
    public FlatFileRepository(Path inputPath, Path outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    public FlatFileData readFile(Path filePath) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath.toString()),
                                                                                            StandardCharsets.UTF_8))) {
            FlatFileData fileData = new FlatFileData();
            String line;
            while ((line = fileReader.readLine()) != null) {
                for (String data : line.split(DATA_IN_LINE_SEPARATOR)) {
                    try {
                        addDataFromFile(data.trim(), fileData);
                    } catch (ObjectParsingException | IllegalArgumentException ex) {
                        logger.warn("Skipping data: ", ex);
                    }
                }
            }
            return fileData;
        }
    }

    public void writeFile(String filename, OutputData outputData) throws IOException {
        Path filePath = outputPath.resolve(filename);
        try (BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath.toString()),
                                                                                                StandardCharsets.UTF_8))) {
            fileWriter.write(outputData.toString());
        }
    }

    public List<Path> readInputDirectoryContent() throws IOException {
        try (Stream<Path> paths = Files.walk(inputPath)) {
            return paths.filter(filename -> filename.toString().endsWith(FLAT_FILE_EXTENSION))
                    .collect(Collectors.toList());
        }
    }

    public void ensureDirectoriesAvailability() throws MissingDirectoryException {
        ensureDirectoryExists(inputPath);
        ensureDirectoryExists(outputPath);
    }

    private void ensureDirectoryExists(Path directoryPath) {
        try {
            if (directoryPath.toFile().exists()) return;
            if (!directoryPath.toFile().mkdirs()) throw new MissingDirectoryException(directoryPath);
        } catch (SecurityException ex) {
            throw new MissingDirectoryException(directoryPath);
        }
    }

    private void addDataFromFile(String dataString, FlatFileData fileData) {
        String formatId = dataString.substring(0, 3);
        switch (formatId) {
            case "001": fileData.addSalesman(FileParser.parseSalesman(dataString));
                break;
            case "002": fileData.addCustomer(FileParser.parseCustomer(dataString));
                break;
            case "003": fileData.addSale(FileParser.parseSale(dataString));
                break;
            default: throw new IllegalArgumentException(formatId + " is not a valid format ID");
        }
    }
}