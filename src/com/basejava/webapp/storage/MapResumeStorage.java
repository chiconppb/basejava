package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    public Resume doGet(Resume existSearchKey) {
        return storage.get(existSearchKey.getUuid());
    }

    @Override
    public void doSave(Resume notExistSearchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void doUpdate(Resume existSearchKey, Resume resume) {
        storage.put(existSearchKey.getUuid(), resume);

    }

    @Override
    public void doDelete(Resume existSearchKey) {
        storage.remove(existSearchKey.getUuid());
    }

    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }

}
