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
	<div class="portlet box  ">
		 
		<div class="portlet-body  ">

				<ul class="nav nav-tabs">
					<li class="active">
						<a   href="memberMar.jsp">开卡送礼</a></li>
					<li><a   href="memberMar2.jsp">积分兑换</a></li>
					<li><a   href="memberMar3.jsp">储值奖励</a></li>
					<li><a   href="memberMar4.jsp">用户唤醒</a></li>
				</ul>

				<div class="row">
					 	
					 	<div id="add-card" class="col-md-6">
					 		<span class="fa fa-plus"></span> 新增会员新增开卡送礼活动
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
											<input type="checkbox" class="group-checkable"> 待激活卡性质
										</th>
										<th>卡号</th>
										<th>售卡金额</th>
										<th>卡数量</th>
										<th>购卡人</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<tr class="odd gradeX">
										 
										<td>
											<input type="checkbox" class="checkboxes" value="1">团体购卡
										</td>
										<td>0001——1000</td>
										<td>50.00</td>
										<td class="center">1000</td>
										<td class="center">王小花（香奈儿公司）</td>
										<td>
											<div class="col-md-6">
													<a class="label label-sm label-success">
														编辑
													</a>
											</div>

											<div class="col-md-6">
													<a class="label label-sm label-success">
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
	
	<div class="form-horizontal">
		<div class="form-body">
			<div class="form-group">
				<label  class="col-md-3 col-md-offset-1 ">设置活动名称</label>
				<div class="col-md-8">
					<input name="number" type="text" class="form-control"  placeholder=""> 
				</div>
			</div>

			<div class="form-group">
				<label  class="col-md-10  col-md-offset-1 ">设置初次办会员卡的奖励</label>
				 
			</div>

			<div class="form-group">

				
				<label  class="col-md-3   col-md-offset-1 ">
					<input type="checkbox" class="group-checkable">
					送积分
				</label>
				<div class="col-md-3">
					<input type="text" class="form-control"  placeholder=""> 
					
				</div>
					分
			</div>

			<div class="form-group">

				
				<label  class="col-md-3  col-md-offset-1  ">
					<input type="checkbox" class="group-checkable">
					送优惠券
				</label>
				<div class="col-md-3">
					<select class="form-control">
						<option>Option 1</option>
						<option>Option 2</option>
						<option>Option 3</option> 
					</select>
				</div>

				<div class="col-md-3">
					<select class="form-control">
						<option>Option 1</option>
						<option>Option 2</option>
						<option>Option 3</option> 
					</select>
				</div> 
					 
			</div>

			<div class="form-group">

				
				<label  class="col-md-3  col-md-offset-1  ">
					<input type="checkbox" class="group-checkable">
					送奖品
				</label>
				<div class="col-md-3">
					<input type="text" class="form-control"  placeholder=""> 
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

					$dialog = showModal_2('#msg','新增开卡'
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