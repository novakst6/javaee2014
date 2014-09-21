<%-- 
    Document   : index
    Created on : 31.8.2014, 19:22:59
    Author     : Stenlik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../../../jspf/header.jspf" %>
<style>
    .title-caption {
        background-image: linear-gradient(to bottom,#563d7c 0,#6f5499 100%);
        background-repeat: repeat-x;
        padding: 20px;
        color: #fff;
    }
    .title-main {
        background-image: linear-gradient(to bottom,#563d7c 0,#6f5499 100%);
        background-repeat: repeat-x;
        padding-left: 5px;
        padding-right: 5px;
        color: #fff;
    }
</style>   

<script>
    $(document).ready(function(){
        var roleId = $("#roles").find("tr").eq(1).find("td").eq(0).html();
        getRoleDetail(roleId);
    });
    
    function getRoleDetail(id){
        $.get( "detail.htm?id="+id, function( data ) {
        $("#info").html(data);
        });
    }
</script> 
<div class="title-caption">
    <h1>Administration - ROLE</h1>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <div class="title-main">
                <h3>LIST OF ROLES</h3>    
            </div>
            <table class="table table-hover" id="roles">
                <tr><th>ID</th><th>NAME</th><th>ACTIONS</th></tr>  
                <c:forEach items="${roles}" var="r">
                    <tr onclick="getRoleDetail(${r.id})">
                        <td>${r.id}</td>
                        <td>${r.name}</td>
                        <td>EDIT | DEL</td>
                    </tr>
                </c:forEach>
        </table>
        </div>
        <div class="col-md-6">
            <div class="title-main">
            <h3>INFO</h3>
            </div>
            <p id="info"></p>
        </div>
    </div>
    
</div>

<%@include file="../../../jspf/footer.jspf" %>