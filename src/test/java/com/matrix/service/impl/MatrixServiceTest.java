package com.matrix.service.impl;

import com.matrix.exception.BadRequestException;
import com.matrix.service.FileProcessorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class MatrixServiceTest {

    @Mock
    private FileProcessorService fileProcessorService;

    @InjectMocks
    MatrixServiceImpl matrixService;

    @Test
    public void echoMatrix_Successful() {
//      Arrange
        List<List<String>> matrix = buildMatrix();
        String filename = "matrix.csv";
        when(fileProcessorService.getMatrix(filename)).thenReturn(matrix);

        //Act
        String response = matrixService.echo(filename);

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.length() > 0);
    }

    @Test
    public void echoMatrix_NotFileName_ThrowBadRequestException() {
        //Arrange
        List<List<String>> matrix = buildMatrix();
        String filename = "";
        when(fileProcessorService.getMatrix(filename)).thenReturn(matrix);

        //Act
        BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () -> matrixService.echo(filename), "Please provide a file name");

        //Assert
        Assertions.assertNotNull(thrown);
        Assertions.assertEquals("Please provide a file name", thrown.getMessage());
    }

    @Test
    public void echoMatrix_FileNameWithoutExtension_ThrowBadRequestException() {
        //Arrange
        List<List<String>> matrix = buildMatrix();
        String filename = "matrix";
        when(fileProcessorService.getMatrix(filename)).thenReturn(matrix);

        //Act
        BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () -> matrixService.echo(filename), "Please include the file extension");

        //Assert
        Assertions.assertNotNull(thrown);
        Assertions.assertEquals("Please include the file extension", thrown.getMessage());
    }

    @Test
    public void echoMatrix_FileNotCSVType_ThrowBadRequestException() {
        //Arrange
        List<List<String>> matrix = buildMatrix();
        String filename = "matrix.png";
        when(fileProcessorService.getMatrix(filename)).thenReturn(matrix);

        //Act
        BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () -> matrixService.echo(filename), "Invalid File. Only .csv file required");

        //Assert
        Assertions.assertNotNull(thrown);
        Assertions.assertEquals("Invalid File. Only .csv file required", thrown.getMessage());
    }

    @Test
    public void echoMatrix_MatrixNotASquare_ThrowBadRequestException() {
        //Arrange
        List<List<String>> matrix = buildNotASquareMatrix();
        String filename = "matrix.csv";
        when(fileProcessorService.getMatrix(filename)).thenReturn(matrix);

        //Act
         BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () -> matrixService.echo(filename), "Matrix is not a square");

        //Assert
        Assertions.assertNotNull(thrown);
        Assertions.assertEquals("Matrix is not a square", thrown.getMessage());
    }

    @Test
    public void flattenMatrix_Successful() {
//      Arrange
        List<List<String>> matrix = buildMatrix();
        String filename = "matrix.csv";
        when(fileProcessorService.getMatrix(filename)).thenReturn(matrix);

        //Act
        String response = matrixService.flatten(filename);

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.length() > 0);
    }

    @Test
    public void invertMatrix_Successful() {
//      Arrange
        List<List<String>> matrix = buildMatrix();
        String filename = "matrix.csv";
        when(fileProcessorService.getMatrix(filename)).thenReturn(matrix);

        //Act
        String response = matrixService.invert(filename);

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.length() > 0);
    }

    @Test
    public void sumMatrix_Successful() {
//      Arrange
        List<List<String>> matrix = buildMatrix();
        String filename = "matrix.csv";
        when(fileProcessorService.getMatrix(filename)).thenReturn(matrix);

        //Act
        long response = matrixService.sum(filename);

        //Assert
        Assertions.assertEquals(45, response);
    }

    @Test
    public void sumMatrix_MatrixContainsNonInteger_ThrowBadRequestException() {
        //Arrange
        List<List<String>> matrix = buildMatrixWithNonInteger();
        String filename = "matrix.csv";
        when(fileProcessorService.getMatrix(filename)).thenReturn(matrix);

        //Act
        BadRequestException thrown = Assertions.assertThrows(BadRequestException.class, () -> matrixService.sum(filename), "U is not a valid number");

        //Assert
        Assertions.assertNotNull(thrown);
        Assertions.assertEquals( "U is not a valid number", thrown.getMessage());
    }

    @Test
    public void multiplyMatrix_Successful() {
//      Arrange
        List<List<String>> matrix = buildMatrix();
        String filename = "matrix.csv";
        when(fileProcessorService.getMatrix(filename)).thenReturn(matrix);

        //Act
        long response = matrixService.multiply(filename);

        //Assert
        Assertions.assertEquals(362880, response);
    }

    private List<List<String>> buildMatrix(){
        List<List<String>> matrix = new ArrayList<>();

        List<String> row1 = new ArrayList<>();
        row1.add("1");
        row1.add("2");
        row1.add("3");

        List<String> row2 = new ArrayList<>();
        row2.add("4");
        row2.add("5");
        row2.add("6");

        List<String> row3 = new ArrayList<>();
        row3.add("7");
        row3.add("8");
        row3.add("9");

        matrix.add(row1);
        matrix.add(row2);
        matrix.add(row3);

        return matrix;
    }

    private List<List<String>> buildNotASquareMatrix(){
        List<List<String>> matrix = new ArrayList<>();

        List<String> row1 = new ArrayList<>();
        row1.add("1");
        row1.add("2");
        row1.add("3");

        List<String> row2 = new ArrayList<>();
        row2.add("4");
        row2.add("5");
        row2.add("6");

        List<String> row3 = new ArrayList<>();
        row3.add("7");
        row3.add("8");

        matrix.add(row1);
        matrix.add(row2);
        matrix.add(row3);

        return matrix;
    }

    private List<List<String>> buildMatrixWithNonInteger(){
        List<List<String>> matrix = new ArrayList<>();

        List<String> row1 = new ArrayList<>();
        row1.add("U");
        row1.add("M");
        row1.add("3");

        List<String> row2 = new ArrayList<>();
        row2.add("4");
        row2.add("5");
        row2.add("6");

        List<String> row3 = new ArrayList<>();
        row3.add("7");
        row3.add("8");
        row3.add("9");

        matrix.add(row1);
        matrix.add(row2);
        matrix.add(row3);

        return matrix;
    }
}