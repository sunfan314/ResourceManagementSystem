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
	<div data-options="region:'center'" title="资产分配请求列表">
		<div style="margin-left: 20px; margin-right: 20px; margin-top: 40px">
			<table id="allocationApplicationList">
			</table>
		</div>
	</div>

	<div data-options="region:'east'" style="width: 40%" title="资产分配请求详情">
		<div id="applicationInfo"
			style="margin-left: 20px; margin-right: 20px; margin-top: 40px; display: none">
			<table class="tableStyle">
				<tr>
					<th class="thStyle">申请提交时间</th>
					<td id="time" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">资产申请人</th>
					<td id="receiver" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">申请资产详情</th>
					<td class="tdStyle">
						<table id="resourceInfo">

						</table>
					</td>
				</tr>
				<tr>
					<th class="thStyle">备注信息</th>
					<td id="remark" class="tdStyle"></td>
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
			$('#allocationApplicationList').datagrid({
				remoteSort : false,
				singleSelect : true,
				nowrap : false,
				fitColumns : true,
				url : '${ctx}/manager/getResourceAllocationApplications.do',
				columns:[[{
					field:'id',
					title:'申请标识',
					width:40,
					hidden:true
				},{
					field:'resourceName',
					title:'资产名称',
					width:60
				},{
					field:'receiver',
					title:'资产申请人',
					width:60
				},{
					field:'time',
					title:'申请发起时间',
					width:90
				},{
					field:'remark',
					title:'备注信息',
					width:90
				},{
					field:'resource',
					title:'资产类型',
					width:60,
					formatter:function(value,row,index){
						if(value){
							return value.type.name;
						}
					}
				}]],
				onLoadSuccess:function(data){
					if(data.total==0){
						//设置没有数据时在表格中进行提示
						$('#allocationApplicationList').datagrid('appendRow',{
							resourceName:"<div style='font-weight:bold;text-align:center;'>没有相关数据</div>"
						});
						$('#allocationApplicationList').datagrid('mergeCells',{
							index:0,
							field:'resourceName',
							colspan:5
						});
					}
				},
				//双击显示申请详情
				onDblClickRow:function(index,row){
					$('#dlg').dialog('close');
					if(!row.resource){
						return;
					}
					//显示申请详情信息
					$('#applicationInfo').show();
					applicationId=row.id;
					var timeTd=document.getElementById('time');
					var receiverTd=document.getElementById('receiver');
					var remarkTd=document.getElementById('remark');
					timeTd.innerHTML=row.time;
					receiverTd.innerHTML=row.receiver;
					remarkTd.innerHTML=row.remark;
					//根据资产类别显示资产详情
					var resourceInfo=document.getElementById('resourceInfo');
					resourceInfo.innerHTML="<iframe name='resourceInfoIframe' src='${ctx}/resource/resourceInfo.do?rid="
							+row.resource.id+"' frameborder='no'  style='width:100%;' "
							+"onload='this.height=resourceInfoIframe.document.body.scrollHeight' "
							+"></iframe>";
				}
			});
		});
		
		function acceptApplication() {
			$.post('${ctx}/manager/dealResourceAllocationApplication.do', {
				aId : applicationId,
				accept : true
			}, function(result) {
				//申请已被处理
				if (result.applicationCompleted) {
					$('#dialogInfo').text("申请已被处理，请勿重复提交请求！");
					$('#info-dlg').dialog('open').dialog('setTitle', '警告');
				} else {
					if (result.success) {
						$('#dialogInfo').text("申请审核成功，已同意资产分配申请！");
						$('#info-dlg').dialog('open').dialog('setTitle', '成功');
					}
				}

			}, 'json');
		}

		function refuseApplication(value) {
			$('#dlg').dialog('open').dialog('setTitle', '拒绝资产分配申请');
		}

		function submitRefuseApplication() {
			$.post('${ctx}/manager/dealResourceAllocationApplication.do', {
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
						$('#dialogInfo').text("申请审核成功，已拒绝资产分配申请！");
						$('#info-dlg').dialog('open').dialog('setTitle', '成功');
					}
				}

			}, 'json');
		}
	</script>
</body>
</html>