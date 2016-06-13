<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration, java.util.LinkedHashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
 <script type="text/javascript">
	 function checkInfo(){
			var cardNo = $('#cardNo').val();
			if(!cardNo){
				alert('请输入会员卡号/手机号');
				$('#cardNo').focus();
				return false;
			}
			if(isNaN(Number(cardNo))){
				alert('请填写正确卡号');
				return false;
			}	
			if(cardNo.length !=11 && cardNo.length !=19 ){
				alert('请输入11位手机号或19为卡号');
				return false;
			}		
			return true;
		}
 </script>
 
<style>
	.page-content{
		padding-bottom:200px;
	}
</style>
</head>
<body>
<div class="page-content">
	
	<form class="form-horizontal" action="${p:go('','query','') }" onsubmit="return  checkInfo()" >
		 
		<div class="form-body  ">
				<div class="form-group">
					<label class="col-md-3 control-label">
						输入会员卡号/手机号
					</label>
					<div class="col-md-4">
						<input  maxlength="19" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  name="pojo.card_no"  id ="cardNo"  type="text" class="form-control" placeholder="请输入11位手机号或19为卡号"> 
					</div>
				</div>

				<div class="form-group">
					<label class="col-md-3 control-label">
						交易门店
					</label>
					<div class="col-md-4">
						<select name="pojo.store_code" class="form-control">
						<option value="">查询当前商户</option>
							<c:forEach items="${MAKECARD}" var="pojo">
								<option value="${pojo['STORE_CODE']}">${pojo['STORE_NAME']}</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-md-3 control-label">
						交易日期
					</label>
					<div class="col-md-4">
						 <div class="input-group" > 
						    <input readonly id="_datetime"   name="pojo.dateTime" class="form-control"  size="16" type="text" value="" placeholder="选择消费日期" />
						    
						    <span class="input-group-btn">  
								<button id="btn_datetime" class="add-on btn btn-success" type="button">
									<i class="fa fa-calendar"></i>
								</button> 
							</span>
						 </div>
					</div>
				</div>

				<div class="form-group">
					<div class="text-right col-md-5">
						 <button type="submit" class="btn green">查询</button>
					</div>
				</div>
 		
		</div>
	</form>
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
	    });
		
		$("#btn_datetime").click(function(){ 
			$('#_datetime').focus();
		})
	},100)
</script> 
</body>
</html>