<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>申请进度查看</title>
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
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui/expand/datagrid-detailview.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'" title="申请列表">
		<div style="margin-left: 20px; margin-right: 20px; margin-top: 40px">
			<table id="applicationDatagrid" class="easyui-datagrid"
				toolbar="#toolbar" ,onDbClickRow="test()"
				data-options="url:'${ctx}/user/getUserApplications.do',fitColumns:true,singleSelect:true">
				<thead>
					<tr>
						<th data-options="field:'id',width:20">申请标识</th>
						<th
							data-options="field:'resource.name',width:60,formatter:resourceFormatter">资产名称</th>
						<th data-options="field:'owner',width:60">资产拥有人</th>
						<th data-options="field:'receiver',width:60">资产接收人</th>
						<th data-options="field:'time',width:60">提交时间</th>
						<th data-options="field:'type',widh:60,formatter:typeFormatter">申请类型</th>
					</tr>
				</thead>
			</table>

		</div>
	</div>

	<div data-options="region:'east'" style="width: 35%" title="申请进度">
		<div id="processDiv"
			style="margin-left: 20px; margin-right: 20px; margin-top: 40px">
			<table id="applicationProcessList">
			</table>
		</div>
	</div>

	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" plain="true"
			onclick="applyResourceApplications()">在库资产申请</a> <a href="#"
			class="easyui-linkbutton" plain="true"
			onclick="transferResourceApplications()">资产转移申请</a> <a href="#"
			class="easyui-linkbutton" plain="true"
			onclick="returnResourceApplications()">资产归还申请</a>
	</div>

	<script type="text/javascript">
		function test() {
			alert("fadfa");
		}
		function resourceFormatter(value, row, index) {

		}

		function typeFormatter(value, row, index) {
			if (value == 0) {
				return "在库资产申请";
			} else if (value == 1) {
				return "个人资产转移";
			} else if (value == 2) {
				return "个人资产归还";
			}
		}

		function applyResourceApplications() {
			$('#applicationDatagrid').datagrid({
				singleSelect:false
			});
		}

		function transferResourceApplications() {

		}

		function returnResourceApplications() {

		}
	</script>

</body>
</html>