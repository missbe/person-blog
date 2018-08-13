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
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="<%=basePath%>static/adminLte/bootstrap/select/css/bootstrap-select.min.css">
<div class="box box-warning">
    <div class="box-header with-border">
      <h3 class="box-title"><c:if test="${roles!=null}">修改角色</c:if><c:if test="${roles==null}">添加角色</c:if></h3>
    </div>
    <!-- /.box-header -->
    <div class="box-body">
      <form role="form">
        <!-- text input -->
        <div class="form-group">
          <input type="hidden" id="id" value="${roles.id}" class="form-control" placeholder="英文ID">
        </div>
        <div class="form-group">
          <label>角色名称</label>
          <input type="text" id="name" value="${roles.name}" class="form-control" placeholder="角色名称">
        </div>
        <div class="form-group">
          <label>角色描述</label>
          <input type="text" id="content" value="${roles.description}" class="form-control" placeholder="角色描述">
        </div>
        <div class="form-group">
          <label for="permission">分配权限</label>

          <select  id="permission" class="selectpicker" data-width="55%" data-max-options="4" multiple data-live-search="true">
            <c:forEach var="permission" items="${permissions}">
              <option value="${permission.id}" data-subtext="${permission.permission}">${permission.name}</option>
            </c:forEach>
          </select>
        </div>

        <input id="menuSubmitBtn" onclick="menuSubmit(this);" class="btn btn-primary" value="提交保存"/>

      </form>
    </div>
    <!-- /.box-body -->
  </div>
<jsp:include  page="../../../admin/common/footer.jsp"/>
!-- Latest compiled and minified JavaScript -->
<script src="<%=basePath%>static/adminLte/bootstrap/select/js/bootstrap-select.min.js"></script>

<!-- (Optional) Latest compiled and minified JavaScript translation files -->
<script src="<%=basePath%>static/adminLte/bootstrap/select/js/i18n/defaults-*.min.js"></script>

<script>
function menuSubmit(dom){
    var data = {
        id : $('#id').val(),
        name : $('#name').val(),
        content : $('#content').val(),
      permissions:$('#permission').val()
    }
    submit(data, "user/roles/roles/add", dom);
}
$(document).ready(function() {
  $('#permission').selectpicker({
    style: 'btn-success',
    size: 8
  });

});
</script>