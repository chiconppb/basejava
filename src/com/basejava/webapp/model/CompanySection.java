package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanySection extends AbstractSection {
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
        for (Company c : companies) {
            if (c.getName().equals(company.getName())) {
                for (int i = 0; i < company.getPeriods().size(); i++) {
                    c.addPeriod(company.getPeriods().get(i));
                }
                return;
            }
            companies.add(company);
        }
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
