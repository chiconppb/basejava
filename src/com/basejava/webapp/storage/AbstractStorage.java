package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public Resume get(String uuid) {
        return doGet(getExistingSearchKey(uuid));
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

    protected Object getExistingSearchKey(String uuid) {
        if (!isExist(getSearchKey(uuid))) {
            throw new NotExistStorageException(uuid);
        }
        return getSearchKey(uuid);
    }

    protected Object getNotExistingSearchKey(String uuid) {
        if (isExist(getSearchKey(uuid))) {
            throw new ExistStorageException(uuid);
        }
        return getSearchKey(uuid);
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object o);

    public abstract Resume doGet(Object searchKey);

    public abstract void doSave(Object searchKey, Resume resume);

    public abstract void doUpdate(Object searchKey, Resume resume);

    public abstract void doDelete(Object searchKey);


}
