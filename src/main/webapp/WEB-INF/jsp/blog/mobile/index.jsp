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
<title>${blogInfo.realName}的博客</title>
<meta name="keywords" content="${blogInfo.realName}个人博客,个人博客,${blogInfo.realName}" />
<meta name="description" content="${blogInfo.realName}的博客，优雅、稳重、大气,低调。" />
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
<link href="<%=basePath %>static/blog/css/base_m.css" rel="stylesheet">
<link href="<%=basePath %>static/blog/css/index_m.css" rel="stylesheet">
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

<div class="template">
  <div class="box">
    <h3>
      <p><span>${indexSection.description}</span>-${indexSection.menuName}</p>
    </h3>
    <ul>
        <c:set value="http" var="http" />

        <c:forEach items="${sectionBlogs}" var="blog">
            <li>
                <a href="<%=basePath %>blog/${account}/detail/${blog.id}.html">
                    <c:if test="${fn:length(blog.image)>=4}">
                        <c:set var="substring" value="${fn:substring(blog.image, 0,4)}"/>

                        <img src="<c:if test='${substring != http}'><%=basePath %></c:if>${blog.image}">
                    </c:if>
                </a>
                <span>${blog.title}</span>
            </li>
        </c:forEach>
     </ul>
  </div>
</div>
<h2 class="title_tj">
  <p>文章<span>推荐</span></p>
</h2>
<ul>
<div class="bloglist">
    <c:forEach items="${recommendBlogs}" var="blog">
    <h3><a href="<%=basePath %>blog/${account}/detail/${blog.id}.html" title="${blog.title}" target="_blank">${blog.title}</a></h3>
        <c:if test="${fn:length(blog.image)>=4}">
            <c:set var="substring" value="${fn:substring(blog.image, 0,4)}"/>
            <figure>
                <img src="<c:if test='${substring != http}'><%=basePath %></c:if>${blog.image}">
            </figure>
        </c:if>
        <ul>
          <p>${blog.simpleContent}</p>
          <a title="/" href="<%=basePath %>blog/${account}/detail/${blog.id}.html" target="_blank" class="readmore">阅读全文>></a>
        </ul>
        <p class="dateview"><span>${blog.date}</span><span>作者：${blog.author}</span><span>${blog.menuName}：[<a href="<%=basePath %>blog/${account}/section/${blog.section}.html">${blog.sectionName}</a>]</span></p>
     </c:forEach>
</div>
<div class="news">
  <h3>
    <p>最新<span>文章</span></p>
  </h3>
  <ul class="rank">
    <c:forEach items="${newBlogs}" var="blog">
      <li><a href="<%=basePath %>blog/${account}/detail/${blog.id}.html" title="${blog.title}" target="_blank">${blog.title}</a></li>
    </c:forEach>
  </ul>
  <h3 class="ph">
    <p>点击<span>排行</span></p>
  </h3>
  <ul class="paih">
   <c:forEach items="${hotBlogs}" var="blog">
     <li><a href="<%=basePath %>blog/${account}/detail/${blog.id}.html" title="${blog.title}" target="_blank">${blog.title}</a></li>
   </c:forEach>
  </ul>
  <div class="guanzhu">扫描二维码关注<span>${blogInfo.weixin}</span>官方微信账号</div>
  <a href="/" class="weixin"><img style="width:60%;"src="${blogInfo.weixinQrcode}"></a></div>
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