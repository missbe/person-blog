<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include  page="../../../admin/common/header.jsp"/>
<div class="box box-warning">
    <div class="box-header with-border">
      <h3 class="box-title"><c:if test="${permission!=null}">修改权限</c:if><c:if test="${permission==null}">添加权限</c:if></h3>
    </div>
    <!-- /.box-header -->
    <div class="box-body">
      <form role="form">
        <!-- text input -->
        <div class="form-group">
          <input type="hidden" id="id" value="${permission.id}" class="form-control" placeholder="英文ID">
        </div>
        <div class="form-group">
          <label>权限名称</label>
          <input type="text" id="name" value="${permission.name}" class="form-control" placeholder="权限名称">
        </div>
        <div class="form-group">
          <label>URL路径</label>
          <input type="text" id="content" value="${permission.permission}" class="form-control" placeholder="URL路径">
        </div>
        <input id="sectionSubmitBtn" onclick="sectionSubmit(this);" class="btn btn-primary" value="提交保存"/>

      </form>
    </div>
    <!-- /.box-body -->
  </div>
<jsp:include  page="../../../admin/common/footer.jsp"/>
<script>
$(function () {
    $(".select2").select2();
});
function sectionSubmit(dom){
    var data = {
        id : $('#id').val(),
        name : $('#name').val(),
        content : $('#content').val()
    }
    submit(data, "user/permission/permission/add", dom);
}
</script>