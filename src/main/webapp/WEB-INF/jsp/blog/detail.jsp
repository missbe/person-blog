<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="cn.missbe.missbe_www.util.DateUtil" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//    System.out.println(request.getLocalName()+"_>"+request.getServerName());
%>
<!doctype html>
<html>
<head>
<meta charset="gb2312">
<title>${nowBlog.title}-${blogInfo.realName}</title>
<meta name="keywords" content="${blogInfo.realName},${section.description},个人博客,Missbe" />
<meta name="description" content="${blogInfo.realName}的博客,${section.description},Missbe" />
<link href="<%=basePath %>static/blog/css/base.css" rel="stylesheet">
<link href="<%=basePath %>static/blog/css/learn.css" rel="stylesheet">
<link href="<%=basePath %>static/blog/css/new.css" rel="stylesheet">
<link href="<%=basePath %>static/blog/css/index.css" rel="stylesheet">

<!-- 引入其他文件 -->
<link href="<%=basePath%>static/web/css/bootstrap.min.css"   rel="stylesheet">
<link href="<%=basePath %>static/web/css/master.css"  rel="stylesheet">
<link href="<%=basePath %>static/web/css/model.css"   rel="stylesheet">
<link rel="stylesheet"  href="<%=basePath %>static/adminLte/font-awesome.min.css">
<link rel="stylesheet"  href="<%=basePath %>static/adminLte/ionicons.min.css">
<link rel="stylesheet"  href="<%=basePath %>static/adminLte/dist/css/AdminLTE.min.css">
<link rel="stylesheet"  href="<%=basePath %>static/adminLte/dist/css/skins/_all-skins.min.css">
<link rel="stylesheet"  href="<%=basePath %>static/css/messenger.css">
 <link rel="stylesheet" href="<%=basePath %>static/css/messenger-theme-flat.css">

<!--[if lt IE 9]>
<script src="<%=basePath %>static/js/modernizr.js"></script>
<![endif]-->
</head>
<body>
<header>
<div style="width: 260px; height: 60px; margin: 10px 0 0 0; position: absolute; display: block;background: url(<%=basePath %>${blogInfo.logo}) no-repeat;background-size: 260px 60px;"><a href="<%=basePath %>blog/${account}.html"></a></div>
  <nav class="topnav" id="topnav">
      <a href="<%=basePath %>blog/${account}.html"><span>首页</span><span class="en">Protal</span></a>
      <a href="<%=basePath %>blog/${account}/about.html"><span>关于我</span><span class="en">About</span></a>
      <c:forEach items="${menus}" var="m">
        <a <c:if test='${menu.id == m.id}'>id="topnav_current"</c:if> href="<%=basePath %>blog/${account}/menu/${m.id}.html"><span>${m.name}</span><span class="en">${m.englishName}</span></a>
      </c:forEach>
  </nav>
</header>
<article class="blogs">
<h1 class="t_nav"><span>${section.content} </span><a href="<%=basePath %>blog/${account}.html" class="n1">网站首页</a><a href="<%=basePath %>blog/${account}/section/${section.id}.html" class="n2">${section.description}</a></h1>
<div class="newblog left">
 <div class="index_about">
    <h2 class="c_titile">${nowBlog.title}</h2>
    <p class="box_c"><span class="d_time">发布时间：${nowBlog.date}</span><span>编辑：${nowBlog.author}</span><span>阅读：${nowBlog.click}</span></p>
    <ul class="infos">
      ${nowBlog.content}
    </ul>
    <div class="keybq">
    <p><span>关键字词</span>：${nowBlog.sectionName}</p>
    </div>
    <div class="ad"> </div>
    <div class="nextinfo">
      <p>上一篇：<a href="<%=basePath %>blog/${account}/detail/${lastBlog.id}.html">${lastBlog.title}</a></p>
      <p>下一篇：<a href="<%=basePath %>blog/${account}/detail/${nextBlog.id}.html">${nextBlog.title}</a></p>
    </div>
  </div>

</div>

