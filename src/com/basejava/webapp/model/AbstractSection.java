package com.basejava.webapp.model;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.storage.AbstractStorage;

import java.util.logging.Logger;

public abstract class AbstractSection<T> {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    protected final String title;

    public AbstractSection(SectionType e) {
        title = e.getTitle();
    }

    public String getName() {
        return title;
    }

    protected T getExistElement(T e) {
        if (!isExist(e)) {
            LOG.warning("The element " + e + " is not exist!");
            throw new StorageException("The element is not exist", e.toString());
        }
        return e;
    }

    protected T getNotExistElement(T e) {
        if (isExist(e)) {
            LOG.warning("The element " + e + " is already exist!");
            throw new StorageException("The element is already exist", e.toString());
        }
        return e;
    }

    public void add(T e) {
        LOG.info("Add to <" + this.getName() + "> section.\n" + "[" + e + "]\n");
        doAdd(e);
    }

    public T get(T e) {
        LOG.info("Get " + e + " from <" + this.getName() + "> section." + "\n" + getInfo());
        return doGet(e);
    }

    protected abstract boolean isExist(T e);

    protected abstract void doAdd(T e);

    protected abstract T doGet(T e);

    protected abstract String getInfo();

    public String toString() {
        LOG.info(getInfo() + "\n");
        return getInfo();
    }
}
