<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration, java.util.LinkedHashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>
<%@ taglib prefix="cip" uri="/cip-1.0-tag"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="chs" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="chs" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="chs">
<!--<![endif]-->
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<script type="text/javascript" src="/resources/own/script/common.js"></script>
</head>
<script type="text/javascript"> 
		Common.init();
</script>
<body>
<div class="page-content">
	<div class="portlet box ">
		<div class="portlet-body ">
			<!-- table -->
			<!-- primaryKey -->
			<!-- action -->
			<!-- page -->
			<!-- checkbox -->
			<cip:grid-list table="T_CIPMP_MYORDER" primaryKey="orderid" action="${p:go('','execute','') }" page="${page}" checkbox="true">

				<!-- view i|m|e -->
				<!-- align left|center|right -->
				<!-- width px|% -->
				<!-- query eq|like|range|timerange -->
				<!-- querylength px|% -->
 				<!-- hidden true|false -->
  				<!-- sortable true|false -->
  				<!-- replace old1_new1,old2_new2,old3_new3 -->
  				<!-- timepattern yyyy-MM-dd HH:mm:ss -->
 				<cip:column view="" name="orderid" display="订单ID" hidden="true"/><!-- 必须要有primaryKey字段 -->
				<cip:column view="im" name="orderno" display="订单号" width="200px" query="like" querylength="400px" hidden="false"/>
				<cip:column view="i" name="actualmoney" display="实付金额" align="right" width="100px" sortable="true" query="range"/>
				<cip:column view="i" name="actualnumber" display="商品数量" align="right" width="100px" sortable="true"/>
				<cip:column view="mi" name="ordername" display="会员姓名" width="200px" query="like"/>
				<cip:column view="mi" name="ordertelephone" display="会员电话" width="200px"/>
				<cip:column view="mi" name="ordertype" display="会员性别" align="left" width="100px" replace="男_0,女_1,未知_2"/>
				<cip:column view="mi" name="orderstate" display="订单状态" align="left" width="100px" replace="未付款_0,已付款_1,已发货_2,已完成_3,哈哈_4" query="eq"/>
				<cip:column view="mi" name="ordertime" display="下单时间" width="200px" sortable="true" query="timerange" timepattern="yyyy-MM-dd HH:mm" hidden="false"/>

				<!-- title -->
				<!-- url insert|自行开发后台程序 -->
				<!-- funname 自行开发预处理javascript -->
 				<cip:toolbar icon="icon-add" title="新增" url="insert"/>
				<cip:toolbar icon="icon-edit" title="我的导出" url="${p:go('','myExport','') }"/>
				<cip:toolbar icon="icon-edit" title="我的新增" url="${p:go('','myInsert','') }" funname="preprocess"/>

				<!-- title -->
				<!-- color green|red|blue|yellow|white -->
				<!-- url update|delete|自行开发后台程序 -->
				<!-- role 是否有权限操作功能按钮 -->
				<!-- exp 功能按钮是否可见的条件表达式 -->
				<!-- exp表达式句法  字段1#判断1#值1||字段2#判断2#值2 -->
				<!-- funname 自行开发预处理javascript-->
				<cip:button title="更改" url="update" exp="orderstate#eq#2||orderstate#eq#1||orderstate#eq#0"/>
				<cip:button title="删除" color="red" url="delete" exp="orderstate#eq#0"/>
				<cip:button title="我的button" color="blue" url="${p:go('','blablabla','') }" exp="ordername#like#韩" funname="preprocess"/>
			</cip:grid-list>
		</div>
	</div>
</div>
<script type="text/javascript">
function preprocess(title,url) {
    alert("this is preprocess......do sth. here........" + title);
	window.location.href= url;
}
</script>
</body>
</html>