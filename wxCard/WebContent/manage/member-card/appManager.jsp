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
										新增APP <i class="fa fa-plus"></i>
									</a>
									 
								</div>
							</div>
							<table class="table table-striped table-bordered table-hover" id="sample_1">
								<thead>
									<tr> 
										<th >编号</th>
										<th >名称</th>
										<th >描述</th> 
										<th >状态</th> 										
										<th >创建时间</th> 
										<th >修改时间</th> 
										<th >操作</th> 
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${list}" var="pojo">
										
											<tr class="odd gradeX">
											 
												<td class="text-center" >
													<span class="list-text">${pojo['APP_NUM']}</span>
												</td>
												<td class="text-center" >
													<span class="list-text">${pojo['APP_DISPLAY']}</span>
												</td>
												<td class="text-center">
													<span class="list-text">${pojo['APP_DESC']}</span>
												</td>
												 <td class="text-center">
													<span class="list-text">
														<c:if test="${pojo['APP_STATE'] == 'T'}">测试</c:if>
														<c:if test="${pojo['APP_STATE'] == 'N'}">正常</c:if>
														<c:if test="${pojo['APP_STATE'] == 'S'}">停用</c:if>
													</span>
												</td>
												<td class="text-center">
													<span class="list-text">${pojo['CREATE_TIME']}</span>
												</td>
												 <td class="text-center">
													<span class="list-text">${pojo['MODIFY_TIME']}</span>
												</td>
												 <td class="text-center">
													<a class="list-text" href="${p:go('','edit','') }&pojo.appid=${pojo['APP_ID']}">编辑</a>
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
						            <li class="prev disabled">
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

	
	
<script type="tpl" id="新增APP">
	
	<div class="form-horizontal">
		<div class="form-body">
			<DIV class="form-group">
				<label  class="col-md-3 control-label">app名称</label>
				<DIV class="col-md-8">
					<input NAME="pojo.APP_DISPLAY" TYPE="text" class="form-control"  placeholder=""> 
				</DIV>
			</DIV>

			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					描述
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.APP_DESC"  TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
 
			
			 

			<DIV class="form-group">
				<label  class="col-md-3 control-label">状态</label>
				<DIV class="col-md-8">
					<SELECT  class="form-control" NAME="pojo.APP_STATE">
						<OPTION VALUE="T">测试</OPTION>
						<OPTION VALUE="N">正常</OPTION>
						<OPTION VALUE="S">停用</OPTION>
					</SELECT>
				</DIV>

				<label   class="text-left col-md-1 control-label">
						 
				</label>
			</DIV>
			 
		</div>


	</div>
	<script>
		dialog_check = function(){
			 
			var number = $dialog.find("[name='pojo.APP_DISPLAY']").val();
			var number2 = $dialog.find("[name='pojo.APP_DESC']").val();
			var number3 = $dialog.find("[name='pojo.APP_STATE']").val();			

				return {
					'pojo.APP_DISPLAY' : number
					,'pojo.APP_DESC' : number2
					,'pojo.APP_STATE' : number3					
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
					  url: "${p:go('','insertApp','') }",
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

					$dialog = showModal_2('#msg','新增APP'
						,$("#新增APP").html());
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