<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Missbe | Log in</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="<%=basePath%>static/adminLte/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="<%=basePath%>static/adminLte/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="<%=basePath%>static/adminLte/ionicons.min.css">
  <!-- Themes style -->
  <link rel="stylesheet" href="<%=basePath%>static/adminLte/dist/css/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="<%=basePath%>static/adminLte/plugins/iCheck/square/blue.css">

  <!--[if lt IE 9]>
  <script src="<%=basePath%>static/adminLte/ie9/html5shiv.min.js"></script>
  <script src="<%=basePath%>static/adminLte/ie9/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
       <img src="<%=basePath%>static/images/logo.jpg" style="width:48px" class="img-circle" alt="logo Image">
       <a href="<%=basePath%>"><b>Admin</b></a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">
        <span class="glyphicon glyphicon-bell">&nbsp;请输入您的登录凭据</span>
    </p>
    <c:if test="${msg!=null}">
        <div class="callout callout-danger">
          <span class="glyphicon glyphicon-remove">&nbsp;${msg}</span>
        </div>
    </c:if>
    <form action="<%=basePath%>login" method="post">
      <div class="form-group has-feedback">
        <input name="username" type="text" class="form-control" placeholder="用户名">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input name="password" type="password" class="form-control" placeholder="密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>

      <div class="form-group has-feedback input-group">
         <input name="verCode" type="text" class="form-control" placeholder="验证码">
         <div class="input-group-btn">
           <img  src="<%=basePath%>captcha" onclick="this.src='<%=basePath%>captcha?d='+new Date().getTime()"/>
         </div>
       </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
              <input name="rememberMe" type="checkbox"> 一周免登录
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
        <!-- /.col -->
      </div>
    </form>

  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.2.3 -->
<script src="<%=basePath%>static/adminLte/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="<%=basePath%>static/adminLte/bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="<%=basePath%>static/adminLte/plugins/iCheck/icheck.min.js"></script>
<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
  });
</script>
</body>
</html>
