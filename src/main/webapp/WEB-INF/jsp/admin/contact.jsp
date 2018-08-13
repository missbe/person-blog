<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include  page="common/header.jsp"/>
<div class="col-md-6 col-md-offset-2" style="font-size: 16px">
	<h1 class="title text-center" id="contact">联系我们</h1>
	<div class="space"></div>
	<div class="row col-md-offset-3">
		<div class="col-sm-6">
			<div class="footer-content">
				<p class="large">&nbsp;&nbsp;&nbsp;&nbsp;精品课程</p>
				<ul class="list-icons">
					<li><i class="fa fa-map-marker pr-10"></i> 自贡市四川理工学院</li>
					<li><i class="fa fa-phone pr-10"></i> +86 15700661261</li>
					<li><i class="fa fa-fax pr-10"></i> +028 36050993 </li>
					<li><i class="fa fa-envelope-o pr-10"></i> missbe@missbe.cn</li>
				</ul>
				<ul class="social-links">
				</ul>
			</div>
		</div>
	</div>
</div>
<jsp:include  page="common/footer.jsp"/>
