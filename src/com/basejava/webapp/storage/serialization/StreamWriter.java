package com.basejava.webapp.storage.serialization;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

@FunctionalInterface
public interface StreamWriter<K,V> {

    void writeStream(Map.Entry<K, V> contact, DataOutputStream dataOutputStream) throws IOException;
}
