<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration, java.util.LinkedHashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>
<!DOCTYPE html>
<!-- [if IE 8]> <html lang="chs" class="ie8 no-js"> <![endif] -->
<!-- [if IE 9]> <html lang="chs" class="ie9 no-js"> <![endif] -->
<!-- [if !IE]><! -->
<html lang="chs class="ie8 no-js">
<!-- <![endif] -->
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<script type="text/javascript" src="/resources/own/script/common.js"></script>
<!-- <link rel="stylesheet" type="text/css" href="/mobile/member-card/css/global.css" /> -->
<!-- <link rel="stylesheet" type="text/css" href="/mobile/member-card/css/app.css"> -->
<!-- <link rel="stylesheet" type="text/css" href="/mobile/member-card/css/add.css"> -->
<!-- <script src="/mobile/member-card/js/jquery-2.0.0.min.js"></script> -->

</head>
<body>
<div class="page-content">
<img src="img/d94f76df-b9a6-4.png" />
			<div  align="center">
			<h1 style="color: red;font-weight: 900;">专卡结算服务平台</h1>
			</div>
	<div class="portlet box blue-madison">
		<div class="portlet-title">
			<div class="caption">
				<i class=""></i> 
			</div>
		</div>
		<div class="portlet-body form">
			<!-- BEGIN FORM-->
			<form  action="" class="form-horizontal">
			<div ajax data-callback="errorMsg" data-action="${p:go('','pay','') }">
			 <script type="text/javascript">
	                  var errorMsg = function(msg){
	                      if(msg['errorMsg']){
	                    	  alert(msg['errorMsg']);
	                    	  return;
	                      }
	                      window.location.href = msg['GATEWAY_URL'];
	                  }
	             </script>
				<div class="form-body">
					<div class="form-group">
						<label class="col-md-3 control-label">订单号:</label>
						<div class="col-md-2">
							<input ajaxItem name="pojo.orderNo" type="hidden" value="${orderNo}"><label class="control-label">${orderNo}</label>
						</div>
						<label class="col-md-2 control-label">交易时间:</label>
						<div class="col-md-2">
							<input name="pojo.timeStart" type="hidden" value="${time_start}"><label class="control-label">${time_start}</label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">收款方:</label>
						<div class="col-md-2">
							<input name="pojo.merName" type="hidden" value="${mer_name}"><label class="control-label">${mer_name}</label>
						</div>
						<label class="col-md-2 control-label">订单金额:</label>
						<div class="col-md-2">
							<input name="pojo.totalFee" type="hidden" value="${total_fee}"><label class="control-label">${total_fee}</label>
						</div>
					
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">订单内容:</label>
						<div class="col-md-2">
							<input name="pojo.orderContent" type="hidden" value="${order_content}"><label class="control-label">${order_content}</label>
						</div>
					</div>
				</div>
				<div class="form-group">
						<label class="col-md-3 control-label">卡号:</label>
						<div class="col-md-4">
							<input ajaxItem check data-checkMsg="卡号不能为空哦" name="pojo.cardNo" type="text" class="form-control input-circle" placeholder="请输入您的卡号">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">密码:</label>
						<div class="col-md-4">
							<input ajaxItem check data-checkMsg="密码不能为空哦" name="pojo.password"  type="password" class="form-control input-circle" placeholder="请输入6位密码">
						</div>
					</div>
				<div class="form-actions fluid" align="">
					<div class="col-md-offset-3 col-md-9">
						<button submit class="btn btn blue-steel"> 提 交 </button>
						<button type="reset" class="btn btn default"> 重 填 </button>
					</div>
				</div>
		 	</div>
		 	</form>
		</div>
	</div>
	<div class="modal fade" id="msg" class="modal fade" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					<h4 class="modal-title">修改成功</h4>
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn green"><i class="icon-ok"></i>确认</button>
				</div>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">Common.init();</script> 
<%if("OK".equals(request.getAttribute("MSG"))){%>
<script type="text/javascript">
showModal('#msg');
</script>
<%}%>
</body>
</html>