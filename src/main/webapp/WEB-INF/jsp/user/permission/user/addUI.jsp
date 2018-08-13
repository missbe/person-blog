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
      <h3 class="box-title">
        <c:if test="${user!=null}">修改角色</c:if><c:if test="${user==null}">添加角色</c:if></h3>
    </div>
    <!-- /.box-header -->
    <div class="box-body">
      <form role="form">
        <!-- text input -->
        <div class="form-group">
          <input type="hidden" id="id" value="${user.id}" class="form-control" placeholder="帐户名(英文)">
        </div>
        <div class="form-group">
          <label>帐户名(英文)</label>
          <input type="text" id="name" value="${user.account}" class="form-control" placeholder="帐户名(英文)">
        </div>

        <div class="form-group">
          <label>帐户密码</label>
          <input type="text" id="password" value="${user.password}" class="form-control" placeholder="帐户密码">
        </div>


        <div class="form-group">
          <label for="roles">分配角色</label>

          <select  id="roles" class="selectpicker" data-width="55%" data-max-options="4" multiple data-live-search="true">
            <c:forEach var="role" items="${roles}">
              <option value="${role.id}" data-subtext="${role.description}">${role.name}</option>
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
        password : $('#password').val(),
      roles:$('#roles').val()
    }
    submit(data, "user/users/users/add", dom);
}
$(document).ready(function() {
  $('#permission').selectpicker({
    style: 'btn-success',
    size: 8
  });

});
</script>