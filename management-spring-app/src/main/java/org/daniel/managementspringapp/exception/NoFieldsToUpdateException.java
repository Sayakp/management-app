package org.daniel.managementspringapp.exception;

public class NoFieldsToUpdateException extends RuntimeException {
    public NoFieldsToUpdateException(String message) {
        super(message);
    }
}
