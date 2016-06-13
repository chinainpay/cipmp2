<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.Enumeration,java.util.LinkedHashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>卡包</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta content="telephone=no" name="format-detection" /> 
    <link rel="stylesheet" type="text/css" href="css/cardTicketPackage.css"/> 
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
</head>
<body>
	

	<div class="main">
		<c:if test="${count == 0}" ><div style="text-align: center;color: #d09a45">卡包空空，快去参加活动领券吧！</div></c:if>
		<div class="list">
		<c:forEach items="${list }" var="pojo">
			 <div onclick="window.location.href = '${p:go('/cardTicketDetail','','') }&cardid=${pojo.card_id }&code=${pojo.code }'" class="item" style="background-color:${pojo.color}">
	   				<div  style="background-color:${pojo.color};"  class="content1">
	   					<div class="logoImg">
				 			 <img src="https://mmbiz.qlogo.cn/mmbiz/qqNSkJXiawtScBYcNdic4SlTqXicY74QHraawnLyNBDh87CibP2kB47RqDricnRj68wI0OibBosiapnDBWVFIAbt683XA/0?wx_fmt=gif" />
				 		</div>

				 		<div class="logoText">
				 			${pojo.title }
				 		</div>
	   				</div>
	   				<div class="content2">
	   					 <div class="left">
						 	 ${pojo.brand_name }
						 </div>
						 <div class="right">
						 	  有效期至：${pojo.end_date }
						 </div>
	   				</div>
			 </div>
		</c:forEach>
		</div>
	</div>

	

</body>
</html>