<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="stylesheet" href="<%=basePath %>static/css/jquery.fileupload.css">
<jsp:include  page="../admin/common/header.jsp"/>
<div class="col-md-8">
  <div class="box box-warning">
    <div class="box-header with-border">
      <h3 class="box-title">修改博客个人信息</h3>
    </div>
    <!-- /.box-header -->
    <div class="box-body">
      <form role="form">
        <!-- text input -->
        <div class="form-group" style="height:90px;">
          <label>logo(260 × 60)</label>
          <img id="logoImg" style="width:260px;height:60px;" src="<%=basePath%>${blog.logo}" />
          <span class="btn btn-success fileinput-button">
              <i class="glyphicon glyphicon-plus"></i>
              <span id="logofileSpan">点击上传</span>
              <input id="logofile" type="file">
          </span>
          <input type="hidden" id="logo" value="${blog.logo}" class="form-control" placeholder="logo">
        </div>
        <div class="form-group">
          <label>网名（英文）</label>
          <input type="text" id="englishName" value="${blog.englishName}" class="form-control" placeholder="网名（英文）">
        </div>
        <div class="form-group">
          <label>网名（中文）</label>
          <input type="text" id="name" value="${blog.name}" class="form-control" placeholder="网名（中文）">
        </div>
        <div class="form-group">
          <label>姓名</label>
          <input type="text" id="realName" value="${blog.realName}" class="form-control" placeholder="姓名">
        </div>
        <div class="form-group">
          <label>出生日期</label>
          <input type="text" id="birthDate" value="${blog.birthDate}" class="form-control" placeholder="出生日期">
        </div>
        <div class="form-group">
          <label>籍贯</label>
          <input type="text" id="birthAddress" value="${blog.birthAddress}" class="form-control" placeholder="籍贯">
        </div>
        <div class="form-group">
          <label>现居地</label>
          <input type="text" id="nowAddress" value="${blog.nowAddress}" class="form-control" placeholder="现居地">
        </div>
        <div class="form-group">
          <label>职业</label>
          <input type="text" id="job" value="${blog.job}" class="form-control" placeholder="职业">
        </div>
        <div class="form-group">
          <label>副业</label>
          <input type="text" id="secondJob" value="${blog.secondJob}" class="form-control" placeholder="副业">
        </div>
        <div class="form-group">
          <label>喜欢的书</label>
          <input type="text" id="books" value="${blog.books}" class="form-control" placeholder="喜欢的书">
        </div>
        <div class="form-group">
          <label>喜欢的音乐</label>
          <input type="text" id="music" value="${blog.music}" class="form-control" placeholder="喜欢的音乐">
        </div>
        <div class="form-group">
          <label>banner内容(空格即换行)</label>
          <input type="text" id="bannerText" value="${blog.bannerText}" class="form-control" placeholder="banner内容">
        </div>
        <div class="form-group" style="height:180px;">
          <label>banner背景图(1900 × 265)</label>
          <img id="bannerImgImg" style="width:100%;height:133px;" src="<%=basePath%>${blog.bannerImg}" />
            <span class="btn btn-success fileinput-button">
                <i class="glyphicon glyphicon-plus"></i>
                <span id="bannerImgfileSpan">点击上传</span>
                <input id="bannerImgfile" type="file">
            </span>
          <input type="hidden" id="bannerImg" value="${blog.bannerImg}" class="form-control" placeholder="banner背景图">
        </div>
        <div class="form-group">
          <label>微信</label>
          <input type="text" id="weixin" value="${blog.weixin}" class="form-control" placeholder="微信">
        </div>
        <div class="form-group" style="height:294px;">
          <label>微信二维码(250 × 274)</label>
          <img id="weixinQrcodeImg" style="width:250px;height:274px;" src="<%=basePath%>${blog.weixinQrcode}" />
          <span class="btn btn-success fileinput-button">
              <i class="glyphicon glyphicon-plus"></i>
              <span id="weixinQrcodefileSpan">点击上传</span>
              <input id="weixinQrcodefile" type="file">
          </span>
          <input type="hidden" id="weixinQrcode" value="${blog.weixinQrcode}" class="form-control" placeholder="微信">
        </div>
        <div class="form-group" style="height:345px;">
          <label>照片(182 × 325)</label>
          <img id="photoImg" style="width:182px;height:325px;" src="<%=basePath%>${blog.photo}" />
            <span class="btn btn-success fileinput-button">
                <i class="glyphicon glyphicon-plus"></i>
                <span id="photofileSpan">点击上传</span>
                <input id="photofile" type="file">
            </span>
          <input type="hidden" id="photo" value="${blog.photo}" class="form-control" placeholder="照片">
        </div>
        <input id="changeBlogInfoBtn" onclick="changeBlogInfo(this);" class="btn btn-primary" value="提交保存"/>
      </form>
    </div>
    <!-- /.box-body -->
  </div>
