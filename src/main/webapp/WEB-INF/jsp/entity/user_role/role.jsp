<%-- 
    Document   : role
    Created on : 19.8.2014, 22:44:52
    Author     : Stenlik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>USER_ROLE!</h1>
        <table>
            <tr><th>ID</th><th>NAME</th></tr>
            <c:forEach items="${roles}" var="r">
                <tr>
                    <td>${r.id}</td>
                    <td>${r.name}</td>
                </tr>
            </c:forEach>
        </table>
        
        <f:form action="role.htm" commandName="formModel" method="POST">
            <f:label path="name">Name:
                <f:input path="name" />
            </f:label>
            <input type="submit" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </f:form>
        
    </body>
</html>
