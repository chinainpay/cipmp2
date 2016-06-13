<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.Enumeration,java.util.LinkedHashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>交易记录</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta name="format-detection" content="email=no">
<link rel="stylesheet" type="text/css" href="css/global.css?r" />
<link rel="stylesheet" type="text/css" href="css/app.css">
<link rel="stylesheet" type="text/css" href="css/add.css">
<script type="text/javascript" src="js/alert.js?r=212"></script>
<script type="text/javascript">
	
</script>
</head>
<body>
	<section class="wrapper">
		<div class="content ">

			<div class="">
				<div class="add_cardFront_activity consumeRecord_box">
					<div class="add_cardFront_activity_box">
						<input style="display: none;" value="1" id="page"
							readonly="readonly">
						<ul class="nav my_card">
							<c:choose>
								<c:when test="${listSize > 0 }">
									<c:forEach items="${list }" var="pojo">
										<li><i class="my_card_time">${pojo.time }</i> <i
											class="my_card_text">${pojo.txnCode }</i> <i
											class="my_card_price">${pojo.money }</i></li>

									</c:forEach>
								</c:when>
								<c:otherwise>
									<li><i
										style="width: 33%; float: left; text-align: center;">&nbsp;</i>
										<i style="width: 33%; float: left; text-align: center;"><b>无记录</b>
									</i> <i style="width: 33%; float: left; text-align: center;">&nbsp;</i>
									</li>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
				</div>

				<c:if test="${total >1}">
					<div class="operation">
						<strong style="margin-left: 10px">第 ${page}/${total}页</strong>
						<a class="btn-gray" style="width: 80px; float: right"
							href="${p:go('','consumeRecord','') }&pojo.page=${page+1}&pojo.pageCount=${total}">下一页</a>
						<a class="btn-gray"
							style="width: 80px; float: right; margin-right: 10px"
							href="${p:go('','consumeRecord','') }&pojo.page=${page-1}&pojo.pageCount=${total}">上一页</a>
					</div>
				</c:if>
			</div>
		</div>
	</section>
</body>
</html>
