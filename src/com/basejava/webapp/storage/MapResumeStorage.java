//package com.basejava.webapp.storage;
//
//import com.basejava.webapp.model.Resume;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class MapResumeStorage extends AbstractStorage{
//    private final Map<Resume> storage = new HashMap();
//
//    @Override
//    protected Object getSearchKey(String uuid) {
//        return null;
//    }
//
//    @Override
//    protected boolean isExist(Object o) {
//        return false;
//    }
//
//    @Override
//    public Resume doGet(Object searchKey) {
//        return null;
//    }
//
//    @Override
//    public void doSave(Object searchKey, Resume resume) {
//
//    }
//
//    @Override
//    public void doUpdate(Object searchKey, Resume resume) {
//
//    }
//
//    @Override
//    public void doDelete(Object searchKey) {
//
//    }
//
//    @Override
//    public void clear() {
//
//    }
//
//    @Override
//    public List<Resume> getAllSorted() {
//        return null;
//    }
//
//    @Override
//    public int size() {
//        return 0;
//    }
//}
