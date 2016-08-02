<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="net.cn.util.ResourceTypeConfig" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资产转移接收</title>
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
	font-size: 12px;
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
	<div data-options="region:'center'" title="资产转移请求列表">
		<div style="margin-left: 20px; margin-right: 20px; margin-top: 20px">
			<table id="transferApplicationList">
			</table>
		</div>
	</div>

	<div data-options="region:'east'" style="width: 40%" title="资产转移请求详情">
		<div id="applicationInfo" style="margin-left:20px;margin-right:20px;margin-top:20px;display:none">
			<table class="tableStyle">
				<tr>
					<th class="thStyle">申请提交时间</th>
					<td id="time" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">资产转移人</th>
					<td id="owner" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">待接收资产详情</th>
					<td id="resourceInfo" class="tdStyle">
					</td>
				</tr>
				<tr>
					<th class="thStyle">申请备注信息</th>
					<td id="remark" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">处理申请</th>
					<td class="tdStyle">
						<table>
							<tr>
								<td><a href="#" class="easyui-linkbutton" iconCls="icon-ok"
									plain="true" onclick="acceptResource()">接收</a></td>
								<td><a href="#" class="easyui-linkbutton" iconCls="icon-no"
									plain="true" onclick="refuseResource()">拒绝</a></td>
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
		$('#transferApplicationList').datagrid({
			remoteSort : false,
			singleSelect : true,
			nowrap : false,
			fitColumns : true,
			url : '${ctx}/user/getResourceTransferApplications.do',
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
				field:'owner',
				title:'资产转移人',
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
					$('#transferApplicationList').datagrid('appendRow',{
						resourceName:"<div style='font-weight:bold;text-align:center;'>没有相关数据</div>"
					});
					$('#transferApplicationList').datagrid('mergeCells',{
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
				//在有数据时显示申请详情信息
				$('#applicationInfo').show();
				applicationId=row.id;
				var timeTd=document.getElementById('time');
				var ownerTd=document.getElementById('owner');
				var remarkTd=document.getElementById('remark');
				timeTd.innerHTML=row.time;
				ownerTd.innerHTML=row.owner;
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
	
	function acceptResource(){
		$.post('${ctx}/user/dealTransferApplication.do', {
			aId:applicationId,
			accept:true
		}, function(result) {
			//申请已被处理
			if(result.applicationCompleted){
				$('#dialogInfo').text("申请已被处理，请勿重复提交请求！");
				$('#info-dlg').dialog('open').dialog(
						'setTitle', '警告');
			}else{
				if (result.success) {
					$('#dialogInfo').text("资产接收成功！");
					$('#info-dlg').dialog('open').dialog(
							'setTitle', '成功');
				}else{
					$('#dialogInfo').text("资产接收失败，资产所有权转移或无权限接收该资产！");
					$('#info-dlg').dialog('open').dialog(
							'setTitle', '失败');
				}
			}		

		}, 'json');		
	}
	
	function refuseResource(){
		$('#dlg').dialog('open').dialog('setTitle', '拒绝资产转移申请');
	}
	
	function submitRefuseApplication(){
		$.post('${ctx}/user/dealTransferApplication.do', {
			aId:applicationId,
			accept:false,
			remark:$('#approvalRemark').val()
		}, function(result) {
			$('#dlg').dialog('close');
			//申请已被处理
			if(result.applicationCompleted){
				$('#dialogInfo').text("申请已被处理，请勿重复提交请求！");
				$('#info-dlg').dialog('open').dialog(
						'setTitle', '警告');
			}else{
				if (result.success) {
					$('#dialogInfo').text("资产拒绝成功！");
					$('#info-dlg').dialog('open').dialog(
							'setTitle', '成功');
				}
			}		

		}, 'json');	
	}
	
	
	</script>
</body>
</html>