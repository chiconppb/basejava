package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private final String title;
    private final LocalDate beginDate;
    private final LocalDate endDate;
    private final String description;

    public Period(String title, LocalDate beginDate, LocalDate endDate, String description) {
        Objects.requireNonNull(title, "Post must not be null!");
        Objects.requireNonNull(beginDate, "Begin date must not be null!");
        Objects.requireNonNull(endDate, "End date must not be null!");
        this.title = title;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int nowYear = LocalDate.now().getYear();
        int nowMonth = LocalDate.now().getMonthValue();
        s.append("Post: ");
        s.append(title);
        s.append("\n Period: ");
        s.append(beginDate.getYear());
        s.append("/");
        s.append(beginDate.getMonthValue());
        s.append(" - ");
        if (endDate.getYear() == nowYear && endDate.getMonthValue() == nowMonth) {
            s.append("Until now");
        } else {
            s.append(endDate.getYear());
            s.append("/");
            s.append(endDate.getMonthValue());
        }
        s.append("\n Description: ");
        s.append(description);

        return s.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, beginDate, endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!period.getTitle().equals(title)) return false;
        return period.getTitle().equals(this.getTitle()) && period.getDescription().equals(this.getDescription()) && period.getBeginDate().equals(this.getEndDate()) && period.getEndDate().equals(this.getEndDate());
    }
}
