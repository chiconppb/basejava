package com.basejava.webapp.exception;

public class StorageOverflowException extends StorageException {
    public StorageOverflowException(String uuid) {
        super("Storage is full!", uuid);
    }
}
