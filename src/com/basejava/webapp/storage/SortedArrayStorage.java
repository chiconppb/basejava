package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, count, searchKey);
    }

    @Override
    protected void insertResume(Resume r) {
        int index = Math.abs(getIndex(r.getUuid()) + 1);
        for (int i = count; i > index; i--)
            storage[i] = storage[i - 1];
        storage[index] = r;
        count++;
    }

    @Override
    protected void eraseResume(int index) {
        while (storage[index + 1] != null) {
            storage[index] = storage[index + 1];
            index++;
        }
        storage[index] = null;
        count--;
    }
}
