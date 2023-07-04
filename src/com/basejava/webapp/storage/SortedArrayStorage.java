package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid,null);
        return Arrays.binarySearch(storage, 0, count, searchKey);
    }

    @Override
    protected void insertResume(Resume r, Integer index) {
        System.arraycopy(storage, index, storage, index + 1, count - index);
        storage[index] = r;
    }

    @Override
    protected void deleteResume(Integer index) {
        System.arraycopy(storage, index + 1, storage, index, count - index);
    }
}
