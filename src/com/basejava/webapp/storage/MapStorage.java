package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public class MapStorage extends AbstractStorage {

    @Override
    public void clear() {
    }

    @Override
    public void update(Resume r) {
    }

    @Override
    public void save(Resume r) {
    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    protected int getIndex(String uuid) {
        return 0;
    }

    @Override
    protected void insertResume(Resume r, int index) {
    }

    @Override
    protected void deleteResume(int index) {
    }
}
