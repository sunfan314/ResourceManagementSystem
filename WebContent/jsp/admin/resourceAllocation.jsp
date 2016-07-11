<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资产分配申请</title>
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
	<div data-options="region:'center'">
		<h2>你有&nbsp;${numOfApplications}&nbsp;条资产分配申请待处理：</h2>


		<c:forEach varStatus="i" var="a" items="${applications}">

			<table class="tableStyle">
				<tr>
					<th class="thStyle">申请提交时间</th>
					<td class="tdStyle">${a.time}</td>
				</tr>
				<tr>
					<th class="thStyle">资产申请人</th>
					<td class="tdStyle">${a.receiver}</td>
				</tr>
				<tr>
					<th class="thStyle">申请资产详情</th>
					<td class="tdStyle">
						<table>
							<tr>
								<th class="thStyle2">资产类型</th>
								<td>${a.resource.typeName}</td>
							</tr>
							<tr>
								<th class="thStyle2">资产标识</th>
								<td>${a.resource.id}</td>
							</tr>
							<tr>
								<th class="thStyle2">资产名称</th>
								<td>${a.resource.name}</td>
							</tr>
							<tr>
								<th class="thStyle2">资产型号</th>
								<td>${a.resource.model}</td>
							</tr>
							<tr>
								<th class="thStyle2">追踪码</th>
								<td>${a.resource.trackingNo}</td>
							</tr>
							<tr>
								<th class="thStyle2">IMEI</th>
								<td>${a.resource.imei}</td>
							</tr>
							<tr>
								<th class="thStyle2">序列号</th>
								<td>${a.resource.serialNo}</td>
							</tr>
							<tr>
								<th class="thStyle2">备注信息</th>
								<td>${a.resource.remark}</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th class="thStyle">备注信息</th>
					<td class="tdStyle">${a.remark}</td>
				</tr>
				<tr>
					<th class="thStyle">处理申请</th>
					<td class="tdStyle">
						<table>
							<tr>
								<td><a href="#" class="easyui-linkbutton" iconCls="icon-ok"
									plain="true" onclick="acceptApplication(${a.id})">同意</a></td>
								<td><a href="#" class="easyui-linkbutton" iconCls="icon-no"
									plain="true" onclick="refuseApplication(${a.id})">拒绝</a></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</c:forEach>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 380px; height: 325px; padding: 20px" closed="true"
		buttons="#dlg-buttons">
		<table cellpadding="5">
			<tr style="display: none;">
				<td>申请标识</td>
				<td><input id="aId" name="rid" class="easyui-validatebox"></td>
			</tr>
			<tr>
				<td>备注信息：（可不填）</td>
			</tr>
			<tr>
				<td><input id="remark" class="easyui-textbox"
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
	function acceptApplication(value){
		$.post('${ctx}/admin/dealResourceAllocationApplication.do', {
			aId:value,
			accept:true
		}, function(result) {
			//申请已被处理
			if(result.applicationCompleted){
				$('#dialogInfo').text("申请已被处理，请勿重复提交请求！");
				$('#info-dlg').dialog('open').dialog(
						'setTitle', '警告');
			}else{
				if (result.success) {
					$('#dialogInfo').text("申请处理成功，已完成资产分配！");
					$('#info-dlg').dialog('open').dialog(
							'setTitle', '成功');
				}else{
					$('#dialogInfo').text("申请处理失败，资产所有权转移！");
					$('#info-dlg').dialog('open').dialog(
							'setTitle', '失败');
				}
			}		

		}, 'json');		
	}
	
	function refuseApplication(value){
		$('#dlg').dialog('open').dialog('setTitle', '拒绝资产分配申请');
		$('#aId').val(value);
	}
	
	function submitRefuseApplication(){
		$.post('${ctx}/admin/dealResourceAllocationApplication.do', {
			aId:$('#aId').val(),
			accept:false,
			remark:$('#remark').val()
		}, function(result) {
			$('#dlg').dialog('close');
			//申请已被处理
			if(result.applicationCompleted){
				$('#dialogInfo').text("申请已被处理，请勿重复提交请求！");
				$('#info-dlg').dialog('open').dialog(
						'setTitle', '警告');
			}else{
				if (result.success) {
					$('#dialogInfo').text("申请处理成功，已拒绝资产分配申请！");
					$('#info-dlg').dialog('open').dialog(
							'setTitle', '成功');
				}
			}		

		}, 'json');	
	}
	</script>
</body>
</html>