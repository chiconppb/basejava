package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

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

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            return;
        }
        System.out.println("ERROR: " + resume.getUuid() + " отсутствует в массиве!");
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[count - 1];
            storage[count - 1] = null;
            count--;
            return;
        }
        System.out.println("ERROR: " + uuid + " отсутствует в массиве!");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
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
