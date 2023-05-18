package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int count;

    void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    void save(Resume r) {
        if (count < storage.length) {
            if (this.search(r.getUuid()) < 0) {
                storage[count] = r;
                count++;
                return;
            }
            System.out.println("ERROR: " + r.getUuid() + " уже существует!");
            return;
        }
        System.out.println("ERROR: Массив полон!");
    }

    Resume get(String uuid) {
        if (this.search(uuid) >= 0) {
            return storage[this.search(uuid)];
        }
        System.out.println("ERROR: " + uuid + " отсутствует в массиве!");
        return null;
    }

    public void update(Resume resume) {
        if (this.search(resume.getUuid()) >= 0) {
            storage[this.search(resume.getUuid())] = resume;
            return;
        }
        System.out.println("ERROR: " + resume.getUuid() + " отсутствует в массиве!");
    }

    void delete(String uuid) {
        if (this.search(uuid) >= 0) {
            storage[this.search(uuid)] = storage[count - 1];
            storage[count - 1] = null;
            count--;
            return;
        }
        System.out.println("ERROR: " + uuid + " отсутствует в массиве!");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    int size() {
        return count;
    }

    private int search(String uuid) {
        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
