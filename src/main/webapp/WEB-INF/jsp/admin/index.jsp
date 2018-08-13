<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include  page="common/header.jsp"/>
<div class="box box-warning">
    <div class="box-header with-border">
      <h3 class="box-title">首页内容</h3>
    </div>
    <!-- /.box-header -->
    <div class="box-body">
      <form role="form" method="POST" action="<%=basePath %>admin/index">
        <!-- text input -->
        <div class="form-group">
          <label>标题左侧</label>
          <input type="text" name="title_left" value="${index.title_left}" class="form-control" placeholder="标题左侧">
        </div>
        <div class="form-group">
          <label>标题右侧(蓝色部分)</label>
          <input type="text" name="title_right" value="${index.title_right}" class="form-control" placeholder="标题右侧(蓝色部分)">
        </div>

        <!-- textarea -->
        <div class="form-group">
          <label>文本段落内容</label>
          <textarea class="form-control" name="content" rows="3" placeholder="文本段落内容">${index.content}</textarea>
        </div>

        <input type="submit" class="btn btn-primary" value="提交保存"/>

      </form>
    </div>
    <!-- /.box-body -->
  </div>
<jsp:include  page="common/footer.jsp"/>
