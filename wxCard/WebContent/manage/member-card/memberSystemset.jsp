<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration,java.util.LinkedHashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="chs" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="chs" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="chs">
<!--<![endif]-->
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<script type="text/javascript" src="/resources/own/script/common.js"></script>
<link rel="stylesheet" type="text/css"
	href="/resources/assets/global/css/bootstrapSwitch.css">
<link rel="stylesheet" type="text/css" href="css/memberSystemset.css">
<style type="text/css">
input.min {
	width: 100px;
	margin-left: 10px;
	margin-right: 10px;
}

input[type='checkbox'] {
	margin-right: 10px;
}

.row {
	margin-top: 10px;
}

.bootstrap-switch {
	width: 100px;
}
</style>
</head>
<body>
	<div class="page-content">


		<div class="portlet box  ">

			<div class="portlet-body form-body   ">

				<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab" href="#page1">会员卡设置</a></li>
<!-- 					<li><a data-toggle="tab" href="#page2">会员资料设置</a></li> -->
				</ul>


				<div class="tab-content">

					<div class="tab-pane active" id="page1">
						<form method="post" class="form-horizontal"
							action="${p:go('','memberCardSetUp','') }"  enctype="multipart/form-data">
<!-- 							<div id="" class="row"> -->
<!-- 								<label class="text-right col-md-3">支付参数1 </label> -->
<!-- 								<div class="col-md-4"> -->
<%-- 									<input name="pojo.TERMINAL" type="text" class="form-control"   value="${pojo['TERMINAL']}"/> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div id="" class="row"> -->
<!-- 								<label class="text-right col-md-3">支付参数2</label> -->
<!-- 								<div class="col-md-4"> -->
<%-- 									<input name="pojo.MTORENO" type="text" class="form-control"   value="${pojo['MTORENO']}"/> --%>
<!-- 								</div> -->
<!-- 							</div> -->
							<div class="row">
								<label class="text-right col-md-3"> 单张售卡是否需要激活 </label>
								<div class="col-md-7">
									<input name="pojo.PERSONALCARD" bootstrapSwitch type="checkbox"
										<c:if test="${pojo['ALLOW_ACTIVATION_PERSONALCARD'] == 0}">checked</c:if>
										data-on-color="是" data-off-color="否">
									（卡被售出后，在使用之前是否需要财务部门激活确认）

								</div>
							</div>

							<div class="row">
								<label class="text-right col-md-3"> 是否允许批量售卡 </label>
								<div class="col-md-7">
									<input name="GroupCard" bootstrapSwitch type="checkbox"
										<c:if test="${pojo['ALLOW_ACTIVATION_TREAMCARD'] != '2'}">checked</c:if>
										data-on-color="是" data-off-color="否">
								</div>
							</div>



							<div style="display: none;" id="TREAMCARD" class="row">
								<label class="text-right col-md-3"> 批量售卡是否需要激活 </label>
								<div class="col-md-7">

									<input name="pojo.TREAMCARD" bootstrapSwitch type="checkbox"
										<c:if test="${pojo['ALLOW_ACTIVATION_TREAMCARD'] == 0}">
			 										checked 	
			 										</c:if>
										data-on-color="是" data-off-color="否">
									（卡被售出后，在使用之前是否需要财务部门激活确认）

								</div>
							</div>
							<div class="row">
								<label class="text-right col-md-3"> 微信虚拟卡 </label>
								<div class="col-md-7">
									<input name="GroupCard2" bootstrapSwitch type="checkbox"
										<c:if test="${pojo['ALLOW_ACTIVATION_VIRTUALCARD'] == 0}">checked</c:if> data-on-color="开"
										data-off-color="关">
								</div>
							</div>
<!-- 							<div style="display: none;" id="TREAMCARD2" class="row"> -->
<!-- 								<label class="text-right col-md-3"> 虚拟卡类型 </label> -->
<!-- 								<div class="col-md-3"> -->
<!-- 									<select name="pojo.v1" class="form-control"> -->
<!-- 									<option value="0">请选择</option> -->
<%-- 									 <c:forEach items="${cardTypeList}" var="pojo2"> --%>
<%-- 										<option <c:if test="${pojo['VIRTUALCARD_TYPE'] == pojo2['CARD_TYPE']}">selected</c:if> value="${pojo2['CARD_TYPE']}">${pojo2['CARD_TYPE']}</option> --%>
<%-- 									</c:forEach> --%>
<!-- 									</select> -->
<!-- 								</div> -->

