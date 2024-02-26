package com.basejava.webapp.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanySection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final CompanySection DEFAULT = new CompanySection(Company.getEmptyCompany());

    private final List<Company> companies = new ArrayList<>();

    public CompanySection() {
    }

    public CompanySection(Company company) {
        Objects.requireNonNull(company, "Company can't be null!");
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

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Company com : companies) {
            s.append(com.toString());
            s.append("\n ");
        }
        return " Company section\n Description: \n " + s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(companies, that.companies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companies);
    }

    public static CompanySection getEmptySection() {
        return DEFAULT;
    }
}
