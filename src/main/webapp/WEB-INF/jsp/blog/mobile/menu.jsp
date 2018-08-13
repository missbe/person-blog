<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
<meta charset="gb2312">
<title>${menu.name}-${blogInfo.realName}博客</title>
<meta name="keywords" content="${blogInfo.realName},${menu.name},个人博客,Missbe" />
<meta name="description" content="${blogInfo.realName}的博客,${menu.name},Missbe" />
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
<link href="<%=basePath %>static/blog/css/base_m.css" rel="stylesheet">
<link href="<%=basePath %>static/blog/css/mobile_main.css" rel="stylesheet">
<link href="<%=basePath%>favicon.ico" rel="shortcut icon" />

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

<div class="rnav">
  <ul>
    <c:forEach items="${menuSections}" var="section" varStatus="status">
     <li class="rnav${ (status.index%5)+1}"><a href="<%=basePath %>blog/${account}/section/${section.id}.html">${section.description}</a></li>
  </c:forEach>
  </ul>
</div>
<div class="newblog">
    <ul>
        <c:set value="http" var="http" />

        <c:forEach items="${menuBlogs}" var="blog">
       <h2><a href="<%=basePath %>blog/${account}/detail/${blog.id}.html" title="${blog.title}">${blog.title}</a></h2>
        <p class="dateview">${blog.date}</p>
        <c:if test="${fn:length(blog.image)>=4}">
            <c:set var="substring" value="${fn:substring(blog.image, 0,4)}"/>

            <img src="<c:if test='${substring != http}'><%=basePath %></c:if>${blog.image}">
        </c:if>

        <ul class="nlist">
          <p>${blog.simpleContent}</p>
          <a title="/" href="<%=basePath %>blog/${account}/detail/${blog.id}.html" class="readmore">详细信息>></a>
        </ul>
        <div class="line"></div>
    </c:forEach>
    <div class="line"></div>
    <div class="blank"></div>
    <div class="page">
        <a title="总条数"><b>${count}</b></a>
        <c:if test='${pageNow-2>0}'>
        <a href="<%=basePath %>blog/${account}/menu/${menu.id}_${pageNow-2}.html">${pageNow-2}</a>
        </c:if>
        <c:if test='${pageNow-1>0}'>
        <a href="<%=basePath %>blog/${account}/menu/${menu.id}_${pageNow-1}.html">${pageNow-1}</a>
        </c:if>
        <b>${pageNow}</b>
        <c:if test='${pageNow+1<=pageAll}'>
        <a href="<%=basePath %>blog/${account}/menu/${menu.id}_${pageNow+1}.html">${pageNow+1}</a>
        </c:if>
        <c:if test='${pageNow+2<=pageAll}'>
        <a href="<%=basePath %>blog/${account}/menu/${menu.id}_${pageNow+2}.html">${pageNow+2}</a>
        </c:if>
        <c:if test='${pageAll>1&&pageNow!=pageAll}'>
        <a href="<%=basePath %>blog/${account}/menu/${menu.id}_${pageNow+1}.html">&gt;
        </a><a href="<%=basePath %>blog/${account}/menu/${menu.id}_${pageAll}.html">&gt;&gt;</a>
        </c:if>
    </div>
    </ul>
</div>
<footer>
    <p>Copyright © 2016 <a href="<%=basePath%>" target="_blank">四川理工学院汇南4栋612</a>&nbsp;<a href="http://www.miitbeian.gov.cn/" target="_blank">蜀ICP备16003487号-2</a>&nbsp;&nbsp;<a target="_blank" href="<%=basePath%>sitemap.xml">网站地图</a></p>

</footer>
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