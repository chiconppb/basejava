package com.basejava.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private final SectionType section;
    private final String text;

    public TextSection(SectionType sectionType, String description) {
        section = sectionType;
        text = description;
    }

    public String getTitle() {
        return section.getTitle();
    }

    public String getDescription() {
        return text;
    }

    @Override
    public String toString() {
        return " Section name: " + this.getTitle() + "\n Description: " + text;
    }

    @Override
    public int hashCode() {
        return Objects.hash(section.getTitle(), text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection sectionType = (TextSection) o;

        if (!(sectionType.getTitle().equals(this.getTitle()))) return false;
        return sectionType.getTitle().equals(section.getTitle()) && sectionType.getDescription().equals(text);
    }


}
