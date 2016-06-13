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
<link rel="stylesheet" type="text/css" href="css/cardActivate.css">
</head>
<body>
<div class="page-content">
	<div class="portlet box  ">
		 
		<div class="portlet-body  ">

				<div class="row table-toolbar">
					 
						
						<label class="col-md-6 control-label">
							卡派发记录
						</label>
						
						<div class="text-right col-md-6" >
							<a href="physicalcardManage.jsp" class="btn green">返回</a>
						</div>
								 
				</div>
							<table class="table table-bordered table-striped table-condensed flip-content">
								<thead class="flip-content">

									<tr>
										 
										<th>
											卡名称
										</th>
										<th >制卡总量</th>
										<th >总部库存</th>
										<th >门店库存</th>
										<th >状态</th>
										<th >操作</th>
									</tr>
								</thead>
								<tbody>
									<tr class="odd gradeX">
										 
										<td>
											普通卡
										</td>
										<td >2000</td>
										<td >1000</td>
										<td class="center">2000</td>
										<td class="center">派发卡片</td>
										<td >
											<a class="label label-sm label-success">
												派发卡片
											</a>
										</td>
									</tr>

			 
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