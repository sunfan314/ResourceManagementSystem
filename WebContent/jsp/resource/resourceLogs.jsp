<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="net.cn.util.ResourceTypeConfig"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资产使用记录表</title>
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
td {
	WORD-WRAP: break-word;
}
</style>
</head>
<body>
	<div>
		<table id="dg" class="easyui-datagrid"
			data-options="fitColumns:true,singleSelect:true">
			<thead>
				<tr>
					<th data-options="field:'id',width:35,hidden:true">日志标识</th>
					<th data-options="field:'owner',width:50,formatter:userFormatter">拥有人</th>
					<th data-options="field:'startTime',width:70">开始时间</th>
					<th data-options="field:'endTime',width:70">结束时间</th>
					<th data-options="field:'remark',width:100">备注信息</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="l" items="${logs}">
					<tr>
						<td>${l.id}</td>
						<td>${l.owner}</td>
						<td>${l.startTime}</td>
						<td>${l.endTime}</td>
						<td>${l.remark}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>


	<script type="text/javascript">
		$(function() {
			var dataSize = "${fn:length(logs)}";
			if (dataSize == 0) {
				//设置没有数据时在表格中进行提示
				$('#dg').datagrid('appendRow',{
					owner : "<div style='font-weight:bold;text-align:center;'>没有相关数据</div>"
				});
				$('#dg').datagrid('mergeCells', {
					index : 0,
					field : 'owner',
					colspan : 4
				});
			}
		});

		function userFormatter(value, row, index) {
			if (value == "warehouse") {
				return "仓库";
			} else {
				return value;
			}
		}
	</script>
</body>
</html>