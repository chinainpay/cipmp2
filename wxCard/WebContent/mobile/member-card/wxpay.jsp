<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>
<script src="js/jquery-2.0.0.min.js"></script>
<script>
$(function(){
	$("#weixinpay").click(function(){
		var total_fee = $("#pricemoney").val(); 
			total_fee = parseFloat(total_fee);
		var pattern = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
			if(!pattern.test(total_fee)&& total_fee!=""){
				tip("请输入正确金额");
				return false;
			}
		  window.location.href="${p:go('','pay','') }&pojo.total_fee="+total_fee+"";

	})
})	
</script>