package com.basejava.webapp.model;

public enum ContactType {
    PHONE_NUMBER("+79218550482"),
    SKYPE("grigory.kislin"),
    EMAIL("gkislin@yandex.ru"),
    LINKEDIN("https://www.linkedin.com/in/gkislin"),
    GITHUB("https://github.com/gkislin"),
    STACKOVERFLOW("https://stackoverflow.com/users/548473/grigory-kislin"),
    HOMEPAGE("https://gkislin.ru/");


    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
