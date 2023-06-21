package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public Resume get(String uuid) {
        if (!isExist(getSearchKey(uuid))) {
            throw new NotExistStorageException(uuid);
        }
        return doGet(uuid);
    }

    public void save(Resume resume) {
        if (isExist(getSearchKey(resume.getUuid()))) {
            throw new ExistStorageException(resume.getUuid());
        }
        doSave(resume);
    }

    public void update(Resume resume) {
        if (!isExist(getSearchKey(resume.getUuid()))) {
            throw new NotExistStorageException(resume.getUuid());
        }
        doUpdate(resume);
    }

    public void delete(String uuid) {
        if (!isExist(getSearchKey(uuid))) {
            throw new NotExistStorageException(uuid);
        }
        doDelete(uuid);
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object o);

    public abstract Resume doGet(String uuid);

    public abstract void doSave(Resume resume);

    public abstract void doUpdate(Resume resume);

    public abstract void doDelete(String uuid);


}
