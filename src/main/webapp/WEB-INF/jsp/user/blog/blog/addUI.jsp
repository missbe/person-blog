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
      <h3 class="box-title"><c:if test="${blog!=null}">修改文章</c:if><c:if test="${blog==null}">添加文章</c:if></h3>
    </div>
    <!-- /.box-header -->
    <div class="box-body">
      <form role="form">
        <!-- text input -->
        <div class="form-group">
          <input type="hidden" id="id" value="${blog.id}" class="form-control" placeholder="英文ID">
        </div>
        <div class="form-group col-md-6">
            <label>所属菜单</label>
            <select id="menu" class="form-control select2" style="width: 100%;" onchange="getSections(this);">
              <c:forEach items="${menus}" var="menu">
                    <option value="${menu.id}">${menu.name}</option>
              </c:forEach>
            </select>
        </div>
        <div class="form-group col-md-6">
            <label>所属栏目</label>
            <select id="section" class="form-control select2" style="width: 100%;">
              <c:forEach items="${sections}" var="section">
                    <option value="${section.id}">${section.description}</option>
              </c:forEach>
            </select>
        </div>
        <div class="form-group">
          <label>文章标题</label>
          <input type="text" id="title" value="${blog.title}" class="form-control" placeholder="文章标题">
        </div>
        <div class="form-group">
          <label>文章内容</label>
          <script id="content" type="text/plain" style="width:666px;height:500px;">${blog.content}</script>
        </div>
        <input id="blogSubmitBtn" onclick="blogSubmit(this);" class="btn btn-primary" value="提交保存"/>

      </form>
    </div>
    <!-- /.box-body -->
  </div>
<jsp:include  page="../../../admin/common/footer.jsp"/>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>static/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="<%=basePath%>static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script>
$(function () {
    $(".select2").select2();
});
var ue;
function blogSubmit(dom){
    var data = {
        id : $('#id').val(),
        title : $('#title').val(),
        content : ue.getContent(),
        menu : $('#menu').val(),
        section : $('#section').val()
    }
    submit(data, "user/blog/blog/add", dom);
}
function getSections(dom){
    var data = {
        id : $('#menu').val()
    }
    getAjaxData(data, "user/blog/section/listByMenu", dom, function(result){
        if(result.success){
            $('#section').empty();
            for(var i in result.result){
                $('#section').append("<option value='"+result.result[i].id+"'>"+result.result[i].description+"</option>");
            }
        }
    });
}
$(document).ready(function() {
	//先删除编辑器
	UE.delEditor('content');
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	ue = UE.getEditor('content');
});
</script>