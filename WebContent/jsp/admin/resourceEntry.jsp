<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.cn.util.ResourceTypeConfig" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新购资产入库</title>
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

tr.hide {
	display:none;
}

</style>
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

			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="addType()" style="margin-left: 30px;">新建类别</a>
		</div>

	</div>

	<div data-options="region:'center'" title="企业资产列表">
		<div id="resourceList" style="margin:20px;">
		
		</div>
	</div>

	<div data-options="region:'east'" style="width: 40%" title="资产使用记录">
		<div id="resourceLogList"style="margin:20px;">
		
		</div>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 350px; height: 520px; padding: 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellpadding="5">
				<tr>
					<th>资产类别</th>
					<td><label id="tName"></label></td>
				</tr>
				<tr style="display:none;">
					<th>资产标识</th>
					<td><input id="id" name="id" class="easyui-validatebox"
						readonly></td>
				</tr>
				<tr>
					<th>资产名称</th>
					<td><input id="name" name="name" class="easyui-validatebox"></td>
				</tr>
				<tr id="modelTr">
					<th>资产型号</th>
					<td><input id="model" name="model" class="easyui-validatebox"></td>
				</tr>
				<tr id="trackingNoTr">
					<th>追踪码</th>
					<td><input id="trackingNo" name="trackingNo"
						class="easyui-validatebox"></td>
				</tr>
				<tr id="trackingNo2Tr">
					<th>对外追踪码</th>
					<td><input id="trackingNo2" name="trackingNo2"
						class="easyui-validatebox"></td>
				</tr>
				<tr id="imeiTr">
					<th>IMEI</th>
					<td><input id="imei" name="imei" class="easyui-validatebox"></td>
				</tr>
				<tr id="serialNoTr">
					<th>序列号</th>
					<td><input id="serialNo" name="serialNo"
						class="easyui-validatebox"></td>
				</tr>
				<tr id="phoneNumberTr">
					<th id="phoneNumberTd">手机号码</th>
					<td><input id="phoneNumber" name="phoneNumber"
						class="easyui-validatebox"></td>
				</tr>
				<tr id="purchaserTr">
					<th>购买人</th>
					<td><input id="purchaser" name="purchaser" class="easyui-combobox"
						data-options="
				url:'${ctx}/admin/getUsers.do',
				method:'get',
				valueField:'value',
				textField:'text',
				panelHeight:'200px'"></td>
				</tr>
				<tr id="imsiTr">
					<th>IMSI</th>
					<td><input id="imsi" name="imsi"
						class="easyui-validatebox"></td>
				</tr>
				<tr id="packTr">
					<th>套餐信息</th>
					<td><input id="pack" name="pack"
						class="easyui-validatebox"></td>
				</tr>
				<tr id="passwordTr">	
					<th>密码</th>
					<td><input id="password" name="password"
						class="easyui-validatebox"></td>
				</tr>
				<tr>
					<th>资产状态</th>
					<td><select id="status" name="status" panelHeight="auto"
						class="easyui-combobox" editable="false">
							<option value="0">资产正常</option>
							<option value="1">资产损坏</option>
							<option value="2">资产被消耗</option>
						</select>
					</td>
				</tr>
				<tr class="hide">
					<th>资产类别</th>
					<td><input id="type" name="type.id" class="easyui-validatebox"></td>
				</tr>
				<tr id="ownerTr1">
					<th id="ownerTd">资产拥有人</th>
					<td><input id="owner" name="owner" class="easyui-combobox"
						data-options="
				url:'${ctx}/admin/getUsers.do',
				method:'get',
				valueField:'value',
				textField:'text',
				panelHeight:'200px'"></td>
				</tr>
				<tr id="ownerTr2">
					<th>资产拥有人</th>
					<td><label id="ownerLabel"></label></td>
				</tr>
				<tr id="entryDateTr1">
					<th>入库时间</th>
					<td><input id="entryDate" name="entryDate"
						class="easyui-validatebox"></td>
				</tr>
				<tr id="entryDateTr2">
					<th>入库时间</th>
					<td><label id="entryDateLabel"></label></td>
				</tr>
				<tr>
					<th>备注信息</th>
					<td><input id="remark" name="remark" class="easyui-textbox"
						data-options="multiline:true" style="height: 80px;"></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="submit()">提交</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>

	<div id="type-dlg" class="easyui-dialog"
		style="width: 300px; height: 230px; padding: 20px" closed="true"
		buttons="#type-dlg-buttons">
		<form id="type-fm">
			<table style="border-collapse: separate; border-spacing: 0px 20px;">
				<tr>
					<th>所属类目:</th>
					<td><input style="height: 30px;" id="fatherType"
						class="easyui-combobox"
						data-options="
				url:'${ctx}/admin/getFatherTypes.do',
				method:'get',
				valueField:'id',
				textField:'name',
				editable:false,
				panelHeight:'auto'
			"></td>
				</tr>
				<tr>
					<th>类别名称：</th>
					<td><input id="typeName" class="easyui-validatebox"></td>
				</tr>
			</table>
		</form>

	</div>

	<div id="type-dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="addNewType()">提交</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#type-dlg').dialog('close')">取消</a>
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
			//设置dlg中资产类别提示
			$('#tName').html($('#resourceType').combobox('getText'));
			$.post(
				"${ctx}/resource/getFatherType.do",
				{
					type:value
				},function(result){
					fatherType=result.fatherType;
				},"json"
			);
			var resourceList = document.getElementById("resourceList");
			resourceList.innerHTML = "<iframe id='resourceListIframe' name='resourceListIframe' "
				+"src='${ctx}/user/getCompanyResources.do?type="+ type
				+ "' frameborder='no'  style='width:100%;' "
				+"onload='javascript:resourceIframeHeight()'>"
				+"</iframe>";
			$('iframe#resourceListIframe').on("load",function(){
				//为资产列表添加管理资产工具栏
				window.frames["resourceListIframe"].hideApplyResourceToolbar();
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
		
		//创建新建类别dlg
		function addType(){
			$('#type-dlg').dialog('open').dialog('setTitle', '新建类别');
		}
		
		//创建新类别
		function addNewType(){
			$.post('${ctx}/admin/addType.do', {
				typeName:$('#typeName').val(),
				fatherType:$('#fatherType').combobox('getValue')
			}, function(result) {
				$('#type-dlg').dialog('close');
				//申请已被处理
				if (result.success) {
					$('#dialogInfo').text("类别添加成功！");
					$('#info-dlg').dialog('open').dialog('setTitle', '成功');
					$('#type-fm').form('clear');
					//刷新类别列表数据
					$('#resourceType').combobox('reload');
					
				} else{
					$('#dialogInfo').text("类别添加失败，该类别已存在！");
					$('#info-dlg').dialog('open').dialog('setTitle', '失败');
				}
			}, 'json');
		}
	
		//入库新购资产
		function addResource() {
			$('#dlg').dialog('open').dialog('setTitle', '新购资产入库');
			$('#fm').form('clear');
			//由于id传递到后台的resource对象属性中，所以不能是空字符，传入0在后台进行处理
			$('#id').val(0);
			$('#type').val(type);
			//设置入库时间为当前时间
			$('#entryDate').val(getTime());
			//设置资产状态为正常状态
			$('#status').combobox('setValue', 0);
			//设置资产拥有人为仓库
			$('#owner').combobox('setValue', 'warehouse');
			//控制组件的显示与隐藏
			$('#ownerTr1').removeClass();
			$('#ownerTr2').addClass("hide");
			$('#entryDateTr1').removeClass();
			$('#entryDateTr2').addClass("hide");
			//根据类别决定显示的组件
			componentDisplay();
			url = "${ctx}/admin/enterNewResource.do";
		}

		//编辑资产信息
		function editResource(row) {
			$('#dlg').dialog('open').dialog('setTitle', '编辑资产信息');
			//控制组件的显示与隐藏
			$('#ownerTr1').addClass("hide");
			$('#ownerTr2').removeClass();
			$('#entryDateTr1').addClass("hide");
			$('#entryDateTr2').removeClass();
			//根据类别决定显示的组件
			componentDisplay();
			//设置资产信息的显示
			$('#id').val(row.id);
			$('#name').val(row.name);
			$('#model').val(row.model);
			$('#trackingNo').val(row.trackingNo);
			$('#trackingNo2').val(row.trackingNo2);
			$('#imei').val(row.imei);
			$('#serialNo').val(row.serialNo);
			$('#phoneNumber').val(row.phoneNumber);
			$('#purchaser').combobox('setValue',row.purchaser);
			$('#imsi').val(row.imsi);
			$('#pack').val(row.pack);
			$('#password').val(row.password);
			$('#status').combobox('setValue', row.status);
			$('#type').val(type);
			$('#owner').combobox('setValue', row.owner);
			$('#entryDate').val(row.entryDate);
			$('#remark').textbox('setValue', row.remark);
			if(row.owner=="warehouse"){
				$('#ownerLabel').html("仓库");
			}else{
				$('#ownerLabel').html(row.owner);
			}
			
			$('#entryDateLabel').html(row.entryDate);
			url = "${ctx}/admin/editResource.do";
		}

		//提交请求
		function submit() {
			$('#fm').form('submit', {
				url : url,
				success : function(result) {
					$('#dlg').dialog('close'); // close the dialog
					result = JSON.parse(result);
					if (result.success) {
						//iframe页面的刷新
						document.getElementById('resourceListIframe').contentWindow.location.reload(true);
						//$('#dialogInfo').text("操作成功，已成功添加（修改）资产信息");
						//$('#info-dlg').dialog('open').dialog('setTitle', '成功');
					} else {
						$('#dialogInfo').text("操作失败，资产拥有人不存在，请在列表中选择资产拥有人！");
						$('#info-dlg').dialog('open').dialog('setTitle', '失败');
					}

				}
			});
		}
		
		//根据类别显示不同的dialog
		function componentDisplay(){
			//SIM卡
			if(type==<%=ResourceTypeConfig.SIM_CARD%>){
				$('#modelTr').addClass("hide");
				$('#trackingNoTr').addClass("hide");
				$('#trackingNo2Tr').addClass("hide");
				$('#imeiTr').addClass("hide");
				$('#serialNoTr').addClass("hide");
				$('#phoneNumberTr').removeClass();
				$('#purchaserTr').addClass("hide");
				$('#imsiTr').removeClass();
				$('#packTr').removeClass();
				$('#passwordTr').removeClass();
				$('#phoneNumberTd').html("手机号码");
				$('#ownerTd').html("资产拥有人");
				
			}//手机充值卡
			else if(type==<%=ResourceTypeConfig.PHONE_CARD%>){
				$('#modelTr').addClass("hide");
				$('#trackingNoTr').addClass("hide");
				$('#trackingNo2Tr').addClass("hide");
				$('#imeiTr').addClass("hide");
				$('#serialNoTr').addClass("hide");
				$('#phoneNumberTr').removeClass();
				$('#purchaserTr').removeClass();
				$('#imsiTr').addClass("hide");
				$('#packTr').addClass("hide");
				$('#passwordTr').addClass("hide");
				$('#phoneNumberTd').html("充值号码");
				$('#ownerTd').html("领用人");
				$('#purchaser').combobox('setValue', 'warehouse');
			}//消耗类物品
			else if(fatherType==<%=ResourceTypeConfig.CONSUMABLE%>){
				$('#modelTr').addClass("hide");
				$('#trackingNoTr').addClass("hide");
				$('#trackingNo2Tr').addClass("hide");
				$('#imeiTr').addClass("hide");
				$('#serialNoTr').addClass("hide");
				$('#phoneNumberTr').addClass("hide");
				$('#purchaserTr').removeClass();
				$('#imsiTr').addClass("hide");
				$('#packTr').addClass("hide");
				$('#passwordTr').addClass("hide");
				$('#ownerTd').html("领用人");
				$('#purchaser').combobox('setValue', 'warehouse');
				
			}//通用设备
			else{
				$('#modelTr').removeClass();
				$('#trackingNoTr').removeClass();
				$('#trackingNo2Tr').removeClass();
				$('#imeiTr').removeClass();
				$('#serialNoTr').removeClass();
				$('#phoneNumberTr').addClass("hide");
				$('#purchaserTr').addClass("hide");
				$('#imsiTr').addClass("hide");
				$('#packTr').addClass("hide");
				$('#passwordTr').addClass("hide");
				$('#ownerTd').html("资产拥有人");
			}
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
				//考虑到不同浏览器兼容性问题，在计算出的高度上加20px以免出现滚动条
				ifm.height = subWeb.body.scrollHeight+20;
			}
		}
		
		//显示当前时间
		function getTime(){
			var date = new Date();
			var year=date.getFullYear();
			//月份的显示范围为0-11
			var month=format(date.getMonth()+1);		
			var day=format(date.getDay());
			var hour=format(date.getHours());
			var minute=format(date.getMinutes());
			var second=format(date.getSeconds());
			var timeStr=year+"/"+month+"/"+day+" "+hour+":"+minute+":"+second;
			return timeStr;
		}
		
		//对日期显示格式化
		function format(value){
			if(value<10){
				return "0"+value;
			}else{
				return value;
			}
		}
		
	</script>
</body>
</html>