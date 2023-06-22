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
    public Resume doGet(Object searchKey) {
        return storage.get((int)searchKey);
    }

    @Override
    public void doSave(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    public void doUpdate(Object searchKey, Resume resume) {
        storage.add((int)searchKey, resume);
    }

    @Override
    public void doDelete(Object searchKey) {
        storage.remove((int)searchKey);

    }
}

