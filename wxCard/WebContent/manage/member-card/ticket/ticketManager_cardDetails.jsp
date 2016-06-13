<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration, java.util.LinkedHashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link onerror="wx_loaderror(this)" rel="stylesheet" type="text/css"
	href="https://res.wx.qq.com/mpres/htmledition/style/base/layout_head218878.css" />
<link onerror="wx_loaderror(this)" rel="stylesheet" type="text/css"
	href="https://res.wx.qq.com/mpres/htmledition/style/base/base25404f.css" />
<link onerror="wx_loaderror(this)" rel="stylesheet" type="text/css"
	href="https://res.wx.qq.com/mpres/htmledition/style/base/lib22f1a9.css" />
<link onerror="wx_loaderror(this)" rel="stylesheet"
	href="https://res.wx.qq.com/mpres/htmledition/style/page/cardticket/card_control242c7c.css">

<link rel="stylesheet" type="text/css"
	href="https://res.wx.qq.com/c/=/mpres/htmledition/style/page/cardticket/dialog_select_shop22bfc4.css,/mpres/htmledition/style/page/media/dialog_img_pick25404f.css,/mpres/htmledition/style/widget/pagination218878.css,/mpres/htmledition/style/widget/upload22a2de.css,/mpres/htmledition/style/biz_web/widget/dropdown218878.css" />
<link rel="stylesheet" type="text/css" media="all"
	href="css/daterangepicker-bs3.css" />
<link rel="stylesheet" type="text/css" href="css/base.css">
<style type="text/css">
body {
	word-break: break-all;
}

.cardInfo {
	margin: 0px 10px;
	padding: 30px 0;
	width: 800px;
	background: #fff;
	font-family: "微软雅黑";
}

.cardInfo .fl {
	float: left;
}

.cardInfo .sidebar_a {
	width: 285px;
	background: #F5F5F9;
	border: 1px solid #E7E7EB;
	overflow: hidden;
}

.cardInfo .sidebar_a .card_name {
	/*
	position: relative;
	width: 285px;
	height: 57px;
	background: url(https://res.wx.qq.com/mpres/zh_CN/htmledition/comm_htmledition/style/page/cardticket/card_msg_z25676c.png) 0 0 no-repeat;
	font-size: 18px;
	color: #fff;
	text-align: center;
	*/
	text-align: center;
	height: 57px;
	line-height: 74px;
	color: #fff;
	background:
		url("https://res.wx.qq.com/mpres/zh_CN/htmledition/comm_htmledition/style/page/cardticket/card_msg_z25676c.png")
		0 -280px no-repeat;
}

.cardInfo .sidebar_a .card_name h2 {
	position: absolute;
	top: 25px;
	left: 42%;
	font-size: 16px;
	font-weight: 500;
	height: 25px;
	line-height: 25px;
}

.cardInfo .sidebar_a .card_content {
	padding: 5px 0 10px 0;
	background: /* #E75634 */ url(img/card_topbg.png) left bottom repeat-x;
}
.cardInfo .sidebar_a .card_content .brand_logo{
	display: block;
	width: 38px;
	height: 38px;
	padding-top: 0;
	margin-right: 7px;
	border-radius: 22px;
	-moz-border-radius: 22px;
	-webkit-border-radius: 22px;
	border: 1px solid #d3d3d3;
	float: left;
	margin-left: 10px;
}

.brand_logo img{
	height: 100%;
	width: auto;
}

.cardInfo .sidebar_a .card_content .brand_name {
	background: url(img/PC_icon.png) 15px center/54px 54px scroll no-repeat;
	min-height: 40px;
	line-height: 40px;
	overflow: hidden;
	text-align: center; 
	color: #fff;
	float: left;
}

.card_dis {
	padding: 10px 20px;
	color: #fff;
	font-size: 14px;
}

.card_dis h3 {
	font-size: 18px;
	font-weight: 600;
	letter-spacing: 1px;
	margin-bottom: 5px;
	text-align: center; 
}

.card_barcode {
	padding-bottom: 20px;
	width: 285px;
	border-bottom: #E7E7EB;
	text-align: center;
	font-size: 15px;
	background: #fff;
}

.card_barcode .card_barcode_p1 {
	margin: 0px auto 5px auto;
	padding-top: 10px;
	width: 300px;
}

