<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page import="com.basejava.webapp.model.ListSection" %>
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
            <hr>
            <h3>Contacts:</h3>
            <p>
                <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><label>
                    <input type="text" name="${type.name()}" size=30 value="${resume.getContact(type).trim()}">
                </label></dd>
            </dl>
            </c:forEach>
            <hr>
            <c:forEach var="sectionType" items="<%=SectionType.values()%>">
                <c:set var="section" value="${resume.getSection(sectionType)}"/>
                <jsp:useBean id="section" type="com.basejava.webapp.model.AbstractSection"/>
                <h3>${sectionType.title}</h3>
                <c:choose>
                    <c:when test="${sectionType=='PERSONAL'||sectionType=='OBJECTIVE'}">
                        <label>
                            <textarea name="${sectionType}" rows="10" cols="80"><%=section.toString()%></textarea>
                        </label>
                    </c:when>
                    <c:when test="${sectionType=='ACHIEVEMENT'||sectionType=='QUALIFICATIONS'}">
                        <label>
                            <textarea name="${sectionType}" rows="10"
                                      cols="80"><%=String.join("\n", ((ListSection) section).getStrings()).trim()%></textarea>
                        </label>
                    </c:when>
                    <%--                    <c:when test="${sectionType=='EDUCATION'||sectionType=='EXPERIENCE'}">--%>

                    <%--                    </c:when>--%>
                </c:choose>
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