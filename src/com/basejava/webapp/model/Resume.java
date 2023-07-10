package com.basejava.webapp.model;

import com.basejava.webapp.exception.StorageException;
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
    private Map<String, String> contacts = new HashMap<>();
    private List<AbstractSection<Object>> sections = new ArrayList<>();

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

    public void addContact(Contact c) {
        Objects.requireNonNull(c, "Contact can't be empty!");
        LOG.info("Add contact: " + c + "\n");
        contacts.put(c.getName(), c.getContent());
    }

    public void addSection(AbstractSection s) {
        LOG.info("Add section <" + s.title + ">\n");
        sections.add(s);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContact(String contactType) {
        String contact = contacts.get(contactType);
        LOG.info("Get contact: " + contactType + " - " + contact + "\n");
        return contact;
    }

    public AbstractSection<Object> getSection(String sectionName) {
        for (AbstractSection<Object> o : sections) {
            if (o.getName().equalsIgnoreCase(sectionName)) {
                LOG.info("Get section <" + o.title + ">\n");
                return o;
            }
        }
        throw new StorageException("The section is not exist", sectionName);
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
