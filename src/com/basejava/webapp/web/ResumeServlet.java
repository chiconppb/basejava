package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    public void init(ServletConfig config) {
        storage = Config.getInstance().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
        }
        Resume r;
        switch (Objects.requireNonNull(action)) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "new":
                r = Resume.getEmptyResume();
                break;
            case "view":
            case "edit":
                r = storage.get(uuid);
                for (SectionType sectionType : SectionType.values()) {
                    AbstractSection section = r.getSection(sectionType);
                    switch (sectionType) {
                        case PERSONAL, OBJECTIVE:
                            if (section == null) {
                                section = TextSection.getEmptySection();
                            }
                            break;
                        case QUALIFICATIONS, ACHIEVEMENT:
                            if (section == null) {
                                section = ListSection.getEmptySection();
                            }
                            break;
                        case EDUCATION, EXPERIENCE:
                            if (section == null) {
                                section = CompanySection.getEmptySection();
                            }
                            break;
                    }
                    r.addSection(sectionType, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal.");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        if (uuid == null || uuid.isEmpty()) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType contactType : ContactType.values()) {
            String value = request.getParameter(contactType.name());
            if (value != null && !value.trim().isEmpty()) {
                r.addContact(contactType, value);
            } else {
                r.getContacts().remove(contactType);
            }
        }
        for (SectionType sectionType : SectionType.values()) {
            String value = request.getParameter(sectionType.name());
            if (value != null && !value.trim().isEmpty()) {
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE:
                        r.addSection(sectionType, new TextSection(value));
                        break;
                    case ACHIEVEMENT, QUALIFICATIONS:
                        r.addSection(sectionType, new ListSection(value));
                        break;
                    case EDUCATION, EXPERIENCE:
                        new CompanySection();
                        break;
                }
            } else {
                r.getSections().remove(sectionType);
            }
        }
        if (uuid == null || uuid.isEmpty()) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }
}
