<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <h2>${resume.fullName}</h2>
        <p>
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
            </c:forEach>
        </p>
        <c:forEach var="section" items="<%=resume.getSections()%>">
            <h4>${section.key}</h4>
            <text>${section.value}</text>
            <br>
        </c:forEach>
        <hr>
        <button onclick="window.history.back()">Back</button>
    </section>
    <jsp:include page="fragments/footer.jsp"/>
</div>
</body>
</html>