.card_barcode .card_barcode_p2 {
	font-size: 16px;
}

.card_foot {
	margin-top: 20px;
	margin-bottom: 50px;
	background: #fff;
	border: 1px solid #E7E7EB;
	color: #000;
	font-size: 15px;
}

.card_foot div.card_foot_p {
	border-bottom: 1px solid #E7E7EB;
	font-size: 15px;
	height: 40px;
	line-height: 40px;
	overflow: hidden;
	padding: 0 10px;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.card_foot div.card_foot_pr {
	position: relative;
}

.card_foot div p.phone_bg {
	background: url(img/icon_con.png) no-repeat scroll right 50%/8.5px 13px
		rgba(0, 0, 0, 0);
	display: block;
	font-size: 100%;
	position: relative;
}

.card_foot div p.card_foot_pa {
	position: absolute;
	top: 0;
	right: 25px;
	color: #888;
	font-size: 15px;
}
.card_p{
	overflow: hidden;
}
</style>
<script type="text/javascript" src="/resources/own/script/common.js"></script>
<script type="text/javascript"> 
		Common.init();   
</script>
<script type="text/javascript" src="js/moment.min.js"></script>
<script type="text/javascript" src="js/daterangepicker.js"></script>
<script type="text/javascript" src="js/ticketManager_cardDetails.js?R=2"></script>
<cip:base type="jquery,easyui,tools,DatePicker,ckeditor,ckfinder"></cip:base>
</head>
<body style="background: #fff;">
	<div class="cardInfo">
		<div class="sidebar_a fl">
			<div class="card_name">
				<h2>${list.card_type }</h2>
			</div>
			<div class="card_content" id="mainBackColor"
				style="background-color: ${list.color }">
				<div class="card_p">
					<span class="brand_logo"><img src="${list.logo_url }" width="80px"></span>
					<p class="brand_name">${list.brand_name }</p></div>
				<div class="card_dis">
					<h3>
						${list.title }
					</h3>					
					<p>有效期至${list.date_info }</p>
				</div>
			</div>
			<div class="card_barcode">
				<p class="card_barcode_p1">
					<img src="img/yiweima.jpg" width="85%">
				</p>
				<p>1513-2290-1878</p>
				<p class="card_barcode_p2">${list.notice }</p>
			</div>
			<div class="card_foot">
				<div class="card_foot_p">
					<p class="phone_bg">${list.card_type }详情</p>
				</div>
				<div class="card_foot_p card_foot_pr">
					<p class="phone_bg">适用门店</p>
					<p class="card_foot_pa">0家</p>
				</div>
			</div>
		</div>
		<div class="sidebar_b fl">
			<div class="sidebar_b_main">
				<h4>劵面信息</h4>
				<ul>
					<li>
						<div class="fl sidebar_b_fl">卡劵类型</div>
						<div class="fl sidebar_b_fr">${list.card_type }</div>
						<div style="clear: both;"></div>
					</li>
					<li>
						<div class="fl sidebar_b_fl">卡劵标题</div>
						<div class="fl sidebar_b_fr">${list.title }</div>
						<div style="clear: both;"></div>
					</li>
					<c:if test="${list.card_type=='代金券' }">
						<li>
							<div class="fl sidebar_b_fl">减免金额</div>
							<div class="fl sidebar_b_fr">${list.reduce_cost }元</div>
							<div style="clear: both;"></div>
						</li>
						<li>
							<div class="fl sidebar_b_fl">抵扣条件</div>
							<div class="fl sidebar_b_fr">${list.least_cost }元</div>
							<div style="clear: both;"></div>
						</li>
					</c:if>
					<c:if test="${list.card_type=='折扣券' }">
						<li>
							<div class="fl sidebar_b_fl">折扣额度</div>
							<div class="fl sidebar_b_fr">${list.discount }折</div>
							<div style="clear: both;"></div>
						</li>
					</c:if>
					<li>
						<div class="fl sidebar_b_fl">副标题</div>
						<div class="fl sidebar_b_fr">${list.sub_title}</div>
						<div style="clear: both;"></div>
					</li>
					<li>
						<div class="fl sidebar_b_fl">卡劵颜色</div>
						<div class="fl sidebar_b_fr">
							<span style="background-color: ${list.color}">&nbsp;</span>
						</div>
						<div style="clear: both;"></div>
					</li>
					<li>
						<div class="fl sidebar_b_fl">有效期</div>
						<div class="msg">
							<span data-value="${list.date_info }" id="js_time_container">${list.date_info }</span>
							&nbsp;&nbsp;<a href="javascript:void(0);" id="js_modifytime_btn">延长有效期</a>
						</div>

						<div style="clear: both;"></div>
					</li>
					<li>
						<div class="fl sidebar_b_fl">商家名称</div>
						<div class="fl sidebar_b_fr">天同赛伯</div>
						<div style="clear: both;"></div>
					</li>
					<li>
						<div class="fl sidebar_b_fl">商家logo</div>
						<div class="fl sidebar_b_fr">
<cip:ckfinder name="logo_url" uploadType="Images" value="${list.logo_url}" width="160" height="80" buttonClass="ui-button" buttonValue="上传图片"></cip:ckfinder>
						</div>
						<div style="clear: both;"></div>
					</li>
					</table>
			</div>
			<div class="sidebar_b_main mt40">
				<h4>投放设置</h4>
				<ul>
					<li>
						<div class="fl sidebar_b_fl">库存</div>
						<div class="fl sidebar_b_fr">${list.quantity }</div>
						<div style="clear: both;"></div>
					</li>
					<li>
						<div class="fl sidebar_b_fl">销券条码</div>
						<div class="fl sidebar_b_fr">${list.code_type }</div>
						<div style="clear: both;"></div>
					</li>
					<li>
						<div class="fl sidebar_b_fl">操作提示</div>
						<div class="fl sidebar_b_fr">${list.notice }</div>
						<div style="clear: both;"></div>
					</li>
					<li>
						<div class="fl sidebar_b_fl">领取限制</div>
						<div class="fl sidebar_b_fr">每个用户限领${list.get_limit }张</div>
						<div style="clear: both;"></div>
					</li>
					<li>
						<div class="fl sidebar_b_fl">分享设置</div>
						<div class="fl sidebar_b_fr">${list.can_share }</div>
						<div style="clear: both;"></div>
					</li>
					<li>
						<div class="fl sidebar_b_fl">转赠设置</div>
						<div class="fl sidebar_b_fr">${list.can_give_friend }</div>
						<div style="clear: both;"></div>
					</li>
				</ul>
			</div>
			<div class="sidebar_b_main mt40">
				<h4>${list.card_type }详情</h4>
				<ul>
					<li>
						<div class="fl sidebar_b_fl">使用须知</div>
						<div class="fl sidebar_b_fr"><cip:ckeditor name="description" isfinder="false" type="width:360,height:240" value="${list.description } iewuoweiu pwewowoepri多个改偶然皮特热偶尔欧体饿哦平问题我跑  了我替偶尔破问题未批提问破二人我脾胃欧赔提问哦菩提巫婆人我听跟我劈腿外婆诶"></cip:ckeditor></div>
						<div style="clear: both;"></div>
					</li>
					<li>
						<div class="fl sidebar_b_fl">优惠详情</div>
						<div class="fl sidebar_b_fr">${list.defaultDetail }</div>
						<div style="clear: both;"><cip:upload name="defaultDetail" buttonText="选择要导入的文件" uploader="${controller_name}.do?importExcel" extend="*.xls;*.xlsx" id="file_upload" formData="documentTitle"></cip:upload></div>
					</li>
					<li>
						<div class="fl sidebar_b_fl">客服电话</div>
						<div class="fl sidebar_b_fr">${list.service_phone }</div>
						<div style="clear: both;"></div>
					</li>
				</ul>
			</div>
<!-- 			<div class="sidebar_b_main mt40"> -->
<!-- 				<h4> -->
<!-- 					代金劵详情<em>修改</em> -->
<!-- 				</h4> -->
<!-- 				<ul> -->
<!-- 					<li> -->
<!-- 						<div class="fl sidebar_b_fl">适用门店</div> -->
<!-- 						<div class="fl sidebar_b_fr">暂无数据</div> -->
<!-- 						<div style="clear: both;"></div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
		<div style="clear: both;"></div>
	</div>
</body>
</html>
