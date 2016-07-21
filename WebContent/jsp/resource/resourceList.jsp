<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="net.cn.util.ResourceTypeConfig"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="resourceType" value="${type.id}" />
<c:set var="resourceFatherType" value="${type.fatherType}" />
<c:set var="SIM_CARD_TYPE" value="<%=ResourceTypeConfig.SIM_CARD%>" />
<c:set var="PHONE_CARD_TYPE" value="<%=ResourceTypeConfig.PHONE_CARD%>" />
<c:set var="CONSUMABLE_TYPE" value="<%=ResourceTypeConfig.CONSUMABLE%>" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业资产列表</title>
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
<body>
	<table id="dg" class="easyui-datagrid"
		data-options="fitColumns:true,singleSelect:true">
		<thead>
			<tr>
				<th data-options="field:'id',width:30">资产标识</th>
				<th data-options="field:'name',width:50">资产名称</th>
				<th data-options="field:'model',width:50">资产型号</th>
				<th data-options="field:'trackingNo',width:50">追踪码</th>
				<th data-options="field:'imei',width:50">IMEI</th>
				<th data-options="field:'serialNo',width:50">序列号</th>
				<th data-options="field:'phoneNumber',width:50"><c:choose>
						<c:when test="${resourceType==PHONE_CARD_TYPE}">
								充值号码
							</c:when>
						<c:otherwise>
								手机号码
							</c:otherwise>
					</c:choose></th>
				<th data-options="field:'imsi',width:50">IMSI</th>
				<th data-options="field:'pack',width:80">套餐信息</th>
				<th data-options="field:'password',width:50">密码</th>
				<th data-options="field:'purchaser',width:50">购买人</th>
				<th data-options="field:'owner',width:50,formatter:userFormatter">
					<c:choose>
						<c:when test="${resourceType==PHONE_CARD_TYPE}">
								领用人
							</c:when>
						<c:when test="${resourceFatherType==CONSUMABLE_TYPE}">
								领用人
							</c:when>
						<c:otherwise>	
								拥有人
							</c:otherwise>
					</c:choose>
				</th>
				<th data-options="field:'entryDate',width:50">入库时间</th>
				<th data-options="field:'statusValue',width:40">资产状态</th>
				<th data-options="field:'remark',width:80">备注信息</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="r" items="${resources}">
				<tr>
					<td>${r.id}</td>
					<td>${r.name}</td>
					<td>${r.model}</td>
					<td>${r.trackingNo}</td>
					<td>${r.imei}</td>
					<td>${r.serialNo}</td>
					<td>${r.phoneNumber}</td>
					<td>${r.imsi}</td>
					<td>${r.pack}</td>
					<td>${r.password}</td>
					<td>${r.purchaser}</td>
					<td>${r.owner}</td>
					<td>${r.entryDate}</td>
					<td>${r.statusValue}</td>
					<td>${r.remark}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<script type="text/javascript">
		$(function() {
			//控制不同类别的资产显示的属性字段
			var SIM_CARD =<%=ResourceTypeConfig.SIM_CARD%>;
			var PHONE_CARD =<%=ResourceTypeConfig.PHONE_CARD%>;
			var CONSUMABLE =<%=ResourceTypeConfig.CONSUMABLE%>;
			var type = ${type.id};
			var fatherType = ${type.fatherType};
			if (type == SIM_CARD) {
				$('#dg').datagrid('hideColumn', 'model');
				$('#dg').datagrid('hideColumn', 'trackingNo');
				$('#dg').datagrid('hideColumn', 'imei');
				$('#dg').datagrid('hideColumn', 'serialNo');
				$('#dg').datagrid('hideColumn', 'purchaser');
			} else if (type == PHONE_CARD) {
				$('#dg').datagrid('hideColumn', 'model');
				$('#dg').datagrid('hideColumn', 'trackingNo');
				$('#dg').datagrid('hideColumn', 'imei');
				$('#dg').datagrid('hideColumn', 'serialNo');
				$('#dg').datagrid('hideColumn', 'imsi');
				$('#dg').datagrid('hideColumn', 'pack');
				$('#dg').datagrid('hideColumn', 'password');

			} else if (fatherType == CONSUMABLE) {
				$('#dg').datagrid('hideColumn', 'model');
				$('#dg').datagrid('hideColumn', 'trackingNo');
				$('#dg').datagrid('hideColumn', 'imei');
				$('#dg').datagrid('hideColumn', 'serialNo');
				$('#dg').datagrid('hideColumn', 'phoneNumber');
				$('#dg').datagrid('hideColumn', 'imsi');
				$('#dg').datagrid('hideColumn', 'pack');
				$('#dg').datagrid('hideColumn', 'password');
			} else {
				$('#dg').datagrid('hideColumn', 'phoneNumber');
				$('#dg').datagrid('hideColumn', 'imsi');
				$('#dg').datagrid('hideColumn', 'pack');
				$('#dg').datagrid('hideColumn', 'password');
				$('#dg').datagrid('hideColumn', 'purchaser');
			}
			$('#dg').datagrid({
				onDblClickRow : function(index, row) {
					parent.showResourceLogList(row.id);
				}
			});
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