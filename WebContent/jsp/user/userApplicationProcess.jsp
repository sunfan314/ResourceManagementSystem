<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>申请进度查看</title>
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

td.tdStyle {
	padding-left: 10px;
	border-width: 2px;
	border-style: solid;
	border-color: #95B8E7;
	background-color: #ffffff;
	width: 600px;
}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'" title="申请列表">
		<div style="margin-left: 20px; margin-right: 20px; margin-top: 40px">
			<table id="applicationDatagrid" class="easyui-datagrid"
				toolbar="#toolbar"
				data-options="url:'${ctx}/user/getUserApplications.do',fitColumns:true,singleSelect:true">
				<thead>
					<tr>
						<th data-options="field:'id',width:20,hidden:true">申请标识</th>
						<th data-options="field:'resourceName',width:60">资产名称</th>
						<th data-options="field:'owner',width:50,formatter:userFormatter">资产拥有人</th>
						<th data-options="field:'receiver',width:50,formatter:userFormatter">资产接收人</th>
						<th data-options="field:'time',width:50">提交时间</th>
						<th data-options="field:'type',width:60,formatter:typeFormatter">申请类型</th>
						<th
							data-options="field:'status',width:60,formatter:statusFormatter,styler:statusStyler,sortable:true">申请状态</th>
						<th data-options="field:'finished',hidden:true">是否结束</th>
						<th data-options="field:'refused',hidden:true">是否拒绝</th>
						<th data-options="field:'step',hidden:true">所处步骤</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<div data-options="region:'east'" style="width: 35%" title="申请详情">
		<div id="applicationDiv"
			style="margin-left: 20px; margin-right: 20px; margin-top: 40px">
			<table id="applicationDetailInfo" class="tableStyle">
				<tr>
					<th class="thStyle">申请提交时间</th>
					<td id="time" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">申请类型</th>
					<td id="type" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">申请进度</th>
					<td id="status" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">资产拥有者</th>
					<td id="owner" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">资产接收者</th>
					<td id="receiver" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">资产详情</th>
					<td class="tdStyle">
						<table>
							<tr>
								<th class="thStyle2">资产类型</th>
								<td id="rType"></td>
							</tr>
							<tr>
								<th class="thStyle2">资产标识</th>
								<td id="rId"></td>
							</tr>
							<tr>
								<th class="thStyle2">资产名称</th>
								<td id="rName"></td>
							</tr>
							<tr>
								<th class="thStyle2">资产型号</th>
								<td id="rModel"></td>
							</tr>
							<tr>
								<th class="thStyle2">追踪码</th>
								<td id="rTrackingNo"></td>
							</tr>
							<tr>
								<th class="thStyle2">IMEI</th>
								<td id="rIMEI"></td>
							</tr>
							<tr>
								<th class="thStyle2">序列号</th>
								<td id="rSerialNo"></td>
							</tr>
							<tr>
								<th class="thStyle2">备注信息</th>
								<td id="rRemark"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th class="thStyle">申请备注</th>
					<td id="remark" class="tdStyle"></td>
				</tr>
				<tr id="managerTr">
					<th class="thStyle">经理审核</th>
					<td id="managerApproval" class="tdStyle"></td>
				</tr>
				<tr id="adminTr">
					<th class="thStyle">管理员审核</th>
					<td id="adminApproval" class="tdStyle"></td>
				</tr>
				<tr id="receiverTr">
					<th class="thStyle">接收人审核</th>
					<td id="receiverApproval" class="tdStyle"></td>
				</tr>
			</table>
		</div>
	</div>

	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" plain="true"
			onclick="applyResourceApplications()">在库资产申请</a> <a href="#"
			class="easyui-linkbutton" plain="true"
			onclick="transferResourceApplications()">资产转移申请</a> <a href="#"
			class="easyui-linkbutton" plain="true"
			onclick="returnResourceApplications()">资产归还申请</a><a href="#"
			class="easyui-linkbutton" plain="true" onclick="allApplications()">查看所有申请</a>
	</div>

	<script type="text/javascript">
	
		$(function() {
			$('#applicationDatagrid').datagrid({
				//设置双击事件
				onDblClickRow : function(index, row) {
					$.post('${ctx}/user/getApplicationDetailInfo.do', {
						aId : row.id
					}, function(result) {
						var a=result.application;
						var r=result.application.resource;
						
						var timeTd=document.getElementById("time");
						var typeTd=document.getElementById("type");
						var statusTd=document.getElementById("status");
						var ownerTd=document.getElementById("owner");
						var receiverTd=document.getElementById("receiver");
						var rTypeTd=document.getElementById("rType");
						var rIdTd=document.getElementById("rId");
						var rNameTd=document.getElementById("rName");
						var rModelTd=document.getElementById("rModel");
						var rTrackingNoTd=document.getElementById("rTrackingNo");
						var rIMEITd=document.getElementById("rIMEI");
						var rSerialNoTd=document.getElementById("rSerialNo");
						var rRemarkTd=document.getElementById("rRemark");
						var remarkTd=document.getElementById("remark");
						var managerTr=document.getElementById("managerTr");
						var adminTr=document.getElementById("adminTr");
						var receiverTr=document.getElementById("receiverTr");
						var managerApprovalTd=document.getElementById("managerApproval");
						var adminApprovalTd=document.getElementById("adminApproval");
						var receiverApprovalTd=document.getElementById("receiverApproval");
											
						timeTd.innerHTML=a.time;
						if(a.type==0){
							typeTd.innerHTML="在库资产分配";
							managerTr.style.display="";
							adminTr.style.display="";
							receiverTr.style.display="none";
						}else if(a.type==1){
							typeTd.innerHTML="个人资产转移";
							managerTr.style.display="none";
							adminTr.style.display="none";
							receiverTr.style.display="";
						}else if(a.type==2){
							typeTd.innerHTML="个人资产归还";
							managerTr.style.display="none";
							adminTr.style.display="";
							receiverTr.style.display="none";
						}
						if(a.finished==1){
							if(a.refused==0){
								statusTd.innerHTML="申请通过";
							}else if(a.refused==1){
								statusTd.innerHTML="申请拒绝";
							}
						}else if(a.finished==0){
							if(a.step==1){
								statusTd.innerHTML="尚未审核";
							}else{
								statusTd.innerHTML="正在审核";
							}
						}
						ownerTd.innerHTML=a.owner;
						receiverTd.innerHTML=a.receiver;
						rTypeTd.innerHTML=a.type;
						rIdTd.innerHTML=r.id;
						rNameTd.innerHTML=r.name;
						rModelTd.innerHTML=r.model;
						rTrackingNoTd.innerHTML=r.trackingNo;
						rIMEITd.innerHTML=r.imei;
						rSerialNoTd.innerHTML=r.serialNo;
						rRemarkTd.innerHTML=r.remark;
						remarkTd.innerHTML=a.remark;
						
					}, 'json');
				}
			});
		});
		
		//资产拥有人和资产接收人单元格格式
		function userFormatter(value,row,index){
			if(value=="warehouse"){
				return "仓库";
			}else{
				return value;
			}
		}
		
		//申请类型单元格格式
		function typeFormatter(value, row, index) {
			if (value == 0) {
				return "在库资产申请";
			} else if (value == 1) {
				return "个人资产转移";
			} else if (value == 2) {
				return "个人资产归还";
			}
		}
		//申请状态单元格格式
		function statusFormatter(value, row, index) {
			if (row.finished == 1) {//申请结束
				if (row.refused == 0) {
					return "申请通过";
				} else if (row.refused == 1) {
					return "申请拒绝";
				}
			} else if (row.finished == 0) {//申请尚未结束
				if (row.step == 1) {
					return "尚未审核";
				} else {
					return "正在审核";
				}
			}
		}

		//申请状态单元格样式
		function statusStyler(value, row, index) {
			if (row.finished == 1) {
				if (row.refused == 0) {
					//申请通过审核
					return "color:green;";
				} else if (row.refused == 1) {
					//申请被拒绝
					return "color:red;";
				}
			} else if (row.finished == 0) {
				if (row.step == 1) {
					//申请尚未审核
					return "color:black;";
				} else {
					//申请正在审核
					return "color:grey;";
				}
			}
		}

		function applyResourceApplications() {
			$('#applicationDatagrid').datagrid({
				url : '${ctx}/user/getUserResourceApplyApplications.do'
			});
		}

		function transferResourceApplications() {
			$('#applicationDatagrid').datagrid({
				url : '${ctx}/user/getUserResourceTransferApplications.do'
			});
		}

		function returnResourceApplications() {
			$('#applicationDatagrid').datagrid({
				url : '${ctx}/user/getUserResourceReturnApplications.do'
			});
		}

		function allApplications() {
			$('#applicationDatagrid').datagrid({
				url : '${ctx}/user/getUserApplications.do'
			});
		}
	</script>

</body>
</html>