package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected boolean isExist(Object o) {
        return (int) o >= 0;
    }

    @Override
    public Resume doGet(String uuid) {
        return storage.get((int) getSearchKey(uuid));
    }

    @Override
    public void doSave(Resume resume) {
        storage.add(resume);
    }

    @Override
    public void doUpdate(Resume resume) {
        storage.add((int) getSearchKey(resume.getUuid()), resume);
    }

    @Override
    public void doDelete(String uuid) {
        storage.remove((int) getSearchKey(uuid));

    }
}

