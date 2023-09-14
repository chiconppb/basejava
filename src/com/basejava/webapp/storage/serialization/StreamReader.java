package com.basejava.webapp.storage.serialization;

import java.io.IOException;

@FunctionalInterface
public interface StreamReader {
    void readStream() throws IOException;
}
