package com.basejava.webapp.exception;

import com.basejava.webapp.model.ContactType;

public class NotExistContactException extends RuntimeException {
    public NotExistContactException(ContactType contactType) {
        super("Contact " + contactType.getTitle() + " is not exist!");
    }
}
