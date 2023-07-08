package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<T> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return doGet(getExistingSearchKey(uuid));
    }

    public List<Resume> getAllSorted() {
        LOG.info("Get all sorted");
        List<Resume> allResumes = doCopyAll();
        Collections.sort(allResumes);
        return allResumes;
    }

    public void save(Resume resume) {
        LOG.info("Save " + resume);
        doSave(getNotExistingSearchKey(resume.getUuid()), resume);
    }

    public void update(Resume resume) {
        LOG.info("Update " + resume);
        doUpdate(getExistingSearchKey(resume.getUuid()), resume);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        doDelete(getExistingSearchKey(uuid));
    }

    protected T getExistingSearchKey(String uuid) {
        if (!isExist(getSearchKey(uuid))) {
            LOG.warning("Resume " + uuid + " is not exist!");
            throw new NotExistStorageException(uuid);
        }
        return getSearchKey(uuid);
    }

    protected T getNotExistingSearchKey(String uuid) {
        if (isExist(getSearchKey(uuid))) {
            LOG.warning("Resume " + uuid + " is already exist!");
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
