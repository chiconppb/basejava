package com.basejava.webapp.storage.serialization;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements Serializer {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        Objects.requireNonNull(r);
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Set<Map.Entry<ContactType, String>> contacts = r.getContacts().entrySet();
            Set<Map.Entry<SectionType, AbstractSection>> sections = r.getSections().entrySet();
            writeWithException(contacts, dos, (contact) -> {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            });
            writeWithException(sections, dos, (section) -> {
                dos.writeUTF(section.getKey().name());
                switch (section.getKey()) {
                    case PERSONAL, OBJECTIVE -> writeTextSection(section.getValue(), dos);
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection listSection = (ListSection) section.getValue();
                        List<String> strings = listSection.getStrings();
                        writeWithException(strings, dos, dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        CompanySection companySection = (CompanySection) section.getValue();
                        List<Company> companies = companySection.getCompanies();
                        writeWithException(companies, dos, (company) -> {
                            dos.writeUTF(company.getName());
                            dos.writeUTF(company.getWebsite());
                            List<Company.Period> periods = company.getPeriods();
                            writeWithException(periods, dos, (period) -> {
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF(period.getDescription());
                                dos.writeUTF(period.getBeginDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                            });
                        });
                    }
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(type, readTextSection(dis));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection listSection = new ListSection();
                        readWithException(dis, () -> listSection.addString(dis.readUTF()));
                        resume.addSection(type, listSection);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        CompanySection companySection = new CompanySection();
                        readWithException(dis, () -> {
                            String name = dis.readUTF();
                            String website = dis.readUTF();
                            Link link = new Link(name, website);
                            List<Company.Period> periods = new ArrayList<>();
                            readWithException(dis, () -> {
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                LocalDate beginDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                Company.Period period = new Company.Period(beginDate, endDate, title, description);
                                periods.add(period);
                            });
                            Company company = new Company(link, periods);
                            companySection.addCompany(company);
                        });
                        resume.addSection(type, companySection);
                    }
                }
            });
            return resume;
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dataOutputStream, StreamWriter<T> writer) throws IOException {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(dataOutputStream);
        dataOutputStream.writeInt(collection.size());
        for (T elem : collection) {
            writer.writeStream(elem);
        }
    }

    private void readWithException(DataInputStream dataInputStream, StreamReader reader) throws IOException {
        Objects.requireNonNull(dataInputStream);
        int counter = dataInputStream.readInt();
        for (int i = 0; i < counter; i++) {
            reader.readStream();
        }
    }

    private TextSection readTextSection(DataInputStream dataInputStream) throws IOException {
        Objects.requireNonNull(dataInputStream);
        return new TextSection(dataInputStream.readUTF());
    }

    private void writeTextSection(AbstractSection section, DataOutputStream dataOutputStream) throws IOException {
        Objects.requireNonNull(dataOutputStream);
        TextSection textSection = (TextSection) section;
        dataOutputStream.writeUTF(textSection.toString());
    }
}