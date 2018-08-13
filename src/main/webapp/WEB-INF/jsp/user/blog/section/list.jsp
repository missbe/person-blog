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
<div class="box">
    <div class="box-body">
        <table id="${className}Table" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <c:forEach items="${columns}" var="column">
                        <th>${column}</th>
                    </c:forEach>
                </tr>
            </thead>
        </table>
    </div>
</div>

<jsp:include  page="../../../admin/common/footer.jsp"/>
<script>
  var oTable;
  $(document).ready(function() {
        oTable = $('#${className}Table').DataTable( {
            "processing": true,
            "serverSide": true,
            "ajax": "<%=basePath%>user/blog/${className}/list",
            order:[[0, 'desc']],
            dom: 'Bfrtip',
            select: {
                style: 'multi'
            },
            buttons: [
                {
                    className: 'btn btn-success pull-left',
                    text:      '<i class="fa fa-plus"/>新增',
                    action: function () {
                        window.location.href="<%=basePath%>user/blog/${className}/addUI";
                    }
                },
                {
                    className: 'btn btn-primary pull-left',
                    text:      '<i class="fa fa-trash"/>修改',
                    action: function () {
                        var selected = oTable.rows( { selected: true } ).data();
                        if(selected.length!=1){
                            alert_msg('有且只有选中一项才能进行修改!',false);
                        }else{
                            window.location.href="<%=basePath%>user/blog/${className}/addUI?id="+selected[0][0];
                        }
                    }
                },
                {
                    className: 'btn btn-danger pull-left',
                    text:      '<i class="fa fa-trash"/>删除',
                    action: function (dom) {
                        var selected = oTable.rows( { selected: true } ).data();
                        if(selected.length==0){
                            alert_msg('没有选择需要删除的项.',false);
                        }else{
                            var text = "";
                            for(var i =0 ; i<selected.length;i++){
                                if(i==selected.length-1){
                                    text += selected[i][1];
                                }else{
                                    text += selected[i][1]+",";
                                }
                            }
                            bootstrap_confirm("确认删除以下"+selected.length+"项吗？",text,function(){
                                var ids = new Array(selected.length);
                                for(var i =0 ; i<selected.length;i++){
                                    ids[i] = selected[i][0];
                                }
                                var data = {
                                    ids : ids
                                }
                                submit(data, "user/blog/${className}/delete", dom, function(res){
                                    if(res.success){
                                        oTable.ajax.reload( null, false );
                                    }
                                });
                            })
                        }
                    }
                },
                {
                 className: 'btn btn-info pull-left',
                 text:      '<i class="fa fa-refresh"/>刷新',
                 action: function () {
                     oTable.ajax.reload( null, false );
                 }
                },
                {
                    text: '设置为首页展示栏目',
                    className: 'btn btn-warning pull-left',
                    action: function (dom) {
                        var selected = oTable.rows( { selected: true } ).data();
                        if(selected.length!=1){
                            alert_msg('有且只有选中一项才能进行该操作!',false);
                        }else{
                            var text = "";
                            for(var i =0 ; i<selected.length;i++){
                                if(i==selected.length-1){
                                    text += selected[i][1];
                                }else{
                                    text += selected[i][1]+",";
                                }
                            }
                            bootstrap_confirm("确认将"+text+"设置为首页展示项吗？","设置之后，之前旧的展示项将自动取消。",function(){
                                var data = {
                                    id : selected[0][0]
                                }
                                submit(data, "user/blog/${className}/indexShow", dom, function(res){
                                    if(res.success){
                                        oTable.ajax.reload( null, false );
                                    }
                                });
                            })
                        }
                    }
                },
                {
                    text: '全选',
                    className: 'btn btn-primary pull-left',
                    action: function () {
                        oTable.rows().select();
                    }
                },
                {
                    text: '取消选中',
                    className: 'btn btn-default pull-left',
                    action: function () {
                        oTable.rows().deselect();
                    }
                }
            ]
        } );
        $('.dt-buttons').addClass('col-md-9 pull-left');
        $('#${className}Table_filter').prepend($('.dt-buttons'));
        $.fn.dataTable.select.init(oTable);
  } );
</script>