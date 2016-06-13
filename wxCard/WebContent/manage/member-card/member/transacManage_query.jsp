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
<script type="text/javascript">
var msg = '${mag}';
if(!msg){
}else{
	alert(msg);
	window.location.href="${p:go('','execute','') }";
}
</script>
</head>
<body>
	<div class="page-content">
		<div class="portlet box  ">

			<div class="portlet-body  ">

				
				<table class="table table-striped table-bordered table-advance table-hover"
					id="sample_1">
					<thead>
			<tr>

				<th>流水号</th>
				<th>交易渠道</th>
				<th>交易类型</th>
				<th>赚取金额</th>
				<th>兑换金额</th>
				<th>转账金额</th>
				<th>调整金额</th>
				<th>成功/失败</th>
				<th>交易日期</th>
				<th>发卡商户</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tradeDetailsList }" var="pojo">
				<tr>
					<td class="text-center">${pojo.txnId }</td>
					<td class="text-center">${pojo.inputChannel }</td>
					<td class="text-center">${pojo.txnCode }</td>
					<td class="text-center">${pojo.earnAmount }</td>
					<td class="text-center">${pojo.redeemAmount }</td>
					<td class="text-center">${pojo.transferAmount }</td>
					<td class="text-center">${pojo.adjustAmount }</td>
					<td class="text-center">${pojo.status }</td>
					<td class="text-center">${pojo.txnTime }</td>
					<td class="text-center">${pojo.merchantNo }</td>
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
						                <a href="${p:go('','query','') }&pojo.page=${page-1}&pojo.dateTime=${dateTime}&pojo.card_no=${card_no}" title="Prev">
						                    <i class="fa fa-angle-left">
						                    </i>
						                </a>
						            </li>
						            <li class="active">
						                <a href="#">
						                    ${page}/${total}
						                </a>
						            </li>
						            
						            <li class="next">
						                <a href="${p:go('','query','') }&pojo.page=${page+1}&pojo.dateTime=${dateTime}&pojo.card_no=${card_no}" title="Next">
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



	</script>




	<script type="text/javascript">
		Common.init();
		var $dialog = null;

		setTimeout(function() {
			$("#msg").find("[modal-submit]").click(function() {
				if (dialog_submit() === false) {
					return false;
				}
			});

			$("[派发卡片]").click(function() {

				$dialog = showModal_2('#msg', '派发卡片', $("#派发卡片").html());

				$dialog.data('id', '456');

				return false;
			})
		}, 100)
</script>
<%if("OK".equals(request.getAttribute("MSG"))){%>
<script type="text/javascript">
showModal('#msg');
</script>
<%}%>
</body>
</html>