package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {


    protected abstract int getIndex(String uuid);

    protected abstract void insertResume(Resume r, int index);

    protected abstract void deleteResume(int index);
}