</div>
<div class="col-md-4">
  <div class="box box-warning">
    <div class="box-header with-border">
      <h3 class="box-title">修改账户信息(可只修改昵称)</h3>
    </div>
    <!-- /.box-header -->
    <div class="box-body">
      <form role="form" method="POST" action="<%=basePath %>user/profile">
        <!-- text input -->
        <div class="form-group">
          <label>昵称</label>
          <input type="text" id="nickname" value="${user.nickname}" class="form-control" placeholder="昵称">
        </div>
        <div class="form-group">
          <label>头像(140 x 140)</label>
          <div class="user-header">
              <img id="avatorImg" style="width:160px;height:160px;" src="<%=basePath%>${user.avator}" class="img-circle" alt="头像">
              <span class="btn btn-success fileinput-button">
                  <i class="glyphicon glyphicon-plus"></i>
                  <span id="avatorfileSpan">点击上传</span>
                  <input id="avatorfile" type="file">
              </span>
          </div>
          <input type="hidden" id="avator" value="${user.avator}" class="form-control" placeholder="头像">
        </div>
        <div class="form-group">
          <label>旧密码(不改密码勿填写)</label>
          <input type="password" id="oldpassword" class="form-control" placeholder="旧密码">
        </div>
        <div class="form-group">
          <label>新密码(不改密码勿填写)</label>
          <input type="password" id="password" class="form-control" placeholder="新密码">
        </div>
        <div class="form-group">
          <label>重复新密码(不改密码勿填写)</label>
          <input type="password" id="passwordrepeat" class="form-control" placeholder="重复新密码">
        </div>
        <input id="changeUserInfoBtn" onclick="changeUserInfo(this);" class="btn btn-primary" value="提交保存"/>
      </form>
    </div>
    <!-- /.box-body -->
  </div>
