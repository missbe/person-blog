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
<title>${blogInfo.realName}的博客网站-Missbe</title>
<meta name="keywords" content="${blogInfo.realName},个人博客,Missbe" />
<meta name="description" content="${blogInfo.realName}的博客,Missbe" />
<link href="<%=basePath %>static/blog/css/base.css" rel="stylesheet">
<link href="<%=basePath %>static/blog/css/about.css" rel="stylesheet">
<!--[if lt IE 9]>
<script src="<%=basePath %>static/js/modernizr.js"></script>
<![endif]-->
</head>
<body>
<header>
      <div style="width: 260px; height: 60px; margin: 10px 0 0 0; position: absolute; display: block;background: url(<%=basePath %>${blogInfo.logo}) no-repeat;background-size: 260px 60px;"><a href="<%=basePath %>blog/${account}.html"></a></div>
        <nav class="topnav" id="topnav">
            <a href="<%=basePath %>blog/${account}.html"><span>首页</span><span class="en">Protal</span></a>
            <a href="<%=basePath %>blog/${account}/about.html"><span>关于我</span><span class="en">About</span></a>
            <c:forEach items="${menus}" var="menu">
              <a  href="<%=basePath %>blog/${account}/menu/${menu.id}.html"><span>${menu.name}</span><span class="en">${menu.englishName}</span></a>
            </c:forEach>
        </nav>
</header>
<article class="aboutcon" style="background:url(<%=basePath %>${blogInfo.photo}) no-repeat bottom right;background-size: 182px 325px;">
<h1 class="t_nav"><span>像“草根”一样，紧贴着地面，低调的存在，冬去春来，枯荣无恙。</span><a href="<%=basePath %>blog/${account}.html" class="n1">网站首页</a><a href="<%=basePath %>blog/${account}/about.html" class="n2">关于我</a></h1>
<div class="about left">
  ${aboutBlog.content}
</div>
<aside class="right">
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
</aside>
</article>
<footer>
    <p>Copyright © 2016 <a href="<%=basePath%>" target="_blank">四川理工学院汇南4栋612</a>&nbsp;<a href="http://www.miitbeian.gov.cn/" target="_blank">蜀ICP备16003487号-2</a>&nbsp;&nbsp;<a target="_blank" href="<%=basePath%>sitemap.xml">网站地图</a></p>

</footer>
<script src="<%=basePath %>static/blog/js/silder.js"></script>
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