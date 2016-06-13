<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration, java.util.LinkedHashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
</head>
<body>
<div class="page-content">
	<div class="portlet box  ">
		<div class="portlet-body  ">
				<div class="table-toolbar">
		<form id="f1" method="post" action="${p:go('','','') }"
			enctype="multipart/form-data" class="form-horizontal">
			<div class="btn-group pull-right">
				<label> <input type="hidden" value="1" name="page">
					<div class="col-md-4">
						<div class="input-group">
							<input readonly id="_datetime" name="TRADE_DATE_S"
								class="form-control" size="16" type="text"
								value="${TRADE_DATE_S}" placeholder="选择开始日期" /> <span
								class="input-group-btn">
								<button id="btn_datetime" class="add-on btn btn-success"
									type="button">
									<i class="fa fa-calendar"></i>
								</button>
							</span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<input readonly id="_datetimeend" name="TRADE_DATE_E"
								class="form-control" size="16" type="text"
								value="${TRADE_DATE_E}" placeholder="选择结束日期" /> <span
								class="input-group-btn">
								<button id="btn_datetimeend" class="add-on btn btn-success"
									type="button">
									<i class="fa fa-calendar"></i>
								</button>
							</span>
						</div>
					</div>
				</label>
				<div class="form-group">
					<DIV class="col-md-4">
						<label class="col-md-3 control-label" style="width: 120px">
							手机号 </label> 
						<input  name="pojo.phoneNo" class="form-control"
							style="width: 160px" onKeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))"  onkeyup="value=this.value.replace(/\D+/g,'')" type="text" value="${phoneNo}" />
					</DIV>
					<DIV class="col-md-4">
						<label class="col-md-3 control-label" style="width: 120px">
							卡号 </label> 
						<input  name="pojo.cardNo" class="form-control"
							style="width: 160px" onKeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))"  onkeyup="value=this.value.replace(/\D+/g,'')" type="text" value="${cardNo}" />
					</DIV>
					
					<button style="margin-left: 80px" type="submit" class="btn green">
					搜索 <i class="fa fa-search"></i>
				</button>
				</div>

			</div>
		</form>
							<table class="table table-striped table-bordered table-advance table-hover" id="sample_1">
								<thead>
									<tr>
										 
										<th>
											*会员卡号
										</th>
										<th >*会员姓名</th>
										<th >*手机号</th>
										<th >*注册日期</th>
										<th >操作</th>
									</tr>
								</thead>
								<tbody>
								 <c:forEach items="${list}" var="pojo">
										<tr class="odd gradeX">
										
											<td>
												${pojo['CARD_NUM']}
											</td>
											<td >${pojo['MEMB_NAME']}</td>
											<td >${pojo['MEMB_PHONE']}</td>
											<td ><fmt:formatDate value="${pojo['REGISTER_TIME']}"
						pattern="yyyy-MM-dd HH:mm:ss" />
											<td >
												<a  href="${p:go('/sell/memberTradeOP','transactionChange','') }&pojo.cardNo=${pojo['CARD_NUM']}"  class="label label-sm label-success">
													详情
												</a>
											</td>
										</tr>
									</c:forEach>
			 
 								</tbody>
							</table> 
				</div>

				<div class="row">
			<div class=" col-md-12 col-sm-12 ">
				<div class="pull-right dataTables_paginate paging_bootstrap">
					<ul class="pagination" style="visibility: visible;">
						<li class="prev"><script type="text/javascript"></script> <a
							href="${p:go('','','') }&page=${page-1}&TRADE_DATE_S=${TRADE_DATE_S}&TRADE_DATE_E=${TRADE_DATE_E}&cardNo=${cardNo}&phoneNo=${phoneNo}"
							title="Prev"
							onclick="<c:if test="${page==1}">return false</c:if>"> <i
								class="fa fa-angle-left"> </i>
						</a></li>
						<li class="active"><a href="#"> ${page}/ ${pageCount} </a></li>

						<li class="next"><a
							href="${p:go('','','') }&page=${page+1}&TRADE_DATE_S=${TRADE_DATE_S}&TRADE_DATE_E=${TRADE_DATE_E}&cardNo=${cardNo}&phoneNo=${phoneNo}"
							title="Next"
							onclick="<c:if test="${page==pageCount}">return false</c:if>"> <i
								class="fa fa-angle-right"> </i>
						</a></li>
					</ul>
				</div>
			</div>
		</div>
		</div>
	</div>
	<div class="modal fade" id="msg" class="modal fade" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					<h4 modal-title class="modal-title">修改成功</h4>
				</div>
				<div modal-body class="modal-body">
					
				</div>
				<div class="modal-footer">
					<button modal-submit type="button" data-dismiss="modal" class="btn green"><i class="icon-ok"></i>确认</button>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript"> 
		Common.init();   
</script>
<%if("OK".equals(request.getAttribute("MSG"))){%>
<script type="text/javascript">
showModal('#msg');
</script>
<%}%>
<link href="/resources/assets/global/plugins/bootstrap-datetimepicker/css/datetimepicker.css"
		rel="stylesheet" media="screen">
	<script type="text/javascript"
		src="/resources/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript"
		src="/resources/assets/global/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
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
function go(){
	document.getElementById("f2").submit();
}

function changelistMerId(obj){
    $("#storeCode").find("option").remove(); 
    $("#storeCode").append("<option  value=''>所有</option>"); 
    $.ajax({
		   type: "POST",
		   url: "${p:go('','changelistMerId','') }",
		   data:$.param({
			   'json' : obj
		   }),
		   dataType : 'json',
		   success: function(data){
			   for(var n=0;n<data['listStoreName'].length;n++){
			   	  var sn=data['listStoreName'][n].STORE_NAME;
			   	var sc=data['listStoreName'][n].STORE_CODE;
				  $("#storeCode").append("<option id='"+sc+"' value='"+sc+"'>"+sn+"</option>");
			  }
 
		      	
		   }
		
	});
}
</script>
</body>
</html>