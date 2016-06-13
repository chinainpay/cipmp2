<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	
	<form action="memberList.jsp" class="form-horizontal">
		 
		<div class="form-body  ">
				<div class="form-group">
					<label class="col-md-3 control-label">
						*会员卡号
					</label>
					<div class="col-md-4">
						<input type="text" class="form-control" placeholder="Enter text"> 
					</div>
				</div>

				<div class="form-group">
					<label class="col-md-3 control-label">
						*会员姓名
					</label>
					<div class="col-md-4">
						<input type="text" class="form-control" placeholder="Enter text"> 
					</div>
				</div> 

				<div class="form-group">
					<label class="col-md-3 control-label">
						*会员电话
					</label>
					<div class="col-md-4">
						<input type="text" class="form-control" placeholder="Enter text"> 
					</div>
				</div> 

				<div class="form-group">
					<label class="col-md-3 control-label">
						*卡内余额
					</label>
					<div class="col-md-4">
						<input type="text" class="form-control" placeholder="Enter text"> 
					</div>
				</div> 

				
				<div class="form-group">
					<label class="col-md-3 control-label">
						*积分
					</label>
					<div class="col-md-4">
						<input type="text" class="form-control" placeholder="Enter text"> 
					</div>
				</div> 

				<div class="form-group">
					<label class="col-md-3 control-label">
						*会员等级
					</label>
					<div class="col-md-4">
						<input type="text" class="form-control" placeholder="Enter text"> 
					</div>
				</div> 
				 
 				<div class="form-group">
					 
					<div class="col-md-offset-3 col-md-9">
						<button type="submit" class="btn green">保存</button>
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
</body>
</html>