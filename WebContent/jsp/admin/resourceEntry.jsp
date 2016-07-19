<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新购资产入库</title>
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
	margin-bottom: 50px;
}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" title="资产类别选择" style="height: 100px">
		<div style="margin-top: 25px; margin-left: 30px">
			<label style="font-weight: bolder; font-size: 20px;">资产类别选择：</label>
			<input style="height: 30px;" id="resourceType"
				class="easyui-combobox" name="type"
				data-options="
				url:'${ctx}/user/getResourceTypes.do',
				method:'get',
				valueField:'value',
				textField:'text',
				groupField:'group',
				panelHeight:'auto',
				onChange:function(newValue, oldValue){ typeSelected(newValue)}
			">

			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="addType()" style="margin-left: 30px;">新建类别</a>
		</div>

	</div>

	<div data-options="region:'center'" title="企业资产列表">
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
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="addResource()">新购资产入库</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-edit" plain="true"
			onclick="editResource()">编辑资产信息</a>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 350px; height: 500px; padding: 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellpadding="5">
				<tr id="idTr">
					<td>资产标识</td>
					<td><input id="id" name="id" class="easyui-validatebox"
						readonly></td>
				</tr>
				<tr>
					<td>资产名称</td>
					<td><input id="name" name="name" class="easyui-validatebox"></td>
				</tr>
				<tr>
					<td>资产型号</td>
					<td><input id="model" name="model" class="easyui-validatebox"></td>
				</tr>
				<tr>
					<td>追踪码</td>
					<td><input id="trackingNo" name="trackingNo"
						class="easyui-validatebox"></td>
				</tr>
				<tr>
					<td>IMEI</td>
					<td><input id="imei" name="imei" class="easyui-validatebox"></td>
				</tr>
				<tr>
					<td>序列号</td>
					<td><input id="serialNo" name="serialNo"
						class="easyui-validatebox"></td>
				</tr>
				<tr>
					<td>资产状态</td>
					<td><select id="status" name="status" panelHeight="auto"
						class="easyui-combobox" editable="false">
							<option value="0">资产正常</option>
							<option value="1">资产损坏</option></td>
				</tr>
				<tr style="display: none;">
					<td>资产类别</td>
					<td><input id="type" name="type" class="easyui-validatebox"></td>
				</tr>
				<tr id="ownerTr1">
					<td>资产拥有人</td>
					<td><input id="owner" name="owner" class="easyui-combobox"
						data-options="
				url:'${ctx}/admin/getUsers.do',
				method:'get',
				valueField:'value',
				textField:'text',
				panelHeight:'200px'"></td>
				</tr>
				<tr id="ownerTr2">
					<td>资产拥有人</td>
					<td><input id="ownerValidatebox" class="easyui-validatebox"
						readonly="true"></td>
				</tr>
				<tr>
					<td>入库时间</td>
					<td><input id="entryDate" name="entryDate"
						class="easyui-validatebox"></td>
				</tr>
				<tr>
					<td>备注信息</td>
					<td><input id="remark" name="remark" class="easyui-textbox"
						data-options="multiline:true" style="height: 80px;"></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="submit()">提交</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>

	<div id="type-dlg" class="easyui-dialog"
		style="width: 300px; height: 230px; padding: 20px" closed="true"
		buttons="#type-dlg-buttons">
		<form id="type-fm">
			<table style="border-collapse: separate; border-spacing: 0px 20px;">
				<tr>
					<th>类别名称：</th>
					<td><input id="typeName" class="easyui-validatebox"></td>
				</tr>
				<tr>
					<th>所属类目:</th>
					<td><input style="height: 30px;" id="fatherType"
						class="easyui-combobox"
						data-options="
				url:'${ctx}/admin/getFatherTypes.do',
				method:'get',
				valueField:'id',
				textField:'name',
				panelHeight:'auto'
			"></td>
				</tr>
			</table>
		</form>

	</div>

	<div id="type-dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="addNewType()">提交</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#type-dlg').dialog('close')">取消</a>
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
		var type;
		function typeSelected(value) {
			type = value;
			$('#logDiv').hide();
			$('#resourceList')
					.datagrid(
							{
								toolbar : "#toolbar",
								remoteSort : false,
								singleSelect : true,
								nowrap : false,
								fitColumns : true,
								url : '${ctx}/user/getCompanyResources.do?type='+ type,
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
									field : 'remark',
									title : '备注信息',
									width : 120,
									hidden : true
								},{
									field : 'status',
									title : '资产状态',
									width : 60,
									hidden : true
								}] ],
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

		}
		
		//资产拥有人单元格格式
		function ownerFormatter(value,row,index){
			if(value=="warehouse"){
				return "仓库";
			}else{
				return value;
			}
		}
		
		function addType(){
			$('#type-dlg').dialog('open').dialog('setTitle', '新建类别');
		}
		
		function addNewType(){
			$.post('${ctx}/admin/addType.do', {
				typeName:$('#typeName').val(),
				fatherType:$('#fatherType').combobox('getValue')
			}, function(result) {
				$('#type-dlg').dialog('close');
				//申请已被处理
				if (result.success) {
					$('#dialogInfo').text("类别添加成功！");
					$('#info-dlg').dialog('open').dialog('setTitle', '成功');
					$('#type-fm').form('clear');
					//刷新类别列表数据
					$('#resourceType').combobox('reload');
					
				} else{
					$('#dialogInfo').text("类别添加失败，该类别已存在！");
					$('#info-dlg').dialog('open').dialog('setTitle', '失败');
				}
			}, 'json');
		}

		function addResource() {
			$('#dlg').dialog('open').dialog('setTitle', '新购资产入库');
			$('#fm').form('clear');
			//控制组件的隐藏
			var idTr=document.getElementById("idTr");
			idTr.style.display="none";
			//由于id传递到后台的resource对象属性中，所以不能是空字符，传入0在后台进行处理
			$('#id').val(0);
			$('#type').val(type);
			<%SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String time = df.format(new Date());%>
			//设置入库时间为当前时间
			$('#entryDate').val("<%=time%>");
			//设置资产状态为正常状态
			$('#status').combobox('setValue', 0);
			//设置资产拥有人为仓库
			$('#owner').combobox('setValue', 'warehouse');
			//控制组件的显示与隐藏
			var ownerTr1 = document.getElementById("ownerTr1");
			var ownerTr2 = document.getElementById("ownerTr2");
			ownerTr1.style.display = "";
			ownerTr2.style.display = "none";
			url = "${ctx}/admin/enterNewResource.do";
		}

		function editResource() {
			var row = $('#resourceList').datagrid('getSelected');
			if (row) {
				$('#dlg').dialog('open').dialog('setTitle', '编辑资产信息');
				//控制组件的显示
				var idTr = document.getElementById("idTr");
				idTr.style.display = "";
				//设置资产信息的显示
				$('#id').val(row.id);
				$('#name').val(row.name);
				$('#model').val(row.model);
				$('#trackingNo').val(row.trackingNo);
				$('#imei').val(row.imei);
				$('#serialNo').val(row.serialNo);
				$('#status').combobox('setValue', row.status);
				$('#type').val(type);
				$('#owner').combobox('setValue', row.owner);
				$('#ownerValidatebox').val(row.owner);
				//控制组件的显示与隐藏
				var ownerTr1 = document.getElementById("ownerTr1");
				var ownerTr2 = document.getElementById("ownerTr2");
				ownerTr1.style.display = "none";
				ownerTr2.style.display = "";
				$('#entryDate').val(row.entryDate);
				$('#remark').textbox('setValue', row.remark);
				url = "${ctx}/admin/editResource.do";
			}
		}

		function submit() {
			$('#fm').form('submit', {
				url : url,
				success : function(result) {
					$('#dlg').dialog('close'); // close the dialog
					result = JSON.parse(result);
					if (result.success) {
						$('#resourceList').datagrid('reload'); // reload the user data
						$('#dialogInfo').text("操作成功，已成功添加（修改）资产信息！");
						$('#info-dlg').dialog('open').dialog('setTitle', '成功');
					} else {
						$('#dialogInfo').text("操作失败，资产拥有人不存在，请在列表中选择资产拥有人！");
						$('#info-dlg').dialog('open').dialog('setTitle', '失败');
					}

				}
			});
		}
	</script>
</body>
</html>