package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collections;

public class ListStorage extends AbstractStorage {
    protected ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        if(getIndex(r.getUuid())<0){
            throw new NotExistStorageException(r.getUuid());
        }
        storage.add(getIndex(r.getUuid()), r);
    }

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        }
        storage.add(r);
    }

    @Override
    public Resume get(String uuid) {
        if(getIndex(uuid)<0){
            throw new NotExistStorageException(uuid);
        }
        return storage.get(getIndex(uuid));
    }

    @Override
    public void delete(String uuid) {
        if(getIndex(uuid)<0){
            throw new NotExistStorageException(uuid);
        }
        deleteResume(getIndex(uuid));
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected int getIndex(String uuid) {
        return Collections.binarySearch(storage, new Resume(uuid));
    }

    protected void insertResume(Resume r, int index) {
        storage.add(index, r);
    }

    protected void deleteResume(int index) {
        storage.remove(index);
    }

}

