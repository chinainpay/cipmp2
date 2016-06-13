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
										新增RSA <i class="fa fa-plus"></i>
									</a>
									 
								</div>
							</div>
							<table class="table table-striped table-bordered table-hover" id="sample_1">
								<thead>
									<tr> 
										<th >登录名</th>
										<th >登录密码</th>
										<th >描述</th> 
										<th >类型</th> 										
										<th >最大链接数</th> 
										<th >IP锁</th> 
										<th >操作</th> 
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${list}" var="pojo">
										
											<tr class="odd gradeX">
											 
												<td class="text-center" >
													<span class="list-text">${pojo['RSA_LOGIN']}</span>
												</td>
												<td class="text-center" >
													<span class="list-text">${pojo['RSA_PWD']}</span>
												</td>
												<td class="text-center">
													<span class="list-text">${pojo['RSA_DESC']}</span>
												</td>
												 <td class="text-center">
													<span class="list-text">
														<c:if test="${pojo['RSA_TYPE'] == 'T'}">测试</c:if>
														<c:if test="${pojo['RSA_TYPE'] == 'N'}">正常</c:if>
														<c:if test="${pojo['RSA_TYPE'] == 'S'}">停用</c:if>
													</span>
												</td>
												<td class="text-center">
													<span class="list-text">${pojo['RSA_POOL']}</span>
												</td>
												 <td class="text-center">
													<span class="list-text">${pojo['RSA_IPC']}</span>
												</td>
												 <td class="text-center">
													<a class="list-text" href="${p:go('','edit','') }&pojo.RSA_LOGIN=${pojo['RSA_LOGIN']}">编辑</a>
													<a class="list-text" href="${p:go('','delRsa','') }&pojo.RSA_LOGIN=${pojo['RSA_LOGIN']}">删除</a>
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

	
	
<script type="tpl" id="新增RSA">
	
	<div class="form-horizontal">
		<div class="form-body">
			<DIV class="form-group">
				<label  class="col-md-3 control-label">登录名</label>
				<DIV class="col-md-8">
					<input NAME="pojo.RSA_LOGIN" TYPE="text" class="form-control"  placeholder=""> 
				</DIV>
			</DIV>

			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					密码
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.RSA_PWD"  TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					确认密码
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.RE_RSA_PWD"  TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					描述
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.RSA_DESC"  TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">状态</label>
				<DIV class="col-md-8">
					<SELECT  class="form-control" NAME="pojo.RSA_TYPE">
						<OPTION VALUE="T">测试</OPTION>
						<OPTION VALUE="N">正常</OPTION>
						<OPTION VALUE="S">停用</OPTION>
					</SELECT>
				</DIV>

				<label   class="text-left col-md-1 control-label">
						 
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					最大连接数
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.RSA_POOL"  TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					IP锁
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.RSA_IPC"  TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			 
		</div>


	</div>
	<script>
		dialog_check = function(){
			 
			var number = $dialog.find("[name='pojo.RSA_LOGIN']").val();
			var number2 = $dialog.find("[name='pojo.RSA_PWD']").val();
			var number3 = $dialog.find("[name='pojo.RE_RSA_PWD']").val();
			var number4 = $dialog.find("[name='pojo.RSA_DESC']").val();
			var number5 = $dialog.find("[name='pojo.RSA_TYPE']").val();
			var number6 = $dialog.find("[name='pojo.RSA_POOL']").val();			
			var number7 = $dialog.find("[name='pojo.RSA_IPC']").val();			

				return {
					'pojo.RSA_LOGIN' : number
					,'pojo.RSA_PWD' : number2
					,'pojo.RE_RSA_PWD' : number3					
					,'pojo.RSA_DESC' : number4
					,'pojo.RSA_TYPE' : number5				
					,'pojo.RSA_POOL' : number6
					,'pojo.RSA_IPC' : number7				
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
					  url: "${p:go('','insertRSA','') }",
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

					$dialog = showModal_2('#msg','新增RSA'
						,$("#新增RSA").html());
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