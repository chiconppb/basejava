<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<div style="text-align: center">
    <jsp:include page="fragments/header.jsp"/>
    <section>
        <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="uuid" value="${resume.uuid}">
            <dl>
                <dt>Name:</dt>
                <dd><input type="text" name="fullName" required size="50"
                           value="${resume.fullName.trim()}" pattern="^\S+ \S+$"
                           title="Name pattern: Mike Wazowski"></dd>
            </dl>
            <h3>Contacts:</h3>
            <p>
                <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><label>
                    <input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}">
                </label></dd>
            </dl>
            </c:forEach>
            <c:forEach var="section" items="<%=resume.getSections()%>">
                <h3>${section.key.title}</h3>
                <label>
                    <textarea rows="10" cols="80">${section.value}</textarea>
                </label>
            </c:forEach>
            <hr>
            <button type="submit">Сохранить</button>
            <button onclick="window.history.back()" type="reset">Отменить</button>
        </form>
    </section>
    <jsp:include page="fragments/footer.jsp"/>
</div>
</body>
</html>