<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="net.cn.util.ResourceStatusConfig" %>
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
		<div id="resourceList" style="margin:20px;height:85%">

		</div>
	</div>

	<div data-options="region:'east'" style="width: 40%" title="资产使用记录">
		<div id="resourceLogList" style="margin:20px;">

		</div>
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
				panelHeight:'200px'"></td>
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
		$(function(){
			resourceList.innerHTML = "<iframe id='resourceListIframe' name='resourceListIframe' "
				+ "src='${ctx}/user/getPersonalResources.do?uid=${uid}"
				+ "' frameborder='no'  style='width:100%;height:100%;'"
				+ "></iframe>";
		});
		
		//显示资产使用日志信息
		function showResourceLogList(rid) {
			$('#resourceLogList').show();
			var resourceLogList = document.getElementById("resourceLogList");
			resourceLogList.innerHTML = "<iframe id='resourceLogIframe' name='resourceLogIframe'"
					+ " src='${ctx}/user/getResourceLogs.do?rid="+ rid
					+ "' frameborder='no'  style='width:100%;'"
					+ "onload='javascript:resourceLogIframeHeight()'"
					+ "></iframe>";
		}
		
		//资产归还入库
		function returnResource(rid,statusValue) {
			//资产被消耗
			if(statusValue=='<%=ResourceStatusConfig.RESOURCE_USED%>'){
				$('#dialogInfo').text("请求提交失败，被消耗资产无法归还入库！");
				$('#info-dlg').dialog('open').dialog(
						'setTitle', '失败');
				return;
			}
			var ui1 = document.getElementById("resourceReceiverLabel");
			var ui2 = document.getElementById("resourceReceiver");
			ui1.style.display = "none";
			ui2.style.display = "none";
			$('#dlg').dialog('open').dialog('setTitle', '资产归还入库');
			$('#rid').val(rid);
			url = "${ctx}/user/returnResourceApplication.do";
		}
		
		//资产转移
		function transferResource(rid,statusValue) {
			if(statusValue=='<%=ResourceStatusConfig.RESOURCE_USED%>'){
				$('#dialogInfo').text("请求提交失败，被消耗资产无法进行转移！");
				$('#info-dlg').dialog('open').dialog(
						'setTitle', '失败');
				return;
			}
			var ui1 = document.getElementById("resourceReceiverLabel");
			var ui2 = document.getElementById("resourceReceiver");
			ui1.style.display = "";
			ui2.style.display = "";
			$('#dlg').dialog('open').dialog('setTitle', '资产转移');
			$('#rid').val(rid);
			url = "${ctx}/user/transferPersonalResourceApplication.do";
		}
	
		//提交请求
		function submitApplication() {
			$('#fm').form(
					'submit',
					{
						url : url,
						success : function(result) {
							$('#dlg').dialog('close'); // close the dialog
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
		
		//iFrame(resourceLogIframe)自适应高度
		function resourceLogIframeHeight(){
			var ifm= document.getElementById("resourceLogIframe");
			var subWeb = document.frames ? document.frames["resourceLogIframe"].document:ifm.contentDocument;
			if(ifm != null && subWeb != null) {
				//考虑到不同浏览器兼容性问题，在计算出的高度上加20px以免出现滚动条
				ifm.height = subWeb.body.scrollHeight+20;
			}
		}
	</script>
</body>
</html>