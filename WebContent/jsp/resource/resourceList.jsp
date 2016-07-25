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
<title>某类别资产列表</title>
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
					<th data-options="field:'status',width:80" hidden=true>资产状态</th>
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
						<td>${r.status}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div id="applyResourceToolbar" style="display: none">
		<a href="#" class="easyui-linkbutton" iconCls="icon-return"
			plain="true" onclick="applyResource()">在库资产申请</a>
	</div>

	<div id="entryResourceToolbar" style="display: none">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="addResource()">新购资产入库</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-edit" plain="true"
			onclick="editResource()">编辑资产信息</a>
	</div>

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

		//用户字段格式
		function userFormatter(value, row, index) {
			if (value == "warehouse") {
				return "仓库";
			} else {
				return value;
			}
		}
		
		//***************资产申请--开始****************************
		//为数据表添加资产申请工具栏
		function initApplyResourceToolbar() {
			$('#dg').datagrid({
				toolbar : '#applyResourceToolbar'
			})
		}

		//父页面创建在库资产申请
		function applyResource() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				parent.applyResource(row.id);
			}
		}
		//***************资产申请--结束****************************
		
		
		
		//***************资产入库--开始****************************
		//为数据表添加资产入库工具栏
		function initEntrtyResourceToolbar() {
			$('#dg').datagrid({
				toolbar : '#entryResourceToolbar'
			})
		}

		//在父页面创建资产入库dlg
		function addResource() {
			parent.addResource();
		}

		//在父页面创建资产编辑dlg
		function editResource() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				parent.editResource(row);
			}
		}
		//***************资产入库--结束****************************
	</script>
</body>
</html>