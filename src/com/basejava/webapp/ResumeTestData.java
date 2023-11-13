package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.time.LocalDate;

public class ResumeTestData {
    Resume resume;

    //////////////////////// Initialization of content ////////////////////////
    String personalString = "Leading internships and corporate training in Java Web and Enterprise technologies";

    String qualificationsString_1 = "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy";
    String qualificationsString_2 = "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts";

    String companyName_1 = "Siemens AG";
    String companyWebsite_1 = "https://www.siemens.com/";
    String companyPost_1 = "Software Developer";
    String companyActivity_1 = "Information model development, interface design, software implementation and debugging on the Siemens @vantage mobile IN platform (Java, Unix).";
    LocalDate begin_1 = LocalDate.of(2005, 1, 1);
    LocalDate end_1 = LocalDate.of(2007, 2, 1);
    Company.Period companyPeriod_1 = new Company.Period(begin_1, end_1, companyPost_1, companyActivity_1);

    String companyName_2 = "Alcatel";
    String companyWebsite_2 = "https://www.alcatel.ru/";
    String companyPost_2 = "Hardware and Software Testing Engineer";
    String companyActivity_2 = "Testing, debugging, implementation of the Alcatel 1000 S12 digital telephone exchange (CHILL, ASM).";
    LocalDate begin_2 = LocalDate.of(1997, 9, 1);
    LocalDate end_2 = LocalDate.of(2005, 1, 1);
    Company.Period companyPeriod_2 = new Company.Period(begin_2, end_2, companyPost_2, companyActivity_2);

    Company company_1 = new Company(companyName_1, companyWebsite_1, companyPeriod_1);
    Company company_2 = new Company(companyName_2, companyWebsite_2, companyPeriod_2);

    public Resume getFilledResume(String uuid, String fullName) {
        resume = new Resume(uuid, fullName);

        //////////////////////// Add contacts to resume ////////////////////////
//        resume.addContact(ContactType.PHONE_NUMBER, "911");
//        resume.addContact(ContactType.HOMEPAGE, "https://www.urise.com");

        //////////////////////// Creating sections ////////////////////////
        TextSection personal = new TextSection(personalString);

        ListSection qualifications = new ListSection(qualificationsString_1);
        qualifications.addString(qualificationsString_2);

        CompanySection experience = new CompanySection(company_1);
        experience.addCompany(company_2);

        //////////////////////// Add sections to resume ////////////////////////
//        resume.addSection(SectionType.PERSONAL, personal);
//        resume.addSection(SectionType.QUALIFICATIONS, qualifications);
//        resume.addSection(SectionType.EXPERIENCE, experience);

        return resume;
    }

}
