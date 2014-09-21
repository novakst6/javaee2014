<%-- 
    Document   : login
    Created on : 23.8.2014, 23:16:27
    Author     : Stenlik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="../jspf/header.jspf" %>
<style>
    .form-signin {
        margin-top: 20px;
        margin-left: auto;
        margin-right: auto;
        max-width: 350px;
        padding-left: 20px;
        padding-right: 20px;
        padding-bottom: 20px;
        border-style: solid;
        border-width: 1px;
        border-color: #ddd;
        border-radius: 5px;
    }
</style>
<div class="container">
<form action="<c:url value="/login.htm"/>" method="post" role="form" class="form-signin">
    <h2 class="form-signin-heading">Login</h2>
    <c:if test="${not empty param.error}">
        <p class="alert alert-danger">Your login attempt was not successful.</p>
    </c:if>
        <input class="form-control" type='text' name='j_username' placeholder="Username" />
        <input class="form-control" type='password' name='j_password' placeholder="Password" />			
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
</form>
</div>

<%@include file="../jspf/footer.jspf" %>