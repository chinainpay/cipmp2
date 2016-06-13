<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration, java.util.LinkedHashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>


<!DOCTYPE html>
<html lang="chs" class="no-js">
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8" />
	<title></title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />	 
	<script type="text/javascript" src="/resources/own/script/common.js"></script>

	<style type="text/css">
	.page-content {margin-left: 0px;}
	</style>
</head>
<body  >

	
	
	<div class="page-content">
		 <div class="row">
			<div class="col-md-12">
				<span class="title001">处理制卡请求</span>
			</div>
		 </div>

		 <div class="row">
			<div class="col-md-10">
				<!-- BEGIN SAMPLE TABLE PORTLET-->
				 	 <iframe name="hidden_frame"  style="display:none"></iframe>  
					<form id="form" name="form"  action="${p:go('','exportTxt','') }"  enctype="multipart/form-data" method="post" target="hidden_frame" >
						<input id="file" type="file" name="file" style="display: none" />
						
			        	<input type="hidden"  name="makeCard_id" id="makeCard_id"  />
			        	<input type="hidden"  name="card_pro" id="card_pro" value="" />
			        	
			        	
			       
			        	
			        </form>
						<div class="portlet-body">
							<div class="table-responsive">
								<table class="table table-bordered">
								<thead>
								<tr>
									<th>
										 商户名称
									</th>
									<th>
										 卡产品名称
									</th>
									<th>
										 制卡数量
									</th>

									<th>
										 制卡请求时间
									</th>

									<th>
										 状态
									</th>
									<th>
										卡类型
									</th>
									<th>
										 下载次数
									</th>

									<th>
										 操作
									</th>
									 
								</tr>
								</thead>
								<tbody>
								
								
								<c:forEach items="${list}" var="pojo"  varStatus="status">
								
										 
								
								
								
								
								<tr _file_form>
									
									<td>
											${pojo['MER_CODE']}  
									</td>
									<td>
										 	${pojo['PROC_NO']}
									</td>
									<td>
											${pojo['CARD_AMT']}
									</td>


									<td>
										 	${pojo['ASK_TIME']}
									</td>
									<td>
									
									                <c:if test="${pojo['CARD_STATES']=='0'}" var="_testAccount" scope="session" >
														<b style="color: red;" >申请中 </b>
													</c:if>
													<c:if test="${pojo['CARD_STATES']=='1'}" var="_testAccount" scope="session" >
														<b style="color: green;" >已经上传数据  </b>
													</c:if>
													<c:if test="${pojo['CARD_STATES']=='2'}" var="_testAccount" scope="session" >
														<b style="color: green;" >已经下载数据  </b>
													</c:if>
													<c:if test="${pojo['CARD_STATES']=='3'}" var="_testAccount" scope="session" >
														<b style="color: green;" >已经入库 </b>
													</c:if>
									
										 
									</td>
									<td>
											<c:if test="${pojo['VIRTUAL_CARD'] == 0 }">虚拟卡</c:if> 
											<c:if test="${pojo['VIRTUAL_CARD'] == 1 }">实体卡</c:if> 
									</td>
									<td>
											${pojo['DOWM_NUM']} 次
									</td>
									
									<td>
									   
										 <button data-makeCard_id="${pojo['MAKECARD_NO']}" data-card_pro="${pojo['PROC_NO']}" file_btn type="button" class="btn btn-info"  onclick="setData('${pojo['MAKECARD_NO']}')"> 上传制卡数据 </button>	
									</td>
									 				
								</tr>
								
								
								
												
								 </c:forEach>
								</tbody>
								</table>
							</div>
						</div>
					 
			</div>
		 </div>
		 
		 
		 
	<div class="modal fade" id="msg" class="modal fade" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					<h4 class="modal-title">${MSGCONTENT}</h4>
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn green"><i class="icon-ok"></i>确认</button>
				</div>
			</div>
		</div>
	</div>	 
		 
		 

	</div>
	     
	     
	  
	     
	<script type="text/javascript">
	Common.init();
	
	window.upload_callback = function(msg){
		if(msg){
			if(msg['errorMsg']){
				alert(msg['errorMsg']);
				return;
			}
		}
		
		window.location.reload();
	}
	$(function(){ 
		setTimeout(function(){ 
			
			var bind = function($file_form){
			 
				$file_form.find("[file_btn]").click(function(){ 
					$("#card_pro").val($(this).attr('data-card_pro'));
					$("#makeCard_id").val($(this).attr('data-makeCard_id'));
				 
					setTimeout(function(){
						$("#file").click();
					},100)
				});
				
				
				
			}
			
			var $iframe = $("[name='hidden_frame']");
				$iframe.load(function(){
					 
				})
			
		 
			
			$("[_file_form]").each(function(){
				bind($(this));
			});
			
			
			$("#file").change(function(){    
					$("#form").submit(); 
			});
			
			
		},100)
	
		
		
		
	})
	
	
	
	
	function setData(a){
		document.getElementById('makeCard_id').value=a; 
		
		
	}
	
	
	
	
	
	</script>
	
	
	<%if("OK".equals(request.getAttribute("MSGCODE"))){%>
		<script type="text/javascript">
		showModal('#msg');
	</script>
<%}%>
	
</body>
</html>