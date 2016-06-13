<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.Enumeration,java.util.LinkedHashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>领卡</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<link rel="stylesheet" type="text/css" href="css/getCardTicket.css?r=123">
</head>
<script type="text/javascript">
function onBridgeReady(){
 WeixinJSBridge.call('hideOptionMenu');
}

if (typeof WeixinJSBridge == "undefined"){
    if( document.addEventListener ){
        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
    }else if (document.attachEvent){
        document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
    }
}else{
    onBridgeReady();
}
</script>
<body>
	<div class="main">

		<div style="background-color:${color};" class="margin-bottom20 content1">
			<div class="logoImg">
				<img src="https://mmbiz.qlogo.cn/mmbiz/qqNSkJXiawtScBYcNdic4SlTqXicY74QHraawnLyNBDh87CibP2kB47RqDricnRj68wI0OibBosiapnDBWVFIAbt683XA/0?wx_fmt=gif" />
			</div>

			<div class="logoText">${pojo.brand_name }</div>

			<div class="text1">${pojo.title }</div>

	 		<div class="text3">
	 			${pojo.sub_title }
	 		</div>
			<div class="text2">有效期：${begin_timestamp	}至${end_timestamp }</div>
		</div>

		<div onclick="window.location.href = '${p:go('/discountDetail','','') }&cardid=${pojo.card_id }'" class="item">

			<div class="left">${cardType }详情</div>
			<div class="right">></div>
		</div>

		<div onclick="window.location.href = '${p:go('/cardTicketPackage','','') }';"
			class="margin-bottom30 item last">

			<div class="left">我的卡包</div>
			<div class="right">></div>

		</div>
		<div>
			<p>&nbsp;</p>
		</div>

		<c:if test="${pojo.dateSate == '0'}">
			<a href="${p:go('','receiveCardTicket','') }&cardid=${pojo.card_id}&cardtype=${pojo.card_type}" class=" btn"> 立即领取
			</a>
		</c:if>
		<c:if test="${pojo.dateSate == '1'}">
			<a href="#" class=" btn"> 已失效 </a>
		</c:if>
		<c:if test="${pojo.dateSate == '2'}">
			<a href="#" class=" btn"> 超过领取限额</a>
		</c:if>
		<c:if test="${pojo.dateSate == '3'}">
			<a href="#" class=" btn">已抢完</a>
		</c:if>

		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
	</div>



</body>
</html>