package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        if (count >= STORAGE_LIMIT) {
            System.out.println("ERROR: Массив полон!");
        } else if (getIndex(r.getUuid()) >= 0) {
            System.out.println("ERROR: " + r.getUuid() + " уже существует!");
        } else {
            int index = Math.abs(getIndex(r.getUuid()) + 1);
            for (int i = count; i > index; i--)
                storage[i] = storage[i - 1];
            storage[index] = r;
            count++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR: " + uuid + " отсутствует в массиве!");
        } else {
            while (storage[index + 1] != null) {
                storage[index] = storage[index + 1];
                index++;
            }
            storage[index] = null;
            count--;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, count, searchKey);
    }

}
