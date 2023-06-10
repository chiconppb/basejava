package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageOverflowException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_EXIST = "dummy";

    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);
    private static final Resume NOT_EXIST_UUID_RESUME = new Resume(UUID_NOT_EXIST);

    public AbstractArrayStorageTest(Storage s) {
        storage = s;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);

        Resume[] allResumes = storage.getAll();
        Assert.assertArrayEquals(new Resume[0], allResumes);
    }

    @Test(expected = NotExistStorageException.class)
    public void update() {
        Resume srcResume = storage.get(UUID_1);
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        Assert.assertEquals(srcResume, newResume);
        Assert.assertNotSame(srcResume, newResume);
        updateNotExist(NOT_EXIST_UUID_RESUME);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        deleteNotExist(UUID_2);
    }

    @Test
    public void getAll() {
        Resume[] expected = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Resume[] actual = storage.getAll();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIST);
    }

    @Test(expected = StorageOverflowException.class)
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 1; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageOverflowException e) {
            Assert.fail("Array is overflowed before it's filled up");
        }
        storage.save(new Resume("uuid" + (AbstractArrayStorage.STORAGE_LIMIT + 1)));
    }


    public void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    public void assertGet(Resume resume) {
        Assert.assertNotNull(storage.get(resume.getUuid()));
    }

    public void updateNotExist(Resume resume) {
        storage.update(resume);
    }

    public void deleteNotExist(String uuid) {
        storage.delete(UUID_NOT_EXIST);
    }
}