<aside class="right">
   <script type="text/javascript">(function(){document.write(unescape('%3Cdiv id="bdcs"%3E%3C/div%3E'));var bdcs = document.createElement('script');bdcs.type = 'text/javascript';bdcs.async = true;bdcs.src = 'http://znsv.baidu.com/customer_search/api/js?sid=17794598472099483822' + '&plate_url=' + encodeURIComponent(window.location.href) + '&t=' + Math.ceil(new Date()/3600000);var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(bdcs, s);})();</script>
   <div class="rnav">
      <h2>栏目导航</h2>
      <ul>
      <c:forEach items="${menuSections}" var="section" varStatus="status">
       <li class="rnav${ (status.index%5)+1}"><a href="<%=basePath %>blog/${account}/section/${section.id}.html">${section.description}</a></li>
      </c:forEach>
     </ul>
    </div>
   <div class="rnavs">
         <h2>栏目导航</h2>
         <ul>
         <c:forEach items="${menuSections}" var="section">
          <li><a href="<%=basePath %>blog/${account}/section/${section.id}.html">${section.description}</a></li>
         </c:forEach>
        </ul>
       </div>
   <div class="news">
<h3>
      <p>最新<span>文章</span></p>
    </h3>
    <ul class="rank">
      <c:forEach items="${newBlogs}" var="blog">
        <li><a href="<%=basePath %>blog/${account}/detail/${blog.id}.html" title="${blog.title}" target="_blank">${blog.title}</a></li>
      </c:forEach>
    </ul>
    <h3 class="ph">
      <p>点击<span>排行</span></p>
    </h3>
    <ul class="paih">
      <c:forEach items="${hotBlogs}" var="blog">
        <li><a href="<%=basePath %>blog/${account}/detail/${blog.id}.html" title="${blog.title}" target="_blank">${blog.title}</a></li>
      </c:forEach>
    </ul>
    <div class="bdsharebuttonbox">
        <a href="#" class="bds_more" data-cmd="more"></a>
        <a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间">

        </a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
        <a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a>
        <a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网">

        </a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
    </div>
    <script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"1","bdSize":"32"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
    <div class="guanzhu">
        扫描二维码关注<span>${blogInfo.weixin}</span>微信账号
    </div>
        <a href="/" class="weixin" style="background: url(<%=basePath %>${blogInfo.weixinQrcode}) no-repeat;background-size:250px 274px;"> </a>
    </div>
</aside>

<%--<div style="height: 10px;clear: both;" />--%>
<div class="container ">
    <div class="col-md-7 clearfix">
        <script id="content" type="text/plain" style="height:200px;"></script>
        <button type="button" class="btn btn-primary btn-lg" onclick="return modal(this);">
            提交发表评论
        </button>
    </div>
    <div class="col-md-7 clearfix">
        <!-- tab-pane -->
        <div class="active tab-pane" id="activity">
            <!-- Post -->
            <div class="post clearfix" id="themes">
                <c:forEach var="theme" items="${themes}" varStatus="index">
                    <div style="margin:30px 0px;">
                        <!---hidden start-->
                        <input type="hidden" value="${theme.blog.id}" id="topic${index.count}"/>
                        <input type="hidden" value="${theme.id}" id="theme${index.count}"/>
                        <!---hidden end-->
                        <div class="user-block" style="margin:5px 0px;" >
                            <c:if test="${theme.avator==null || empty theme.avator}">
                                <img class="img-circle img-bordered-sm" src="<%=basePath%>static/adminLte/dist/img/user7-128x128.jpg" alt="User Image">
                            </c:if>
                            <c:if test="${theme.avator!=null && !empty theme.avator}">
                                <img class="img-circle img-bordered-sm" src="<%=basePath%>${theme.avator}" alt="User Image">
                            </c:if>
                            <c:if test="${theme.publisher!=null}">
                        <span class="username">
                          <a href="#">${theme.publisher}</a>
                          <a href="#" class="pull-right btn-box-tool">${theme.blog.sectionName}</a>
                        </span>
                            </c:if>
                            <span class="description">${theme.time}</span>
                        </div>
                        <!-- /.user-block -->
                        <div style="margin:5px 0px;">
                                ${theme.content}
                        </div>
                        <div id="comments${index.count}">
                            <c:forEach var="comment" items="${comments[theme.idStr]}">
                                <div class="user-block" style="margin:5px 50px">
                                    <c:if test="${comment.avator==null || empty comment.avator}">
                                        <img class="img-circle img-bordered-sm" src="<%=basePath%>static/adminLte/dist/img/user7-128x128.jpg" alt="User Image">
                                    </c:if>
                                    <c:if test="${comment.avator!=null && !empty comment.avator}">
                                        <img class="img-circle img-bordered-sm" src="<%=basePath%>${comment.avator}" alt="User Image">
                                    </c:if>
                                    <c:if test="${comment.publisher!=null}">
                           <span class="username">
                             <a href="#">${comment.publisher}</a>
                             <a href="#" class="pull-right btn-box-tool">${theme.blog.sectionName}</a>
                           </span>
                                    </c:if>
                                    <span class="description">${comment.time}</span>
                                </div>
                                <!-- /.user-block -->
                                <div style="margin:5px 100px;">
                                        ${comment.content}
                                </div>

                            </c:forEach>
                        </div>

                        <ul class="list-inline">
                            <li><a href="#" class="link-black text-sm"><i class="fa fa-thumbs-o-up margin-r-5"></i> Like</a>
                            </li>
                            <li class="pull-right">
                                <a href="#commit" class="link-black text-sm" onclick="toggle($('#commit${index.count}'));">
                                    <i class="fa fa-comments-o margin-r-5"></i>回复/隐藏</a>
                            </li>
                            <li class="pull-right">
                                <a href="#commit" class="link-black text-sm" onclick="ajax_comment_all($('#theme${index.count}'),$('#comments${index.count}'),this);">
                                    <i class="fa fa-plus-square margin-r-5"></i>查看所有回复</a>
                            </li>
                        </ul>

                        <form class="form-horizontal" class="commit" id="commit${index.count}" style="display: none;">
                            <div class="form-group margin-bottom-none">
                                <div class="col-sm-9">
                                    <input type="text" class="form-control input-sm" placeholder="Response" id="content${index.count}">
                                </div>
                                <div class="col-sm-3">
                                    <button type="button" onclick="ajax_comment_submit($('#theme${index.count}'),$('#content${index.count}'),$('#topic${index.count}'),$('#comments${index.count}'),this);"
                                            class="btn btn-danger pull-right btn-block btn-sm">回复</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </c:forEach>

            </div>
            <!-- /.post -->

        </div>
        <!-- /.tab-pane -->
        <!---paginator-->
        <div id="example" style="text-align: right">
            <ul id="pageLimit"></ul>
        </div>
        <!---/.paginator-->
    </div>
