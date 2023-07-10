package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.time.LocalDate;

public class ResumeTestData {
    private static final Resume resume = new Resume("Ivan Ivanov");

    public static void main(String[] args) {

        final Contact contact1 = new Contact("Phone number", "+79218550482");
        final Contact contact2 = new Contact("Skype", "grigory.kislin");
        final Contact contact3 = new Contact("Email", "gkislin@yandex.ru");

        final AbstractSection<String> section1 = new TextSection(SectionType.OBJECTIVE);
        final AbstractSection<String> section2 = new TextSection(SectionType.PERSONAL);
        final AbstractSection<String> section3 = new ListSection(SectionType.ACHIEVEMENT);
        final AbstractSection<String> section4 = new ListSection(SectionType.QUALIFICATIONS);
        final AbstractSection<Company> section5 = new HireSection(SectionType.EXPERIENCE);
        final AbstractSection<Company> section6 = new HireSection(SectionType.EDUCATION);

        final ActivePeriod period1 = new ActivePeriod("Project author", LocalDate.of(2013, 10, 1), LocalDate.now(), "Creating, organizing and conducting Java online projects and internships.");
        final ActivePeriod period2 = new ActivePeriod("Lead programmer", LocalDate.of(2007, 3, 1), LocalDate.of(2008, 6, 1), "Implementation of client (Eclipse RCP) and server (JBoss 4.2, Hibernate 3.0, Tomcat, JSP) parts of a cluster J2EE application (OLAP, Data mining).");
        final ActivePeriod period3 = new ActivePeriod("Programmer C, C++", LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1), "Postgraduate study");

        final Company company1 = new Company("Java Online Projects", "https://javaops.ru", period1);
        final Company company2 = new Company("Enkata", "https://enkata.com", period2);
        final Company company3 = new Company("ITMO University", "https://itmo.ru", period3);


        final String textObjective = "Leading internships and corporate training in Java Web and Enterprise technologies.";
        final String textPersonal = "Analytical mindset, strong logic, creativity, initiative. A purist of code and architecture.";
        final String textAchievement = "Implementation of two-factor authentication for the online project management platform Wrike.";
        final String textQualifications1 = "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy";
        final String textQualifications2 = "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts";

        resume.addContact(contact1);
        resume.addContact(contact2);
        resume.addContact(contact3);

        resume.addSection(section1);
        resume.addSection(section2);
        resume.addSection(section3);
        resume.addSection(section4);
        resume.addSection(section5);
        resume.addSection(section6);

        section1.add(textObjective);
        section2.add(textPersonal);
        section3.add(textAchievement);
        section4.add(textQualifications1);
        section4.add(textQualifications2);
        section5.add(company1);
        section5.add(company2);
        section6.add(company3);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        resume.getContact("Phone number");

        resume.getSection("Personal").toString();
        resume.getSection("Qualifications").toString();
        resume.getSection("Experience").toString();
    }
}
