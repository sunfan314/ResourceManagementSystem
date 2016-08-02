<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'" title="购买申请列表">
		<div style="margin-left: 20px; margin-right: 20px; margin-top: 40px">
			<table id="dg" class="easyui-datagrid"
				data-options="toolbar:'#toolbar',fitColumns:true,singleSelect:true">
				<thead>
					<tr>
						<th data-options="field:'id',width:20,hidden:true">申请标识</th>
						<th data-options="field:'name',width:40">物品名称</th>
						<th data-options="field:'number',width:40">物品数量</th>
						<th data-options="field:'description',width:80">申购描述</th>
						<th data-options="field:'time',width:60">申请时间</th>
						<th
							data-options="field:'status',width:60,formatter:statusFormatter,styler:statusStyler,sortable:true">申请状态</th>						
						<th data-options="field:'finished',hidden:true">是否结束</th>
						<th data-options="field:'refused',hidden:true">是否拒绝</th>
						<th data-options="field:'step',hidden:true">所处步骤</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="a" items="${applications}">
						<tr>
							<td>${a.id}</td>
							<td>${a.name}</td>
							<td>${a.number}</td>
							<td>${a.description}</td>
							<td>${a.time}</td>			
							<td></td>
							<td>${a.finished}</td>
							<td>${a.refused}</td>
							<td>${a.step}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<div data-options="region:'east'" style="width: 40%" title="申请详情">
		<div id="applicationDiv"
			style="margin-left: -10px; margin-right: 40px; margin-top: 40px; display: none;">
			<table id="applicationDetailInfo" class="tableStyle">
				<tr>
					<th class="thStyle">申请提交时间</th>
					<td id="time" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">申请人</th>
					<td id="user" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">申请进度</th>
					<td id="status" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">物品名称</th>
					<td id="name" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">物品数量</th>
					<td id="number" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">申购描述</th>
					<td id="description" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">经理审核</th>
					<td id="managerApproval" class="tdStyle"></td>
				</tr>
				<tr>
					<th class="thStyle">管理员审核</th>
					<td id="adminApproval" class="tdStyle"></td>
				</tr>
			</table>
		</div>
	</div>

	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="resourcePurchase()">资产申购</a>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 420px; height: 380px; padding: 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellpadding="5">
				<tr>
					<th>资产申请人：</th>
					<td>${uid}</td>
				</tr>
				<tr>
					<th>物品名称：</th>
					<td><input name="name" class="easyui-validatebox"
						style="width: 250px;"></td>
				</tr>
				<tr>
					<th>物品数量：</th>
					<td><input name="number" class="easyui-validatebox"
						style="width: 250px;"></td>
				</tr>
				<tr>
					<th>申购描述：</th>
					<td><input name="description" class="easyui-textbox"
						data-options="multiline:true" style="width: 250px; height: 100px"></td>
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
			onclick="javascript:$('#info-dlg').dialog('close');location.reload(true);">确定</a>
	</div>

	<script type="text/javascript">
		$(function() {
			var dataSize = "${fn:length(applications)}";
			if (dataSize == 0) {
				//设置没有数据时在表格中进行提示
				$('#dg').datagrid('appendRow',{
					name : "<div style='font-weight:bold;text-align:center'>没有相关数据</div>"
				});
				$('#dg').datagrid('mergeCells', {
					index : 0,
					field : 'name',
					colspan : 5
				});
			} else {
				initDatagrid();
			}
		});

		//初始化数据表格
		function initDatagrid() {
			$('#dg').datagrid({
				//设置双击事件
				onDblClickRow : function(index, row) {
					$.post(
						'${ctx}/user/getPurchaseApplicationDetailInfo.do',
						{
							aId : row.id
						},function(result) {
							var a = result.application;
							$('#time').html(a.time);
							$('#user').html(a.user);
							if (a.finished == 1) {//申请结束
								if (a.refused == 0) {
									$('#status').html("申请通过");
								} else if (a.refused == 1) {
									$('#status').html("申请拒绝");
								}
							} else if (a.finished == 0) {//申请尚未结束
								if (a.step == 1) {
									$('#status').html("尚未审核");
								} else {
									$('#status').html("正在审核");
								}
							}
							$('#name').html(a.name);
							$('#number').html(a.number);
							$('#description').html(a.description);
							//设置经理审核和管理员审核内容
							var managerApproval = 0;
							var adminApproval = 0;
							var approvals = a.approvals;
							for (var i = 0; i < approvals.length; i++) {
								var temp = approvals[i];
								if (temp.step == 1) {
									managerApproval = temp;
								} else if (temp.step == 2) {
									adminApproval = temp;
								}
							}
							if (!managerApproval == 0) {
								var approvalResult;
								if (managerApproval.approve == 0) {
									approvalResult = "审核通过"
								} else {
									approvalResult = "审核拒绝";
								}
								$('#managerApproval').html(
										"<table><tr><th class=\"thStyle2\">审核结果</th><td>"
										+ approvalResult
										+ "</td></tr><tr><th class=\"thStyle2\">审核人</th><td>"
										+ managerApproval.reviewer
										+ "</td></tr><tr><th class=\"thStyle2\">审核时间</th><td>"
										+ managerApproval.time
										+ "</td></tr><tr><th class=\"thStyle2\">备注信息</th><td>"
										+ managerApproval.remark
										+ "</td></tr><table>");			
							} else {
								$('#managerApproval').html("经理尚未审核");
							}
							if (!adminApproval == 0) {
								var approvalResult;
								if (adminApproval.approve == 0) {
									approvalResult = "审核通过"
								} else {
									approvalResult = "审核拒绝";
								}
								$('#adminApproval').html(
										"<table><tr><th class=\"thStyle2\">审核结果</th><td>"
										+ approvalResult
										+ "</td></tr><tr><th class=\"thStyle2\">审核人</th><td>"
										+ adminApproval.reviewer
										+ "</td></tr><tr><th class=\"thStyle2\">审核时间</th><td>"
										+ adminApproval.time
										+ "</td></tr><tr><th class=\"thStyle2\">备注信息</th><td>"
										+ adminApproval.remark
										+ "</td></tr><table>");			
							} else {
								$('#adminApproval').html("管理员尚未审核");
							}	
						}, 'json');			
					$('#applicationDiv').show();
				}
			});				
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
	
		//打开资产申购dlg
		function resourcePurchase() {
			$('#dlg').dialog('open').dialog('setTitle', '资产申购');
			url = "${ctx}/user/purchaseResourceApplication.do";
		}
		
		//提交资产申购申请
		function submitApplication(){
			$('#fm').form('submit', {
				url : url,
				success : function(result) {
					$('#dlg').dialog('close'); // close the dialog
					$('#fm').form('clear');//clear the form
					result = JSON.parse(result);
					if (result.success) {
						$('#dialogInfo').text("请求提交成功！");
						$('#info-dlg').dialog('open').dialog('setTitle', '成功');
					} else {
						$('#dialogInfo').text("请求提交失败，服务器异常！");
						$('#info-dlg').dialog('open').dialog('setTitle', '失败');

					}
				}
			});
		}
	</script>
</body>
</html>