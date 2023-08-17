package com.basejava.webapp.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanySection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;

    private final SectionType section;
    private final List<Company> companies = new ArrayList<>();

    public CompanySection(SectionType sectionType, Company company) {
        Objects.requireNonNull(sectionType, "Section type can't be null!");
        Objects.requireNonNull(company, "Company can't be null!");
        section = sectionType;
        companies.add(company);
    }

    public void addCompany(Company company) {
        Objects.requireNonNull(company);
        if (!companies.isEmpty()) {
            for (int i = 0; i < companies.size(); i++) {
                if (companies.get(i).getName().equals(company.getName())) {
                    Company newCompany = companies.get(i);
                    for (int j = 0; j < company.getPeriods().size(); j++) {
                        newCompany.addPeriod(company.getPeriods().get(i));
                    }
                    companies.add(i, newCompany);
                    return;
                }
            }
        }
        companies.add(company);
    }

    public SectionType getSectionType() {
        return section;
    }

    public List<Company> getAll() {
        return companies;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Company com : companies) {
            s.append(com.toString());
            s.append("\n ");
        }
        return " Section name: " + section.getTitle() + "\n Description: \n " + s;
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
        if (!(sectionType.getSectionType().getTitle().equals(this.getSectionType().getTitle()))) return false;
        return sectionType.getSectionType().getTitle().equals(this.getSectionType().getTitle()) && sectionType.getAll().equals(this.getAll());
    }

}
