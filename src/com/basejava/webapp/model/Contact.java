package com.basejava.webapp.model;

import java.util.Objects;

public class Contact {
    private String name;
    private String content;

    public Contact(String n, String c) {
        name = n;
        content = c;
    }

    public String getName() {
        return name;
    }

    public void updateName(String newName) {
        name = newName;
    }

    public String getContent() {
        return content;
    }

    public void updateContent(String newContent) {
        content = newContent;
    }

    @Override
    public String toString() {
        return name + ": " + content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return (name.equals(contact.getName()) && content.equals(contact.getContent()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, content);
    }

}