</div>

    <!-- Button trigger modal -->
    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button"  class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel" >验证通过发表评论</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group has-feedback input-group">
                        <input name="verCode" id="verCode" type="text" maxlength="4" class="form-control" placeholder="请正确输入验证码">
                        <div class="input-group-btn">
                            <img id="verImg" src="<%=basePath%>user/themes/themes/captcha" onclick="this.src='<%=basePath%>user/themes/themes/captcha?d='+new Date().getTime()"/>
                        </div>
                    </div>
                    <div>
                        <span id="verMessage" class="text-info">点击图片可以更换验证码</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消发表评论</button>
                    <button type="button" class="btn btn-primary" onclick="captcha_ajax();">发表评论</button>
                </div>
            </div>
        </div>
    </div>
<script src="<%=basePath%>static/web/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>static/web/js/bootstrap.min.js"></script>

<!-- FastClick -->
<script src="<%=basePath%>static/adminLte/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="<%=basePath%>static/adminLte/dist/js/app.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="<%=basePath%>static/adminLte/dist/js/demo.js"></script>
<!-- iCheck -->
<script src="<%=basePath%>static/adminLte/plugins/iCheck/icheck.min.js"></script>
<!--messager-->
<script src="<%=basePath%>static/js/messenger.min.js"></script>
<script src="<%=basePath%>static/js/messenger-theme-flat.js"></script>
<script src="<%=basePath%>static/js/common_backup.min.js"></script>
<!-- Paginator -->
<script src="<%=basePath%>static/paginator/js/bootstrap-paginator.min.js"></script>
<!----加载分页插件-->
<script type='text/javascript'>
    function initPaginator(count,parent){
        var options = {
            currentPage: 1,
            totalPages:count ,
            size:"normal",
            alignment:"right",
            bootstrapMajorVersion: 3,
            numberOfPages:4,
            itemTexts: function (type, page, current) {
                switch (type) {
                    case "first": return "首页";
                    case "prev": return "上一页";
                    case "next": return "下一页";
                    case "last": return "末页";
                    case "page": return page;
                }
            },//点击事件，用于通过Ajax来刷新整个list列表
            onPageClicked: function (event, originalEvent, type, page) {
                ajax_new_page(page,parent);
            }
        };
        $('#pageLimit').bootstrapPaginator(options);
    }
    initPaginator(${count},$('#themes'));
</script>
<script type="text/javascript" charset="utf-8"
        src="<%=basePath%>static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
        src="<%=basePath%>static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8"
        src="<%=basePath%>static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
    ///全局变量
