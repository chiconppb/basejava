<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<div align="center">
    <jsp:include page="fragments/header.jsp"/>
    <section>
        <table border="1" cellpadding="8" cellspacing="0">
            <a href="resume?action=new">
                <button style="background-color: darkseagreen; border-color: green">Add resume +</button>
            </a>
            <hr>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${resumes}" var="resume">
                <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume"/>
                <tr>
                    <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                    <td>${ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))}</td>
                    <td><a href="resume?uuid=${resume.uuid}&action=delete">
                        <button style="background-color:indianred; border-color: darkred">Delete</button>
                    </a></td>
                    <td><a href="resume?uuid=${resume.uuid}&action=edit">
                        <button style="background-color:cornflowerblue; border-color: darkblue">Edit</button>
                    </a></td>
                </tr>
            </c:forEach>
        </table>
    </section>
    <jsp:include page="fragments/footer.jsp"/>
</div>
</body>
</html>
