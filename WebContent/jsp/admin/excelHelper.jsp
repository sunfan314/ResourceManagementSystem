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
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center'" title="企业资产数据导入">
				<div style="margin-left:200px;margin-top:100px;">
					<form id="fm" method="post" enctype="multipart/form-data">
						<table cellspacing="20">
							<tr>
								<th>资产类别选择：</th>
								<td><input style="height: 30px; width: 100%"
									class="easyui-combobox" name="type"
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
								<td colspan="2"><input class="easyui-filebox" id="file" name="file"
									data-options="prompt:'请选择一个文件'"
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
				title="企业资产数据导出"></div>
		</div>
	</div>

	<div data-options="region:'east'" style="width: 40%;" title="操作执行结果">

	</div>






	<script type="text/javascript">
		function uploadFile() {
			if($('#file').filebox('getValue')==""){
				alert("请选择文件");
				return;
			}
			$('#fm').form('submit', {
				url : "${ctx}/admin/uploadFile.do",
				success : function(result) {
					result = JSON.parse(result);
					if(result.success){
						alert("导入数据成功");
					}else{
						alert("导入数据失败");
					}
				}
			});
		}
	</script>


</body>
</html>