</div>
<jsp:include  page="../admin/common/footer.jsp"/>
<script src="<%=basePath %>static/js/jquery.fileupload.js"></script>
<script src="<%=basePath %>static/js/jquery.fileupload-process.js"></script>
<script>
function changeUserInfo(dom){
    var data = {
        nickname : $('#nickname').val(),
        password : $('#password').val(),
        oldpassword : $('#oldpassword').val(),
        avator : $('#avator').val(),
        passwordrepeat : $('#passwordrepeat').val()
    }
    submit(data, "user/profile", dom.id);
}
function changeBlogInfo(dom){
    var data = {
        englishName : $('#englishName').val(),
        name : $('#name').val(),
        avator : $('#avator').val(),
        realName : $('#realName').val(),
        birthAddress : $('#birthAddress').val(),
        job : $('#job').val(),
        secondJob : $('#secondJob').val(),
        books : $('#books').val(),
        bannerText : $('#bannerText').val(),
        bannerImg : $('#bannerImg').val(),
        music : $('#music').val(),
        weixinQrcode : $('#weixinQrcode').val(),
        weixin : $('#weixin').val(),
        photo : $('#photo').val(),
        logo : $('#logo').val(),
        nowAddress : $('#nowAddress').val(),
        birthDate : $('#birthDate').val()
    }
    submit(data, "user/blogProfile", dom.id);
}
$(document).ready(function() {
    var photofileName;
    $("#photofile").fileupload({
        url:"<%=basePath %>imageUpload"+"?timenow="+new Date().getTime(),
        dataType: 'json',
        maxNumberOfFiles : 1,
        autoUpload: true,
        done:function(e,data){
            if(data.result.success){
                $('#photo').val(data.result.result);
                $('#photoImg').attr("src",data.result.result);
            }else{
                $('#photofileSpan').text(data.result.result);
            }
        }
    }).on(
        'fileuploadadd',
        function(e, data) {
        $.each(data.files, function(index, file) {
            $('#photofileSpan').text(file.name+"进度：0%");
            photofileName=file.name;
        });
    }).on('fileuploadprogressall', function(e, data) {
        var progress = parseInt(data.loaded / data.total * 100, 10);
        $('#photofileSpan').text(photofileName+"进度："+progress + '%');
    });
    var weixinQrcodeName;
    $("#weixinQrcodefile").fileupload({
        url:"<%=basePath %>imageUpload"+"?timenow="+new Date().getTime(),
        dataType: 'json',
        maxNumberOfFiles : 1,
        autoUpload: true,
        done:function(e,data){
            if(data.result.success){
                $('#weixinQrcode').val(data.result.result);
                $('#weixinQrcodeImg').attr("src",data.result.result);
            }else{
                $('#weixinQrcodefileSpan').text(data.result.result);
            }
        }
    }).on(
        'fileuploadadd',
        function(e, data) {
        $.each(data.files, function(index, file) {
            $('#weixinQrcodefileSpan').text("进度：0%");
            weixinQrcodefileName=file.name;
        });
    }).on('fileuploadprogressall', function(e, data) {
        var progress = parseInt(data.loaded / data.total * 100, 10);
        $('#weixinQrcodefileSpan').text("进度："+progress + '%');
    });
    var bannerImgName;
    $("#bannerImgfile").fileupload({
        url:"<%=basePath %>imageUpload"+"?timenow="+new Date().getTime(),
        dataType: 'json',
        maxNumberOfFiles : 1,
        acceptFileTypes:  /(\.|\/)(apk)$/i,
        autoUpload: true,
        done:function(e,data){
            if(data.result.success){
                $('#bannerImg').val(data.result.result);
                $('#bannerImgImg').attr("src",data.result.result);
            }else{
                $('#bannerImgfileSpan').text(data.result.result);
            }
        }
    }).on(
        'fileuploadadd',
        function(e, data) {
        $.each(data.files, function(index, file) {
            $('#bannerImgfileSpan').text("进度：0%");
            bannerImgfileName=file.name;
        });
    }).on('fileuploadprogressall', function(e, data) {
        var progress = parseInt(data.loaded / data.total * 100, 10);
        $('#bannerImgfileSpan').text(bannerImgfileName+"进度："+progress + '%');
    });
    var avatorName;
    $("#avatorfile").fileupload({
        url:"<%=basePath %>imageUpload"+"?timenow="+new Date().getTime(),
        dataType: 'json',
        maxNumberOfFiles : 1,
        acceptFileTypes:  /(\.|\/)(apk)$/i,
        autoUpload: true,
        done:function(e,data){
            if(data.result.success){
                $('#avator').val(data.result.result);
                $('#avatorImg').attr("src",data.result.result);
            }else{
                $('#avatorfileSpan').text(data.result.result);
            }
        }
    }).on(
        'fileuploadadd',
        function(e, data) {
        $.each(data.files, function(index, file) {
            $('#avatorfileSpan').text(file.name+"进度：0%");
            avatorfileName=file.name;
        });
    }).on('fileuploadprogressall', function(e, data) {
        var progress = parseInt(data.loaded / data.total * 100, 10);
        $('#avatorfileSpan').text("进度："+progress + '%');
    });
    var logoName;
    $("#logofile").fileupload({
        url:"<%=basePath %>imageUpload"+"?timenow="+new Date().getTime(),
        dataType: 'json',
        maxNumberOfFiles : 1,
        acceptFileTypes:  /(\.|\/)(apk)$/i,
        autoUpload: true,
        done:function(e,data){
            if(data.result.success){
                $('#logo').val(data.result.result);
                $('#logoImg').attr("src",data.result.result);
            }else{
                $('#logofileSpan').text(data.result.result);
            }
        }
    }).on(
        'fileuploadadd',
        function(e, data) {
        $.each(data.files, function(index, file) {
            $('#logofileSpan').text(file.name+"进度：0%");
            logofileName=file.name;
        });
    }).on('fileuploadprogressall', function(e, data) {
        var progress = parseInt(data.loaded / data.total * 100, 10);
        $('#logofileSpan').text("进度："+progress + '%');
    });
});
$('#content-wrapper').css("height","1980px");
</script>
