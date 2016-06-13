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
<link rel="stylesheet" type="text/css" href="css/memberSystemset.css">
</head>
<body>
<div class="page-content">
	
	<form class="form-horizontal">

		<ul class="nav nav-tabs">
					<li >
						<a   href="memberSystemset.jsp">会员卡设置</a></li>
					<li ><a   href="memberSystemset2.jsp">会员卡设置</a></li>
					<li class="active"><a   href="memberSystemset3.jsp">会员等级设置</a></li> 
				</ul>
		 
		<div class="form-body  ">
				<div class="form-group">
					<label class="text-left col-md-5 control-label">
						勾选会员等级的判定标准（单选）
					</label>
					 
				</div>

				<div class="form-group">
					
					<div class="checkbox-list">
						<label class="checkbox-inline">
							<div class="checker" >
								<span>
									<input type="checkbox"   value="option1">
								</span>
							</div> 消费金额（积分）
						</label> 

						<label class="checkbox-inline">
							<div class="checker" >
								<span>
									<input type="checkbox"   value="option1">
								</span>
							</div> 开卡储值金额
						</label> 
					</div>
				</div>

				<table class="table table-striped table-bordered table-hover" id="sample_1">
								<thead>
									<tr>
										<th>
											操作	
										</th>
										<th>
											会员等级
										</th>
										<th>等级标准</th>
										<th>折扣额度（1-100）</th>
										 
									</tr>
								</thead>
								<tbody id="item-list">
									

			 
 								</tbody>
							</table>

							<div class="row">
								<div class="text-right col-md-12">
										<button type="button" id="add" class="btn green">
											 <i class="fa fa-plus"></i>
										</button>
								</div>
							</div>

							<div class="form-group">
								<label class="text-left col-md-2 control-label">
										是否有积分
								</label>
								<div class="radio-list col-md-8">
									<label class="radio-inline">
										<div class="radio" id="uniform-optionsRadios22">
											<span>
												<input type="radio" name="optionsRadios" id="optionsRadios22" value="option1" checked="">
											</span>
										</div> 是
									</label>

									<label class="radio-inline">
										<div class="radio" id="uniform-optionsRadios22">
											<span>
												<input type="radio" name="optionsRadios" id="optionsRadios22" value="option1" checked="">
											</span>
										</div> 否
									</label>
								</div>
							</div>


							<div class="form-group">
								
								<label class="text-left col-md-8 control-label">
										设置积分规则
								</label>	 
								<div class="text-left radio-list col-md-8">
									 
									<div class="checkbox-list">
												<label class="checkbox-inline">
													<div class="checker" id="uniform-inlineCheckbox21">	   <span>
															<input type="checkbox" id="inlineCheckbox21" value="option1">
														</span>
													</div> 

													1积分 = 消费 
													<input style="width:50px;" type="text" class="min form-control"  placeholder="">
													元
												</label>
												
												  
											</div>

									<div class="checkbox-list">
												<label class="checkbox-inline">
													<div class="checker" id="uniform-inlineCheckbox21">	   <span>
															<input type="checkbox" id="inlineCheckbox21" value="option1">
														</span>
													</div> 

													1积分 = 消费 
													<input style="width:50px;" type="text" class="min form-control"  placeholder="">
													元
												</label>


												
												  
											</div>
								</div>
							</div>
 			
 			<div class="row">
				<div class="text-center col-md-12">
						<button type="submit" class="btn green">
							保存设置
						</button>
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

	
<script type="tpl" id="item-tpl">
	<tr item class="odd gradeX">
				
				<td>
					<button del class="btn red">
						 <i class="fa fa-times"></i>
					</button>
				</td> 
				<td>
					
					<input type="text" class="min form-control"  placeholder="">
				</td>
				<td>
					 
						
						<label class="control-label">消费</label>
						<input type="text" class="min form-control"  placeholder="">
						<label   class="control-label">元</label>
						
					 
						<label class="control-label">至</label>
						<input type="text" class="min form-control"  placeholder="">
						<label   class="control-label">元</label>
						
					 
					
				</td>
				<td>
					<label>
						<input type="text" class="min form-control"  placeholder="Enter text">
					 	%

					</label>
							


						
					 
					
					
				</td>
				 
			</tr>
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