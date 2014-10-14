<%-- 
    Document   : add
    Created on : 3.9.2014, 21:46:29
    Author     : Stenlik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<script src="<s:url value="js/vendor/jquery.ui.widget.js"/>"></script>
<script src="<s:url value="js/jquery.iframe-transport.js"/>"></script>
<script src="<s:url value="js/jquery.fileupload.js"/>"></script>

<link href="<s:url value="css/dropzone.css"/>" type="text/css" rel="stylesheet" />
<script src="<s:url value="js/myuploadfunction.js"/>"></script>

<div class="container">
    <input id="fileupload" type="file" name="files[]" data-url="/upload.htm" multiple />

    <div id="dropzone" class="fade well">Drop files here</div>

    <div id="progress" class="progress">
        <div class="bar" style="width: 0%;"></div>
    </div>

    <table id="uploaded-files" class="table">
        <tr>
            <th>File Name</th>
            <th>File Size</th>
            <th>File Type</th>
            <th>Download</th>
        </tr>
    </table>
</div>
