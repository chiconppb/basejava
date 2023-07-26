package com.basejava.webapp.model;

import com.basejava.webapp.exception.NotExistContactException;
import com.basejava.webapp.storage.AbstractStorage;

import java.util.*;
import java.util.logging.Logger;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    private final String uuid;
    private final String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "UUID must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume(String fullName) {
        uuid = UUID.randomUUID().toString();
        this.fullName = fullName;
    }

    public void addContact(ContactType contactType, String contact) {
        Objects.requireNonNull(contactType, "Contact must not be null!");
        LOG.info("Add contact:\n " + contactType + " = " + contact);
        contacts.put(contactType, contact);
    }

    public String getContact(ContactType contactType) {
        Objects.requireNonNull(contactType, "Contact type can't be null!");
        if (!contacts.containsKey(contactType)) {
            throw new NotExistContactException(contactType);
        }
        LOG.info("Get contact: \n " + contactType + " = " + contacts.get(contactType));
        return contacts.get(contactType);
    }

    public void addSection(SectionType sectionType, AbstractSection section) {
        Objects.requireNonNull(section, "Section must not be null!");
        LOG.info("\n Add section\n" + section);
        sections.put(sectionType, section);
    }

    public AbstractSection getSection(SectionType sectionType) {
        Objects.requireNonNull(sectionType, "Section type can't be null!");
        return sections.get(sectionType);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "UUID = " + uuid + ", " + "FullName = " + fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public int compareTo(Resume o) {
        int i = fullName.compareTo(o.fullName);
        return i != 0 ? i : uuid.compareTo(o.uuid);
    }
}
