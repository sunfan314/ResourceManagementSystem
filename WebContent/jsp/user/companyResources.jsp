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
				editable:false,
				panelHeight:'auto',
				onChange:function(newValue, oldValue){ typeSelected(newValue)}
			">
		</div>
	</div>

	<div data-options="region:'center'" title="企业资产列表">
		<div id="resourceList" style="margin:20px;">
		</div>
	</div>
	
	
	<div data-options="region:'east'" style="width: 40%;" title="资产使用记录">
		<div id="resourceLogList" style="margin:20px;">
		</div>
	</div>


	<script type="text/javascript">
		//选择资产类别之后显示该类别资产列表
		function typeSelected(value) {
			$('#resourceLogList').hide();
			type = value;
			var resourceList = document.getElementById("resourceList");
			resourceList.innerHTML = "<iframe id='resourceListIframe' name='resourceListIframe' "
					+"src='${ctx}/user/getCompanyResources.do?type="+ type
					+ "' frameborder='no'  style='width:100%;'"
					+"onload='javascript:resourceIframeHeight()'>"
					+"</iframe>";
			$('iframe#resourceListIframe').on("load",function(){
				//为资产列表添加管理资产工具栏
				window.frames["resourceListIframe"].hideToolbars();
			});		
		}
		
		//显示资产使用日志信息
		function showResourceLogList(rid){
			$('#resourceLogList').show();
			var resourceLogList=document.getElementById("resourceLogList");
			resourceLogList.innerHTML="<iframe id='resourceLogIframe' name='resourceLogIframe' "
				+"src='${ctx}/user/getResourceLogs.do?rid="+ rid
				+ "' frameborder='no'  style='width:100%;'"
				+"onload='javascript:resourceLogIframeHeight()'"
				+"></iframe>";
		}
		
		//iFrame(resourceListIframe)自适应高度
		function resourceIframeHeight() {
			var ifm= document.getElementById("resourceListIframe");
			var subWeb = document.frames ? document.frames["resourceListIframe"].document:ifm.contentDocument;
			if(ifm != null && subWeb != null) {
				ifm.height = subWeb.body.scrollHeight;
			}
		} 
		
		//iFrame(resourceLogIframe)自适应高度
		function resourceLogIframeHeight(){
			var ifm= document.getElementById("resourceLogIframe");
			var subWeb = document.frames ? document.frames["resourceLogIframe"].document:ifm.contentDocument;
			if(ifm != null && subWeb != null) {
				//考虑到不同浏览器兼容性问题，在计算出的高度上加10px以免出现滚动条
				ifm.height = subWeb.body.scrollHeight+10;
			}
		}
		
		
	</script>

</body>
</html>