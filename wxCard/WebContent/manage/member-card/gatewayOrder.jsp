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
 
</head>
<body>
<div class="page-content">
	<div class="portlet box  ">
		 
		<div class="portlet-body  ">

				<div class="table-toolbar">

								<div class="col-md-6">
<!-- 									<a href="newMember.jsp" > -->
<!-- 										<span class="fa fa-plus"></span>	新增会员 -->
<!-- 									</a> -->
									
								</div>
								<form action="${p:go('','pageInto','') }">
								<div class="btn-group pull-right">
									<label> 
										<input  maxlength="19" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  name ="pojo.ss" type="text" aria-controls="sample_1" placeholder="请输入订单号" class="form-control input-medium" value="${ssText}">
									</label>

									<button id="sample_editable_1_new" class="btn green" >
										搜索 <i class="fa fa-search"></i>
									</button>
								</div>
								</form>
							</div>
							<table class="table table-striped table-bordered table-advance table-hover" id="sample_1">
								<thead>
									<tr>
										 
										<th>
											订单编号
										</th>
										<th>
											商户编号
										</th>
										<th>
											商户名称
										</th>
										<th >交易卡号</th>
										<th >订单金额</th>
										<th >订单内容</th>
										<th >交易时间</th>
										<th >交易状态</th>
									</tr>
								</thead>
								<tbody>
								 <c:forEach items="${list}" var="pojo">
										<tr class="odd gradeX">
										
											<td>
												${pojo['ORDER_ID']}
											</td>
											<td >${pojo['MER_CODE']}</td>
											<td >${pojo['MER_NAME']}</td>
											<td class="center">${pojo['CARD_NO']}</td>
											<td class="center">${pojo['TOTAL_FEE']/100}</td>
											<td class="center">${pojo['ORDER_CONTENT']}</td>
											<td class="center">${pojo['TIME_END']}</td>
											<td >
												<c:if test="${pojo['TRADE_STATE']==1}">交易完成</c:if>
												<c:if test="${pojo['TRADE_STATE']==0}">交易中...</c:if>
												<c:if test="${pojo['TRADE_STATE']==3}">交易关闭</c:if>
											</td>
										</tr>
									</c:forEach>
			 
 								</tbody>
							</table> 
				</div>
				<a href="${p:go('','download','') }" target="_self">下载数据</a>
				<div class="row">
						<div class=" col-md-12 col-sm-12 ">
						    <div class="pull-right dataTables_paginate paging_bootstrap">
						        <ul class="pagination" style="visibility: visible;">
						            <li class="prev">
						                <a href="${p:go('','pageInto','') }&pojo.page=${page-1}&pojo.ss=${ssText}" title="Prev">
						                    <i class="fa fa-angle-left">
						                    </i>
						                </a>
						            </li>
						            <li class="active">
						                <a href="#">
						                    ${page}/ ${sum}
						                </a>
						            </li>
						            
						            <li class="next">
						                <a href="${p:go('','pageInto','') }&pojo.page=${page+1}&pojo.ss=${ssText}" title="Next">
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