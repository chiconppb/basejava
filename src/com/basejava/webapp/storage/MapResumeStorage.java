package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        return storage.values().stream().sorted(RESUME_FULLNAME_COMPARATOR).toList();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    public Resume doGet(Object existSearchKey) {
        return storage.get(((Resume) existSearchKey).getUuid());
    }

    @Override
    public void doSave(Object notExistSearchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void doUpdate(Object existSearchKey, Resume resume) {
        storage.put(((Resume) existSearchKey).getUuid(), resume);

    }

    @Override
    public void doDelete(Object existSearchKey) {
        storage.remove(((Resume) existSearchKey).getUuid());
    }

}