<!-- 							</div> -->
							<div style="display: none;" id="TREAMCARD4" class="row">
								<label class="text-right col-md-3">虚拟卡图片</label>
								<div class="col-md-6">
									
										<!-- img alt="上传图片" src="" onerror="imgs/errorImg.jpg" -->
										<input type="file" name="file"  value="上传图片"/>	
								</div>
							</div>
							<div style="display: none;" id="TREAMCARD3" class="row">
								<label class="text-right col-md-3"> 微信特权 </label>
								<div class="col-md-6">
									<textarea name="pojo.v2" class="form-control"
										style="margin: 0px -47.5px 0px 0px; height: 202px; width: 100%;" >${pojo['VIRTUALCARD_PRIVILEGE']}</textarea>
								</div>
							</div>
<!-- 							<div id="TREAMCARD5" class="row"> -->
<!-- 								<label class="text-right col-md-3">网关支付回调地址 </label> -->
<!-- 								<div class="col-md-6"> -->
<%-- 									<input name="pojo.http" type="text" class="form-control" placeholder="http://"  value="${pojo['GATEWAY_URL']}"/> --%>
<!-- 								</div> -->
<!-- 							</div> -->
							
							<div class="row">
								<div class="text-center col-md-12">
									<button type="submit" class="btn green">保存设置</button>
								</div>
							</div>
						</form>
					</div>

					<script>
							var memberInfo = ${memberInfo};
						</script>
					<div class="tab-pane" id="page2">

						<form method="post" class="form-horizontal"
							action="${p:go('','memberSetUp','') }">
							<table class="table table-striped table-bordered table-hover">
								<thead>

								</thead>
								<tbody id="item-list">



								</tbody>
							</table>


							<div style="margin-top: 30px;" class="row">
								<div class="text-right col-md-12">
									<button id="add" type="button" class="btn green">添加</button>
								</div>
							</div>

							<div class="row">
								<div class="text-center col-md-12">
									<button type="submit" class="btn green">保存设置</button>
								</div>
							</div>
						</form>
					</div>
				</div>


			</div>



		</div>
		<div class="modal fade" id="msg" class="modal fade" tabindex="-1">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button>
						<h4 modal-title class="modal-title">修改成功</h4>
					</div>
					<div modal-body class="modal-body"></div>
					<div class="modal-footer">
						<button modal-submit type="button" data-dismiss="modal"
							class="btn green">
							<i class="icon-ok"></i>确认
						</button>
					</div>
				</div>
			</div>
		</div>


	</div>



	<script type="tpl" id="item-tpl">
	 <tr item class="odd gradeX">
				<input name="pojo.NO" type="hidden" >
				 
				<td> 
					<label class="control-label">字段</label>
					<input name="pojo.PRO_NAME" type="text" class="min form-control"  placeholder="">
				</td>

				<td> 
					<label class="control-label">字段提示文字</label>
					<input name="pojo.PROMPT_CONTENT" type="text" class="min form-control"  placeholder="">
				</td>

				<td> 
					<label class="control-label">字段类型</label> 
					<select  name="pojo.PRO_TYPE" >
						<option>文本</option>
						<option>数字</option>
						<option>日期</option>
						<option>日期+时间</option>
						<option>时间</option>
					</select>
				</td>
				<td>
					 
						
						<label class="control-label">必填项</label>
						<input  name="pojo.IS_REQUIRED"  value="test"   bootstrapSwitch type="checkbox" checked 
 										data-on-color="是"
 										data-off-color="否"
 										>
						
					 
					
				</td>
				<td>
					<button del class="btn red">
						 <i class="fa fa-times"></i>
					</button>
				</td>
				 
			</tr>
