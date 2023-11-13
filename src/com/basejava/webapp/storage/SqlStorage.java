package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.util.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class SqlStorage implements Storage {
    private final SqlHelper SQLHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        SQLHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        SQLHelper.doRequest("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        SQLHelper.doRequest("UPDATE resume SET full_name=? WHERE uuid=?", (ps) -> {
            get(r.getUuid());
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            ps.execute();
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        SQLHelper.doRequest("INSERT INTO resume (uuid, full_name) VALUES (?,?)", (ps) -> {
            try {
                get(r.getUuid());
            } catch (NotExistStorageException e) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
                return null;
            }
            throw new ExistStorageException("Resume " + r.getUuid() + " is already exists!");
        });
    }

    @Override
    public Resume get(String uuid) {
        return SQLHelper.doRequest("SELECT * FROM resume WHERE uuid=?", (ps) -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException("Resume " + uuid + " is not exist");
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        SQLHelper.doRequest("DELETE FROM resume WHERE uuid=?", (ps) -> {
            get(uuid);
            ps.setString(1, uuid);
            ps.execute();
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>();
        return SQLHelper.doRequest("SELECT * FROM resume ORDER BY full_name, uuid", (ps) -> {
            ResultSet rs = ps.executeQuery();
            for (int i = 0; i < size(); i++) {
                rs.next();
                list.add(i, new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return list;
        });
    }

    @Override
    public int size() {
        return SQLHelper.doRequest("SELECT count(*) FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }
}

