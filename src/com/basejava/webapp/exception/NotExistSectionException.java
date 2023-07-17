package com.basejava.webapp.exception;

import com.basejava.webapp.model.SectionType;

public class NotExistSectionException extends RuntimeException {
    public NotExistSectionException(SectionType sectionType) {
        super("Section " + sectionType.getTitle() + " is not exist!");
    }

}
