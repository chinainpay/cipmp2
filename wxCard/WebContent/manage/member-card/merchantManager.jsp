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
<style type="text/css">
	.list-pic{
		width: 200px;
		height: 100px;
	}

	.list-text{
		line-height: 100px;
	} 


</style>
</head>
<body>
<div class="page-content">
	<div class="portlet box  ">
		 
		<div class="portlet-body  ">

				<div class="table-toolbar">
								
								<div style="margin-bottom:20px;"  class="btn-group pull-right">
 
									<a id="add" href="javascript:;" class="btn green">
										新增商户<i class="fa fa-plus"></i>
									</a>
									 
								</div>
							</div>
							<table class="table table-striped table-bordered table-hover" id="sample_1">
								<thead>
									<tr> 
										<th >商户号</th>
										<th >商户来源</th>
										<th >商户类型</th> 
										<th >商户级别</th> 										
										<th >商户级别有效期</th> 
										<th >商户名称</th> 
										<th >安全邮箱</th> 
										<th >联系人手机</th> 
										<th >联系人电话</th> 
										<th >联系人QQ号</th> 
										<th >联系人EMAIL</th> 
										<th >邀请码</th> 
										<th >商户管理密码</th> 
										<th >商户状态</th> 
										<th >操作</th> 
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${list}" var="pojo">
										
											<tr class="odd gradeX">
											 
												<td class="text-center" >
													<span class="list-text">${pojo['MER_CODE']}</span>
												</td>
												<td class="text-center" >
													<span class="list-text">
														<c:if test="${pojo['MER_SOURCE'] == 'KF'}">开发</c:if>
														<c:if test="${pojo['MER_SOURCE'] == 'SH'}">高汇通已有商户</c:if>
														<c:if test="${pojo['MER_SOURCE'] == 'WZ'}">网站</c:if>
														<c:if test="${pojo['MER_SOURCE'] == 'SS'}">销售</c:if>
													</span>
												</td>
												<td class="text-center">
													<span class="list-text">
														<c:if test="${pojo['MER_TYPE'] == 'Z'}">自有功能的商户</c:if>
														<c:if test="${pojo['MER_TYPE'] == 'K'}">开发商户</c:if>
														<c:if test="${pojo['MER_TYPE'] == 'S'}">标准商户</c:if>
													</span>
												</td>
												 <td class="text-center">
													<span class="list-text">
														<c:if test="${pojo['MER_LEVEL'] == 'J'}">基础</c:if>
														<c:if test="${pojo['MER_LEVEL'] == 'Q'}">旗舰</c:if>
													</span>
												</td>
												<td class="text-center">
													<span class="list-text">${pojo['MER_EXPIRYDATE']}</span>
												</td>
												 <td class="text-center">
													<span class="list-text">${pojo['MER_NAME']}</span>
												</td>
												 <td class="text-center">
													<span class="list-text">${pojo['SECURE_EMAIL']}</span>
												</td>
												 <td class="text-center">
													<span class="list-text">${pojo['LINKMAN_PHONE']}</span>
												</td>
												 <td class="text-center">
													<span class="list-text">${pojo['LINKMAN_TEL']}</span>
												</td>
												 <td class="text-center">
													<span class="list-text">${pojo['LINKMAN_QQ']}</span>
												</td>
												 <td class="text-center">
													<span class="list-text">${pojo['LINKMAN_EMAIL']}</span>
												</td>
												 <td class="text-center">
													<span class="list-text">${pojo['INVITE_CODE']}</span>
												</td>
												 <td class="text-center">
													<span class="list-text">${pojo['MER_PWD']}</span>
												</td>
												 <td class="text-center">
													<span class="list-text">
														<c:if test="${pojo['MER_STATE'] == 'K'}">开发</c:if>
														<c:if test="${pojo['MER_STATE'] == 'S'}">生产</c:if>
													</span>
												</td>
												 <td class="text-center">
													<a class="list-text" href="${p:go('','edit','') }&pojo.MER_CODE=${pojo['MER_CODE']}">编辑</a>
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
						            <li class="prev">
						                <a href="#" title="Prev">
						                    <i class="fa fa-angle-left">
						                    </i>
						                </a>
						            </li>
						            <li class="active">
						                <a href="#">
						                    1
						                </a>
						            </li>
						            
						            <li class="next">
						                <a href="#" title="Next">
						                    <i class="fa fa-angle-right">
						                    </i>
						                </a>
						            </li>
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
					<button modal-submit type="button" data-dismiss="modal" class="btn green"><i class="icon-ok"></i>确认添加</button>
				</div>
			</div>
		</div>
	</div>
</div>

	
	
