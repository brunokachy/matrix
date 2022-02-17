package com.matrix.service.impl;

import com.matrix.exception.BadRequestException;
import com.matrix.service.FileProcessorService;
import com.matrix.service.MatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MatrixServiceImpl implements MatrixService {

    @Autowired
    private FileProcessorService fileProcessorService;

    private static final String COMMA_DELIMITER = ",";

    @Override
    public String echo(String filename) {

        List<List<String>> matrix = fetchMatrix(filename);

        StringBuilder result = new StringBuilder();

        for (List<String> record : matrix) {
            for (String s : record) {
                result.append(s);
                result.append(COMMA_DELIMITER);
            }
            result.setLength(result.length() - COMMA_DELIMITER.length());
            result.append("\n");
        }
        return result.toString();
    }

    @Override
    public String flatten(String filename) {
        List<List<String>> matrix = fetchMatrix(filename);

        StringBuilder result = new StringBuilder();

        for (List<String> record : matrix) {
            for (String s : record) {
                result.append(s);
                result.append(COMMA_DELIMITER);
            }
        }
        result.setLength(result.length() - COMMA_DELIMITER.length());

        return result.toString();
    }

    @Override
    public String invert(String filename) {

        List<List<String>> matrix = fetchMatrix(filename);

        StringBuilder result = new StringBuilder();

        String[][] transpose = new String[matrix.size()][matrix.size()];

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                transpose[j][i] = matrix.get(i).get(j);
            }
        }

        for (String[] strings : transpose) {
            for (String string : strings) {
                result.append(string);
                result.append(COMMA_DELIMITER);
            }
            result.setLength(result.length() - COMMA_DELIMITER.length());
            result.append("\n");
        }

        return result.toString();
    }

    @Override
    public Long sum(String filename) {
        List<List<String>> matrix = fetchMatrix(filename);

        long sum = 0;

        for (List<String> record : matrix) {
            for (String s : record) {
                sum += convertToInteger(s);
            }
        }
        return sum;
    }

    @Override
    public Long multiply(String filename) {
        List<List<String>> matrix = fetchMatrix(filename);

        long product = 1;

        for (List<String> record : matrix) {
            for (String s : record) {
                product *= convertToInteger(s);
            }
        }
        return product;
    }

    private void checkIfMatrixIsSquare(List<List<String>> records) {
        int rows = records.size();
        for (List<String> row : records) {
            if (row.size() != rows) {
                throw new BadRequestException("Matrix is not a square");
            }
        }
    }

    private int convertToInteger(String value) {
        int num;

        try {
            num = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new BadRequestException(value + " is not a valid number");
        }

        return num;
    }

    private void validateFilename(String filename) {
        if (!StringUtils.hasLength(filename)) {
            throw new BadRequestException("Please provide a file name");
        }

        int dotIndex = filename.lastIndexOf('.');

        if (dotIndex == -1) {
            throw new BadRequestException("Please include the file extension");
        }

        if (!filename.substring(dotIndex + 1).equals("csv")) {
            throw new BadRequestException("Invalid File. Only .csv file required");
        }
    }

    private List<List<String>> fetchMatrix(String filename) {
        validateFilename(filename);

        List<List<String>> records = fileProcessorService.getMatrix(filename);

        checkIfMatrixIsSquare(records);

        return records;
    }
}
