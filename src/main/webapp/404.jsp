<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="404错误" />
<meta name="description" content="404错误" />
<title>404</title>
<meta http-equiv="refresh" content="10;url=<%=basePath%>">
<!-- content="60，即60秒后返回主页，可根据需要修改或者删除这段代码 -->
<link href="<%=basePath%>static/404/404css/error.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="<%=basePath%>favicon.ico">
</head>
<body>
<!-- 代码 开始 -->
<div id="container">
    <img class="png" src="<%=basePath%>static/404/404images/404.png" /> 
    <img class="png msg" src="<%=basePath%>static/404/404images/404_msg.png" />
  <p><a href="<%=basePath%>" target="_top">
  <img class="png" src="<%=basePath%>static/404/404images/404_to_index.png" /></a> </p>
</div>
<div id="cloud" class="png"></div>
<!-- 代码 结束 -->
 
</body>
</html>