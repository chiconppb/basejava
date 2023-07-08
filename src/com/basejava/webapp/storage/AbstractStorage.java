package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<T> implements Storage {

    public Resume get(String uuid) {
        return doGet(getExistingSearchKey(uuid));
    }

    public List<Resume> getAllSorted() {
        List<Resume> allResumes = doCopyAll();
        Collections.sort(allResumes);
        return allResumes;
    }

    public void save(Resume resume) {
        doSave(getNotExistingSearchKey(resume.getUuid()), resume);
    }

    public void update(Resume resume) {
        doUpdate(getExistingSearchKey(resume.getUuid()), resume);
    }

    public void delete(String uuid) {
        doDelete(getExistingSearchKey(uuid));
    }

    protected T getExistingSearchKey(String uuid) {
        if (!isExist(getSearchKey(uuid))) {
            throw new NotExistStorageException(uuid);
        }
        return getSearchKey(uuid);
    }

    protected T getNotExistingSearchKey(String uuid) {
        if (isExist(getSearchKey(uuid))) {
            throw new ExistStorageException(uuid);
        }
        return getSearchKey(uuid);
    }

    protected abstract T getSearchKey(String uuid);

    protected abstract boolean isExist(T o);

    protected abstract Resume doGet(T searchKey);

    protected abstract void doSave(T searchKey, Resume resume);

    protected abstract void doUpdate(T searchKey, Resume resume);

    protected abstract void doDelete(T searchKey);

    protected abstract List<Resume> doCopyAll();
}
