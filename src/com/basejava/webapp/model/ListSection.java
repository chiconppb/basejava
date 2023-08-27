package com.basejava.webapp.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;

    private SectionType section;
    private final List<String> strings = new ArrayList<>();

    public ListSection() {
    }

    public ListSection(SectionType sectionType, String text) {
        Objects.requireNonNull(sectionType, "Section type can't be null!");
        Objects.requireNonNull(text, "String type can't be null!");
        section = sectionType;
        strings.add(text);
    }

    public SectionType getSectionType() {
        return section;
    }

    public void addString(String string) {
        strings.add(string);
    }

    public List<String> getStrings() {
        return strings;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (String str : strings) {
            s.append(str);
            s.append("\n ");
        }
        return " Section name: " + section.getTitle() + "\n Description:\n " + s;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSectionType().getTitle(), strings);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection sectionType = (ListSection) o;
        if (!(sectionType.getSectionType().getTitle().equals(this.getSectionType().getTitle()))) return false;
        return sectionType.getSectionType().getTitle().equals(this.getSectionType().getTitle()) && sectionType.getStrings().equals(this.getStrings());
    }
}
