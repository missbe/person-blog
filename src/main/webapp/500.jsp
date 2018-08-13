<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/30 0030
  Time: 下午 5:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var="basePath" value="<%=basePath %>" />
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>系统内部错误</title>
    <link rel="stylesheet" href="${basePath}static/weui/weui.min.css"/>
    <link rel="stylesheet" href="${basePath}static/weui/example.css"/>
</head>
<body>
<div class="container" id="container">
    <div class="msg">
        <div class="weui_msg">
            <div class="weui_icon_area">
                <img style="width:30%;" src="${basePath}static/images/weixin.jpg"></img></div>
            <div class="weui_text_area">
                <h2 class="weui_msg_title">
                    系统内部错误,可以通过以下途径反馈.扫描二维码关注<br>也可搜索公众号：依然慢节奏</h2>
            </div>
            <div class="weui_opr_area">
                <p class="weui_btn_area">
                    <a href="${basePath}" class="weui_btn weui_btn_primary">前往我们的首页</a>
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>
