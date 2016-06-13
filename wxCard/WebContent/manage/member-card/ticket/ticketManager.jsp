<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration, java.util.LinkedHashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<script type="text/javascript" src="/resources/own/script/common.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/ticketManager.js"></script>
<style type="text/css">
.list-pic {
	width: 200px;
	height: 100px;
}

.thead>tr>th {
	vertical-align: middle;
	text-align: center;
}
</style>
<title>卡票管理系统</title>
</head>
<body>
	<div class="page-content">
		<div class="portlet box  ">

			<div class="portlet-body  ">

				<div class="table-toolbar">


					<a id="add" href="javascript:;" class="btn blue" onclick=""><i
						class="icon-plus"></i>添加免费券</a>
				</div>
				<table
					class="table table-striped table-bordered table-advance table-hover"
					id="sample_1">
					<thead>
						<tr>
							<th>卡券类型</th>
							<th>卡券名称</th>
							<th>卡券有效期</th>
							<th><select>
									<option>所有</option>
									<option>审核中</option>
									<option>未通过</option>
									<option>待投放</option>
									<option>已投放</option>
							</select></th>
							<th>库存</th>
							<th>领取总数</th>
							<th>核销总数</th>
							<th>未核销总数</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="pojo">
							<tr class="odd gradeX">
								<td class="text-center"><span class="list-text">
										${pojo.card_type } </span></td>
								<td class="text-center"><span class="list-text">${pojo.title}</span>
								</td>
								<td class="text-center"><span class="list-text">${pojo.date}</span>
								</td>
								<td class="text-center"><span class="list-text">${pojo.state}</span>
								</td>
								<td class="text-center"><span class="list-text">${pojo.quantity}</span>
								</td>
								<td class="text-center"><span class="list-text">${pojo.lqcount+pojo.hxcount}</span>
								</td>
								<td class="text-center"><span class="list-text">${pojo.hxcount}</span>
								</td>
								<td class="text-center"><span class="list-text">${pojo.lqcount}</span>
								</td>
								<td class="text-center"><c:if test="${pojo.dateState!=1 }">
										<a class="btn default btn-xs green"
											href="${p:go('','deliveryCardTicketImg','') }&pojo.cardid=${pojo.cardid}&pojo.cardtype=${pojo.card_type_param }"><i
											class="fa fa-edit"></i>直接群发卡券</a>
										<a class="btn default btn-xs green"
											href="${p:go('','deliveryCardTicket','') }&pojo.cardid=${pojo.cardid}&pojo.cardtype=${pojo.card_type_param }"<%--onclick="cardTouFangDiv();"--%>><i
											class="fa fa-edit"></i>下载二维码投放</a>
									</c:if> <a class="btn default btn-xs green"
									href="${p:go('','cardDetails','') }&pojo.cardid=${pojo.cardid}&pojo.cardtype=${pojo.card_type_param }"><i
										class="fa fa-edit"></i>详情</a> <a class="btn default btn-xs green"
									href="${p:go('','cardUserDetails','') }&pojo.cardid=${pojo.cardid}&pojo.cardtype=${pojo.card_type_param }"><i
										class="fa fa-edit"></i>使用详情</a> <a
									class="btn default btn-xs green"
									href="${p:go('','delCardTicket','') }&pojo.cardid=${pojo.cardid}&pojo.cardtype=${pojo.card_type_param }"><i
										class="fa fa-edit"></i>删除</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<p>
					<i>注：直接群发卡券投放，只针对近24小时和公众号有互动的用户</i>
				</p>
			</div>

			<div class="row">
				<div class=" col-md-12 col-sm-12 ">
					<div class="pull-right dataTables_paginate paging_bootstrap">
						<ul class="pagination" style="visibility: visible;">
							<li class="prev"><a
								href="${p:go('','','') }&pageSize=${page.upperPage }"
								title="上一页"> <i class="fa fa-angle-left"></i>
							</a></li>
							<li class="active"><a href="#">${page.currentCount }/${page.total}</a></li>

							<li class="next"><a
								href="${p:go('','','') }&pageSize=${page.nextPage }" title="下一页">
									<i class="fa fa-angle-right"></i>
							</a></li>
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
	<!-- 隔离层 -->
	<div id="fadeCreateVip" class="black_overlay"></div>
	<script type="tpl" id="选择卡券类型">
					<div  class="radio_control_group group">
                             
                             <div>
                               <label radio class="frm_radio_label frm_radio_input " for="checkbox1">
                                   <i class="icon_radio">
                                   </i>
                                   <input name="type" type="radio" value="1" class="frm_radio js_validtime" id="checkbox1" checked="checked">
                                   <span class="lbl_content">
                                       折扣券
                                   </span>
                                   
                                   <p class="frm_tips">
		                                可为用户提供消费折扣
		                           </p>
                               </label>
                             </div>
                             
                             <div>
                               <label radio class="frm_radio_label frm_radio_input " for="checkbox2">
                                   <i class="icon_radio">
                                   </i>
 									<input name="type" type="radio" value="2" class="frm_radio js_validtime" id="checkbox2" />
                                   <span class="lbl_content">
                                       代金券
                                   </span>
                                  
                                    
                                   <p class="frm_tips">
		                               可为用户提供抵扣现金服务。可设置成为“满*元，减*元”
		                           </p>
                               </label>
                             </div>
                             
                             
                           <div>
                               <label radio class="frm_radio_label frm_radio_input " for="checkbox3">
                                   <i class="icon_radio">
                                   </i>
                                   <input name="type" type="radio" value="3" class="frm_radio js_validtime" id="checkbox3"/>
                                   <span class="lbl_content">
                                       礼品券
                                   </span>
                                    
                                   <p class="frm_tips">
		                               可为用户提供消费送赠品服务
		                           </p>
                               </label>
                             </div>   
                             
                             <div>
                               <label radio class="frm_radio_label frm_radio_input " for="checkbox4">
                                   <i class="icon_radio">
                                   </i>
                                   <input name="type" type="radio" value="4" class="frm_radio js_validtime" id="checkbox4" >
                                   <span class="lbl_content">
                                       团购券
                                   </span>
                                    
                                   <p class="frm_tips">
		                               可为用户提供团购套餐服务
		                           </p>
                               </label>
                             </div>       
                             
                             <div>
                               <label radio class="frm_radio_label frm_radio_input " for="checkbox5">
                                   <i class="icon_radio">
                                   </i>
                                   <input name="type" type="radio" value="5" class="frm_radio js_validtime" id="checkbox5" >
                                   <span class="lbl_content">
                                       优惠券
                                   </span>
                                    
                                   <p class="frm_tips">
		                               即“通用券”，建议当以上四种无法满足需求时采用
		                           </p>
                               </label>
                             </div>
                        </div>

						<script>
		dialog_check = function(){
			 
			var v = $dialog.find(":checked").val(); 

				return {
					'v' : v 
				}

		}
		
 	 
		dialog_submit = function(){
			if(dialog_check() === false){
				return false;
			}
			
			var submit_data = dialog_check();
				//alert('${p:go('','groupTicketUrl','') }&state='+submit_data['v']);
				window.location.href = '${p:go('','groupTicketUrl','') }&state='+submit_data['v'];
			 
				 
		}
	</script>
</body>


<script type="text/javascript">
	$("#fadeCreateVip").css('display', 'none');
	$("#toufang").css('display', 'none');
	Common.init();
</script>
</html>