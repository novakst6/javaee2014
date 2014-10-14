<%-- 
    Document   : index
    Created on : 12.10.2014, 23:28:30
    Author     : Stenlik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%@include file="../../../jspf/header.jspf" %>
<script src="<s:url value="/js/vendor/jquery.ui.widget.js"/>"></script>
<script src="<s:url value="/js/jquery.iframe-transport.js"/>"></script>
<script src="<s:url value="/js/jquery.fileupload.js"/>"></script>

<link href="<s:url value="/css/dropzone.css"/>" type="text/css" rel="stylesheet" />
<script src="<s:url value="/js/myuploadfunction.js"/>"></script>

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
    
    .upload-field {
        margin-top: 20px;
        margin-bottom: 20px;
    }
</style>   

<div class="title-caption">
    <h1>Administration - FILE</h1>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <div class="title-main">
                <h3>LIST OF FILES</h3>    
            </div>
            <table class="table table-hover" id="files">
                <tr><th>ID</th><th>NAME</th><th>ACTIONS</th></tr>  
                        <c:forEach items="${files}" var="f">
                    <tr>
                        <td>${f.id}</td>
                        <td>${f.name}</td>
                        <td><a href="download.htm?id=${f.id}">DOWNLOAD</a> | DEL</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-md-6">
                <div class="title-main">
                    <h3>UPLOAD NEW FILE</h3>    
                </div>
                <div class="upload-field">
                    <input id="fileupload" type="file" name="files[]" data-url="<s:url value="/entity/upload.htm"/>" multiple />
                </div>    
                <!--
                <div id="dropzone" class="fade well">Drop files here</div>
                -->
                <div class="upload-field"> 
                    <div>Uploading progress:</div>
                    <div id="progress-upload" class="progress">
                        <div class="progress-bar progress-bar-striped active"  role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                        </div>
                    </div> 
                </div>
                <div class="upload-field">
                    <div id="fileupload-error"></div>
                </div>

            
        </div>
    </div>

</div>

<%@include file="../../../jspf/footer.jspf" %>
