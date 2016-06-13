<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration, java.util.LinkedHashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>会员卡首页</title>
<link rel="stylesheet" type="text/css" href="css/ment.css?r=1">
<link rel="stylesheet" type="text/css" href="css/base.css?r=1">
<link rel="stylesheet" type="text/css" href="css/memberManager.css?r1">
<link rel="stylesheet" type="text/css" href="css/component.css?r122" >
<link rel="stylesheet" type="text/css" href="css/animations.css?r23" />
<link rel="stylesheet" type="text/css" href="css/global.css?r4" />
		<script type="text/javascript" src="js/alert.js?r=212"></script>


<style>
	.tequantitle{
		display:block;white-space:nowrap; overflow:hidden; text-overflow:ellipsis;
	}
</style>
</head>
<script type="text/javascript">
		//Common.init(); 
	var actionUrl = "${p:go('','fintPassword','') }";
	</script>
<body> 
<div  id="pt-main" class="pt-perspective ">

       <div class="pt-page  pt-page-current">

					 <div id="cardFront">
						<div class="topBox">
						  <div class="topBg"><img src="img/topbg" width="100%"></div>
						  <div class="topBaner">
						    <div><img style="width:100%" src="${imgUrl['IMAGE_ICON'] }"></div>
						    <p class="p1"><i>卡号：</i>${cardNum }</p>
						    <p class="p1"><i>余额：</i>${yuMoney }</p>
						  </div>
						</div>
						<div class="main">
						  <div class="main_action">
						    <div class="leftsidebar bgffb849 pr" front_paybtn data-src="pic/pay_finished.png"> <span class="icon icon1"></span> <span class="btn_txt"> <span class="btn_txt1">付款</span> </span> </div>
						    <a href="${p:go('/consumeRecord','','') }"><div class="rightsidebar bg3caee2 pr"> <span class="icon icon2"></span> <span class="btn_txt"> <span class="btn_txt1">账单</span> </span> </div></a>
						    <div style="clear:both;"></div></div></div><div class="navBox clearfix"><div class="navMain"><div class="navTab" ><ul id="nav" class="navTab_list"><li><a class="nav_a select" data-ref="div1" href="javascript:void(0)" >服务</a></li><li class="pr"><a data-ref="div2" class="nav_a" href="javascript:void(0)">特权<em></em></a></li><li><a data-ref="div3" class="nav_a " href="javascript:void(0)">附近</a></li><li><a  data-ref="div4" class="nav_a" href="javascript:void(0)">设置</a></li></ul>
							      <div  id="div1" class = "tabs"><div class="services"><ul class="servicesBox"><li> <a href=""><div class="services-menu">
							              	<a href="${p:go('','recharge','') }"><div class="services-pic"><img src="img/icon1"></div><div class="services-name">充值</div>
								            </a></div></a> </li>
							              <li> <a href="">	 <div class="services-menu" front_paybtn  data-src="pic/pay_finished2.png">
							              	<a href="">	   <div class="services-pic"><img src="img/icon5.png"></div>
								                <div class="services-name">线下充值</div>
								            </a>		  </div>
							              </a> </li>  <li> <a href="">
							              <div class="services-menu">	<a href="${p:go('/cardUserInfo','','') }">
								                <div class="services-pic"><img src="img/icon3.png"></div>
								                <div class="services-name">完善信息</div>
								            </a> </div>	  </a> </li>						           
							          </ul></div>	    </div>	 <div id="div2" class ="tabs hidden"> <nav>		<ul>		           <li class="privilege"> <a href="" class="peList">
							              <div class="peBox pr">  <div class="peInfo">	  <h2 class="tequantitle">${teQuan['VIRTUALCARD_PRIVILEGE'] }</h2>
							                  <p class="favorable">${teQuan['VIRTUALCARD_PRIVILEGE'] }</p>
							                </div>  </div>
							              </a> </li> </ul>
							        </nav>	   </div>
							      <div id="div3" class = "tabs hidden" ><div class="nearby">
							          <div class="nearbyMain">		<h2>离你最近</h2>	  <ul>  </ul>	 </div>
							        </div>
							      </div> <div id="div4" class = "tabs hidden">
							        <div class="infoBox">
							          <ul><a class="phone_bg" href="modifyPassword.html"><li>修改密码</li></a>		<a class="phone_bg" href="javascript:getType()"><li>找回密码</li></a> </ul>
							        </div>
							      </div>
							    </div>
							  </div>
							</div>
					</div>
		</div>
		 <div class="pt-page ">   
					<div id="cardBack">
					                 <div class="add_cardBack_imgBox" id="imgBox_back"> 
					                      <div class="add_cardBack_imgBoxIn">
					                       	 <img src="${p:go('','payment','') }&what=123" class="add_cardBack_imgBox_codeImg1" /> 
					                          <p class="add_cardBack_imgBox_number">${User.cardNo }</p>
					                          <img src="${p:go('','payment','') }&what=QR" class="add_cardBack_imgBox_codeImg2" /> 
					                          <img src="" class="add_cardBack_imgBox_paybtn" id="back_paybtn"/>
					                      </div>
					               </div>
					                
					                 <div class="add_cardBack_tipBox">
					                      <span class="add_cardBack_tipBox_title">扫码小秘诀</span>
					                      <span class="add_cardBack_tipBox_content">
					                            <p><i>1</i>扫描不成功，快去将屏幕设置最亮.</p>
					                            <p><i>2</i>忘记密码了？快去<a href="javascript:getType()">找回密码.</a></p>
					                      </span>
					                 </div>
					</div> 
		</div>
		</div>
</body>

<script type="text/javascript" src="js/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="js/alert.js?r=212"></script>
<script type="text/javascript" src="js/memberManager.js?r12221112"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="js/modernizr.custom.js?r=21"></script>
<script src="js/jquery.dlmenu.js?r=21"></script>
<script type="text/javascript" src="js/pagetransitions.js?r=21"></script>


<script type="text/javascript">
//loading.show();
$("#nav li").delegate(".nav_a","click",function(){
	var ref = $(this).data("ref");
	$(".tabs").hide();
	$("#"+ref).show();
	$(".nav_a").removeClass("select");
	$(this).addClass("select");
});
 </script>
</html>
