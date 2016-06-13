<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="js/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<title>Insert title here</title>
</head>
<body>
	<input type="button" id="batchAddCard"
		style="width: 300px; height: 200px;" value="立即领取">
</body>
<script type="text/javascript" id="my" src="js/wyzCard.js">
</script>
<script type="text/javascript">
setTimeout(create_action1(),3000);//延时3秒 
	var create_action = "${p:go('','getSign','') }";
	var create_action1 = "${p:go('','getSignTicket','') }";
</script>
</html>