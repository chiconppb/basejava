package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(String.valueOf(directory), uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.toString(), e);
        }
    }

    @Override
    protected void doSave(Path path, Resume r) {
        try {
            Files.createFile(path);
            doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path", path.getFileName().toString(), e);
        }
        doUpdate(path, r);
    }

    @Override
    protected void doUpdate(Path path, Resume r) {
        try {
            doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.toString(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> allResumes = new ArrayList<>();
        List<Path> paths;
        try {
            paths = getPathsList().toList();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
        for (Path p : paths) {
            allResumes.add(doGet(p));
        }
        return allResumes;

    }

    @Override
    public void clear() {
        try {
            getPathsList().forEach(path -> doDelete(path));
        } catch (IOException e) {
            throw new StorageException("I/O error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) getPathsList().count();
        } catch (IOException e) {
            throw new StorageException("I/O error", null);
        }

    }

    private Stream<Path> getPathsList() throws IOException {
        return Files.list(directory);
    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

}