package com.example.carrentingservicebackend.controller;

import com.example.carrentingservicebackend.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Generated;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Generated
@Hidden
public class ExceptionHandlerController {

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleConstraintViolationException(jakarta.validation.ConstraintViolationException e) {
        return ExceptionResponse.builder()
                .withMessage(e.getMessage())
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .withDate(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleHibernateConstraintViolationException(
            org.hibernate.exception.ConstraintViolationException e) {
        return ExceptionResponse.builder()
                .withMessage(e.getMessage())
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .withDate(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleConversionFailedException(
            ConversionFailedException e) {
        return ExceptionResponse.builder()
                .withMessage(e.getMessage())
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .withDate(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleConversionFailedException(
            IllegalArgumentException e) {
        return ExceptionResponse.builder()
                .withMessage(e.getMessage())
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .withDate(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleConversionFailedException(
            NotFoundException e) {
        return ExceptionResponse.builder()
                .withMessage(e.getMessage())
                .withHttpStatus(HttpStatus.NOT_FOUND)
                .withDate(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleConversionFailedException(
            HttpMessageNotReadableException e) {
        return ExceptionResponse.builder()
                .withMessage(e.getMessage())
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .withDate(LocalDateTime.now())
                .build();
    }

}