</script>






	<script type="text/javascript"> 
		Common.init();   
		var indexYS = 0;
		setTimeout(function(){
			var $item_list = $("#item-list");
			var item_tpl = $("#item-tpl").html();
			var $item = null;

			var bind_item = function($item){
					$item.find("[del]").click(function(){
						$item.remove();
					})
					
					bind_bootstrapSwitch($item.find("[bootstrapSwitch]"));
			};
			
			var add_item = function(data){
						console.log(data)
					var item = item_tpl; 
						$item = $(item);
						/*
						if(data){
							for(var i in data){
								
								if(i == 'IS_REQUIRED'){ 
									if(data[i]== '0'){
										data[i] = true;
									}else{
										data[i] = false;
									}
									$item.find("[name='pojo."+i+"[]']")[0].checked = data[i];
								}else{
									$item.find("[name='pojo."+i+"[]']").val(data[i]);
								}
								
								
							}
							
						}
						*/
						
						if(data){
							$item.find("[name='pojo.PRO_NAME']").val(data['PRO_NAME']);
							$item.find("[name='pojo.PROMPT_CONTENT']").val(data['PROMPT_CONTENT']);
							$item.find("[name='pojo.PRO_TYPE']").val(data['PRO_TYPE']);
							if(data['IS_REQUIRED'] == '0'){
								$item.find("[name='pojo.IS_REQUIRED']")[0].checked = true;
								$item.find("[name='pojo.IS_REQUIRED']").val('on');
							}else{
								$item.find("[name='pojo.IS_REQUIRED']")[0].checked = false;
								$item.find("[name='pojo.IS_REQUIRED']").val('off');
							}
							
							
							
						}
						
					 
						$item.find("[name='pojo.IS_REQUIRED']").attr('name','pojo.IS_REQUIRED'+($("[name^='pojo.IS_REQUIRED']").size()+1));
						$item_list.append($item);
						/*
						var req= $item.find("[name='pojo.NO']");
						req[0].value=(indexYS);
						var req= $item.find("[name='pojo.IS_REQUIRED']");
						req[0].name="pojo.IS_REQUIRED"+(indexYS++);
						*/
						bind_item($item);
						$item.find("[bootstrapSwitch]").bootstrapSwitch();
						return $item;
			
			}


				$("#add").click(function(){ 
					
					
					add_item();
					
					/*
					console.log('bootstrapSwitch');
					$("[bootstrapSwitch]").each(function(){
						console.log(this.checked);
					})
					*/
				})	
				
				//memberInfo

				
				 
				$("#submit").click(function(){
					$("[bootstrapSwitch]").each(function(){
						//alert(this.checked +' - '+this.value +' - '+this.name)
					}) 
				})
				
				var bind_bootstrapSwitch = function($dom){
					$dom.bootstrapSwitch({
						onSwitchChange : function(){
							console.log([this.name,this.checked])
							
							if(this.checked){
								this.value = 'on';
							}else{
								this.value = 'off';
							}
							 
							if(this.name == 'GroupCard'){
								if(this.checked){
									$("#TREAMCARD").show();
								}else{
									$("#TREAMCARD").hide();
								}
							}
							if(this.name == 'GroupCard2'){
								if(this.checked){
									$("#TREAMCARD2").show();
									$("#TREAMCARD3").show();
									$("#TREAMCARD4").show();
								}else{
									$("#TREAMCARD2").hide();
									$("#TREAMCARD3").hide();
									$("#TREAMCARD4").hide();
								}
							}
							//TREAMCARD
						}
					})
				};

				 
				bind_bootstrapSwitch($("[bootstrapSwitch]"));
				 
				if($("[name='GroupCard']")[0].checked){
					$("#TREAMCARD").show();
				}
				if($("[name='GroupCard2']")[0].checked){
					$("#TREAMCARD2").show();
					$("#TREAMCARD3").show();
					$("#TREAMCARD4").show();
				}
				
				
				//console.log(memberInfo);
				if(memberInfo.length > 0){
					for(var i=0,len=memberInfo.length;i<len;i++){
						add_item(memberInfo[i]);
					}
				}else{
					$("#add").click();
				}
				 
				 
		},100)
</script>
<script type="text/javascript"	src="/resources/assets/global/scripts/bootstrapSwitch.js"></script>
<%if("OK".equals(request.getAttribute("MSG"))){%>
<script type="text/javascript">
showModal('#msg');
</script>
<%}%>
<script>
	Common.init();
</script>
</body>
</html>