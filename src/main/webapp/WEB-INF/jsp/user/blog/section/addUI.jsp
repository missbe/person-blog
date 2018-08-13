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
      <h3 class="box-title"><c:if test="${section!=null}">修改栏目</c:if><c:if test="${section==null}">添加栏目</c:if></h3>
    </div>
    <!-- /.box-header -->
    <div class="box-body">
      <form role="form">
        <!-- text input -->
        <div class="form-group">
          <input type="hidden" id="id" value="${section.id}" class="form-control" placeholder="英文ID">
        </div>
        <div class="form-group">
            <label>所属菜单</label>
            <select id="menu" class="form-control select2" style="width: 100%;">
              <c:forEach items="${menus}" var="menu">
                    <option value="${menu.id}">${menu.name}</option>
              </c:forEach>
            </select>
        </div>
        <div class="form-group">
          <label>栏目名字</label>
          <input type="text" id="description" value="${section.description}" class="form-control" placeholder="栏目名字">
        </div>
        <div class="form-group">
          <label>栏目简介</label>
          <input type="text" id="content" value="${section.content}" class="form-control" placeholder="栏目简介">
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
        description : $('#description').val(),
        content : $('#content').val(),
        menu : $('#menu').val()
    }
    submit(data, "user/blog/section/add", dom);
}
</script>