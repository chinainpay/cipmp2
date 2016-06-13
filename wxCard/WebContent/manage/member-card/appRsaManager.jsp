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
							</div>
							<table class="table table-striped table-bordered table-hover" id="sample_1">
								<thead>
									<tr> 
										<th >登录名</th>										
										<th >描述</th> 
										<th >类型</th> 										
										<th >最大链接数</th> 										
										<th >应用程序列表</th> 
										<th >操作</th> 
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${rsalist}" var="pojo">
										
											<tr class="odd gradeX">
											 
												<td class="text-center" >
													<span class="list-text">${pojo['RSA_LOGIN']}</span>
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
													<c:forEach items="${applist}" var="app">
														<c:if test="${app.key==pojo['RSA_LOGIN']}">
															<c:forEach items="${app.value }" var="APPList">	
																${APPList['APP_NUM']}——${APPList['APP_DISPLAY']}
															<br/>
															</c:forEach>
														</c:if>
													</c:forEach>
												</td>											
												 <td class="text-center">
													<a class="list-text" href="${p:go('','appList','') }&pojo.RSA_LOGIN=${pojo['RSA_LOGIN']}">编辑</a>
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


<%if("OK".equals(request.getAttribute("MSG"))){%>
<script type="text/javascript">
showModal('#msg');
</script>
<%}%>
</body>
</html>