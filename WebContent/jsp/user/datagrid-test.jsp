<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>test</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui/themes/color.css">
<script
	src="${pageContext.request.contextPath}/jquery/jquery-2.1.3.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
</head>
<body>
	

	<table id="dg" title="Surveys" class="easyui-datagrid"
		style="width: 550px;height:300px" toolbar="#toolbar"
		rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="id" width="20">Id</th>
				<th field="name" width="50">Name</th>
				<th field="number" width="50">Number</th>
				<th field="description" width="50">Description</th>
			</tr>
		</thead>
	</table>


	<script type="text/javascript">
		var myview = $.extend({}, $.fn.datagrid.defaults.view, {
			onAfterRender : function(target) {
				$.fn.datagrid.defaults.view.onAfterRender.call(this, target);
				var opts = $(target).datagrid('options');
				var vc = $(target).datagrid('getPanel').children(
						'div.datagrid-view');
				vc.children('div.datagrid-empty').remove();
				if (!$(target).datagrid('getRows').length) {
					var d = $('<div class="datagrid-empty"></div>').html(
							opts.emptyMsg || 'no records').appendTo(vc);
					d.css({
						position : 'absolute',
						left : 0,
						top : 50,
						width : '100%',
						textAlign : 'center'
					});
				}
			}
		});
		$(function() {
			var list = [];//数据列表为空

			$('#dg').datagrid({
				data : list,
				view : myview,
				emptyMsg : 'no records found'

			});
		})
	</script>
</body>
</html>