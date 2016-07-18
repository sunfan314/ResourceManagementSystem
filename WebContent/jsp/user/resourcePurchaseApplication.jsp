<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资产购买申请</title>
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
<style>
tr {
	margin-bottom:20px;
}
th {
	text-align: right;
	padding: 15px;
	width: 100px;
}

td {
	padding-left: 10px;
	background-color: #ffffff;
}



</style>
</head>
<body>
	<div style="margin-left: 400px;">
		<form id="fm" method="post">
			<table>
				<tr>
					<th>申请人:</th>
					<td>${uid}</td>
				</tr>
				<tr>
					<th>物品名称:</th>
					<td><input class="easyui-validatebox"></td>
				</tr>
				<tr>
					<th>申购描述:</th>
					<td><input class="easyui-textbox"
						data-options="multiline:true" style="height: 80px;"></td>
				</tr>
				<tr>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		
	</script>
</body>
</html>