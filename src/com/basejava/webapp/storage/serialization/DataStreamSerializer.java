package com.basejava.webapp.storage.serialization;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                switch (entry.getKey().name()) {
                    case "PERSONAL", "OBJECTIVE" -> writeTextSection(entry.getValue(), dos);
                    case "ACHIEVEMENT", "QUALIFICATIONS" -> writeListSection(entry.getValue(), dos);
                    case "EXPERIENCE", "EDUCATION" -> writeCompanySection(entry.getValue(), dos);
                }
            }
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
                String type = dis.readUTF();
                switch (type) {
                    case "PERSONAL", "OBJECTIVE" -> {
                        TextSection textSection = (TextSection) readTextSection(type, dis);
                        resume.addSection(textSection.getSectionType(), textSection);
                    }
                    case "ACHIEVEMENT", "QUALIFICATIONS" -> {
                        ListSection listSection = (ListSection) readListSection(type, dis);
                        resume.addSection(listSection.getSectionType(), listSection);
                    }
                    case "EXPERIENCE", "EDUCATION" -> {
                        CompanySection companySection = (CompanySection) readCompanySection(type, dis);
                        resume.addSection(companySection.getSectionType(), companySection);
                    }
                }
            }
            return resume;
        }
    }

    private AbstractSection readTextSection(String type, DataInputStream dataInputStream) throws IOException {
        return new TextSection(SectionType.valueOf(type), dataInputStream.readUTF());
    }

    private AbstractSection readListSection(String type, DataInputStream dataInputStream) throws IOException {
        ListSection section = new ListSection(SectionType.valueOf(type));
        int stringsCount = dataInputStream.readInt();
        for (int i = 0; i < stringsCount; i++) {
            section.addString(dataInputStream.readUTF());
        }
        return section;
    }

    private AbstractSection readCompanySection(String type, DataInputStream dataInputStream) throws IOException {
        CompanySection companySection = new CompanySection(SectionType.valueOf(type));
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
        dataOutputStream.writeUTF(textSection.getSectionType().name());
        dataOutputStream.writeUTF(textSection.getDescription());
    }

    private void writeListSection(AbstractSection section, DataOutputStream dataOutputStream) throws IOException {
        ListSection listSection = (ListSection) section;
        List<String> strings = listSection.getStrings();
        dataOutputStream.writeUTF(listSection.getSectionType().name());
        dataOutputStream.writeInt(strings.size());
        for (String s : strings) {
            dataOutputStream.writeUTF(s);
        }
    }

    private void writeCompanySection(AbstractSection section, DataOutputStream dataOutputStream) throws IOException {
        CompanySection companySection = (CompanySection) section;
        List<Company> companies = companySection.getCompanies();
        dataOutputStream.writeUTF(companySection.getSectionType().name());
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
