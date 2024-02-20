package com.basejava.webapp.model;

import com.basejava.webapp.storage.AbstractStorage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serial;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Initial resume class
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Resume DEFAULT = new Resume();

    static {
        DEFAULT.addSection(SectionType.PERSONAL, TextSection.getEmptySection());
        DEFAULT.addSection(SectionType.OBJECTIVE, TextSection.getEmptySection());
        DEFAULT.addSection(SectionType.ACHIEVEMENT, ListSection.getEmptySection());
        DEFAULT.addSection(SectionType.QUALIFICATIONS, ListSection.getEmptySection());
        DEFAULT.addSection(SectionType.EXPERIENCE, CompanySection.getEmptySection());
        DEFAULT.addSection(SectionType.EDUCATION, CompanySection.getEmptySection());
    }

    private String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "UUID must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume(String fullName) {
        uuid = UUID.randomUUID().toString();
        this.fullName = fullName;
    }

    public void addContact(ContactType contactType, String contact) {
        Objects.requireNonNull(contactType, "Contact must not be null!");
        LOG.info("Add contact:\n " + contactType + " = " + contact);
        contacts.put(contactType, contact);
    }

    public String getContact(ContactType contactType) {
        Objects.requireNonNull(contactType, "Contact type can't be null!");
        if (!contacts.containsKey(contactType)) {
            return "";
//            throw new NotExistContactException(contactType);
        }
        LOG.info("Get contact: \n " + contactType + " = " + contacts.get(contactType));
        return contacts.get(contactType);
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public void addSection(SectionType sectionType, AbstractSection section) {
        Objects.requireNonNull(section, "Section must not be null!");
        LOG.info("\n Add section\n" + section);
        sections.put(sectionType, section);
    }

    public AbstractSection getSection(SectionType sectionType) {
        Objects.requireNonNull(sectionType, "Section type can't be null!");
        return sections.get(sectionType);
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName) && Objects.equals(contacts, resume.contacts) && Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public int compareTo(Resume o) {
        int i = fullName.compareTo(o.fullName);
        return i != 0 ? i : uuid.compareTo(o.uuid);
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public static Resume getEmptyResume() {
        return DEFAULT;
    }
}
