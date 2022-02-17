package com.matrix.service.impl;

import com.matrix.exception.UnProcessableFile;
import com.matrix.service.FileProcessorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileProcessorServiceImpl implements FileProcessorService {

    private static final String COMMA_DELIMITER = ",";

    @Value("${file.directory}")
    private String directory;

    @Override
    public List<List<String>> getMatrix(String filename) {
        List<List<String>> matrix = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(readFile(filename))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                matrix.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnProcessableFile(e.getMessage());
        }
        return matrix;
    }

    private FileReader readFile(String filename) {
        String file = directory + filename;

        FileReader inputStream;

        try {
            inputStream = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new com.matrix.exception.FileNotFoundException(filename + " was not found in the directory");
        }

        return inputStream;
    }
}
