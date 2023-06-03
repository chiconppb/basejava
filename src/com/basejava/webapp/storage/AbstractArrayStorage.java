package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count;

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            return;
        }
        System.out.println("ERROR: " + resume.getUuid() + " отсутствует в массиве!");
    }

    public void save(Resume r) {
        if (count >= STORAGE_LIMIT) {
            System.out.println("ERROR: Массив полон!");
        } else if (getIndex(r.getUuid()) >= 0) {
            System.out.println("ERROR: " + r.getUuid() + " уже существует!");
        } else {
            int index = Math.abs(getIndex(r.getUuid()) + 1);
            insertResume(r, index);
            count++;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("ERROR: " + uuid + " отсутствует в массиве!");
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR: " + uuid + " отсутствует в массиве!");
        } else {
            deleteResume(index);
            count--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    public int size() {
        return count;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertResume(Resume r, int index);

    protected abstract void deleteResume(int index);
}
