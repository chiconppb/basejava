package com.basejava.webapp.storage;

import com.basejava.webapp.ResumeTestData;
import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = new File("C:\\Users\\CHICO\\Desktop\\lessons\\basejava\\storage");
    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";
    private static final String UUID_NOT_EXIST = "dummy";

    private static final String NAME_1 = "Name1";
    private static final String NAME_2 = "Name2";
    private static final String NAME_3 = "Name3";
    private static final String NAME_4 = "Name4";

    private static final Resume RESUME_1 = new ResumeTestData().getFilledResume(UUID_1, NAME_1);
    private static final Resume RESUME_2 = new ResumeTestData().getFilledResume(UUID_2, NAME_2);
    private static final Resume RESUME_3 = new ResumeTestData().getFilledResume(UUID_3, NAME_3);
    private static final Resume RESUME_4 = new ResumeTestData().getFilledResume(UUID_4, NAME_4);
    private static final Resume NOT_EXIST_UUID_RESUME = new ResumeTestData().getFilledResume(UUID_NOT_EXIST, UUID_NOT_EXIST);
    private static final Resume SAME_NAME_RESUME = new ResumeTestData().getFilledResume(UUID_5, NAME_1);

    protected AbstractStorageTest(Storage s) {
        storage = s;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        storage.save(SAME_NAME_RESUME);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        List<Resume> allResumes = storage.getAllSorted();
        Resume[] actualResumes = allResumes.toArray(new Resume[storage.size()]);
        Assert.assertArrayEquals(new Resume[0], actualResumes);
    }

    @Test
    public void update() {
        Resume srcResume = storage.get(UUID_1);
        Resume newResume = new Resume(UUID_1, "New_Name");
        storage.update(newResume);
        Assert.assertNotSame(srcResume, newResume);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(5);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
        assertGet(SAME_NAME_RESUME);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertSize(3);
        storage.get(UUID_2);
    }

    @Test
    public void getAll() {
        Resume[] expected = new Resume[]{RESUME_1, SAME_NAME_RESUME, RESUME_2, RESUME_3};
        List<Resume> resumes = storage.getAllSorted();
        Resume[] actualResumes = resumes.toArray(new Resume[0]);
        Assert.assertEquals(4, storage.size());
        Assert.assertArrayEquals(expected, actualResumes);
    }

    @Test
    public void size() {
        assertSize(4);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIST);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(NOT_EXIST_UUID_RESUME);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_NOT_EXIST);
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

}
