package com.basejava.webapp.storage.serialization;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements Serializer {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Set<Map.Entry<ContactType, String>> contacts = r.getContacts().entrySet();
            Set<Map.Entry<SectionType, AbstractSection>> sections = r.getSections().entrySet();
            dos.writeInt(contacts.size());
            writeContactsWithException(contacts, dos, (contact, dataOutputStream) -> {
                dataOutputStream.writeUTF(contact.getKey().name());
                dataOutputStream.writeUTF(contact.getValue());
            });
            dos.writeInt(sections.size());
            writeSectionsWithException(sections, dos, (section, dataOutputStream) -> {
                dataOutputStream.writeUTF(section.getKey().name());
                switch (section.getKey()) {
                    case PERSONAL, OBJECTIVE -> writeTextSection(section.getValue(), dataOutputStream);
                    case ACHIEVEMENT, QUALIFICATIONS -> writeListSection(section.getValue(), dataOutputStream);
                    case EXPERIENCE, EDUCATION -> writeCompanySection(section.getValue(), dataOutputStream);
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
            int contactsCount = dis.readInt();
            for (int i = 0; i < contactsCount; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sectionsCount = dis.readInt();
            for (int i = 0; i < sectionsCount; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> {
                        TextSection textSection = (TextSection) readTextSection(dis);
                        resume.addSection(type, textSection);
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection listSection = (ListSection) readListSection(dis);
                        resume.addSection(type, listSection);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        CompanySection companySection = (CompanySection) readCompanySection(dis);
                        resume.addSection(type, companySection);
                    }
                }
            }
            return resume;
        }
    }

    private void writeContactsWithException(Set<Map.Entry<ContactType, String>> contacts, DataOutputStream dataOutputStream, StreamWriter<ContactType, String> writer) throws IOException {
        for (Map.Entry<ContactType, String> contact : contacts) {
            writer.writeStream(contact, dataOutputStream);
        }
    }

    private void writeSectionsWithException(Set<Map.Entry<SectionType, AbstractSection>> sections, DataOutputStream dataOutputStream, StreamWriter<SectionType, AbstractSection> writer) throws IOException {
        for (Map.Entry<SectionType, AbstractSection> section : sections) {
            writer.writeStream(section, dataOutputStream);
        }
    }

    private AbstractSection readTextSection(DataInputStream dataInputStream) throws IOException {
        return new TextSection(dataInputStream.readUTF());
    }

    private AbstractSection readListSection(DataInputStream dataInputStream) throws IOException {
        ListSection section = new ListSection();
        int stringsCount = dataInputStream.readInt();
        for (int i = 0; i < stringsCount; i++) {
            section.addString(dataInputStream.readUTF());
        }
        return section;
    }

    private AbstractSection readCompanySection(DataInputStream dataInputStream) throws IOException {
        CompanySection companySection = new CompanySection();
        int companiesCount = dataInputStream.readInt();
        for (int i = 0; i < companiesCount; i++) {
            String name = dataInputStream.readUTF();
            String website = dataInputStream.readUTF();
            Link link = new Link(name, website);
            List<Company.Period> periods = new ArrayList<>();
            int periodsCount = dataInputStream.readInt();
            for (int j = 0; j < periodsCount; j++) {
                String title = dataInputStream.readUTF();
                String description = dataInputStream.readUTF();
                LocalDate beginDate = LocalDate.parse(dataInputStream.readUTF());
                LocalDate endDate = LocalDate.parse(dataInputStream.readUTF());
                Company.Period period = new Company.Period(beginDate, endDate, title, description);
                periods.add(period);
            }
            Company company = new Company(link, periods);
            companySection.addCompany(company);
        }
        return companySection;
    }

    private void writeTextSection(AbstractSection section, DataOutputStream dataOutputStream) throws IOException {
        TextSection textSection = (TextSection) section;
        dataOutputStream.writeUTF(textSection.getDescription());
    }

    private void writeListSection(AbstractSection section, DataOutputStream dataOutputStream) throws IOException {
        ListSection listSection = (ListSection) section;
        List<String> strings = listSection.getStrings();
        dataOutputStream.writeInt(strings.size());
        for (String s : strings) {
            dataOutputStream.writeUTF(s);
        }
    }

    private void writeCompanySection(AbstractSection section, DataOutputStream dataOutputStream) throws IOException {
        CompanySection companySection = (CompanySection) section;
        List<Company> companies = companySection.getCompanies();
        dataOutputStream.writeInt(companies.size());
        for (Company c : companies) {
            dataOutputStream.writeUTF(c.getName());
            dataOutputStream.writeUTF(c.getWebsite());
            List<Company.Period> periods = c.getPeriods();
            dataOutputStream.writeInt(periods.size());
            for (Company.Period p : periods) {
                dataOutputStream.writeUTF(p.getTitle());
                dataOutputStream.writeUTF(p.getDescription());
                dataOutputStream.writeUTF(p.getBeginDate().toString());
                dataOutputStream.writeUTF(p.getEndDate().toString());
            }
        }
    }
}
