package com.matrix.exception;

public class UnProcessableFile extends RuntimeException{

    public UnProcessableFile(String message) {
        super(message);
    }
}
