package com.matrix.controller;

import com.matrix.service.MatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("api/v1/matrix")
public class MatrixController {

    @Autowired
    private MatrixService matrixService;

    @GetMapping(value = "echo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String echo(@RequestParam(name = "filename") String filename) {
        return matrixService.echo(filename);
    }

    @GetMapping(value = "invert", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String invert(@RequestParam(name = "filename") String filename) {
        return matrixService.invert(filename);
    }

    @GetMapping(value = "flatten", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String flatten(@RequestParam(name = "filename") String filename) {
        return matrixService.flatten(filename);
    }

    @GetMapping(value = "sum", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Long sum(@RequestParam(name = "filename") String filename) {
        return matrixService.sum(filename);
    }

    @GetMapping(value = "multiply", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Long multiply(@RequestParam(name = "filename") String filename) {
        return matrixService.multiply(filename);
    }
}
