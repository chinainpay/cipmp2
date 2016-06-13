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
<link rel="stylesheet" type="text/css" href="css/memberMar.css">
</head>
<body>
<div class="page-content">
	<div class="portlet box  ">
		 
		<div class="portlet-body  ">

				<ul class="nav nav-tabs">
					<li >
						<a   href="memberMar.jsp">开卡送礼</a></li>
					<li ><a   href="memberMar2.jsp">积分兑换</a></li>
					<li class="active"  ><a   href="memberMar3.jsp">储值奖励</a></li>
					<li><a   href="memberMar4.jsp">用户唤醒</a></li>
				</ul>

				<div class="row">
					 	
					 	<div id="add-card" class="col-md-6">
					 		<span class="fa fa-plus"></span> 新增储值奖励
					 	</div>

					 	<div class="col-md-6">
							<div class="btn-group pull-right">
	 
										<label> 
											<input type="text" aria-controls="sample_1" class="form-control input-medium">
										</label>

										<button id="sample_editable_1_new" class="btn green">
											搜索 <i class="fa fa-search"></i>
										</button> 
									 
							</div>
						</div>
					  
				</div>



				<table class="table table-striped table-bordered table-hover" id="sample_1">
								<thead>
									<tr>
										 
										<th>
											 活动名称
										</th>
										<th>储值奖励</th>
										<th>储值金额需满</th>
										<th>活动时间</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<tr class="odd gradeX">
										 
										<td>
											 大礼包
										</td>
										<td>叮当猫玩具一份</td>
										<td>150</td>
										<td class="center">2014-12-17——2015-01-01</td>
										<td class="center">进行中</td>
										<td>

											<div class="col-md-6">
												<a class=" label label-sm label-success">
													编辑
												</a>
											</div>
											
											<div class="col-md-6">
												<a class=" label label-sm label-success">
													活动失效
												</a>
											</div>
 
										</td>
									</tr>

			 
 								</tbody>
							</table>




				 
		</div>



	</div>
	<div class="modal fade" id="msg" class="modal fade" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					<h4 modal-title class="modal-title"></h4>
				</div>
				<div modal-body class="modal-body">
					
				</div>
				<div class="modal-footer">
					<button modal-submit type="button" data-dismiss="modal" class="btn green"><i class="icon-ok"></i>保存设置</button>
				</div>
			</div>
		</div>
	</div>
</div>



	
<script type="tpl" id="add-card-tpl">

	<style type="text/css">
		input.min{
			width: 50px;
		}

		select.min{
			width: 80px;
			display: inline-block;
		}
	</style>

	
	<div class="form-horizontal">
		<div class="form-body">
			<div class="form-group">
				<label  class="col-md-3 col-md-offset-1 ">设置活动名称</label>
				<div class="col-md-8">
					<input name="number" type="text" class="form-control"  placeholder=""> 
				</div>
			</div>

			
			<div class="form-group">
				<label  class="col-md-3 col-md-offset-1 ">储值类型</label>
				<div class="col-md-8">
					<div class="col-md-3" >
						<label>
							<input   type="radio"    />
							不限
						</label>
					</div> 

					<div class="col-md-4" >
						<label>
							<input   type="radio"    />
							线下储值
						</label>
					</div> 

					<div class="col-md-5" >
						<label>
							<input   type="radio"    />
							微信公众号内储值
						</label>
					</div> 
				</div>
			</div>

			<div class="form-group">
				<label  class="col-md-3 col-md-offset-1 ">设置奖励内容</label>
				<div class="col-md-8">
					<label>
						<input   type="radio"    />
						储值送现金
					</label>

					满<input class="min" type="text" />元

					送<input class="min" type="text" />元（返还到会员卡内）

				</div>

				<div class="col-md-8 col-md-offset-4 ">
					<label>
						<input   type="radio"    />
						储值送现金
					</label>

					满<input class="min" type="text" />元

					送<input class="min" type="text" />积分

				</div>

				<div class="col-md-8 col-md-offset-4 ">
					<label>
						<input   type="radio"    />
						储值送现金
					</label>

					满<input class="min" type="text" />元

					送
					 
							<select class="min form-control">
								<option>Option 1</option>
								<option>Option 2</option>
								<option>Option 3</option> 
							</select>
						 
							<select class="min form-control">
								<option>Option 1</option>
								<option>Option 2</option>
								<option>Option 3</option> 
							</select>
					 

				</div>

				<div class="col-md-8 col-md-offset-4 ">
					<label>
						<input   type="radio"    />
						储值送现金
					</label>

					满<input class="min" type="text" />元

					送<input class="min" type="text" /> 

				</div>
			</div>

			 

			 

			 

			 

			<div class="form-group">

				
				<label  class="col-md-3  col-md-offset-1  ">
					<input type="checkbox" class="group-checkable">
					短信通知
				</label>
				<div class="col-md-8">
					需要以短信形式将积分兑换活动通知会员
				</div> 
			</div>

			<div class="form-group">

				
				<label  class="col-md-3  col-md-offset-1 ">
					<input type="checkbox" class="group-checkable">
					活动时间
				</label>
				<div class="col-md-3">
					  
				 </div> 
			</div>
		  
		</div>


	</div>


	<script>


		dialog_check = function(){
			 
			var number = $dialog.find("[name='number']").val();

				if(!number){
					alert('没有填写手机号哦');
					return false;
				}

				return {
					number : number
				}

		}
 
		dialog_submit = function(){
			if(dialog_check() === false){
				return false;
			}

			var submit_data = dialog_check();

				console.log(submit_data);
				alert('dialog_submit1');
		}

	</script>

</script>
 

 


<script type="text/javascript"> 
		Common.init();   

		var $dialog = null;



		 
			
			setTimeout(function(){
				$("#msg").find("[modal-submit]").click(function(){
					if(dialog_submit() === false){
						return false;
					}
				});



				$("#add-card").click(function(){

					$dialog = showModal_2('#msg','新增储值奖励'
						,$("#add-card-tpl").html());
				})
			},100)
</script>
<%if("OK".equals(request.getAttribute("MSG"))){%>
<script type="text/javascript">
showModal('#msg');
</script>
<%}%>
</body>
</html>