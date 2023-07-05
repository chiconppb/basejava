package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object o) {
        return o != null;
    }

    @Override
    public Resume doGet(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    public void doSave(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    public void doUpdate(Object searchKey, Resume resume) {
        storage.set((Integer) searchKey, resume);
    }

    @Override
    public void doDelete(Object searchKey) {
        storage.remove(((Integer) searchKey).intValue());

    }

    protected List<Resume> doCopyAll() {
        return storage;
    }

    ;
}

