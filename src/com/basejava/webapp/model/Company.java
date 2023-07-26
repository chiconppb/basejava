package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company {
    private final Link homepage;
    private final List<Period> periods = new ArrayList<>();


    public Company(String name, String website, Period period) {
        this.homepage = new Link(name,website);
        periods.add(period);
    }

    public String getName() {
        return homepage.getName();
    }

    public String getWebsite() {
        return homepage.getUrl();
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Period period : periods) {
            s.append(period);
        }
        return "Company name: " + homepage.getName() + "\n Website: " + homepage.getUrl() + "\n " + s;
    }

    @Override
    public int hashCode() {
        return Objects.hash(homepage, periods);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return company.getName().equals(this.getName()) && company.getPeriods().equals(this.getPeriods()) && company.getWebsite().equals(this.getWebsite());
    }
}
