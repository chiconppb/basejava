package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageOverflowException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest() {
        if (this.getClass().getName().equals("ArrayStorageTest")) {
            storage = new ArrayStorage();
        } else storage = new SortedArrayStorage();
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume srcResume = storage.get(UUID_1);
        Resume newResume = new Resume("uuid1");
        storage.update(newResume);
        Assert.assertEquals(srcResume, newResume);
        Assert.assertNotSame(srcResume, newResume);
    }

    @Test
    public void save() {
        Resume newResume = new Resume("uuid4");
        storage.save(newResume);
        Resume actualResume = storage.get("uuid4");
        Assert.assertEquals(newResume, actualResume);
    }

    @Test
    public void get() {
        Assert.assertNotNull(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid2");
        storage.get("uuid2");
    }

    @Test
    public void getAll() {
        for (int i = 1; i <= storage.size(); i++) {
            Assert.assertNotNull(storage.get("uuid" + i));
        }
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = StorageOverflowException.class)
    public void storageOverflow() throws NoSuchFieldException, IllegalAccessException {
        Field f = storage.getClass().getSuperclass().getDeclaredField("STORAGE_LIMIT");
        int STORAGE_LIMIT = (int) f.get(null);

        //storage.save(new Resume("uuidA"));
        try {
            for (int i = 4; i <= STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageOverflowException e) {
            Assert.fail("Array is overflowed");
        }
        storage.save(new Resume("uuid" + (STORAGE_LIMIT + 1)));
    }
}