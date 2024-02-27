package com.basejava.webapp.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final ListSection DEFAULT = new ListSection("");

    private List<String> strings = new ArrayList<>();

    public ListSection() {
    }

    public ListSection(String text) {
        Objects.requireNonNull(text, "String type can't be null!");
        strings.add(text);
    }

    public ListSection(List<String> strings) {
        Objects.requireNonNull(strings, "String type can't be null!");
        this.strings = strings;
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
            s.append('\n');
        }
        return s.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(strings, that.strings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strings);
    }

    public static ListSection getEmptySection() {
        return DEFAULT;
    }
}
