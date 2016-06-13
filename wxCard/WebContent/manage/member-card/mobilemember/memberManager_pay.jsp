<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
		<script language="javascript" src="js/jquery-2.0.0.min.js"></script>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<meta id="viewport" name="viewport"
			content="width=device-width; initial-scale=1.0; maximum-scale=1; user-scalable=no;" />
	</head>
	<body >
	<form action="${payurl}" id = "allPayForm" method="post">
	<input type="text" name = "payurl" value="${payurl}" style="display: none;"/>
		<input type="text" name = "appId" value="${appId}" style="display: none;"/>
		<input type="text" name = "timeStamp" value="${timeStamp}" style="display: none;"/>
		<input type="text" name = "nonceStr" value="${nonceStr}" style="display: none;"/>
		<input type="text" name = "wxPackage" value="${wxPackage}" style="display: none;"/>
		<input type="text" name = "signType" value="${signType}" style="display: none;"/>
		<input type="text" name = "paySign" value="${paySign}" style="display: none;"/>
		<input type="text" name = "payMoney" value="${payMoney}" style="display: none;"/>
		<input type="text" name = "merName" value="${merName}" style="display: none;"/>
		<input type="text" name = "merchantno" value="${merchantno}" style="display: none;"/>
<!-- 		<button type="submit">提交</button> -->
		
		
<%-- 		<input type="text" name = "total_fee" value="${total_fee}" style="display: none;"/> --%>
<%-- 		<input type="text" name = "out_trade_num" value="${out_trade_num}" style="display: none;"/> --%>
<%-- 		<input type="text" name = "pATERNER_KEY" value="${pATERNER_KEY}" style="display: none;"/> --%>
<%-- 		<input type="text" name = "sub_mch_id" value="${sub_mch_id}" style="display: none;"/> --%>
<%-- 		<input type="text" name = "body" value="${body}" style="display: none;"/> --%>
<%-- 		<input type="text" name = "spbill_create_ip" value="${spbill_create_ip}" style="display: none;"/> --%>
<%-- 		<input type="text" name = "notify_url" value="${notify_url}" style="display: none;"/> --%>
<%-- 		<input type="text" name = "trade_type" value="${trade_type}" style="display: none;"/> --%>
<%-- 		<input type="text" name = "body" value="${body}" style="display: none;"/> --%>
<%-- 		<input type="text" name = "merchantno" value="${merchantno}" style="display: none;"/> --%>
<%-- 		<input type="text" name = "merName" value="${merName}" style="display: none;"/> --%>
	</form>
	</body>
	<script type="text/javascript">
	setTimeout(function(){
		$("#allPayForm").submit();
	},500)
	
	</script>
</html>
