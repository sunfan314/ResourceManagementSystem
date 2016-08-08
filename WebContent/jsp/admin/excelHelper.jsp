<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据导入与导出</title>
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
th.thStyle {
	font-size:15px;
}

td.tdStyle {
	font-size:15px;
	font-weight:bold;
}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center'" title="企业资产数据导入">
				<div style="margin-left: 200px; margin-top: 100px;">
					<form id="fm" method="post" enctype="multipart/form-data">
						<table cellspacing="20">
							<tr>
								<th>资产类别选择：</th>
								<td><input style="height: 30px; width: 100%"
									class="easyui-combobox" id="type" name="type"
									data-options="
									url:'${ctx}/user/getResourceTypes.do',
									method:'get',
									valueField:'value',
									textField:'text',
									groupField:'group',
									editable:false,
									panelHeight:'auto'
									">
								</td>
							</tr>
							<tr>
								<td colspan="2"><input class="easyui-filebox" id="file"
									name="file" data-options="prompt:'请选择一个文件'"
									style="height: 30px; width: 100%;"></td>
							</tr>
							<tr>
								<td colspan="2"><a href="javascript:void(0)"
									class="easyui-linkbutton" style="width: 100%;"
									onclick="uploadFile()">上传数据</a></td>
							</tr>
						</table>

					</form>
				</div>
			</div>
			<div data-options="region:'south'" style="height: 50%;"
				title="企业资产数据导出">
				<div style="margin-left: 200px; margin-top: 100px;">
					<table cellspacing="20">
						<tr>
							<th>资产类别选择：</th>
							<td><input style="height: 30px; width: 100%"
								class="easyui-combobox" id="type_1" name="type_1"
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
							</td>
						</tr>
						<tr>
							<td colspan="2"><a href="javascript:prompt()" id="exportBn"
								class="easyui-linkbutton" style="width: 100%;">导出数据</a></td>
						</tr>
					</table>					
				</div>
			</div>
		</div>
	</div>

	<div data-options="region:'east'" style="width: 60%;" title="操作执行结果">
		<div style="margin:80px;height:80%">
			<table id="logs" cellspacing="20">
			</table>
		</div>

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
		$(function() {
			$('#file').filebox({
				buttonText : '选择文件'
			});
		});
		
		//上传数据并导入数据到数据库中
		function uploadFile() {
			if($('#type').combobox('getValue')==""){
				$('#dialogInfo').text("请选择资产类别");
				$('#info-dlg').dialog('open').dialog('setTitle', '提示');
				return;
			}
			if ($('#file').filebox('getValue') == "") {
				$('#dialogInfo').text("请选择数据文件");
				$('#info-dlg').dialog('open').dialog('setTitle', '提示');
				return;
			}
			$('#fm').form('submit', {
				url : "${ctx}/admin/uploadFile.do",
				success : function(result) {
					result = JSON.parse(result);
					if (result.success) {
						var importResult="<tr><td colspan='2' class='tdStyle' style='font-size=20px;'>操作成功</td></tr>";
						importResult=importResult+"<tr><td class='tdStyle'>导入成功</td><td class='tdStyle'>"+result.successCount+"条资产信息</td></tr>";
						importResult=importResult+"<tr><td class='tdStyle'>导入失败</td><td class='tdStyle'>"+result.failCount+"条资产信息</td></tr>";
						for(var i=0;i<result.logs.length;i++){
							var errorLog=result.logs[i];
							importResult=importResult+"<tr><td class='tdStyle'>第"+errorLog.index+"条记录</td><td class='tdStyle'>用户名 "+errorLog.user+" 不存在</td></tr>";
						}
						$('#logs').html(importResult);
					} else {
						var importResult="<tr><td class='tdStyle' style='font-size=20px;'>"
						+"操作失败，请检查导入文件是否为excel格式以及导入数据的格式是否正确</td></tr>";
						$('#logs').html(importResult);
					}
				}
			});
		}
		
		//设置下载链接
		function typeSelected(value){
			document.getElementById('exportBn').href="${ctx}/admin/downloadFile.do?type="+value;
		}
		
		//提示用户选择资产类别
		function prompt(){
			$('#dialogInfo').text("请选择资产类别");
			$('#info-dlg').dialog('open').dialog('setTitle', '提示');
		}
	</script>


</body>
</html>