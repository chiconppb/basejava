package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.serialization.ObjectStreamSerializer;
import com.basejava.webapp.storage.serialization.Serializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;

    private final Serializer objectStreamSerializer;

    protected FileStorage(File directory, ObjectStreamSerializer objectStreamSerializer) {
        Objects.requireNonNull(directory, "directory must not be null");
        this.objectStreamSerializer = objectStreamSerializer;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return objectStreamSerializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doSave(File file, Resume r) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file", file.getName(), e);
        }
        doUpdate(file, r);
    }

    @Override
    protected void doUpdate(File file, Resume r) {
        try {
            objectStreamSerializer.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File wasn't deleted", file.getName());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> allResumes = new ArrayList<>();
        File[] list = getFilesList();
        for (File f : list) {
            allResumes.add(doGet(f));
        }
        return allResumes;
    }

    @Override
    public void clear() {
        File[] list = getFilesList();
        for (File file : list) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        File[] list = getFilesList();
        return list.length;
    }

    private File[] getFilesList() {
        if (directory.listFiles() == null) {
            throw new StorageException("Directory read error");
        } else {
            return directory.listFiles();
        }
    }
}
