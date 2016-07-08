<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人资产管理</title>
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

	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-return"
			plain="true" onclick="returnResource()">资产归还</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-transfer" plain="true"
			onclick="transferResource()">资产转移</a>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 380px; height: 325px; padding: 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellpadding="5">
				<tr style="display: none">
					<td>资产标识</td>
					<td><input id="rid" name="rid" class="easyui-validatebox"></td>
				</tr>
				<tr id="resourceReceiverLabel">
					<td>资产接收人：</td>
				</tr>
				<tr id="resourceReceiver">
					<td><input name="resourceReceiver" class="easyui-combobox"
						data-options="
				url:'${ctx}/user/getOtherUsers.do?uid=${uid}',
				method:'get',
				valueField:'value',
				textField:'text',
				panelHeight:'auto'"></td>
				</tr>
				<tr>
					<td>备注信息：（可不填）</td>
				</tr>
				<tr>
					<td><input name="remark" class="easyui-textbox"
						data-options="multiline:true" style="width: 300px; height: 100px"></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="submitApplication()">提交</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>

	<div id="info-dlg" class="easyui-dialog"
		style="width: 300px; height: 140px; padding: 10px 20px" closed="true"
		buttons="#info-dlg-buttons">
		<label id="dialogInfo" style="font-size: 15px;"></label>
	</div>

	<div id="info-dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="javascript:$('#info-dlg').dialog('close')">确定</a>
	</div>


	<script type="text/javascript">
		$(function() {
			var user = "${uid }";
			$('#logDiv').hide();
			$('#resourceList')
					.datagrid(
							{
								toolbar : "#toolbar",
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
									width : 50
								}, {
									field : 'statusValue',
									title : '资产状态',
									width : 60
								}, {
									field : 'typeName',
									title : '资产类别',
									width : 100
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
																		width : 100
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
		});

		function returnResource() {
			var row = $('#resourceList').datagrid('getSelected');
			if (row) {
				//控制组件的隐藏
				var ui1 = document.getElementById("resourceReceiverLabel");
				var ui2 = document.getElementById("resourceReceiver");
				ui1.style.display = "none";
				ui2.style.display = "none";
				$('#dlg').dialog('open').dialog('setTitle', '资产归还入库');
				$('#rid').val(row.id);
				url = "${ctx}/user/returnResourceApplication.do";
			}
		}

		function transferResource() {
			var row = $('#resourceList').datagrid('getSelected');
			if (row) {
				//控制组件的显示
				var ui1 = document.getElementById("resourceReceiverLabel");
				var ui2 = document.getElementById("resourceReceiver");
				ui1.style.display = "";
				ui2.style.display = "";
				$('#dlg').dialog('open').dialog('setTitle', '资产转移');
				$('#rid').val(row.id);
				url = "${ctx}/user/transferPersonalResourceApplication.do";
			}
		}

		function submitApplication() {
			$('#fm').form(
					'submit',
					{
						url : url,
						success : function(result) {
							$('#dlg').dialog('close'); // close the dialog
							$('#resourceList').datagrid('reload'); // reload the user data
							$('#fm').form('clear');//clear the form
							result = JSON.parse(result);
							if (result.success) {
								$('#dialogInfo').text("请求提交成功！");
								$('#info-dlg').dialog('open').dialog(
										'setTitle', '成功');

							} else {
								if (result.receiverExists) {
									$('#dialogInfo').text("请求提交失败，用户不具有操作权限！");
									$('#info-dlg').dialog('open').dialog(
											'setTitle', '失败');
								} else {
									$('#dialogInfo').text("请求提交失败，接收用户不存在！");
									$('#info-dlg').dialog('open').dialog(
											'setTitle', '失败');
								}

							}
							
						}
					});
		}
	</script>
</body>
</html>