<script type="tpl" id="新增商户">
	
	<div class="form-horizontal">
		<div class="form-body">
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					*商户来源
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<SELECT  class="form-control" NAME="pojo.MER_SOURCE">
						<OPTION VALUE="">请选择</OPTION>
						<OPTION VALUE="KF">开发</OPTION>
						<OPTION VALUE="SH">高汇通已有商户</OPTION>
						<OPTION VALUE="WZ">网站</OPTION>
						<OPTION VALUE="SS">销售</OPTION>
					</SELECT>
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					*商户类型
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<SELECT  class="form-control" NAME="pojo.MER_TYPE">
						<OPTION VALUE="">请选择</OPTION>
						<OPTION VALUE="Z">自有功能的商户</OPTION>
						<OPTION VALUE="K">开发用商户</OPTION>
						<OPTION VALUE="S">标准商户</OPTION>
					</SELECT>
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					*商户级别
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<SELECT  class="form-control" NAME="pojo.MER_LEVEL">
						<OPTION VALUE="">请选择</OPTION>
						<OPTION VALUE="J">基础</OPTION>
						<OPTION VALUE="Q">旗舰</OPTION>
					</SELECT>
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">*商户级别有效期</label>
				<DIV class="col-md-8">
						<input NAME="pojo.MER_EXPIRYDATE"  TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">
						 
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					*商户名称
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.MER_NAME"  TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					*安全邮箱
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.SECURE_EMAIL"  TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					*联系人手机
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.LINKMAN_PHONE"  TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					联系人电话
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.LINKMAN_TEL"  TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					联系人QQ号
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.LINKMAN_QQ"  TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					联系人EMAIL
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.LINKMAN_EMAIL"  TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					邀请码
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.INVITE_CODE"  TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					*商户管理密码
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.MER_PWD"  TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					*商户状态
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<SELECT  class="form-control" NAME="pojo.MER_STATE">
						<OPTION VALUE="">请选择</OPTION>
						<OPTION VALUE="K">开发</OPTION>
						<OPTION VALUE="S">生产</OPTION>
					</SELECT>					
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			 
		</div>


	</div>
	<script>
		dialog_check = function(){
			var number2 = $dialog.find("[name='pojo.MER_SOURCE']").val();
			var number3 = $dialog.find("[name='pojo.MER_TYPE']").val();
			var number4 = $dialog.find("[name='pojo.MER_LEVEL']").val();
			var number5 = $dialog.find("[name='pojo.MER_EXPIRYDATE']").val();
			var number6 = $dialog.find("[name='pojo.MER_NAME']").val();			
			var number7 = $dialog.find("[name='pojo.SECURE_EMAIL']").val();			
			var number8 = $dialog.find("[name='pojo.LINKMAN_PHONE']").val();
			var number9 = $dialog.find("[name='pojo.LINKMAN_TEL']").val();
			var number10 = $dialog.find("[name='pojo.LINKMAN_QQ']").val();
			var number11 = $dialog.find("[name='pojo.LINKMAN_EMAIL']").val();
			var number12 = $dialog.find("[name='pojo.INVITE_CODE']").val();
			var number13 = $dialog.find("[name='pojo.MER_PWD']").val();			
			var number14 = $dialog.find("[name='pojo.MER_STATE']").val();			
				return {					
					'pojo.MER_SOURCE' : number2
					,'pojo.MER_TYPE' : number3					
					,'pojo.MER_LEVEL' : number4
					,'pojo.MER_EXPIRYDATE' : number5				
					,'pojo.MER_NAME' : number6
					,'pojo.SECURE_EMAIL' : number7				
					,'pojo.LINKMAN_PHONE' : number8
					,'pojo.LINKMAN_TEL' : number9
					,'pojo.LINKMAN_QQ' : number10					
					,'pojo.LINKMAN_EMAIL' : number11
					,'pojo.INVITE_CODE' : number12				
					,'pojo.MER_PWD' : number13
					,'pojo.MER_STATE' : number14				
				}

		}
 
		dialog_submit = function(){
			if(dialog_check() === false){
				return false;
			}

			var submit_data = dialog_check();

				console.log(submit_data);

				
				$.ajax({
					  type: "POST",
					  url: "${p:go('','insertMerchant','') }",
					  dataType: "json",
					  data: jQuery.param(submit_data),
					  success: function(msg){
					     if(msg){
					     	if(msg['errorMsg']){
					     		alert(msg['errorMsg']);
					     		return;
					     	}
					     }

					     	window.location.reload(true);
					  }
				});
				 
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

				$("#add").click(function(){

					$dialog = showModal_2('#msg','新增商户'
						,$("#新增商户").html());
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