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
			<form action="${p:go('','updateMerchant','') }" method="POST">
			<input NAME="pojo.MER_CODE" value="${pojo.MER_CODE }" type="hidden"/>			
			<DIV class="form-group">
				<label  class="col-md-3 control-label">商户号</label>
				<DIV class="col-md-8">
					<input  value="${pojo.MER_CODE }" TYPE="text" class="form-control"  placeholder="" disabled="disabled"> 
				</DIV>
			</DIV>

			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					商户来源
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<SELECT  class="form-control" NAME="pojo.MER_SOURCE">
						<OPTION VALUE="KF"
						<c:if test="${pojo.MER_SOURCE == 'KF' }">selected</c:if>
						>开发</OPTION>
						<OPTION VALUE="SH"
						<c:if test="${pojo.MER_SOURCE == 'SH' }">selected</c:if>
						>高汇通已有商户</OPTION>
						<OPTION VALUE="WZ"
						<c:if test="${pojo.MER_SOURCE == 'WZ' }">selected</c:if>
						>网站</OPTION>
						<OPTION VALUE="SS"
						<c:if test="${pojo.MER_SOURCE == 'SS' }">selected</c:if>
						>销售</OPTION>
					</SELECT>
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					商户类型
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<SELECT  class="form-control" NAME="pojo.MER_TYPE">
						<OPTION VALUE="Z"
						<c:if test="${pojo.MER_TYPE == 'Z' }">selected</c:if>
						>自有功能的商户</OPTION>
						<OPTION VALUE="K"
						<c:if test="${pojo.MER_TYPE == 'K' }">selected</c:if>
						>开发用商户</OPTION>
						<OPTION VALUE="S"
						<c:if test="${pojo.MER_TYPE == 'S' }">selected</c:if>
						>标准商户</OPTION>
					</SELECT>
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					商户级别
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<SELECT  class="form-control" NAME="pojo.MER_LEVEL">
						<OPTION VALUE="J"
						<c:if test="${pojo.MER_LEVEL == 'J' }">selected</c:if>
						>基础</OPTION>
						<OPTION VALUE="Q"
						<c:if test="${pojo.MER_LEVEL == 'Q' }">selected</c:if>
						>旗舰</OPTION>
					</SELECT>
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">商户级别有效期</label>
				<DIV class="col-md-8">
						<input NAME="pojo.MER_EXPIRYDATE" value="${pojo.MER_EXPIRYDATE }" TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">
						 
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					商户名称
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.MER_NAME" value="${pojo.MER_NAME }" TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					安全邮箱
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.SECURE_EMAIL" value="${pojo.SECURE_EMAIL }" TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					联系人手机
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.LINKMAN_PHONE" value="${pojo.LINKMAN_PHONE }" TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					联系人电话
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.LINKMAN_TEL" value="${pojo.LINKMAN_TEL }" TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					联系人QQ号
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.LINKMAN_QQ" value="${pojo.LINKMAN_QQ }" TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					联系人EMAIL
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.LINKMAN_EMAIL" value="${pojo.LINKMAN_EMAIL }" TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					邀请码
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.INVITE_CODE" value="${pojo.INVITE_CODE }" TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					商户管理密码
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<input NAME="pojo.MER_PWD" value="${pojo.MER_PWD }" TYPE="text" class="form-control"  placeholder="">
				</DIV>

				<label   class="text-left col-md-1 control-label">						
				</label>
			</DIV>
			<DIV class="form-group">
				<label  class="col-md-3 control-label">
					商户状态
					<span class="pull-right"></span>
				</label>
				<DIV class="col-md-8">
					<SELECT  class="form-control" NAME="pojo.MER_STATE">
						<OPTION VALUE="K"
						<c:if test="${pojo.MER_STATE == 'K' }">selected</c:if>
						>开发</OPTION>
						<OPTION VALUE="S"
						<c:if test="${pojo.MER_STATE == 'S' }">selected</c:if>
						>生产</OPTION>
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