package com.basejava.webapp.model;

import com.basejava.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.basejava.webapp.util.DateUtil.NOW;
import static com.basejava.webapp.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final Company DEFAULT = new Company("", "");

    private Link homepage;
    private List<Period> periods;

    public Company() {
    }

    public Company(String name, String website, Period... periods) {
        this(new Link(name, website), Arrays.asList(periods));
    }

    public Company(Link homepage, List<Period> periods) {
        this.homepage = homepage;
        this.periods = periods;
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

    public void addPeriod(Period period) {
        periods.add(period);
    }

    @Override
    public String toString() {
        return "Company (" + homepage + ", " + periods + ')';
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {
        private String title;
        private String description;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate beginDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;

        public Period() {
        }

        public Period(int startYear, Month startMonth, String title, String description) {
            this(of(startYear, startMonth), NOW, title, description);
        }

        public Period(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, description);
        }

        public Period(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.beginDate = startDate;
            this.endDate = endDate;
            this.title = title;
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
            s.append("Post: ");
            s.append(title);
            s.append("]\n Period: ");
            s.append(beginDate.getYear());
            s.append("/");
            s.append(beginDate.getMonthValue());
            s.append(" - ");
            if (endDate.getYear() <= NOW.getYear() && endDate.getMonthValue() <= NOW.getMonthValue()) {
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
            return Objects.equals(title, period.title) && Objects.equals(description, period.description) && Objects.equals(beginDate, period.beginDate) && Objects.equals(endDate, period.endDate);
        }
    }

    public static Company getEmptyCompany() {
        return DEFAULT;
    }
}
