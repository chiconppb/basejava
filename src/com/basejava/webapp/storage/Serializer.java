package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer {
    Resume doRead(InputStream is) throws IOException;

    void doWrite(Resume r, OutputStream os) throws IOException;
}
