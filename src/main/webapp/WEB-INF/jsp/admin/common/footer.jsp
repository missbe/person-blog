<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 </div>
  <!-- /.content-wrapper -->

  <footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b> 1.0.0
    </div>
    <strong>Copyright &copy; 2016 <a href="http://www.missbe.cn" target="_blank">Missbe</a>.</strong> All rights
    reserved.
  </footer>

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Create the tabs -->
    <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
    </ul>
    <div class="tab-content">
      <div class="tab-pane" id="control-sidebar-home-tab">
      </div>
    </div>
  </aside>
  <!-- /.control-sidebar -->
  <!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<script type="text/javascript">
var basePath = '<%=basePath%>';
</script>
<!-- jQuery 2.2.3 -->
<script src="<%=basePath%>static/adminLte/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="<%=basePath%>static/adminLte/bootstrap/js/bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="<%=basePath%>static/adminLte/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="<%=basePath%>static/adminLte/plugins/fastclick/fastclick.js"></script>
<script src="<%=basePath%>static/adminLte/plugins/jQueryUI/jquery-ui.min.js"></script>
<!-- AdminLTE App -->
<script src="<%=basePath%>static/adminLte/dist/js/app.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="<%=basePath%>static/adminLte/dist/js/demo.js"></script>
<script src="<%=basePath%>static/js/messenger.min.js"></script>
<script src="<%=basePath%>static/js/messenger-themes-flat.js"></script>
<script src="<%=basePath%>static/adminLte/plugins/select2/select2.full.min.js"></script>
<!-- DataTables -->
<script src="<%=basePath%>static/adminLte/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="<%=basePath%>static/adminLte/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="<%=basePath%>static/adminLte/plugins/datatables/extensions/Buttons/js/dataTables.buttons.min.js"></script>
<script src="<%=basePath%>static/adminLte/plugins/datatables/extensions/Select/js/dataTables.select.js"></script>
<!-- confirm -->
<script src="<%=basePath%>static/js/jquery.confirm.min.js"></script>

<script src="<%=basePath%>static/js/common.js"></script>
</body>
</html>