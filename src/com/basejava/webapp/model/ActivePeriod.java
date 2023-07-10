package com.basejava.webapp.model;

import java.time.LocalDate;

public class ActivePeriod {
    private String title;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String description;

    public ActivePeriod(String title, LocalDate begin, LocalDate end, String description) {
        this.title = title;
        this.beginDate = begin;
        this.endDate = end;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void updateTitle(String newName) {
        title = newName;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void updatePeriod(LocalDate newBegin, LocalDate newEnd) {
        beginDate = newBegin;
        endDate = newEnd;
    }

    public String getDescription() {
        return description;
    }

    public void updateDescription(String newDescription) {
        description = newDescription;
    }

    @Override
    public String toString() {
        String begin = beginDate.getMonthValue() + "/" + beginDate.getYear();
        String end = endDate.getMonthValue() + "/" + endDate.getYear();
        int nowMonth = LocalDate.now().getMonthValue();
        int nowYear = LocalDate.now().getYear();
        String now = nowMonth + "/" + nowYear;
        if (end.equals(now)) {
            end = "Until now";
        }
        String period = begin + " - " + end;
        return period + "] " + "\n " + "Post: " + title + "\n " + "Activity: [" + description;
    }
}
