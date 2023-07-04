package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Comparator;

public abstract class AbstractStorage implements Storage {

    protected static final Comparator<Resume> RESUME_UUID_COMPARATOR = Comparator.comparing(Resume::getUuid);
    protected static final Comparator<Resume> RESUME_FULLNAME_COMPARATOR = Comparator.comparing(Resume::getFullName);

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

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doSave(Object searchKey, Resume resume);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract void doDelete(Object searchKey);
}
