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
  <title>${blogInfo.realName}的博客-Missbe</title>
  <meta name="keywords" content="${blogInfo.realName},个人博客,Missbe" />
  <meta name="description" content="${blogInfo.realName}的博客，优雅、稳重、大气,低调。" />
  <link href="<%=basePath %>static/blog/css/base.css" rel="stylesheet">
  <link href="<%=basePath %>static/blog/css/index.css" rel="stylesheet">
  <!--[if lt IE 9]>
  <script src="<%=basePath %>static/blog/js/modernizr.js"></script>
  <![endif]-->
  <link href="<%=basePath%>favicon.ico" rel="shortcut icon" />
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
<div class="banner" style="background: url(<%=basePath %>${blogInfo.bannerImg}) top center; height: 265px; overflow: hidden">
  <section class="box">
    <ul class="texts">
      <c:forEach items="${blogInfo.bannerText}" var="text">
        <p>${text}</p>
      </c:forEach>
    </ul>
    <div class="avatar"><a href="#" style="background: url(<%=basePath %>${blogInfo.avator}) no-repeat;background-size: 140px 140px;"><span>${blogInfo.realName}</span></a> </div>
  </section>
</div>
<div class="template">
  <div class="box">
    <h3>
      <p><span>${indexSection.description}</span>-${indexSection.menuName}</p>
    </h3>
    <ul>
      <c:set value="http" var="http" />
      <c:forEach items="${sectionBlogs}" var="blog">
        <li><a href="<%=basePath %>blog/${account}/detail/${blog.id}.html"  target="_blank">
          <c:if test="${fn:length(blog.image)>=4}">
            <c:set var="substring" value="${fn:substring(blog.image, 0,4)}"/>

            <img src="<c:if test='${substring != http}'><%=basePath %></c:if>${blog.image}">
          </c:if>
          </a>
          <span>${blog.title}</span></li>
      </c:forEach>
    </ul>
  </div>
</div>
<article>
  <h2 class="title_tj">
    <p>文章<span>推荐</span></p>
  </h2>
  <div class="bloglist left">
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
  <aside class="right">
    <script type="text/javascript">(function(){document.write(unescape('%3Cdiv id="bdcs"%3E%3C/div%3E'));var bdcs = document.createElement('script');bdcs.type = 'text/javascript';bdcs.async = true;bdcs.src = 'http://znsv.baidu.com/customer_search/api/js?sid=17794598472099483822' + '&plate_url=' + encodeURIComponent(window.location.href) + '&t=' + Math.ceil(new Date()/3600000);var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(bdcs, s);})();</script>
    <div class="weather"><iframe width="250" scrolling="no" height="60" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=12&icon=1&num=1"></iframe></div>
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
