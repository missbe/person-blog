<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
<!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="<%=basePath%>static/adminLte/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="<%=basePath%>static/adminLte/font-awesome.min.css">
  <!--[if lt IE 9]>
  <script src="<%=basePath%>static/adminLte/ie9/html5shiv.min.js"></script>
  <script src="<%=basePath%>static/adminLte/ie9/respond.min.js"></script>
  <![endif]-->
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>博客导航-missbe</title>
</head>
<body>

<div class="container">
  <div class="row clearfix">
    <div class="col-md-12 column" style="margin-top: 4%;">
      <h3 class="text-center text-info bg-primary" style="width:92%;height:40px;padding-top:10px;margin-bottom:45px;">
        博客导航
      </h3>
      <div class="row clearfix">
        <c:forEach items="${users}" var="user">
          <div class="col-md-4 column">
            <a href="<%=basePath%>blog/${user.account}.html">
              <img alt="140x140" src="<%=basePath%>${user.avator}" class="img-rounded img-responsive img-thumbnail" style="width:260px;height:260px;" />
            </a>
            <ul class="nav nav-pills">
              <li class="active">
                <a href="<%=basePath%>blog/${user.account}.html"><span class="badge pull-right">博主</span>${user.name}</a>
              </li>
            </ul>
          </div>
        </c:forEach>

      </div>
    </div>
  </div>
</div>

</body>
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?5b78f12b39a2a5c74855fddcbb33d851";
  var s = document.getElementsByTagName("script")[0];
  s.parentNode.insertBefore(hm, s);
})();
</script>
</html>