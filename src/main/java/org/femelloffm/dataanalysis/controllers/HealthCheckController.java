package org.femelloffm.dataanalysis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;

@RestController
@RequestMapping("/health")
public class HealthCheckController {
    private Path inputPath;
    private Path outputPath;

    @Autowired
    public HealthCheckController(Path inputPath, Path outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> healthCheck() {
        String jsonBody = "{\"input_path\": \"" + inputPath + "\", \"output_path\": \"" + outputPath + "\"}";
        return ResponseEntity.ok(jsonBody);
    }
}
