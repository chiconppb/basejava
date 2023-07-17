package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.Objects;

public class Company {
    private final String name;
    private final String website;
    private final ArrayList<Period> periods = new ArrayList<>();


    public Company(String name, String website, Period period) {
        this.name = name;
        this.website = website;
        periods.add(period);
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public ArrayList<Period> getPeriods() {
        return periods;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Period period : periods) {
            s.append(period);
        }
        return "Company name: " + name + "\n Website: " + website + "\n " + s;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, website, periods);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return company.getName().equals(this.getName()) && company.getPeriods().equals(this.getPeriods()) && company.getWebsite().equals(this.getWebsite());
    }
}
