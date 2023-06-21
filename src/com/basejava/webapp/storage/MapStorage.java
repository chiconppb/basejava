package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    private final HashMap<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] allResumes = storage.values().toArray(new Resume[0]);
        Arrays.sort(allResumes);
        return allResumes;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object o) {
        return storage.containsKey((String) o);
    }

    @Override
    public Resume doGet(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void doSave(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void doUpdate(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void doDelete(String uuid) {
        storage.remove(uuid);
    }
}

