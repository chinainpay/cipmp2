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
<style type="text/css">
	input.min,select.min{
		width:150px;
		display: inline-block;
	}
</style>
</head>
<body>
<div class="page-content">
	
	<form class="form-horizontal">

		<ul class="nav nav-tabs">
					<li >
						<a   href="memberSystemset.jsp">会员卡设置</a></li>
					<li class="active"><a   href="memberSystemset2.jsp">会员卡设置</a></li>
					<li><a   href="memberSystemset3.jsp">会员等级设置</a></li> 
				</ul>
		 
		<div   class="form-body  ">
				 	
				 	<div id="item-list">

				 	</div>
				 

					<div style="margin-top:30px;"  class="row">
						  <div class="text-right col-md-12">
						  		<button id="add" type="button" class="btn green">
									添加 
								</button>
						  </div>
					</div>	
 		
		</div>

		<div style="margin-top:30px;" class="row">
			<div class="text-center col-md-12">
					<button type="submit" class="btn green">
						保存设置
					</button>
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

	
	
 

<script type="tpl" id="item-tpl">
	<div class="row">
								 
						<div class="col-md-3">
							
							<button del class="btn red">
								 <i class="fa fa-times"></i>
							</button>

							字段1
								<input type="text" class="min form-control" placeholder="会员姓名">
							 
									
						</div>
									
						<div class="col-md-3">

							字段1提示文字
							
								<input type="text" class="min form-control" placeholder="请输入您的姓名"> 
							 	 
									 
						</div>
								 
						<div class="col-md-3">
									字段1类型
									<select  class="min form-control">

										<option>文本</option>
										<option>数字</option>
									</select>  
						</div>

						<div class="col-md-3">
								
								 

								<label class="col-md-4 control-label">必填项</label>

								<div class="col-md-8">
									<div class="radio-list">
										<label class="radio-inline">
										<div class="radio" id="uniform-optionsRadios25"><span><input type="radio" name="optionsRadios" id="optionsRadios25" value="option1" checked=""></span></div> 
										是
										</label>
										<label class="radio-inline">
										<div class="radio" id="uniform-optionsRadios26"><span class="checked"><input type="radio" name="optionsRadios" id="optionsRadios26" value="option2" checked=""></span></div> 
										否
										</label>
										  
									</div> 
								</div> 
						</div>

	 
					</div>
</script>

<script type="text/javascript"> 
		Common.init();   

		setTimeout(function(){
			var $item_list = $("#item-list");
			var item_tpl = $("#item-tpl").html();
			var $item = null;

			var bind_item = function($item){
					$item.find("[del]").click(function(){
						$item.remove();
					})
			}


				$("#add").click(function(){ 
					$item = $(item_tpl);
					$item_list.append($item);
					bind_item($item);
				})	

				$("#add").click();
		},100)
</script>
<%if("OK".equals(request.getAttribute("MSG"))){%>
<script type="text/javascript">
showModal('#msg');
</script>
<%}%>
</body>
</html>