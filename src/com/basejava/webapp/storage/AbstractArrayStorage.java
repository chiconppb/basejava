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
        return (int) o >= 0;
    }

    public Resume doGet(String uuid) {
        int index = (int) getSearchKey(uuid);
        return storage[index];
    }

    public void doSave(Resume resume) {
        if (count >= STORAGE_LIMIT) {
            throw new StorageOverflowException(resume.getUuid());
        } else {
            int index = Math.abs((int) getSearchKey(resume.getUuid()) + 1);
            insertResume(resume, index);
            count++;
        }
    }

    public void doUpdate(Resume resume) {
        int index = (int) getSearchKey(resume.getUuid());
        storage[index] = resume;
    }

    public void doDelete(String uuid) {
        int index = (int) getSearchKey(uuid);
        deleteResume(index);
        count--;
    }

    protected abstract void insertResume(Resume r, int index);

    protected abstract void deleteResume(int index);

    protected abstract Object getSearchKey(String uuid);

}
