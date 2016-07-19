<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人资产查看</title>
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
	<div data-options="region:'north'" title="用户选择" style="height: 100px">
		<div style="margin-top: 25px; margin-left: 30px">
			<label style="font-weight: bolder; font-size: 20px;">用户选择：</label> <input
				style="height: 30px;" id="userList" class="easyui-combobox"
				data-options="
				url:'${ctx}/user/getUserWithResources.do',
				method:'get',
				valueField:'value',
				textField:'text',
				panelHeight:'auto',
				onChange:function(newValue, oldValue){ userSelected(newValue)}
			">
		</div>

	</div>

	<div data-options="region:'center'" title="个人资产列表">
		<div style="margin-left: 20px; margin-right: 20px; margin-top: 40px">
			<table id="resourceList">
			</table>
		</div>
	</div>

	<div data-options="region:'east'" style="width: 35%" title="资产使用记录">
		<div id="logDiv"
			style="margin-left: 20px; margin-right: 20px; margin-top: 40px">
			<table id="resourceLogList">
			</table>
		</div>
	</div>

	<script type="text/javascript">
		function userSelected(value) {
			var user = value;
			$('#logDiv').hide();
			$('#resourceList')
					.datagrid(
							{
								remoteSort : false,
								singleSelect : true,
								nowrap : false,
								fitColumns : true,
								url : '${ctx}/user/getPersonalResources.do?uid=' + user,
								columns : [ [ {
									field : 'id',
									title : '标识',
									width : 40,
									sortable : true
								}, {
									field : 'name',
									title : '名称',
									width : 80,
									sortable : true
								}, {
									field : 'model',
									title : '型号',
									width : 80
								}, {
									field : 'trackingNo',
									title : '追踪码',
									width : 80
								}, {
									field : 'imei',
									title : 'IMEI',
									width : 80
								}, {
									field : 'serialNo',
									title : '序列号',
									width : 80,
									align : 'center'
								}, {
									field : 'entryDate',
									title : '入库时间',
									width : 80
								}, {
									field : 'owner',
									title : '拥有人',
									width : 50,
									formatter : ownerFormatter
								}, {
									field : 'statusValue',
									title : '资产状态',
									width : 60
								}, {
									field : 'type',
									title : '资产类别',
									width : 100,
									formatter:function(value,row,index){
										if(value){
											return value.name;
										}
									}
								}, {
									field : 'remark',
									title : '备注信息',
									width : 120,
									hidden : true
								} ] ],
								view : detailview,
								//资产备注信息
								detailFormatter : function(rowIndex, rowData) {
									return '<table><tr>'
											+ '<td style="border:0">'
											+ '<p>备注信息: ' + rowData.remark
											+ '</p>' + '</td>'
											+ '</tr></table>';
								},
								//设置双击查看资产使用日志
								onDblClickRow : function() {
									$('#logDiv').show();
									var row = $('#resourceList').datagrid(
											'getSelected');
									if (row) {
										var rid = row.id;
										$('#resourceLogList')
												.datagrid(
														{
															remoteSort : false,
															singleSelect : true,
															nowrap : false,
															fitColumns : true,
															url : '${ctx}/user/getResourceLogs.do?rid='
																	+ rid,
															columns : [ [
																	{
																		field : 'id',
																		title : '日志标识',
																		width : 80,
																		sortable : true
																	},
																	{
																		field : 'owner',
																		title : '资产拥有人',
																		width : 100,
																		formatter : ownerFormatter
																	},
																	{
																		field : 'startTime',
																		title : '开始使用时间',
																		width : 120
																	},
																	{
																		field : 'endTime',
																		title : '结束使用时间',
																		width : 120
																	},
																	{
																		field : 'remark',
																		title : '备注信息',
																		width : 60,
																		hidden : true
																	} ] ],
															view : detailview,
															detailFormatter : function(
																	rowIndex,
																	rowData) {
																return '<table><tr>'
																		+ '<td style="border:0">'
																		+ '<p>备注信息: '
																		+ rowData.remark
																		+ '</p>'
																		+ '</td>'
																		+ '</tr></table>';
															}
														});
									}
								}
							});

		}
		
		//资产拥有人单元格格式
		function ownerFormatter(value,row,index){
			if(value=="warehouse"){
				return "仓库";
			}else{
				return value;
			}
		}
	</script>
</body>
</html>