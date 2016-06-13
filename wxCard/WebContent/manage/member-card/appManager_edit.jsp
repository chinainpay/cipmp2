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
<style type="text/css">
	.list-pic{
		width: 200px;
		height: 100px;
	}

	.list-text{
		line-height: 100px;
	} 


</style>
</head>
<body>
<div  class="form-horizontal">
<div class="form-body">
			<form action="${p:go('','updateApp','') }" method="POST">
			<input type="hidden" name="pojo.APP_NUM" value="${pojo['APP_NUM']}"/>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">app名称：</label>
				<DIV class="col-md-8">
					<input NAME="pojo.APP_DISPLAY" TYPE="text" class="form-control"  value="${pojo['APP_DISPLAY']}"> 
				</DIV>
			</DIV>

			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					描述：
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.APP_DESC"  TYPE="text" class="form-control"  value="${pojo['APP_DESC']}">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">状态：</label>
				<DIV class="col-md-8">
					<SELECT  class="form-control" NAME="pojo.APP_STATE">
						<OPTION VALUE="T"
						<c:if test="${pojo['APP_STATE'] == 'T'}">selected</c:if>
						>测试</OPTION>
						<OPTION VALUE="N"
						<c:if test="${pojo['APP_STATE'] == 'N'}">selected</c:if>
						>正常</OPTION>
						<OPTION VALUE="S"
						<c:if test="${pojo['APP_STATE'] == 'S'}">selected</c:if>
						>停用</OPTION>
					</SELECT>
				</DIV>

				<label   class="text-left col-md-1 control-label">
						 
				</label>
			</DIV>
			<DIV class="btn-group pull-right">
				<button type="submit" class="btn green">修改</button>
			</DIV>
			 </form>
		</div>
</div>

<%if("OK".equals(request.getAttribute("MSG"))){%>
<script type="text/javascript">
showModal('#msg');
</script>
<%}%>
</body>
</html>