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
      <h3 class="box-title">产品服务</h3>
    </div>
    <!-- /.box-header -->
    <div class="box-body">
      <form role="form" method="POST" action="<%=basePath %>admin/services">
        <div class="form-group col-lg-6">
          <label>服务标题(1)</label>
          <input type="text" name="title_one" value="${services.title_one}" class="form-control" placeholder="标题(1)">
        </div>

        <div class="form-group col-lg-6">
          <label>内容(1)</label>
          <textarea class="form-control" name="content_one" rows="3" placeholder="文本段落内容(1)">${services.content_one}</textarea>
        </div>

        <div class="form-group col-lg-6">
          <label>服务标题(2)</label>
          <input type="text" name="title_two" value="${services.title_two}" class="form-control" placeholder="标题(2)">
        </div>

        <div class="form-group col-lg-6">
          <label>内容(2)</label>
          <textarea class="form-control" name="content_two" rows="3" placeholder="文本段落内容(2)">${services.content_two}</textarea>
        </div>

        <div class="form-group col-lg-6">
          <label>服务标题(3)</label>
          <input type="text" name="title_three" value="${services.title_three}" class="form-control" placeholder="标题(3)">
        </div>

        <div class="form-group col-lg-6">
          <label>内容(3)</label>
          <textarea class="form-control" name="content_three" rows="3" placeholder="文本段落内容(3)">${services.content_three}</textarea>
        </div>

        <div class="form-group col-lg-6">
          <label>服务标题(4)</label>
          <input type="text" name="title_four" value="${services.title_four}" class="form-control" placeholder="标题(4)">
        </div>

        <div class="form-group col-lg-6">
          <label>内容(4)</label>
          <textarea class="form-control" name="content_four" rows="3" placeholder="文本段落内容(4)">${services.content_four}</textarea>
        </div>

        <div class="form-group col-lg-6">
          <label>服务标题(5)</label>
          <input type="text" name="title_five" value="${services.title_five}" class="form-control" placeholder="标题(5)">
        </div>

        <div class="form-group col-lg-6">
          <label>内容(5)</label>
          <textarea class="form-control" name="content_five" rows="3" placeholder="文本段落内容(5)">${services.content_five}</textarea>
        </div>

        <div class="form-group col-lg-6">
          <label>服务标题(6)</label>
          <input type="text" name="title_six" value="${services.title_six}" class="form-control" placeholder="标题(6)">
        </div>

        <div class="form-group col-lg-6">
          <label>内容(6)</label>
          <textarea class="form-control" name="content_six" rows="3" placeholder="文本段落内容(6)">${services.content_six}</textarea>
        </div>

        <div class="form-group col-lg-6">
          <label>服务标题(7)</label>
          <input type="text" name="title_seven" value="${services.title_seven}" class="form-control" placeholder="标题(7)">
        </div>

        <div class="form-group col-lg-6">
          <label>内容(7)</label>
          <textarea class="form-control" name="content_seven" rows="3" placeholder="文本段落内容(7)">${services.content_seven}</textarea>
        </div>

        <div class="form-group col-lg-6">
          <label>服务标题(8)</label>
          <input type="text" name="title_eight" value="${services.title_eight}" class="form-control" placeholder="标题(8)">
        </div>

        <div class="form-group col-lg-6">
          <label>内容(8)</label>
          <textarea class="form-control" name="content_eight" rows="3" placeholder="文本段落内容(8)">${services.content_eight}</textarea>
        </div>

        <input type="submit" class="btn btn-primary" value="提交保存"/>

      </form>
    </div>
    <!-- /.box-body -->
  </div>
<jsp:include  page="common/footer.jsp"/>
