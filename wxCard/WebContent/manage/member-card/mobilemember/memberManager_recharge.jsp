<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>充值</title>
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<meta name="format-detection" content="email=no">
		<link rel="stylesheet" type="text/css" href="css/global.css">
		<link rel="stylesheet" type="text/css" href="css/app.css">
		<script src="js/jquery-2.0.0.min.js"></script>
		<script src="js/recharge.js?r=1"></script>
        <script type="text/javascript" src="js/artDialog/artDialog.js"></script>
		<script type="text/javascript" src="js/artDialog/artDialog.js?skin=default"></script>
        <script type="text/javascript" src="js/alert.js"></script>
	</head>
	<body class="layout-mobile-recharge">
		<section class="wrapper">
		<div class="content">
			<form class="form" method="post">
				<h2>
				<script>
				
				$(function(){
					var a = 1000 - ${User.balance};
					var priceLast = a.toFixed(2);
					$("#priceLast").html(priceLast);
				})
				
				</script>
				</h2>
				<ul class="form-mod radio">
					<li>
						<a href="#" data-value="5">5.00</a>
					</li>
					<li>
						<a href="#" data-value="30">30.00</a>
					</li>
					<li>
						<a href="#" data-value="50">50.00</a>
					</li>
					<li>
						<a href="#" data-value="300">300.00</a>
					</li>
					<li>
						<input id="otherPrice" type="text" name="price" placeholder="其它金额"/>
					</li>
				</ul>
				<input type="text" id="pricemoney" style="display: none" value="<%=request.getParameter("money") %>"/>
				<input type="text" id="body" style="display: none" value="微乐付卡充值(<%=request.getParameter("cardno") %>)"/>
				<input type="text" id="path" style="display: none" value="successRecharge.jsp"/>
				<div class="operation">
					<button id="weixinpay" type="button" class="btn-green">
						确认充值
					</button>
				</div>

			</form>
		</div>
		</section>

	</body>
</html>

