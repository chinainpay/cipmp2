<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration, java.util.LinkedHashMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>修改密码</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<meta name="format-detection" content="email=no">
		<link rel="stylesheet" type="text/css" href="css/global.css?r" />
		<script type="text/javascript" src="js/alert.js?r=212"></script>
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
					 var oldPwd = $.trim($('#oldPwd').val());
					 var newPwd = $.trim($('#newPwd').val());

				     if (oldPwd=='') { 
                               _alert("请输入原密码");
                               $("#oldPwd").focus();
                               return false; 
                              }
				     if (newPwd=='') { 
                         _alert("请输入新密码");
                         $("#newPwd").focus();
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
			url : "${p:go('','modifyPassword','') }",
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
						_alert(res['errorMsg']);
						return;
					}
					_alert(res['msg']);
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
							<label for="vcard-phone-num" />
								原密码
							</label>
							<input  type="password" class="pd-left" type="tel" id="oldPwd" name="pojo.oldPwd" 
								placeholder="请输入6位密码" maxlength="6" onkeyup="if(isNaN(value)){_alert('只能输入数字');this.value='';}"
								onafterpaste="if(isNaN(value)){_alert('只能输入数字');this.value='';}" />
							<a class="btn-delete" href=""></a>
						</li>
					</ul>
					<ul class="form-mod pwd">
						<li>
							<label for="vcard-old-pwd" />
								新密码
							</label>
							<input class="pd-left" type="password" id="newPwd"
								name="pojo.newPwd" placeholder="请输入6位密码" maxlength="6"
								onkeyup="if(isNaN(value)){_alert('只能输入数字');this.value='';}"
								onafterpaste="if(isNaN(value)){_alert('只能输入数字');this.value='';}" />
							<a class="btn-delete" href=""></a>
						</li>
					</ul>
					<!-- 操作按钮 [[-->
					<div class="operation">
						<button type="button" id="submit" class="btn-green">
							确认修改
						</button>
					</div>
				</form>
			</div>
			
		</div>
		</section>

	</body>
</html>