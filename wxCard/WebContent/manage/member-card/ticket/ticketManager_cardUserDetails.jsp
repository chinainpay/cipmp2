<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration, java.util.LinkedHashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>
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
<style>
	.page-content{
		padding-bottom:250px;
	}
</style>
</head>
<body>
	<div class="page-content">
	
		<form id="f1" method="post" action="${p:go('','','') }"  enctype="multipart/form-data">
			<div class="btn-group pull-right">
				<label> 
				<input type="hidden" value="1" name="page">
				<input type="hidden" value="${cardtype}" name="cardtype">
				<input type="hidden" value="${cardid}" name="cardid">
					<div class="col-md-4">
						 <div class="input-group" > 
						    <input readonly id="_datetime"   name="TRADE_DATE_S" class="form-control"  size="16" type="text" value="${TRADE_DATE_S}" placeholder="选择开始日期" />
						    
						    <span class="input-group-btn" >  
								<button id="btn_datetime" class="add-on btn btn-success" type="button">
									<i class="fa fa-calendar"></i>
								</button> 
							</span>
						 </div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<input readonly id="_datetimeend" name="TRADE_DATE_E"
								class="form-control" size="16" type="text"
								value="${TRADE_DATE_E}" placeholder="选择结束日期" /> 
							<span class="input-group-btn">
								<button id="btn_datetimeend" class="add-on btn btn-success"
									type="button">
									<i class="fa fa-calendar"></i>
								</button>
								<button id="sample_editable_1_new" class="btn green"
									onclick="subm('')">
									搜索 <i class="fa fa-search"></i>
								</button>
							</span>

						</div>
					</div>
				</label> 
				<button type="button" class="btn btn blue-steel" onclick="download()">下载汇总</button>

			</div>
		</form>
									<table class="table table-striped table-bordered table-advance table-hover">
										<thead class="flip-content">
										<tr>
											<th><b>门店编号</b></th>
											<th><b>门店名称</b></th>
											<th><b>优惠券类型</b></th>
											<th><b>优惠券名称</b></th>
											<th><b>交易类型</b></th>
											<th><b>订单号</b></th>
											<th><b>POS机终端号</b></th>
											<th><b>使用数量</b></th>
											<th><b>交易时间</b></th>
										</tr>
										</thead>
										<tbody>
										<c:forEach items="${list}" var="pojo" varStatus="state">
											<tr>
												<td>${pojo['STORE_CODE']}</td>
												<td>${pojo['STORE_NAME']}</td>
												<td>${pojo['tickettype']}</td>
												<td>${pojo['title']}</td>
												<td>${pojo['ticket_state']}</td>
												<td>${pojo['orderNo']}</td>
												<td>${pojo['Terminal_Identity']}</td>
												<td>${pojo['count']}</td>
												<td>${pojo['used_time']}</td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
				<div class="row">
						<div class=" col-md-12 col-sm-12 ">
						    <div class="pull-right dataTables_paginate paging_bootstrap">
						        <ul class="pagination" style="visibility: visible;">
						            <li class="prev">
						            <script type="text/javascript"></script>
						                <a  href="${p:go('','cardUserDetails','') }&page=${page-1}&TRADE_DATE_S=${TRADE_DATE_S}&TRADE_DATE_E=${TRADE_DATE_E}&pojo.cardtype=${cardtype}&pojo.cardid=${cardid}" title="Prev" onclick="<c:if test="${page==1}">return false</c:if>">
						                    <i class="fa fa-angle-left">
						                    </i>
						                </a>
						            </li>
						            <li class="active">
						                <a href="#">
						                    ${page}/ ${sum}
						                </a>
						            </li>
						            
						            <li class="next">
						                <a href="${p:go('','cardUserDetails','') }&page=${page+1}&TRADE_DATE_S=${TRADE_DATE_S}&TRADE_DATE_E=${TRADE_DATE_E}&pojo.cardtype=${cardtype}&pojo.cardid=${cardid}" title="Next" onclick="<c:if test="${page==sum}">return false</c:if>">
						                    <i class="fa fa-angle-right">
						                    </i>
						                </a>
						            </li>
						        </ul>
						    </div>
						</div>
				</div>		
</div>



<script type="text/javascript">
Common.init();
</script>
<link href="/resources/assets/global/plugins/bootstrap-datetimepicker/css/datetimepicker.css" rel="stylesheet" media="screen"> 
<script type="text/javascript" src="/resources/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/resources/assets/global/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script>
	setTimeout(function(){
	$('#_datetime').datetimepicker({
		format: 'yyyy-mm-dd' 
		,autoclose: true
		,startView:3
		,minView : 2 
		,language : 'zh-CN' 
    }).on('show', function(ev){
    	/*
    	setTimeout(function(){
    		setIframe();
    	},50);
    	*/
    });
	
	$(".page-content").css({
		'height' : ($(".page-content").height() + 300)
	})
	//setIframe
	
	$("#btn_datetime").click(function(){ 
		$('#_datetime').focus();
	})
},100);
	setTimeout(function(){
	$('#_datetimeend').datetimepicker({
		format: 'yyyy-mm-dd' 
		,autoclose: true
		,startView:3
		,minView : 2 
		,language : 'zh-CN' 
    }).on('show', function(ev){
    	/*
    	setTimeout(function(){
    		setIframe();
    	},50);
    	*/
    });
	
	$(".page-content").css({
		'height' : ($(".page-content").height() + 300)
	})
	//setIframe
	
	$("#btn_datetimeend").click(function(){ 
		$('#_datetimeend').focus();
	})
},100);


function subm(){
	var actionStr="${p:go('','cardUserDetails','') }";
	document.getElementById("f1").action=actionStr;
	}
function download(){
	var Str="${p:go('','download','') }";
	document.getElementById("f1").action=Str;
	document.getElementById("f1").submit();
	}
</script>
</body>
</html>