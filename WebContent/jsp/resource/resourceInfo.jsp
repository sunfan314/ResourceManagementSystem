<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="net.cn.util.ResourceTypeConfig"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资产详情</title>
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
table {
	font-size: 12px;
}

th {
	text-align: left;
	padding: 2px;
	width: 100px;
}

td {
	WORD-WRAP: break-word;
}

</style>
</head>
<body>
	<c:set var="SIM_CARD" value="<%=ResourceTypeConfig.SIM_CARD%>" />
	<c:set var="PHONE_CARD" value="<%=ResourceTypeConfig.PHONE_CARD%>" />
	<c:set var="CONSUMABLE" value="<%=ResourceTypeConfig.CONSUMABLE%>" />
	<c:set var="type" value="${resource.type.id}" />
	<c:set var="fatherType" value="${resource.type.fatherType}" />
	<c:choose>
		
		<c:when test="${type==SIM_CARD}">
			<table>
				<tr>
					<th>资产类别</th>
					<td>${resource.type.name}</td>
				</tr>
				<tr>
					<th>资产标识</th>
					<td>${resource.id}</td>
				</tr>
				<tr>
					<th>资产名称</th>
					<td>${resource.name}</td>
				</tr>
				<tr>
					<th>资产入库时间</th>
					<td>${resource.entryDate}</td>
				</tr>
				<tr>
					<th>资产状态</th>
					<td>${resource.statusValue}</td>
				</tr>
				<tr>
					<th>SIM卡号码</th>
					<td>${resource.phoneNumber }</td>
				</tr>
				<tr>
					<th>卡后IMSI号</th>
					<td>${resource.imsi}</td>
				</tr>
				<tr>
					<th>套餐</th>
					<td>${resource.pack}</td>
				</tr>
				<tr>
					<th>密码</th>
					<td>${resource.password}</td>
				</tr>
				<tr>
					<th>备注信息</th>
					<td>${resource.remark}</td>
				</tr>
			</table>
		</c:when>
		
		<c:when test="${type==PHONE_CARD}">
			<table>
				<tr>
					<th>资产类别</th>
					<td>${resource.type.name}</td>
				</tr>
				<tr>
					<th>资产标识</th>
					<td>${resource.id}</td>
				</tr>
				<tr>
					<th>资产名称</th>
					<td>${resource.name}</td>
				</tr>
				<tr>
					<th>资产入库时间</th>
					<td>${resource.entryDate}</td>
				</tr>
				<tr>
					<th>充值号码</th>
					<td>${resource.phoneNumber}</td>
				</tr>
				<tr>
					<th>购买人</th>
					<td>${resource.purchaser}</td>
				</tr>
				<tr>
					<th>领用人</th>
					<td>${resource.owner}</td>
				</tr>
				<tr>
					<th>备注信息</th>
					<td>${resource.remark}</td>
				</tr>
			</table>
		</c:when>
		
		<c:when test="${fatherType==CONSUMABLE}">
			<table>
				<tr>
					<th>资产类别</th>
					<td>${resource.type.name}</td>
				</tr>
				<tr>
					<th>资产标识</th>
					<td>${resource.id}</td>
				</tr>
				<tr>
					<th>资产名称</th>
					<td>${resource.name}</td>
				</tr>
				<tr>
					<th>资产入库时间</th>
					<td>${resource.entryDate}</td>
				</tr>
				<tr>
					<th>购买人</th>
					<td>${resource.purchaser}</td>
				</tr>
				<tr>
					<th>领用人</th>
					<td>${resource.owner}</td>
				</tr>
				<tr>
					<th>备注信息</th>
					<td>${resource.remark}</td>
				</tr>
			</table>
		</c:when>

		<c:otherwise>
			<table>
				<tr>
					<th>资产类别</th>
					<td>${resource.type.name}</td>
				</tr>
				<tr>
					<th>资产标识</th>
					<td>${resource.id}</td>
				</tr>
				<tr>
					<th>资产名称</th>
					<td>${resource.name}</td>
				</tr>
				<tr>
					<th>资产入库时间</th>
					<td>${resource.entryDate}</td>
				</tr>
				<tr>
					<th>资产状态</th>
					<td>${resource.statusValue}</td>
				</tr>
				<tr>
					<th >资产型号</th>
					<td>${resource.model}</td>
				</tr>
				<tr>
					<th>追踪码</th>
					<td>${resource.trackingNo}</td>
				</tr>
				<tr>
					<th>IMEI</th>
					<td>${resource.imei}</td>
				</tr>
				<tr>
					<th>序列号</th>
					<td>${resource.serialNo}</td>
				</tr>
				<tr>
					<th>备注信息</th>
					<td>${resource.remark}</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</body>
</html>