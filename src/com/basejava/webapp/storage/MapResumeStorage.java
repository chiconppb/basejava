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
        List<Resume> allResumes = new ArrayList<>(storage.values());
        allResumes.sort(RESUME_FULLNAME_COMPARATOR);
        return allResumes;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getSearchKey(String uuid) {
    }

    @Override
    protected boolean isExist(Object searchKey) {
    }

    @Override
    public Resume doGet(Object existSearchKey) {
    }

    @Override
    public void doSave(Object notExistSearchKey, Resume resume) {
    }

    @Override
    public void doUpdate(Object existSearchKey, Resume resume) {

    }

    @Override
    public void doDelete(Object existSearchKey) {
    }

}
