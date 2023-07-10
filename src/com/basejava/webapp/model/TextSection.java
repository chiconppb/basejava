package com.basejava.webapp.model;

public class TextSection extends AbstractSection<String> {
    private String text;

    public TextSection(SectionType e) {
        super(e);
    }

    @Override
    protected boolean isExist(String string) {
        return false;
    }

    @Override
    protected void doAdd(String text) {
        if (this.text == null) {
            this.text = text;
        }
        this.text += text;
    }

    @Override
    protected String doGet(String str) {
        if (text == null) {
            return "";
        }
        return this.text;
    }

    @Override
    protected String getInfo() {
        return "Section <" + title + "> contains\n" + "Description:\n" + text;
    }
}
