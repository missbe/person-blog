<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
<meta charset="gb2312">
<title>关于我</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
<link href="<%=basePath %>static/blog/css/base_m.css" rel="stylesheet">
<link href="<%=basePath %>static/blog/css/mobile_main.css" rel="stylesheet">
</head>
<body>
<header>
  <div class="logo"><a href="/"><img src="<%=basePath %>${blogInfo.logo}"></a></div>
  <div class="menu">
    <ul>
      <li><a href="<%=basePath %>blog/${account}.html">首页</a></li>
      <li><a href="<%=basePath %>blog/${account}/about.html">关于我</a></li>
      <c:forEach items="${menus}" var="m">
        <li><a  href="<%=basePath %>blog/${account}/menu/${m.id}.html">${m.name}</a></li>
      </c:forEach>
    </ul>
  </div>
</header>

<div class="about">
  ${aboutBlog.content}
</div>
<div class="about_c">
  <p>网名：<span>${blogInfo.englishName}</span> | ${blogInfo.name}</p>
  <p>姓名：${blogInfo.realName} </p>
  <p>生日：${blogInfo.birthDate}</p>
  <p>籍贯：${blogInfo.birthAddress}</p>
  <p>现居：${blogInfo.nowAddress}</p>
  <p>职业：${blogInfo.job}</p>
  <p>副职业：${blogInfo.secondJob}</p>
  <p>喜欢的书：${blogInfo.books}</p>
  <p>喜欢的音乐：${blogInfo.music}</p>
</div>
<footer>
  <p>Copyright © 2016 <a href="<%=basePath%>" target="_blank">四川理工学院汇南4栋612</a>&nbsp;<a href="http://www.miitbeian.gov.cn/" target="_blank">蜀ICP备16003487号-2</a>&nbsp;&nbsp;<a target="_blank" href="<%=basePath%>sitemap.xml">网站地图</a></p>

</footer>
<script>
  <script>
  var _hmt = _hmt || [];
  (function() {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?b6fe8a81619ca030ba9cc2445b6adfc1";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
  })();
</script>

</body>
</html>
