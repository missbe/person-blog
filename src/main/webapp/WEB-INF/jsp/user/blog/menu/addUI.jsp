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
      <h3 class="box-title"><c:if test="${menu!=null}">修改菜单</c:if><c:if test="${menu==null}">添加菜单</c:if></h3>
    </div>
    <!-- /.box-header -->
    <div class="box-body">
      <form role="form">
        <!-- text input -->
        <div class="form-group">
          <input type="hidden" id="id" value="${menu.id}" class="form-control" placeholder="英文ID">
        </div>
        <div class="form-group">
          <label>菜单名字</label>
          <input type="text" id="name" value="${menu.name}" class="form-control" placeholder="菜单名字">
        </div>
        <div class="form-group">
          <label>菜单简介</label>
          <input type="text" id="content" value="${menu.content}" class="form-control" placeholder="菜单简介">
        </div>
        <div class="form-group">
          <label>菜单英文</label>
          <input type="text" id="englishName" value="${menu.englishName}" class="form-control" placeholder="菜单英文">
        </div>
        <input id="menuSubmitBtn" onclick="menuSubmit(this);" class="btn btn-primary" value="提交保存"/>

      </form>
    </div>
    <!-- /.box-body -->
  </div>
<jsp:include  page="../../../admin/common/footer.jsp"/>
<script>
function menuSubmit(dom){
    var data = {
        id : $('#id').val(),
        englishName : $('#englishName').val(),
        name : $('#name').val(),
        content : $('#content').val()
    }
    submit(data, "user/blog/menu/add", dom);
}
</script>