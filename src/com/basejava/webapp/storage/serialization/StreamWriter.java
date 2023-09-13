package com.basejava.webapp.storage.serialization;

import java.io.IOException;

@FunctionalInterface
public interface StreamWriter<T> {

    void writeStream(T elem) throws IOException;
}
