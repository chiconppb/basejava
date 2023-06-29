package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageOverflowException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count;

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    public int size() {
        return count;
    }

    protected boolean isExist(Object o) {
        return (Integer) o >= 0;
    }

    public Resume doGet(Object searchKey) {
        return storage[(Integer)searchKey];
    }

    public void doSave(Object searchKey, Resume resume) {
        if (count >= STORAGE_LIMIT) {
            throw new StorageOverflowException(resume.getUuid());
        } else {
            int index = Math.abs(((Integer)searchKey) + 1);
            insertResume(resume, index);
            count++;
        }
    }

    public void doUpdate(Object searchKey, Resume resume) {
        storage[(Integer)searchKey] = resume;
    }

    public void doDelete(Object searchKey) {
        deleteResume((Integer)searchKey);
        count--;
    }

    protected abstract void insertResume(Resume r, Integer index);

    protected abstract void deleteResume(Integer index);

    protected abstract Object getSearchKey(String uuid);

}
