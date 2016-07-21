<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业资产查看</title>
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
		<div id="resourceList" style="margin-left: 20px; margin-right: 20px; margin-top: 20px">
		</div>
	</div>
	
	
	<div data-options="region:'east'" style="width: 40%;" title="资产使用记录">
		<div id="resourceLogList" style="margin-left: 20px; margin-right: 20px; margin-top: 20px">
		</div>
	</div>


	<script type="text/javascript">
		function typeSelected(value) {
			$('#resourceLogList').hide();
			type = value;
			var resourceList = document.getElementById("resourceList");
			resourceList.innerHTML = "<iframe name='resourceListIframe' src='${ctx}/user/getCompanyResources.do?type="
					+ type+ "' frameborder='no'  style='width:100%;'>"
					+"onload='this.height=resourceListIframe.document.body.scrollHeight'"
					+"</iframe>";
		}
		
		function showResourceLogList(rid){
			$('#resourceLogList').show();
			var resourceLogList=document.getElementById("resourceLogList");
			resourceLogList.innerHTML="<iframe name='resourceLogIframe' src='${ctx}/user/getResourceLogs.do?rid="
				+ rid+ "' frameborder='no'  style='width:100%;'"
				+"onload='this.height=resourceLogIframe.document.body.scrollHeight'"
				+"></iframe>";
		}
	</script>

</body>
</html>