//    var comment='blog'+74;
    var basePath='<%=basePath%>';
    var ue;
    /////页面加载初始化
    $(function () {
//    console.log("--------init--------");
        //先删除编辑器
        UE.delEditor('content');
        //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
        ue = UE.getEditor('content',{
            toolbars:
                    [["bold","italic","undo","redo",
                        'customstyle', 'paragraph', 'fontfamily', 'fontsize',
                        'directionalityltr', 'directionalityrtl', 'indent','imagenone',
                        'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                        'imageleft', 'imageright', 'imagecenter', 'emotion','preview', 'searchreplace', 'drafts',
                        'horizontal', 'date', 'time', 'spechars', 'snapscreen', '|','help'

                    ]],
            autoHeightEnabled: true,
            autoFloatEnabled: true
        });

    });
    ////模态框加载函数
    function  modal(dom) {
        ///1 代表主题发表 2 代表评论回复发表
        if(!ue.hasContents()){
            alert_msg("请输入评论内容后提交.....");
            return flase;
        }else{
            $('#verImg').click();
            $('#myModal').modal('show');
        }

    }
    ///发表主题
    ////加载Session里的Student
    function ajax_publish_submit(dom){

        var data = {
             content:ue.getContent(),
             user:'${account}',
             blog:'${blog_id}'
        };
        submit(data,"user/themes/themes/add",dom);
    }
</script>
<script type="text/javascript">
    function  captcha_ajax() {
        data={
            verify:$('#verCode').val()
        };
        $.ajax({
                url: basePath+"user/themes/themes/verify?t=" + new Date(),
                dataType: "json",
                data:data,
                success: function (result) {
                    var msgt;
                    if (result.success) {
                        ajax_publish_submit();
                        $('#myModal').modal('hide');
                        $('#verMessage').text('点击图片可以更换验证码');
                    }else{
                        $('#myModal').modal('show');
                       $('#verImg').click();
                        $('#verMessage').text('验证码错误，请重新输入.');
                    }
                    document.getElementById('verCode').value="";
                },
                error: function () {
                    alert_msg("出现错误,检查网络或稍后再试....",false);
                }
        });
    }
