package com.basejava.webapp.model;

import com.basejava.webapp.exception.StorageException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company {
    private String name;
    private String website;
    private List<ActivePeriod> periods = new ArrayList<>();

    public Company(String name, String website, ActivePeriod period) {
        this.name = name;
        this.website = website;
        this.periods.add(period);
    }

    public String getName() {
        return name;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void updateWebsite(String website) {
        this.website = website;
    }

    public void addPeriod(ActivePeriod p) {
        periods.add(p);
    }

    public List<ActivePeriod> getPeriodsList() {
        return periods;
    }

    public void updatePeriod(ActivePeriod p) {
        int index;
        if (periods.contains(p)) {
            index = periods.indexOf(p);
            periods.set(index, p);
            return;
        }
        throw new StorageException("The period is not exist", p.toString());
    }

    @Override
    public String toString() {
        return "Company name: " + name + ",\n Website: " + website + ",\n Period: " + periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company com = (Company) o;
        return name.equals(com.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, website, periods);
    }
}
