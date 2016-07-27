<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在库资产申请</title>
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
		</div>

	</div>

	<div data-options="region:'center'" title="企业资产列表">
		<div id="resourceList"
			style="margin-left: 20px; margin-right: 20px; margin-top: 20px;height:85%">
		</div>
	</div>

	<div data-options="region:'east'" style="width: 40%" title="资产使用记录">
		<div id="resourceLogList"
			style="margin-left: 20px; margin-right: 20px; margin-top: 20px">
		</div>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 380px; height: 300px; padding: 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellpadding="5">
				<tr style="display: none">
					<td>资产标识</td>
					<td><input id="rid" name="rid" class="easyui-validatebox"></td>
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
		//选择资产类别之后显示该类别资产列表
		function typeSelected(value) {
			$('#resourceLogList').hide();
			type = value;
			var resourceList = document.getElementById("resourceList");
			resourceList.innerHTML = "<iframe id='resourceListIframe' name='resourceListIframe' "
					+"src='${ctx}/user/getAvailableResources.do?type="+type
					+ "' frameborder='no'  style='width:100%;height:100%'>"
					+ "</iframe>";
			$('iframe#resourceListIframe').on("load",function(){
				//为资产列表添加申请资产工具栏
				window.frames["resourceListIframe"].hideEntryResourceToolbar();
			});
			
		}

		//显示资产使用日志信息
		function showResourceLogList(rid) {
			$('#resourceLogList').show();
			var resourceLogList = document.getElementById("resourceLogList");
			resourceLogList.innerHTML = "<iframe id='resourceLogIframe' name='resourceLogIframe' "
					+"src='${ctx}/user/getResourceLogs.do?rid="+rid
					+ "' frameborder='no'  style='width:100%;'"
					+ "onload='this.height=resourceLogIframe.document.body.scrollHeight'"
					+ "></iframe>";
		}
		
		//显示资产申请dlg
		function applyResource(rid) {
			$('#dlg').dialog('open').dialog('setTitle', '在库资产申请');
			$('#rid').val(rid);
			url = "${ctx}/user/applyAvailableResource.do";
		}
		
		//提交资产申请
		function submitApplication() {
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
						$('#dialogInfo').text("请求提交失败，请重新查看资产状态！");
						$('#info-dlg').dialog('open').dialog('setTitle', '失败');

					}
				}
			});
		}
	</script>

</body>
</html>