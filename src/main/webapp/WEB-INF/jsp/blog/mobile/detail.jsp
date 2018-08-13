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
    <title>${nowBlog.title}-${blogInfo.realName}</title>
    <meta name="keywords" content="${nowBlog.title},,个人博客,Missbe" />
    <meta name="description" content="${nowBlog.title},,个人博客,Missbe" />
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

<article class="blogs">
    <div class="index_about">
        <h2 class="c_titile">${nowBlog.title}</h2>
        <p class="box_c"><span class="d_time">发布时间：${nowBlog.date}</span><span>作者：${nowBlog.author}</span><span>点击：${nowBlog.click}</span></p>
        <ul class="infos">
              ${nowBlog.content}
            </ul>
            <div class="keybq">
            <p><span>栏目</span>【${nowBlog.sectionName}】</p>
            </div>
            <div class="ad"> </div>
            <div class="nextinfo">
              <p>上一篇<a href="<%=basePath %>blog/${account}/detail/${lastBlog.id}.html">${lastBlog.title}</a></p>
              <p>下一篇<a href="<%=basePath %>blog/${account}/detail/${nextBlog.id}.html">${nextBlog.title}</a></p>
            </div>
        <div class="blank"></div>
    </div>
</article>
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