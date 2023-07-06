package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageOverflowException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage s) {
        super(s);
    }

    @Test(expected = StorageOverflowException.class)
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 1; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("Name" + i));
            }
        } catch (StorageOverflowException e) {
            Assert.fail("Array is overflowed before it's filled up");
        }
        storage.save(new Resume("Overflow"));
    }

}