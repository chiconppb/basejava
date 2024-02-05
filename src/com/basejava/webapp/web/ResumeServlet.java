package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Storage storage = Config.getInstance().getStorage();
        List<Resume> resumes = storage.getAllSorted();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + '!');
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<style>");
        out.println("""
                table {
                  font-family: arial, sans-serif;
                  border-collapse: collapse;
                  width: 50%;
                }""");
        out.println("""
                td, th {
                  border: 1px solid #dddddd;
                  text-align: left;
                  padding: 8px;
                }""");
        out.println("""
                tr:nth-child(even) {
                  background-color: #dddddd;
                }""");
        out.println("</style>");
        out.println("<body>");
        out.println("<table>");
        printRow(out, "uuid", "fullname");
        for (Resume r : resumes) {
            printRow(out, r.getUuid(), r.getFullName());
        }
        out.println("</table>");
        out.println("</body>");
        out.println("/<html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    private void printRow(PrintWriter out, String... strings) {
        out.println("<tr>");
        for (String s : strings) {
            out.println("<td>");
            out.println(s);
            out.println("</td>");
        }
        out.println("</tr>");
    }
}
