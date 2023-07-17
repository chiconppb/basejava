package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private final SectionType section;
    private final ArrayList<String> strings = new ArrayList<>();


    public ListSection(SectionType sectionType, String text) {
        Objects.requireNonNull(sectionType);
        Objects.requireNonNull(text);
        section = sectionType;
        strings.add(text);
    }

    public String getTitle() {
        return section.getTitle();
    }

    public void addString(String string) {
        strings.add(string);
    }

    public ArrayList<String> getStrings() {
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
        return Objects.hash(this.getTitle(), strings);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection sectionType = (ListSection) o;
        if (!(sectionType.getTitle().equals(this.getTitle()))) return false;
        return sectionType.getTitle().equals(this.getTitle()) && sectionType.getStrings().equals(this.getStrings());
    }
}
