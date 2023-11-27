package com.basejava.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Executor<T> {
    T execute(PreparedStatement preparedStatement) throws SQLException;
}
