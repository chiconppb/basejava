package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.*;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;


public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.doRequest("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            actionExecute(r, conn, "DELETE FROM contact WHERE resume_uuid=?");
            actionExecute(r, conn, "DELETE FROM section WHERE resume_uuid=?");
            insertContactToBase(r, conn);
            insertSectionToBase(r, conn);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContactToBase(r, conn);
            insertSectionToBase(r, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doRequest("" +
                "SELECT * FROM resume r " +
                "    LEFT JOIN contact c " +
                "           ON r.uuid = c.resume_uuid " +
                "    LEFT JOIN section s " +
                "           ON r.uuid=s.resume_uuid " +
                "        WHERE uuid = ?", (ps) -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                addContactToResume(r, rs);
                addSectionToResume(r, rs);
            } while (rs.next());
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid=?")) {
                ps.setString(1, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumes = new LinkedHashMap<>();
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT * FROM resume r " +
                    "      ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }
            return null;
        });
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("" +
                    " SELECT * FROM contact c " +
                    "      ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addContactToResume(resume, rs);
                }
            }
            return null;
        });
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT * FROM section s " +
                    "     ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addSectionToResume(resume, rs);
                }
            }
            return null;
        });
        return new ArrayList<>(resumes.values());
    }

    @Override
    public int size() {
        return sqlHelper.doRequest("SELECT count(*) FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void actionExecute(Resume r, Connection connection, String sql) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void insertContactToBase(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContactToResume(Resume resume, ResultSet resultSet) throws SQLException {
        if (resultSet.getString("value") != null) {
            ContactType contactType = ContactType.valueOf(resultSet.getString("type"));
            String value = resultSet.getString("value");
            resume.addContact(contactType, value);
        }
    }

    private void insertSectionToBase(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO section (resume_uuid, section_type, text) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, section.getKey().name());
                switch (section.getKey()) {
                    case PERSONAL, OBJECTIVE -> {
                        TextSection s = (TextSection) section.getValue();
                        ps.setString(3, (s.getDescription()));
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection s = (ListSection) section.getValue();
                        List<String> strings = s.getStrings();
                        String result = String.join("\n", strings);
                        ps.setString(3, (result));
                    }
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addSectionToResume(Resume resume, ResultSet resultSet) throws SQLException {
        if (resultSet.getString("text") != null) {
            SectionType sectionType = SectionType.valueOf(resultSet.getString("section_type"));
            switch (sectionType) {
                case PERSONAL, OBJECTIVE -> {
                    TextSection section = new TextSection(resultSet.getString("text"));
                    resume.addSection(sectionType, section);
                }
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    ListSection section = new ListSection();
                    String string = resultSet.getString("text");
                    String[] array = string.split("\n");
                    for (String s : array) {
                        section.addString(s);
                    }
                    resume.addSection(sectionType, section);
                }
            }
        }
    }
}

