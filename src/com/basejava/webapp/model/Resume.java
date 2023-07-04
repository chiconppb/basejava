package com.basejava.webapp.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    public Resume() {
        uuid = (UUID.randomUUID().toString());
        fullName = null;
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume(String fullName) {
        uuid = (UUID.randomUUID().toString());
        this.fullName = fullName;
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

        return (uuid.equals(resume.uuid) && fullName.equals(resume.fullName));
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }
}
