package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = (Comparator.comparing(Resume::getUuid));
    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid,"dummy");
        return Arrays.binarySearch(storage, 0, count, searchKey, RESUME_COMPARATOR);
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
