<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
	<title>交易流水</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<meta name="format-detection" content="email=no">
	<link rel="stylesheet" type="text/css" href="css/global.css">
	<link rel="stylesheet" type="text/css" href="css/app.css">
    <link rel="stylesheet" type="text/css" href="css/tradeRecord.css">
    <script type="text/javascript" src="js/iscroll.js"></script>
    <script type="text/javascript" src="js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="js/tradeRecord.js"></script>
    <script type="text/javascript" src="js/dateformat.js"></script>
 	<script type="text/javascript">
    	var toclose = "<%=request.getParameter("param")%>";
    	$(function(){  	 	    		
	    		$(".btn-back").click(function(){
	    	 		  if(toclose == "ToClose"){
	    	 		  
					WeixinJSBridge.invoke('closeWindow',{},function(res){
			           
					    //alert(res.err_msg);
			
					});
					}else{
			    		window.location.href="javascript:history.go(-1);";
			    	}
			   })	    		    	
    	})  	
    </script>
</head>
<body class="layout-point">
<header class="tit-header">
	<h1>交易记录</h1>
	<span class="btn-back">返回</span>
</header>

<script type="text/tpl" id="item_tpl" >

	<li class="tradeRecord_box">
	     <div style="overflow:hidden;padding:10px;background:#fff">
		      <div class="tradeRecord_box_left">
			        <div class="tradeRecord_box_left01">
						  <p class="tradeRecord_box_left01_left">{type}</p>
						  <p class="tradeRecord_box_left01_right">{amount}元</p>
			        </div>
			        <div class="tradeRecord_box_left02">
			    	      <p class="tradeRecord_box_left02_left">{date}</p>
				          <p class="tradeRecord_box_left02_right">{status}</p>
			        </div>
		     </div>
		     <div class="tradeRecord_box_right">
			       <img _label="arrow_up" src="img/arrow_up.png"/>
				   <img _label="arrow_down" src="img/arrow_down.png" style="display:none"/>
		     </div>
	    </div>  
	    <div class="tradeRecord_box_details">交易单号：{orderId}</div>
		<div class="tradeRecord_box_details">商户名称：{merchantName}</div>
   </li>
	
</script>

<div id="wrapper">
	<div id="scroller">

		<ul id="thelist">
			

		</ul>
		<div id="pullUp">
			<span class="pullUpIcon"></span><span class="pullUpLabel">加载中...</span>
		</div>
	</div>
</div>


</body>
</html>