package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String o) {
        return storage.containsKey(o);
    }

    @Override
    public Resume doGet(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    public void doSave(String searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void doUpdate(String searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void doDelete(String searchKey) {
        storage.remove(searchKey);
    }

    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }
}

