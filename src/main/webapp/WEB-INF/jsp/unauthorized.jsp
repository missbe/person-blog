<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<title>unauthorized</title>
	</head>
	<body>
		<h1>unauthorized!</h1>
		<br>
		<br>
		<a href="<%=basePath%>admin">Admin Area.</a>
	</body>
</html>