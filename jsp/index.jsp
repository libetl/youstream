<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
response.setStatus(301);
response.setHeader( "Location", request.getContextPath() + "/UI.act" );
response.setHeader( "Connection", "close" );
%>
