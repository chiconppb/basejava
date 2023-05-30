package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        if (count >= STORAGE_LIMIT) {
            System.out.println("ERROR: Массив полон!");
        } else if (getIndex(r.getUuid()) < 0) {
            storage[count] = r;
            count++;
        } else {
            System.out.println("ERROR: " + r.getUuid() + " уже существует!");
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR: " + uuid + " отсутствует в массиве!");
        } else {
            storage[index] = storage[count - 1];
            storage[count - 1] = null;
            count--;
        }
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
