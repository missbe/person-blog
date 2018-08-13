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
      <h3 class="box-title">关于我们</h3>
    </div>
    <!-- /.box-header -->
    <div class="box-body">
      <form role="form" method="POST" action="<%=basePath %>admin/about">
        <!-- text input -->
        <div class="form-group">
          <label>标题左侧</label>
          <input type="text" name="title_left" value="${about.title_left}" class="form-control" placeholder="标题左侧">
        </div>
        <div class="form-group">
          <label>标题右侧(蓝色部分)</label>
          <input type="text" name="title_right" value="${about.title_right}" class="form-control" placeholder="标题右侧(蓝色部分)">
        </div>

        <!-- textarea -->
        <div class="form-group">
          <label>副标题</label>
          <textarea class="form-control" name="title_second" rows="3" placeholder="副标题">${about.title_second}</textarea>
        </div>

        <div class="form-group">
          <label>内容第一段</label>
          <textarea class="form-control" name="content_one" rows="3" placeholder="内容第一段">${about.content_one}</textarea>
        </div>

        <div class="form-group">
          <label>内容第二段</label>
          <textarea class="form-control" name="content_two" rows="3" placeholder="内容第二段">${about.content_two}</textarea>
        </div>

        <div class="form-group">
          <label>列表第一行</label>
          <input type="text" name="list_one" value="${about.list_one}" class="form-control" placeholder="列表第一行">
        </div>

        <div class="form-group">
          <label>列表第二行</label>
          <input type="text" name="list_two" value="${about.list_two}" class="form-control" placeholder="列表第二行">
        </div>

        <div class="form-group">
          <label>列表第三行</label>
          <input type="text" name="list_three" value="${about.list_three}" class="form-control" placeholder="列表第三行">
        </div>

        <input type="submit" class="btn btn-primary" value="提交保存"/>

      </form>
    </div>
    <!-- /.box-body -->
  </div>
<jsp:include  page="common/footer.jsp"/>
