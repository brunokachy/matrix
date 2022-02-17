package com.matrix.service;

public interface MatrixService {

    String echo(String filename);

    String flatten(String filename);

    String invert(String filename);

    Long sum(String filename);

    Long multiply(String filename);
}
