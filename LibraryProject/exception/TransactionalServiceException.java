package com.example.LibraryProject.exception;

public class TransactionalServiceException extends RuntimeException{
    public TransactionalServiceException(String message) {
        super(message);
    }
}
