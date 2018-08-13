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
      <h3 class="box-title">合作客户</h3>
    </div>
    <!-- /.box-header -->
    <div class="box-body">
      <form role="form" method="POST" action="<%=basePath %>admin/customs">

        <div class="form-group col-lg-12">
          <label>标题</label>
          <input type="text" name="title" value="${customs.title}" class="form-control" placeholder="标题">
        </div>

        <div class="form-group col-lg-6">
          <label>客户项目标题(1)</label>
          <input type="text" name="title_one" value="${customs.title_one}" class="form-control" placeholder="标题(1)">
          <label>客户名(1)</label>
          <input type="text" name="custom_one" value="${customs.custom_one}" class="form-control" placeholder="客户名(1)">
        </div>

        <div class="form-group col-lg-6">
          <label>内容(1)</label>
          <textarea class="form-control" name="content_one" rows="4" placeholder="项目简介(1)">${customs.content_one}</textarea>
        </div>


        <div class="form-group col-lg-6">
          <label>客户项目标题(2)</label>
          <input type="text" name="title_two" value="${customs.title_two}" class="form-control" placeholder="标题(2)">
          <label>客户名(2)</label>
          <input type="text" name="custom_two" value="${customs.custom_two}" class="form-control" placeholder="客户名(2)">
        </div>

        <div class="form-group col-lg-6">
          <label>内容(2)</label>
          <textarea class="form-control" name="content_two" rows="4" placeholder="项目简介(2)">${customs.content_two}</textarea>
        </div>

        <div class="form-group col-lg-6">
          <label>客户项目标题(3)</label>
          <input type="text" name="title_three" value="${customs.title_three}" class="form-control" placeholder="标题(3)">
          <label>客户名(3)</label>
          <input type="text" name="custom_three" value="${customs.custom_three}" class="form-control" placeholder="客户名(3)">
        </div>

        <div class="form-group col-lg-6">
          <label>内容(3)</label>
          <textarea class="form-control" name="content_three" rows="4" placeholder="项目简介(3)">${customs.content_three}</textarea>
        </div>

        <div class="form-group col-lg-6">
          <label>客户项目标题(4)</label>
          <input type="text" name="title_four" value="${customs.title_four}" class="form-control" placeholder="标题(4)">
          <label>客户名(4)</label>
          <input type="text" name="custom_four" value="${customs.custom_four}" class="form-control" placeholder="客户名(4)">
        </div>

        <div class="form-group col-lg-6">
          <label>内容(4)</label>
          <textarea class="form-control" name="content_four" rows="4" placeholder="项目简介(4)">${customs.content_four}</textarea>
        </div>

        <div class="form-group col-lg-6">
          <label>客户项目标题(5)</label>
          <input type="text" name="title_five" value="${customs.title_five}" class="form-control" placeholder="标题(5)">
          <label>客户名(5)</label>
          <input type="text" name="custom_five" value="${customs.custom_five}" class="form-control" placeholder="客户名(5)">
        </div>

        <div class="form-group col-lg-6">
          <label>内容(5)</label>
          <textarea class="form-control" name="content_five" rows="4" placeholder="项目简介(5)">${customs.content_five}</textarea>
        </div>

        <div class="form-group col-lg-6">
          <label>客户项目标题(6)</label>
          <input type="text" name="title_six" value="${customs.title_six}" class="form-control" placeholder="标题(6)">
          <label>客户名(6)</label>
          <input type="text" name="custom_six" value="${customs.custom_six}" class="form-control" placeholder="客户名(6)">
        </div>

        <div class="form-group col-lg-6">
          <label>内容(6)</label>
          <textarea class="form-control" name="content_six" rows="4" placeholder="项目简介(6)">${customs.content_six}</textarea>
        </div>

        <input type="submit" class="btn btn-primary" value="提交保存"/>

      </form>
    </div>
    <!-- /.box-body -->
  </div>
<jsp:include  page="common/footer.jsp"/>
