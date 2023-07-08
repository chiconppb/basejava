package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageOverflowException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count;

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    public int size() {
        return count;
    }

    protected boolean isExist(Integer o) {
        return o >= 0;
    }

    public Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    public void doSave(Integer searchKey, Resume resume) {
        if (count >= STORAGE_LIMIT) {
            throw new StorageOverflowException(resume.getUuid());
        } else {
            int index = Math.abs(searchKey + 1);
            insertResume(resume, index);
            count++;
        }
    }

    public void doUpdate(Integer searchKey, Resume resume) {
        storage[searchKey] = resume;
    }

    public void doDelete(Integer searchKey) {
        deleteResume(searchKey);
        storage[count - 1] = null;
        count--;
    }

    protected List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, count));
    }

    protected abstract void insertResume(Resume r, Integer index);

    protected abstract void deleteResume(Integer index);

    protected abstract Integer getSearchKey(String uuid);

}
