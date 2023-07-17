package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.Objects;

public class CompanySection extends AbstractSection {
    private final SectionType section;
    private final ArrayList<Company> companies = new ArrayList<>();

    public CompanySection(SectionType sectionType, Company company) {
        Objects.requireNonNull(sectionType);
        Objects.requireNonNull(company);
        section = sectionType;
        companies.add(company);
    }

    public void addCompany(Company company){
        Objects.requireNonNull(company);
        companies.add(company);
    }

    public String getTitle() {
        return section.getTitle();
    }

    public ArrayList<Company> getAll() {
        return companies;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Company com : companies) {
            s.append(com.toString());
            s.append("\n ");
        }
        return " Section name: " + section.getTitle() + "\n Description: " + s;
    }

    @Override
    public int hashCode() {
        return Objects.hash(section.getTitle(), companies);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection sectionType = (CompanySection) o;
        if (!(sectionType.getTitle().equals(this.getTitle()))) return false;
        return sectionType.getTitle().equals(this.getTitle()) && sectionType.getAll().equals(this.getAll());
    }

}