</script>
<script>
    ///回复/隐藏
    function toggle(dom){
        dom.toggle(2);
    }
    ////评论提交
    function ajax_comment_submit(theme,content,topic,parent,dom){
        if(content=='' || content==null || content==undefined){
            alert_msg("请输入评论内容后发表..");
            return false;
        }
        var data = {
            content:content.val(),
            user:'${account}',
            theme:theme.val()
        };
        submit(data,"user/comments/comments/add",dom);
    }
    ///加载所有评论
    function ajax_comment_all(theme,parent,dom){
//    console.log(topic.val());
        var data = {
            theme_id : theme.val()
        }
//        console.log("--父元素--"+parent);
        submit(data,"user/comments/comments/commentAll",dom,parent,commentAll);
    }
    ////加载所有评论的回调函数
    var commentAll=function callback_ajax_commentall(result,parent) {
//        alert_msg("--回调函数，进行回调--");
//        console.log("结果集--"+result);
//        console.log("--父元素222--"+parent);
        var msgt;
        if (result.success) {
            parent.empty();
            var htmls='<span></span>';
            $.each(result.result,function (index,element) {
                var html='<div class="user-block" style="margin:5px 50px">'+
                        '<img class="img-circle img-bordered-sm" src="<%=basePath%>'+element.avator+'\" alt="User Image">'+
                        '<span class="username">'+
                        '<a href="#">'+element.publisher+'</a>'+
                        '<a href="#" class="pull-right btn-box-tool">'+element.publisher+'</a>'+
                        '</span>'+
                        '<span class="description">'+element.time+'</span>'+
                        '</div>'+
                        '<div style="margin:5px 100px;">'+
                        element.content+
                        '</div><br/>';
                htmls = htmls+html;
            });
            ///重新生成html
            parent.html(htmls);
        }else{
            msgt=result.result;
            alert_msg(msgt,false);
        }
//        console.log(htmls);
//        console.log(msgt);
    }
    ////ajax加载新的页面
    function ajax_new_page(page,parent){
        parent.empty();///预先清空
        var data = {
            page : page,
            blog_id:'${blog_id}'
        };
        $.ajax({
            url: basePath+"user/themes/themes/reload?t=" + new Date(),
            dataType: "json",
            data:data,
            success: function (result) {
                var msgt;
                if (result.success) {
                    var comments=result.result.comments;
                    var themes=result.result.themes;
                    ///重新渲染生成html
                    draw_ajax_page(themes,comments,parent);
                }else{
                    msgt=result.result;
                    alert_msg(msgt,false);
                }
            },
            error: function () {
                alert_msg("出现错误,检查网络或稍后再试",false);
            }
        });
    }
    ////渲染页面
    var draw_ajax_page=function  draw(themes,comments,parent){
//        console.log("themes->"+themes+"->comment:"+comments);
        var commentHTML='';
        $.each(themes,function (index,element) {
            $.each(comments,function (item,comment){
                console.log(element.id+'---->'+comments[element.id]+'--->'+comment);
                commentHTML='';
                if(comments[element.id]!=undefined){
                    $.each(comments[element.id],function (i,data){
//                        console.log(i+"-->"+data);
                        commentHTML+= ' <!---评论开始-->'+
                                '<div class="user-block" style="margin:5px 50px"> '+
                                '<img class="img-circle img-bordered-sm" src="<%=basePath%>'+data.avator+'" alt="User Image">'+
                                '<span class="username">'+
                                '<a href="#">'+data.publisher+'</a>'+
                                '<a href="#" class="pull-right btn-box-tool">'+data.time+'</a>'+
                                '</span> '+
                                '<span class="description">'+data.time+'</span>'+
                                '</div>'+
                                '<!-- /.user-block -->'+
                                '<div style="margin:5px 100px;">'+ data.content+
                                '</div>'+
                                '<!---comment end-->';


                    });//end inner each
                }

            });///end outer eac
//            console.log('CommmentHtml:'+commentHTML);
            var HTML='<!---主题开始-->'+
                    '<div style="margin:30px 0px;">'+
                    '<!---hidden start-->'+
                    '<input type="hidden" value="'+element.id+'" id="theme'+index+'"/>'+
                    '<!---hidden end-->'+
                    '<div class="user-block" style="margin:5px 0px;" >'+
                    '<img class="img-circle img-bordered-sm" src="<%=basePath%>'+element.avator+'" alt="User Image">'+
                    '<span class="username">'+
                    '<a href="#">'+element.publisher+'</a>'+
                    '<a href="#" class="pull-right btn-box-tool">'+element.blog.sectionName+'</a>'+
                    '</span>'+
                    '<span class="description">'+element.time+'</span>'+
                    '</div>'+
                    '<!-- /.user-block -->'+
                    '<div style="margin:5px 0px;">'+
                     element.content+
                    '</div>'+
                    '<div id="comments'+index+'">'+
                    commentHTML+
                    '</div>'+
                    '<ul class="list-inline">'+
                    '<li><a href="#" class="link-black text-sm"><i class="fa fa-thumbs-o-up margin-r-5"></i> Like</a>'+
                    '</li>'+
                    '<li class="pull-right">'+
                    '<a href="#commit" class="link-black text-sm" onclick="toggle($(\'#commit'+index+'\'));">'+
                    '<i class="fa fa-comments-o margin-r-5"></i>回复/隐藏</a>'+
                    '</li>'+
                    '<li class="pull-right">'+
                    '<a href="#commit" class="link-black text-sm" onclick="ajax_comment_all($(\'#theme'+index+'\'),$(\'#comments'+index+'\'),this);">'+
                    '<i class="fa fa-plus-square margin-r-5"></i>查看所有回复</a>'+
                    '</li>'+
                    '</ul>'+

                    '<form class="form-horizontal" class="commit" id="commit'+index+'" style="display: none;">'+
                    '<div class="form-group margin-bottom-none">'+
                    '<div class="col-sm-9">'+
                    '<input type="text" class="form-control input-sm" placeholder="Response" id="content'+index+'">'+
                    '</div>'+
                    '<div class="col-sm-3">'+
                    '<button type="button" onclick="ajax_comment_submit($(\'#theme'+index+'\'),$(\'#content'+index+'\'),$(\'#topic'+index+'\'),$(\'#comments'+index+'\'),this);"'+
                    'class="btn btn-danger pull-right btn-block btn-sm">回复</button>'+
                    '</div> </div> </form></div>';
//            console.log('HTML='+HTML);
            parent.append(HTML);
        });////end  max outer each
    }

</script>
<!-- 引入结束 -->
</article>
<footer>
    <p>Copyright © 2016 <a href="<%=basePath%>" target="_blank">四川理工学院汇南4栋612</a>&nbsp;<a href="http://www.miitbeian.gov.cn/" target="_blank">蜀ICP备16003487号-2</a>&nbsp;&nbsp;<a target="_blank" href="<%=basePath%>sitemap.xml">网站地图</a></p>
</footer>
<script src="<%=basePath %>static/blog/js/silder.js"></script>
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?b6fe8a81619ca030ba9cc2445b6adfc1";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>

</body>
</html>