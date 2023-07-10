package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection<String> {
    private final List<String> strings = new ArrayList<>();

    public ListSection(SectionType e) {
        super(e);
    }

    @Override
    protected boolean isExist(String str) {
        Objects.requireNonNull(str);
        for (String e : strings) {
            return e.equalsIgnoreCase(str);
        }
        return false;
    }

    @Override
    protected void doAdd(String str) {
        strings.add(getNotExistElement(str));
    }

    @Override
    protected String doGet(String str) {
        int index = strings.indexOf(getExistElement(str));
        return strings.get(index);
    }

    @Override
    protected String getInfo() {
        StringBuilder s = new StringBuilder("Section <" + title + "> contains\n" + "Chapters:");
        for (String i : strings) {
            s.append("\n").append(i);
        }
        return s.toString();
    }
}
