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
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Missbe | 后台</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="<%=basePath%>static/adminLte/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="<%=basePath%>static/adminLte/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="<%=basePath%>static/adminLte/ionicons.min.css">
  <!-- Select2 -->
  <link rel="stylesheet" href="<%=basePath%>static/adminLte/plugins/select2/select2.min.css">
  <!-- Themes style -->
  <link rel="stylesheet" href="<%=basePath%>static/adminLte/dist/css/AdminLTE.min.css">
  <!-- DataTables -->
  <link rel="stylesheet" href="<%=basePath%>static/adminLte/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="<%=basePath%>static/adminLte/plugins/datatables/extensions/Select/css/select.bootstrap.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="<%=basePath%>static/adminLte/dist/css/skins/_all-skins.min.css">
  <link rel="stylesheet" href="<%=basePath%>static/css/messenger.css">
  <link rel="stylesheet" href="<%=basePath%>static/css/messenger-themes-flat.css">

  <!--[if lt IE 9]>
  <script src="<%=basePath%>static/adminLte/ie9/html5shiv.min.js"></script>
  <script src="<%=basePath%>static/adminLte/ie9/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
  <header class="main-header">
    <!-- Logo -->
    <a href="<%=basePath%>admin" class="logo">
      <img src="<%=basePath%>static/images/logo.jpg" style="width:32px" class="img-circle" alt="logo Image">
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>

      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">

          <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="<%=basePath%><shiro:principal property="avator"/>" class="user-image" alt="User Image">
              <span class="hidden-xs"><shiro:principal/></span>
            </a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header">
                <img src="<%=basePath%><shiro:principal property="avator"/>" class="img-circle" alt="User Image">

                <p>
                  <shiro:principal/> - <shiro:principal property="nickname"/>
                  <small>Login In <shiro:principal property="lastLoginTime"/></small>
                </p>
              </li>
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                  <a href="<%=basePath%>user/profileUI" class="btn btn-info btn-flat"><i class="fa fa-fw fa-edit"></i>修改个人信息</a>
                </div>
                <div class="pull-right">
                  <a href="<%=basePath%>/logout" class="btn btn-danger btn-flat"><i class="fa fa-fw fa-sign-out"></i>注销登录</a>
                </div>
              </li>
            </ul>
          </li>
          <!-- Control Sidebar Toggle Button -->
          <li>
            <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
          </li>
        </ul>
      </div>
    </nav>
  </header>

  <!-- =============================================== -->

  <!-- Left side column. contains the sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- /.search form -->
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu">
        <li class="header">
             Missbe | 后台
        </li>
        <li>
          <a href="<%=basePath%>admin">
            <i class="fa fa-dashboard"></i> <span>仪表盘</span>
          </a>
        </li>
        <li class="<c:if test='${currentUrl == "user/themes/themes/list" || currentUrl == "user/comments/comments/list" ||currentUrl == "user/blog/blog/add" || currentUrl == "user/blog/section/add" || currentUrl == "user/blog/menu/add" || currentUrl == "user/blog/menu/list" || currentUrl == "user/blog/section/list" || currentUrl == "user/blog/blog/list"}'>active</c:if> treeview">
          <a href="#">
            <i class="fa fa-heart"></i> <span>博客管理</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li class="<c:if test='${currentUrl == "user/blog/blog/add"}'>active</c:if>"><a href="<%=basePath%>user/blog/blog/addUI"><i class="fa fa-plus"></i> 添加文章</a></li>
            <li class="<c:if test='${currentUrl == "user/blog/blog/list"}'>active</c:if>"><a href="<%=basePath%>user/blog/blog/listUI"><i class="fa fa-heart-o"></i> 文章列表</a></li>
            <li class="<c:if test='${currentUrl == "user/blog/section/add"}'>active</c:if>"><a href="<%=basePath%>user/blog/section/addUI"><i class="fa fa-plus-square"></i> 添加栏目</a></li>
            <li class="<c:if test='${currentUrl == "user/blog/section/list"}'>active</c:if>"><a href="<%=basePath%>user/blog/section/listUI"><i class="fa fa-map"></i> 栏目列表</a></li>
            <li class="<c:if test='${currentUrl == "user/blog/menu/add"}'>active</c:if>"><a href="<%=basePath%>user/blog/menu/addUI"><i class="fa fa-plus-square-o"></i> 添加菜单</a></li>
            <li class="<c:if test='${currentUrl == "user/blog/menu/list"}'>active</c:if>"><a href="<%=basePath%>user/blog/menu/listUI"><i class="fa fa-cog"></i> 菜单列表</a></li>
            <li class="<c:if test='${currentUrl == "user/themes/themes/list"}'>active</c:if>"><a href="<%=basePath%>user/themes/themes/listUI"><i class="fa fa-cart-plus"></i> 主题审核</a></li>
            <li class="<c:if test='${currentUrl == "user/comments/comments/list"}'>active</c:if>"><a href="<%=basePath%>user/comments/comments/listUI"><i class="fa fa-check-circle-o"></i> 回复审核</a></li>

          </ul>
        </li>
      <shiro:hasPermission name="/admin/permission">
        <li class="<c:if test='${currentUrl == "user/permission/permission/list" || currentUrl == "user/permission/permission/add"
         || currentUrl == "user/roles/roles/list" || currentUrl == "user/roles/roles/add" || currentUrl == "user/users/users/list" || currentUrl == "user/users/users/add" }'>active</c:if>">
          <a href="#">
            <i class="fa fa-bookmark"></i> <span>用户权限管理</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li class="<c:if test='${currentUrl == "user/users/users/listUI"}'>active</c:if>"><a href="<%=basePath%>user/users/users/listUI"><i class="fa fa-plus-square"></i> 用户管理</a></li>

            <li class="<c:if test='${currentUrl == "user/permission/permission/listUI"}'>active</c:if>"><a href="<%=basePath%>user/permission/permission/listUI"><i class="fa fa-plus"></i> 权限管理</a></li>
            <li class="<c:if test='${currentUrl == "user/roles/roles/listUI"}'>active</c:if>"><a href="<%=basePath%>user/roles/roles/listUI"><i class="fa fa-heart-o"></i> 角色管理</a></li>
          </ul>
        </li>
      </shiro:hasPermission>

      <shiro:hasPermission name="/admin/index">
        <li class="<c:if test='${currentUrl == "admin/index"}'>active</c:if>">
          <a href="<%=basePath%>admin/index">
            <i class="fa fa-trophy"></i> <span>首页</span>
          </a>
        </li>
      </shiro:hasPermission>
      <shiro:hasPermission name="/admin/about">
        <li class="<c:if test='${currentUrl == "admin/about"}'>active</c:if>">
          <a href="<%=basePath%>admin/about">
            <i class="fa fa-trademark"></i> <span>关于我们</span>
          </a>
        </li>
      </shiro:hasPermission>
      <shiro:hasPermission name="/admin/services">
        <li class="<c:if test='${currentUrl == "admin/services"}'>active</c:if>">
          <a href="<%=basePath%>admin/services">
            <i class="fa fa-rocket"></i> <span>产品服务</span>
          </a>
        </li>
      </shiro:hasPermission>
      <shiro:hasPermission name="/admin/customs">
        <li class="<c:if test='${currentUrl == "admin/customs"}'>active</c:if>">
          <a href="<%=basePath%>admin/customs">
            <i class="fa fa-users"></i> <span>合作客户</span>
          </a>
        </li>
      </shiro:hasPermission>
      <shiro:hasPermission name="/admin/contact">
        <li class="<c:if test='${currentUrl == "admin/contact"}'>active</c:if>">
          <a href="<%=basePath%>admin/contact">
            <i class="fa fa-star"></i> <span>联系我们</span>
          </a>
        </li>
      </shiro:hasPermission>
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>
  <div id="content-wrapper" class="content-wrapper">
  <c:if test="${msg!=null}">
      <div class="callout callout-<c:if test="${msg.success}">success</c:if><c:if test="${!msg.success}">danger</c:if>">
        <span class="glyphicon glyphicon-<c:if test="${msg.success}">ok</c:if><c:if test="${!msg.success}">remove</c:if>">&nbsp;${msg.result}</span>
      </div>
  </c:if>