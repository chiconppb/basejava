package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class HireSection extends AbstractSection<Company> {
    private final List<Company> companies = new ArrayList<>();

    public HireSection(SectionType e) {
        super(e);
    }

    @Override
    protected boolean isExist(Company company) {
        return companies.contains(company);
    }

    @Override
    protected void doAdd(Company company) {
        companies.add(getNotExistElement(company));
    }

    @Override
    protected Company doGet(Company company) {
        int index = companies.indexOf(getExistElement(company));
        return companies.get(index);
    }

    @Override
    protected String getInfo() {
        StringBuilder s = new StringBuilder("Section <" + title + "> contains\n" + "Companies:");
        for (Company i : companies) {
            s.append("\n").append(i);
        }
        return s.toString();
    }
}
