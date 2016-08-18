<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="net.cn.util.ResourceTypeConfig"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人资产列表</title>
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
table.tbStyle {
	margin-top:5px;
	margin-bottom:10px;
	border-spacing:5px;
}
th.thStyle {
	text-align: left;
	padding-left: 20px;
	padding-right: 15px;
	width: 80px;
}

td.tdStyle {
	border-style: none;
}
</style>
</head>
<body>
	<div>
		<table id="dg" class="easyui-datagrid"
			data-options="fitColumns:true,singleSelect:true">
			<thead>
				<tr>
					<th data-options="field:'id',width:30,hidden:true">资产标识</th>
					<th data-options="field:'name',width:50">资产名称</th>
					<th data-options="field:'entryDate',width:50">入库时间</th>
					<th data-options="field:'statusValue',width:40">资产状态</th>
					<th data-options="field:'remark',width:80">备注信息</th>
					<th data-options="field:'type',hidden:true">资产类别</th>
					<th data-options="field:'fatherType',hidden:true">资产父类别</th>
					<th data-options="field:'model',hidden:true">资产型号</th>
					<th data-options="field:'trackingNo',hidden:true">追踪码</th>
					<th data-options="field:'trackingNo2',hidden:true">对外追踪码</th>
					<th data-options="field:'imei',hidden:true">IMEI</th>
					<th data-options="field:'serialNo',hidden:true">序列号</th>
					<th data-options="field:'phoneNumber',hidden:true">手机号码</th>
					<th data-options="field:'purchaser',hidden:true">购买人</th>
					<th data-options="field:'imsi',hidden:true">IMSI</th>
					<th data-options="field:'pack',hidden:true">套餐信息</th>
					<th data-options="field:'password',hidden:true">密码</th>
					<th data-options="field:'owner',hidden:true">资产拥有人</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="r" items="${resources}">
					<tr>
						<td>${r.id}</td>
						<td>${r.name}</td>
						<td>${r.entryDate}</td>
						<td>${r.statusValue}</td>
						<td>${r.remark}</td>
						<td>${r.type.id}</td>
						<td>${r.type.fatherType}</td>
						<td>${r.model}</td>
						<td>${r.trackingNo}</td>
						<td>${r.trackingNo2}</td>
						<td>${r.imei}</td>
						<td>${r.serialNo}</td>
						<td>${r.phoneNumber}</td>
						<td>${r.purchaser}</td>
						<td>${r.imsi}</td>
						<td>${r.pack}</td>
						<td>${r.password}</td>
						<td>${r.owner}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<div id="manageResourceToolbar" style="display:none">
		<a href="#" class="easyui-linkbutton" iconCls="icon-return"
			plain="true" onclick="returnResource()">资产归还</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-transfer" plain="true"
			onclick="transferResource()">资产转移</a>
	</div>

	<script type="text/javascript">
		$(function() {
			var dataSize="${fn:length(resources)}";
			if(dataSize==0){
				//设置没有数据时在表格中进行提示
				$('#dg').datagrid('appendRow',{
					name:"<div style='font-weight:bold;text-align:center'>没有相关数据</div>"
				});
				$('#dg').datagrid('mergeCells',{
					index:0,
					field:'name',
					colspan:4
				});
			}else{
				initDatagrid();
			}
		});
		
		//初始化数据表
		function initDatagrid(){
			$('#dg').datagrid({
				toolbar:'#manageResourceToolbar',
				view : detailview,
				detailFormatter : function(index, row) {
					//SIM卡
					if (row.type ==<%=ResourceTypeConfig.SIM_CARD%>) {
						return "<table class='tbStyle'><tr><th class='thStyle'>手机号码</th><td class='tdStyle'>"
								+ row.phoneNumber
								+ "</td></tr><tr><th class='thStyle'>IMSI</th><td class='tdStyle'>"
								+ row.imsi
								+ "</td></tr><tr><th class='thStyle'>套餐信息</th><td class='tdStyle'>"
								+ row.pack
								+ "</td></tr><tr><th class='thStyle'>密码</th><td class='tdStyle'>"
								+ row.password
								+ "</td></tr></table>";
					}//手机充值卡
					else if (row.type ==<%=ResourceTypeConfig.PHONE_CARD%>) {
						return "<table class='tbStyle'><tr><th class='thStyle'>充值号码</th><td class='tdStyle'>"
								+ row.phoneNumber
								+ "</td></tr><tr><th class='thStyle'>购买人</th><td class='tdStyle'>"
								+ row.purchaser
								+ "</td></tr><tr><th class='thStyle'>领用人</th><td class='tdStyle'>"
								+ row.owner
								+ "</td></tr></table>";
					}//一般消耗品
					else if (row.fatherType ==<%=ResourceTypeConfig.CONSUMABLE%>) {
						return "<table class='tbStyle'><tr><th class='thStyle'>购买人</th><td class='tdStyle'>"
								+ row.purchaser
								+ "</td></tr><tr><th class='thStyle'>领用人</th><td class='tdStyle'>"
								+ row.owner
								+ "</td></tr></table>";
					}//通用设备
					else {
						return "<table class='tbStyle'><tr><th class='thStyle'>设备型号</th><td class='tdStyle'>"
								+ row.model
								+ "</td></tr><tr><th class='thStyle'>追踪码</th><td class='tdStyle'>"
								+ row.trackingNo
								+"</td></tr><tr><th class='thStyle'>对外追踪码</th><td class='tdStyle'>"
								+ row.trackingNo2
								+ "</td></tr><tr><th class='thStyle'>IMEI</th><td class='tdStyle'>"
								+ row.imei
								+ "</td></tr><tr><th class='thStyle'>序列号</th><td class='tdStyle'>"
								+ row.serialNo
								+ "</td></tr></table></div>";
					}
				},		
				//设置双击显示资产使用日志
				onDblClickRow : function(index, row) {
					parent.showResourceLogList(row.id);
				}		
			});	
		}
		
		//****************职员资产查看--开始*************************
		//隐藏工具栏
		function hideToolbar() {
			$('#manageResourceToolbar').hide();
		}
		//****************职员资产查看--结束*************************

		
		//****************个人资产管理--开始*************************
		//资产归还
		function returnResource() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				parent.returnResource(row.id,row.statusValue);
			}
		}

		//资产转移
		function transferResource() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				parent.transferResource(row.id,row.statusValue);
			}
		}
		//****************个人资产管理--结束*************************
	</script>
</body>
</html>