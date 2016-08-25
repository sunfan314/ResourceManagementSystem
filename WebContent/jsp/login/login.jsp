<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录界面</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui/themes/color.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
<script
	src="${pageContext.request.contextPath}/jquery/jquery-2.1.3.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
<script
	src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<style type="text/css">
.myBackground {
	background:
		url("${ctx}/resources/pic/background.png")
		no-repeat;
	background-position: 250px 150px;
}

.myInstitute {
	background:
		url("${pageContext.request.contextPath}/resources/pic/institute.png")
		no-repeat;
	background-position: 75px 12px;
}

.myBackgroundColor {
	background-color: #050505;
}
</style>

</head>
<body class="easyui-layout">
	<div data-options="region:'north',split:false" style="height: 70px;overflow:hidden" 
		class="myInstitute myBackgroundColor">
		<h2 style="font-size:30px;color:white;font-weight:bold;margin-left:100px;margin-top:5px;">数字家圆资产管理系统</h2>
	</div>
	<div data-options="region:'center',split:false" style="width: 800px"
		class="myBackground">
		<div style="margin-top: 30px; margin-left: 500px;margin-top:100px;">
			<h2 style="font-size: 60px; color: grey;">亲相见</h2>
			<h2 style="font-size: 60px; color: grey;margin-left:100px;">爱有心</h2>
		</div>
		<div style="margin-top: 80px; margin-left: 1200px;">
			<form action="${ctx}/user/userLogin.do" method="post"
				style="border-left: #CCC 2px solid">
				<div style="margin: 20px; margin-left: 50px;">
					<input type="text" name="uid" class="form-control" id="uid"
						placeholder="用户名" style="width: 250px;">
				</div>
				<div style="margin: 20px; margin-left: 50px;">
					<input type="password" name="password" class="form-control"
						id="password" placeholder="密码" style="width: 250px;">
				</div>
				<div style="margin: 20px; margin-left: 50px;">
					<button type="submit" class="btn btn-default" style="width: 250px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>