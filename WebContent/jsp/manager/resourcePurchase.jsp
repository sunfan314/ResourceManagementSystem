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
table.tableStyle {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 2px;
	border-color: #95B8E7;
	border-collapse: collapse;
	margin-bottom: 30px;
	margin-left: 60px;
	margin-top: 10px;
}

th.thStyle {
	text-align: left;
	padding: 4px;
	border-width: 2px;
	border-style: solid;
	border-color: #95B8E7;
	width: 200px;
}

th.thStyle2 {
	text-align: left;
	padding: 4px;
	width: 50px;
}

td.tdStyle {
	padding-left: 10px;
	border-width: 2px;
	border-style: solid;
	border-color: #95B8E7;
	background-color: #ffffff;
	width: 600px;
}

h2 {
	margin-left: 60px;
}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'" title="资产购买请求列表">
		<div style="margin-left: 20px; margin-right: 20px; margin-top: 40px">
			<table id="dg" class="easyui-datagrid"
				data-options="url:'${ctx}/manager/getResourcePurchaseApplications.do',fitColumns:true,singleSelect:true">
				<thead>
					<tr>
						<th data-options="field:'id',width:20,hidden:true">申请标识</th>
						<th data-options="field:'name',width:40">物品名称</th>
						<th data-options="field:'number',width:40">物品数量</th>
						<th data-options="field:'description',width:80">申购描述</th>
						<th data-options="field:'user',width:40">申购人</th>
						<th data-options="field:'time',width:60">申请时间</th>	
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<div data-options="region:'east'" style="width: 40%" title="资产购买请求详情">
		<div id="applicationInfo"
			style="margin-left: 20px; margin-right: 20px; margin-top: 40px; display: none">
			<table class="tableStyle">
				<tr>
					<th class="thStyle">申请提交时间</th>
					<td id="time" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">资产申购人</th>
					<td id="user" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">申购物品名称</th>
					<td id="name" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">申购物品数量</th>
					<td id="number" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">申购描述</th>
					<td id="description" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">审核申请</th>
					<td class="tdStyle">
						<table>
							<tr>
								<td><a href="#" class="easyui-linkbutton" iconCls="icon-ok"
									plain="true" onclick="acceptApplication()">同意</a></td>
								<td><a href="#" class="easyui-linkbutton" iconCls="icon-no"
									plain="true" onclick="refuseApplication()">拒绝</a></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div id="dlg" class="easyui-dialog"
		style="width: 380px; height: 280px; padding: 20px" closed="true"
		buttons="#dlg-buttons">
		<table cellpadding="5">
			<tr>
				<td>备注信息：（可不填）</td>
			</tr>
			<tr>
				<td><input id="approvalRemark" class="easyui-textbox"
					data-options="multiline:true" style="width: 300px; height: 100px"></td>
			</tr>
		</table>
	</div>

	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="submitRefuseApplication()">提交</a> <a href="#"
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
			onclick="javascript:$('#info-dlg').dialog('close');location.reload(true);">确定</a>
	</div>
	<script type="text/javascript">
		$(function(){
			$('#dg').datagrid({
				onLoadSuccess:function(data){
					if(data.total==0){
						//设置没有数据时在表格中进行提示
						$('#dg').datagrid('appendRow',{
							name:"<div style='font-weight:bold;text-align:center;'>没有相关数据</div>"
						});
						$('#dg').datagrid('mergeCells',{
							index:0,
							field:'name',
							colspan:5
						});
					}
				},
				//双击显示申请详情
				onDblClickRow:function(index,row){
					$('#dlg').dialog('close');
					if(!row.number){
						return;
					}
					applicationId=row.id;
					//显示申请详情信息
					$('#name').html(row.name);
					$('#number').html(row.number);
					$('#description').html(row.description);
					$('#user').html(row.user);
					$('#time').html(row.time);
					$('#applicationInfo').show();
				}
			});
		});
		
		function acceptApplication() {
			$.post('${ctx}/manager/dealResourcePurchaseApplication.do', {
				aId : applicationId,
				accept : true
			}, function(result) {
				//申请已被处理
				if (result.applicationCompleted) {
					$('#dialogInfo').text("申请已被处理，请勿重复提交请求！");
					$('#info-dlg').dialog('open').dialog('setTitle', '警告');
				} else {
					if (result.success) {
						$('#dialogInfo').text("申请审核成功，已同意资产购买申请！");
						$('#info-dlg').dialog('open').dialog('setTitle', '成功');
					}
				}
			}, 'json');
		}

		function refuseApplication(value) {
			$('#dlg').dialog('open').dialog('setTitle', '拒绝资产购买申请');
		}

		function submitRefuseApplication() {
			$.post('${ctx}/manager/dealResourcePurchaseApplication.do', {
				aId : applicationId,
				accept : false,
				remark : $('#approvalRemark').val()
			}, function(result) {
				$('#dlg').dialog('close');
				//申请已被处理
				if (result.applicationCompleted) {
					$('#dialogInfo').text("申请已被处理，请勿重复提交请求！");
					$('#info-dlg').dialog('open').dialog('setTitle', '警告');
				} else {
					if (result.success) {
						$('#dialogInfo').text("申请审核成功，已拒绝资产购买申请！");
						$('#info-dlg').dialog('open').dialog('setTitle', '成功');
					}
				}
			}, 'json');
		}
	</script>
</body>
</html>