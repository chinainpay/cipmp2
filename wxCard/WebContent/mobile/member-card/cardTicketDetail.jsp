<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.Enumeration,java.util.LinkedHashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>卡片详情</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<link rel="stylesheet" type="text/css"
	href="css/cardTicketDetail.css?r=2">

<script type="text/javascript" src="js/jquery-2.0.0.min.js"></script>
<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="js/wyzCard.js?a=03"></script>


<script type="text/javascript">


var cardId = ${pojo.card_id};


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
</head>
<body>
	<div class="main">
		<div style="background-color:${color};" class=" content1">
			<div class="logoImg">
				<img
					src="https://mmbiz.qlogo.cn/mmbiz/qqNSkJXiawtScBYcNdic4SlTqXicY74QHraawnLyNBDh87CibP2kB47RqDricnRj68wI0OibBosiapnDBWVFIAbt683XA/0?wx_fmt=gif" />
			</div>
			<input type="hidden" id="timestamp" name="timestamp" /> <input
				type="hidden" value="${pojo.wx_card_id}" />

			<div class="logoText">${pojo.brand_name }</div>

			<div class="text1">${pojo.title }</div>

			<div class="text3">${pojo.sub_title }</div>

			<div class="text2">
				<div class="text2">有效期：${begin_timestamp	}至${end_timestamp }</div>
			</div>
			<c:if test="${WX_PACKAGE_STATE==1 }">
				<a id="batchAddCard" href="###"  class="text4"> 同步到微信卡包<!-- href="${p:go('/discountDetail','','') }&code=${code }" -->
				</a>
			</c:if>
		</div>

		<div class="margin-bottom20 content2">
			<img onclick="show_qrCode(this);"
				src="${p:go('','getOneCode','') }&what=123&code=${code }"
				width="100%" height="70" />
			<div class="margin-top15 content2-text1" id = "getCode">${code }</div>

			<div class="margin-top15 content2-text2">出示一维码即可</div>
		</div>

		<div
			onclick="window.location.href = '${p:go('/discountDetail','','') }&cardid=${pojo.card_id }'"
			class="item">

			<div class="left">${cardType }详情</div>
			<div class="right">></div>

		</div>
		<div
			onclick="window.location.href = '${p:go('/cardTicketPackage','','') }';"
			class="margin-bottom30 item last">

			<div class="left">我的卡包</div>
			<div class="right">></div>

		</div>
		<div>
			<p>&nbsp;</p>
		</div>
		<div onclick="hide_qrCode();" class="qrCodeWarp" id="qrCodeWarp">

			<img id="qrCode" src="" />

		</div>
		<script type="text/javascript">
		    var show_qrCode = function(el) {
		    		var qrCodeWarp = document.getElementById('qrCodeWarp');
		    			qrCodeWarp.className = 'qrCodeWarp show';

		    		var qrCode = document.getElementById('qrCode');
		    			qrCode.src = el.src;
		    		//alert(el.src);
		    };

		    var hide_qrCode = function() {
		    		var qrCodeWarp = document.getElementById('qrCodeWarp');
		    			qrCodeWarp.className = 'qrCodeWarp';
		    		//alert(el.src);
		    };
	</script>

		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
	</div>
</body>
<script type="text/javascript" defer="defer">

	var timestamp = $("#timestamp").val();
	
	var create_action = "${p:go('/wyzCard','getSign','') }";
	var create_action1 = "${p:go('/wyzCard','getSignTicket','') }&cardid=${pojo.wx_card_id}&code=${code }";

	var create_action2 = "${p:go('/wyzCard','updateWxPackageState','') }";
</script>
</html>