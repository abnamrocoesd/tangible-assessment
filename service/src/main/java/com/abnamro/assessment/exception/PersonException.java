package com.abnamro.assessment.exception;

/**
 * Custom Exception
 */
public class PersonException extends Exception{
    public PersonException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public PersonException(String errorMessage) {
        super(errorMessage);
    }
}