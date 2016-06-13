<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration, java.util.LinkedHashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>完善信息</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<meta name="format-detection" content="email=no">
		<link rel="stylesheet" type="text/css" href="css/global.css?r" />
		<link rel="stylesheet" type="text/css" href="css/app.css">
		<link rel="stylesheet" type="text/css" href="css/add.css">
		<style type="text/css">
			.show_font{
				width:88%;
				margin-left: 20px;
			}
			.show_font strong{
				font-size: 14px;
				line-height: 30px;
			}
			.show_font p{
				font-size: 13px;
				line-height: 20px;
				margin-left: 10px;
			}
		</style>
		<script src="js/jquery-2.0.0.min.js"></script>
		<script type="text/javascript" src="js/alert.js?r=212"></script>
		<script type="text/javascript">

         function checkBind(){   
					var tele = $('#tele').val();
                    var temp_tele =/^1[3|4|5|8][0-9]\d{8}$/;

				    if (tele=='') { 
                              _alert("请输入手机号");
                              $("#tele").focus();
                              return false; 
                             }
                    if(!temp_tele.test(tele)){
					          _alert("请输入正确手机号");
					          $("#tele").focus();
					          return false;
				              }
				
                } 
                var submit = function(callback) {

		var objs = {};
		$("form input").each(function() {
			var $item = $(this);
			objs[$item.attr('name')] = $item.val();
		});

		$.ajax( {
			type : "POST",
			url : "${p:go('','save','') }",
			data : $.param(objs),
			dataType : "json",
			success : function(res) {

				callback(res);

			},
			error : function(msg) {
				callback( {});
			}
		});
	}

	$(function() {
		var locked = false;
		$("#submit").click(function() {
			if (checkBind() === false)
				return;
			
			if(locked == true)return;
				locked = true;
				
			$(this).addClass("btn-gray");
		var me = this;
			submit(function(res) {
				
				$(me).removeClass("btn-gray");
				locked = false;
				if (res) {
					if (res['errorMsg']) {
						_alert(res['errorMsg'])
						return;
					}
					_alert("保存成功")
						window.location.href = 'memberManager.html';
				}
			});
		});
	})
                
     </script>
	</head>
	<body class="layout-point">

		<section class="wrapper">
		<div class="main">
			<div class="content">
				<form class="form">
					<ul class="form-mod">
						<li>
							<label>
								手机号
							</label>
							<input class="pd-left" type="tel" id="tele" name="pojo.phone"
								placeholder="请输入手机号" maxlength="11" value="${MEMB_PHONE}"  readonly="readonly"/>
						</li>
					</ul>
					<ul class="form-mod">
						<li>
							<label>
								姓名
							</label>
							<input class="pd-left"  id="name" name="pojo.name"
								placeholder="请输入姓名" maxlength="11"  value="${MEMB_NAME}"  />
						</li>
					</ul>
					
						<c:forEach items="${memberDataList }" var="pojo">
						<ul class="form-mod">
						<li>
							
							<label><c:if test="${pojo['IS_REQUIRED'] == 0}">*</c:if>${pojo['PRO_NAME']}</label>  
							<input type="text" name="pojo.${pojo['MER_CODE'] }_${pojo['PRO_NO'] }"  id="${pojo['MER_CODE'] }_${pojo['PRO_NO'] }" class="pd-left" value="${pojo['MEMB_PRO_VALUE'] }"  placeholder="${pojo['PROMPT_CONTENT'] }"> 
						</li>	
							</ul>
						</c:forEach>
				
					<!-- 操作按钮 [[-->
					<div class="operation">
						<button type="button" id="submit" class="btn-green">
							确认保存
						</button>
					</div>
				</form>
			</div>
			
		</div>
		</section>

	</body>
</html>