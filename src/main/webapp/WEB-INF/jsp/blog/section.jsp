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
<title>${section.description}-${blogInfo.realName}-Missbe</title>
<meta name="keywords" content="${blogInfo.realName},${section.description},个人博客,Missbe" />
<meta name="description" content="${blogInfo.realName}的博客,${section.description},Missbe" />
<link href="<%=basePath %>static/blog/css/base.css" rel="stylesheet">
<link href="<%=basePath %>static/blog/css/learn.css" rel="stylesheet">
<link href="<%=basePath %>static/blog/css/index.css" rel="stylesheet">
<!--[if lt IE 9]>
<script src="<%=basePath %>static/js/modernizr.js"></script>
<![endif]-->
<link href="<%=basePath%>favicon.ico" rel="shortcut icon" />

</head>
<body>
<header>
        <div style="width: 260px; height: 60px; margin: 10px 0 0 0; position: absolute; display: block;background: url(<%=basePath %>${blogInfo.logo}) no-repeat;background-size: 260px 60px;"><a href="<%=basePath %>blog/${account}.html"></a></div>
          <nav class="topnav" id="topnav">
              <a href="<%=basePath %>blog/${account}.html"><span>首页</span><span class="en">Protal</span></a>
              <a href="<%=basePath %>blog/${account}/about.html"><span>关于我</span><span class="en">About</span></a>
              <c:forEach items="${menus}" var="m">
                <a <c:if test='${menu.id == m.id}'>id="topnav_current"</c:if> href="<%=basePath %>blog/${account}/menu/${m.id}.html"><span>${m.name}</span><span class="en">${m.englishName}</span></a>
              </c:forEach>
          </nav>
</header>
<article class="blogs">
<h1 class="t_nav"><span>${section.content} </span><a href="<%=basePath %>blog/${account}.html" class="n1">网站首页</a><a href="<%=basePath %>blog/${account}/section/${section.id}.html" class="n2">${section.description}</a></h1>
<div class="newblog left">
    <c:set value="http" var="http" />

    <c:forEach items="${sectionBlogs}" var="blog">
       <h2><a href="<%=basePath %>blog/${account}/detail/${blog.id}.html" title="${blog.title}" target="_blank">${blog.title}</a></h2>
       <p class="dateview"><span>&nbsp;&nbsp;发布时间：${blog.date}</span><span>作者：${blog.author}</span><span>栏目：[<a href="<%=basePath %>blog/${account}/section/${blog.section}.html">${blog.sectionName}</a>]</span></p>
        <c:if test="${fn:length(blog.image)>=4}">
            <c:set var="substring" value="${fn:substring(blog.image, 0,4)}"/>
            <figure>
                <img src="<c:if test='${substring != http}'><%=basePath %></c:if>${blog.image}">
            </figure>
        </c:if>

        <ul class="nlist">
          <p>${blog.simpleContent}</p>
          <a title="/" href="<%=basePath %>blog/${account}/detail/${blog.id}.html" target="_blank" class="readmore">详细信息>></a>
        </ul>
        <div class="line"></div>
    </c:forEach>
    <div class="blank"></div>
    <div class="page">
        <a title="总条数"><b>${count}</b></a>
        <c:if test='${pageNow-2>0}'>
        <a href="<%=basePath %>blog/${account}/section/${section.id}_${pageNow-2}.html">${pageNow-2}</a>
        </c:if>
        <c:if test='${pageNow-1>0}'>
        <a href="<%=basePath %>blog/${account}/section/${section.id}_${pageNow-1}.html">${pageNow-1}</a>
        </c:if>
        <b>${pageNow}</b>
        <c:if test='${pageNow+1<=pageAll}'>
        <a href="<%=basePath %>blog/${account}/section/${section.id}_${pageNow+1}.html">${pageNow+1}</a>
        </c:if>
        <c:if test='${pageNow+2<=pageAll}'>
        <a href="<%=basePath %>blog/${account}/section/${section.id}_${pageNow+2}.html">${pageNow+2}</a>
        </c:if>
        <c:if test='${pageAll>1&&pageNow!=pageAll}'>
        <a href="<%=basePath %>blog/${account}/section/${section.id}_${pageNow+1}.html">&gt;
        </a><a href="<%=basePath %>blog/${account}/section/${section.id}_${pageAll}.html">&gt;&gt;</a>
        </c:if>
    </div>
    </div>
</div>
<aside class="right">
   <script type="text/javascript">(function(){document.write(unescape('%3Cdiv id="bdcs"%3E%3C/div%3E'));var bdcs = document.createElement('script');bdcs.type = 'text/javascript';bdcs.async = true;bdcs.src = 'http://znsv.baidu.com/customer_search/api/js?sid=17794598472099483822' + '&plate_url=' + encodeURIComponent(window.location.href) + '&t=' + Math.ceil(new Date()/3600000);var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(bdcs, s);})();</script>
   <div class="rnav">
      <h2>栏目导航</h2>
      <ul>
      <c:forEach items="${menuSections}" var="section" varStatus="status">
       <li class="rnav${ (status.index%5)+1}"><a href="<%=basePath %>blog/${account}/section/${section.id}.html">${section.description}</a></li>
      </c:forEach>
     </ul>
    </div>
   <div class="rnavs">
         <h2>栏目导航</h2>
         <ul>
         <c:forEach items="${menuSections}" var="section">
          <li><a href="<%=basePath %>blog/${account}/section/${section.id}.html">${section.description}</a></li>
         </c:forEach>
        </ul>
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
    <div class="bdsharebuttonbox"><a href="#" class="bds_more" data-cmd="more"></a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a></div>
        <script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"1","bdSize":"32"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
        <div class="guanzhu">扫描二维码关注<span>${blogInfo.weixin}</span>微信账号</div>
                <a href="/" class="weixin" style="background: url(<%=basePath %>${blogInfo.weixinQrcode}) no-repeat;background-size:250px 274px;"> </a>
        </aside>
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