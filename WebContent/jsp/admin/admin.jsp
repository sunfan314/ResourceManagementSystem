<%@ page language="java" contentType="text/html; charset=GB18030"
	pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>资产管理系统管理员界面</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
<script
	src="${pageContext.request.contextPath}/jquery/jquery-2.1.3.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
<style>
article, aside, figure, footer, header, hgroup, menu, nav, section {
	display: block;
}

.west {
	width: 200px;
	padding: 10px;
}

.north {
	height: 80px;
}
</style>
</head>
<body class="easyui-layout">
	<div region="north" class="north" title="资产管理系统管理员界面">	
		<h2 style="margin-left:10px;display:inline-block;float:left">数字家圆资产管理系统</h2>
			
		<h3 style="margin-right:10px;display:inline-block;float:right">
			欢迎,&nbsp;${uid }&nbsp;&nbsp;&nbsp;<a href="${ctx}/user/login.do">退出登录</a>
		</h3>
	</div>
	<div region="center" title="功能面板">
		<div class="easyui-tabs" fit="true" border="false" id="tabs">
			<div title="首页">
				<h1 style="font-size: 30px; margin: 40px;">欢迎来到资产管理系统管理员界面</h1>
			</div>

		</div>
	</div>
	<div region="west" class="west" title="菜单">
		<ul id="tree"></ul>
	</div>

	<div id="tabsMenu" class="easyui-menu" style="width: 120px;">
		<div name="close">关闭</div>
		<div name="Other">关闭其他</div>
		<div name="All">关闭所有</div>
	</div>


	<script>
		//动态菜单数据
		var treeData = [ {
			text : "管理员权限",
			children : [ {
				text : "资产入库",
				children : [ {
					text : "新购资产入库",
					attributes : {
						url : "${ctx}/admin/resourceEntry.do"
					}
				} ]
			}, {
				text : "申请审核",
				children : [ {
					text : "资产分配申请",
					attributes : {
						url : "${ctx}/admin/resourceAllocation.do"
					}
				}, {
					text : "资产归还申请",
					attributes : {
						url : "${ctx}/admin/resourceCallback.do"
					}
				}, {
					text : "资产购买申请",
					attributes : {
						url : "${ctx}/admin/resourcePurchase.do"
					}
				}]
			} ]
		}, {
			text : "用户权限",
			state : "closed",
			children : [ {
				text : "资产查看",
				children : [ {
					text : "企业资产查看",
					attributes : {
						url : "${ctx}/user/companyResources.do"
					}
				}, {
					text : "职员资产查看",
					attributes : {
						url : "${ctx}/user/personalResources.do"
					}
				} ]
			}, {
				text : "资产管理",
				children : [ {
					text : "个人资产管理",
					attributes : {
						url : "${ctx}/user/userResourceManagement.do"
					}
				}, {
					text : "申请进度查看",
					attributes : {
						url : "${ctx}/user/userApplicationProcess.do"
					}
				} ]
			}, {
				text : "资产申请",
				children : [ {
					text : "在库资产申请",
					attributes : {
						url : "${ctx}/user/resourceApplyApplication.do"
					}
				}, {
					text : "资产转移接收",
					attributes : {
						url : "${ctx}/user/resourceTransferApplication.do"
					}
				}, {
					text : "资产购买请求",
					attributes : {
						url : "${ctx}/user/resourcePurchaseApplication.do"
					}
				} ]
			} ]
		} ];

		//实例化树形菜单
		$("#tree").tree({
			data : treeData,
			lines : true,
			onClick : function(node) {
				if (node.attributes) {
					Open(node.text, node.attributes.url);
				}
			}
		});

		//在右边center区域打开菜单，新增tab
		function Open(text, url) {
			if ($("#tabs").tabs('exists', text)) {
				$('#tabs').tabs('select', text);
			} else {
				var content = "<iframe frameborder='0' scrolling='auto' style='width:100%;height:99%' src="
						+ url + "></iframe>";
				$('#tabs').tabs('add', {
					title : text,
					closable : true,
					content : content
				});
			}
		};

		//绑定tabs的右键菜单
		$("#tabs").tabs({
			onContextMenu : function(e, title) {
				e.preventDefault();
				$('#tabsMenu').menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data("tabTitle", title);
			}
		});

		//实例化menu的onClick事件
		$("#tabsMenu").menu({
			onClick : function(item) {
				CloseTab(this, item.name);
			}
		});

		//几个关闭事件的实现
		function CloseTab(menu, type) {
			var curTabTitle = $(menu).data("tabTitle");
			var tabs = $("#tabs");

			if (type === "close") {
				tabs.tabs("close", curTabTitle);
				return;
			}

			var allTabs = tabs.tabs("tabs");
			var closeTabsTitle = [];

			$.each(allTabs, function() {
				var opt = $(this).panel("options");
				if (opt.closable && opt.title != curTabTitle
						&& type === "Other") {
					closeTabsTitle.push(opt.title);
				} else if (opt.closable && type === "All") {
					closeTabsTitle.push(opt.title);
				}
			});

			for (var i = 0; i < closeTabsTitle.length; i++) {
				tabs.tabs("close", closeTabsTitle[i]);
			}
		}
	</script>

</body>
</html>

</html>