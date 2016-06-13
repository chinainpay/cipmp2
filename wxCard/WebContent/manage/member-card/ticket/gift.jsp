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
<link  rel="stylesheet" type="text/css" href="css/processor_bar218878.css">
<link  rel="stylesheet" type="text/css" href="css/layout_head26f6ea.css">
<link  rel="stylesheet" type="text/css" href="css/base2767e5.css">
<link  rel="stylesheet" type="text/css" href="css/lib22f1a9.css">
<link  rel="stylesheet" type="text/css" href="css/card_control276dda.css">

<link  rel="stylesheet" type="text/css" href="css/section_card_notification2767e5.css">

<link rel="stylesheet" type="text/css" media="all" href="css/daterangepicker-bs3.css" /> 
<script type="text/javascript" src="/resources/own/script/common.js"></script>
 
<title>礼品券</title>


<style>
	body{
		background: #fff;
	}
	
	.page-content{
		 
	}
</style>
</head>
<body>

	<div class="page-content">
		<div class="main_bd" id="js_main_content">
    <div class="group">
        <div class="media_preview_area" id="js_preview_area">
            <div class="msg_card">
                <div class="msg_card_inner">
                    <p class="msg_title">
                       礼品券
                    </p>
                    <div class="js_preview msg_card_section shop disabled focus">
                        <div class="shop_panel" id="js_color_preview" js_color_preview  style="background-color: rgb(61, 120, 218);">
                            <div class="logo_area group">
                                <span class="logo l">
                                    <img id="js_logo_url_preview" src="https://mmbiz.qlogo.cn/mmbiz/qqNSkJXiawtScBYcNdic4SlTqXicY74QHraawnLyNBDh87CibP2kB47RqDricnRj68wI0OibBosiapnDBWVFIAbt683XA/0?wx_fmt=gif">
                                </span>
                                <p id="js_brand_name_preview">
                                     ${brandName }
                                </p>
                            </div>
                            <div class="msg_area">
                                <div class="tick_msg">
                                    <p>
                                        <b id="js_title_preview">
                                        </b>
                                    </p>
                                    <span id="js_sub_title_preview">
                                    </span>
                                    <br>
                                </div>
                                <p class="time">
                                    <span id="js_validtime_preview">
                                        有效期：${startDate } - ${startDate }
                                    </span>
                                </p>
                            </div>
                        </div>
                        <div class="msg_card_mask">
                            <span class="vm_box">
                            </span>
                            <a href="javascript:;" data-actionid="10" class="js_edit_icon edit_oper">
                                <i class="icon18_common edit_gray">
                                </i>
                            </a>
                        </div>
                        <div class="deco">
                        </div>
                    </div>
                    <div class="js_preview msg_card_section dispose disabled">
                        <div class="unset" id="js_destroy_title">
                            <p>
                                销券设置
                            </p>
                        </div>
                        <div class="" id="js_destroy_type_preview">
                            <div class="barcode_area js_code_preview js_code_preview_1" style="display: none;">
                                <div class="barcode">
                                </div>
                                <p id="code_num" class="code_num">
                                    1513-2290-1878
                                </p>
                            </div>
                            <div class="qrcode_area js_code_preview js_code_preview_2" style="display: none;">
                                <div class="qrcode">
                                </div>
                                <p class="code_num">
                                    1513-2290-1878
                                </p>
                            </div>
                            <div class="sn_area js_code_preview js_code_preview_0" style="display: none;">
                                1513-2290-1878
                            </div>
                            <p class="sub_msg tc" id="js_notice_preview">
                            </p>
                        </div>
                        <div class="msg_card_mask">
                            <span class="vm_box">
                            </span>
                            <a href="javascript:;" data-actionid="11" class="js_edit_icon edit_oper">
                                <i class="icon18_common edit_gray">
                                </i>
                            </a>
                        </div>
                    </div>
                    <div class="shop_detail">
                        <ul class="list">
                            <li class="msg_card_section js_preview">
                                <div class="li_panel" href="">
                                    <div class="li_content">
                                        <p>
                                            礼品券详情
                                        </p>
                                    </div>
                                    <span class="ic ic_go">
                                    </span>
                                </div>
                                <div class="msg_card_mask">
                                    <span class="vm_box">
                                    </span>
                                    <a href="javascript:;" data-actionid="12" class="js_edit_icon edit_oper">
                                        <i class="icon18_common edit_gray">
                                        </i>
                                    </a>
                                </div>
                            </li>
                            <li class="msg_card_section js_preview last_li">
                                <div class="li_panel" href="">
                                    <div class="li_content">
                                        <p>
                                            适用门店
                                        </p>
                                    </div>
                                    <span class="ic ic_go">
                                    </span>
                                </div>
                                <div class="msg_card_mask">
                                    <span class="vm_box">
                                    </span>
                                    <a href="javascript:;" data-actionid="13" class="js_edit_icon edit_oper">
                                        <i class="icon18_common edit_gray">
                                        </i>
                                    </a>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <form class="media_edit" id="js_editform" novalidate="novalidate">
            <div class="media_edit_area" id="js_edit_area" style="margin-top: 0px;">
                <div pageId="10" class="js_edit_content portable_editor to_left appmsg_editor" style="display: block; zoom: 1;">
                    <input type="hidden" name="type" value="2">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="is_pay_card" value="0">
                    <div class="inner">
                        <div class="editor_section">
                            <h3 class="title">
                                券面信息
                            </h3>
                            <div class="appmsg_edit_item">
                                <label for="" class="frm_label">
                                    <strong class="title">
                                        商家名称
                                    </strong>
                                </label>
                                <span class="appmsg_preview_msg">
                                    ${brandName }
                                </span>
                                <input type="hidden" name="brand_name" value="${brandName }">
                            </div>
                            <div class="appmsg_edit_item frm_normal upload_item">
                                <label for="" class="frm_label">
                                    <strong class="title">
                                        商家Logo
                                    </strong>
                                </label>
                                <span class="appmsg_preview_msg">
                                    <img src="https://mmbiz.qlogo.cn/mmbiz/qqNSkJXiawtScBYcNdic4SlTqXicY74QHraawnLyNBDh87CibP2kB47RqDricnRj68wI0OibBosiapnDBWVFIAbt683XA/0?wx_fmt=gif">
                                    <input type="hidden" name="logo_url" value="http://mmbiz.qpic.cn/mmbiz/R1bkzRvkrKFDrSWUjPtGtX5MN7SApjiajl8rKOajiattviaHOTsFowOpovgsDdW0yYkVHuKYmzRUd1ia2fzdH8ADQA/0">
<%--                                     <p class="frm_tips"> --%>
<!--                                         如商户信息有变更，请在 -->
<!--                                         <a href="/merchant/cardapply?action=listmerchantinfo&amp;t=cardticket/apply_detail&amp;lang=zh_CN&amp;token=276543067"> -->
<!--                                             卡券基础功能 -->
<!--                                         </a> -->
<!--                                         开通页更新。 -->
<%--                                     </p> --%>
                                </span>
                            </div>
                            <div class="appmsg_edit_item">
                                <label for="" class="frm_label">
                                    <strong class="title">
                                        卡券颜色
                                    </strong>
                                </label>
                                <div id="js_colorpicker" class="color_picker dropdown_menu">
                                    <a href="javascript:;" dropdownSwitch class="btn dropdown_switch jsDropdownBt">
                                        <label js_color_preview class="jsBtLabel" style="background-color: rgb(61, 120, 218);">
                                            #3D78DA
                                        </label>
                                        <i class="arrow">
                                        </i>
                                    </a>
                                    <div jsDropdownList class="dropdown_data_container jsDropdownList" style="display: none;">
                                        <ul class="dropdown_data_list">
                                        	<c:forEach items="${color }" var="pojo">
	                                        	<li class="dropdown_data_item  ">
	                                                <a style="background-color: ${pojo.value };" onclick="return false;" href="javascript:; " jsDropdownItem class="jsDropdownItem"
	                                                data-value=" ${pojo.value }" data-index="0" data-name="${pojo.name }">
	                                                   ${pojo.value }
	                                                </a>
	                                            </li>
                                        	</c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <span>
                                    <input type="hidden" value="#3D78DA" name="color" id="js_color">
                                </span>
                            </div>
                            
                            <div class="appmsg_edit_item frm_normal">
                                <label for="" class="frm_label">
                                    <strong class="title">
                                        礼品券标题
                                    </strong>
                                </label>
                                <span class="frm_input_box">
                                    <input value="" name="title" type="text" target="#js_title_tip" data-maxlength="9"
                                    class="frm_input js_can_preview js_maxlength frm_msg_content" placeholder="">
                                </span>
                                 
                                <p id="titleErrorMsg" class="frm_msg fail" style="display: none;">
                                    <span for="title" class="frm_msg_content" >
                                        卡券名称不能为空且长度不超过9个汉字或18个英文字母
                                    </span>
                                </p>
                                <p class="frm_tips">
                                    建议填写礼品券提供的服务或礼品名称，描述卡券提供的具体优惠
                                </p>
                            </div>
                           
                            <div class="appmsg_edit_item">
                                <label for="" class="frm_label">
                                    <strong class="title">
                                        副标题
                                        <br>
                                        <span class="tips">
                                            (选填)
                                        </span>
                                    </strong>
                                </label>
                                <span class="frm_input_box">
                                    <input value="" name="sub_title" type="text" target="#js_sub_title_tip"
                                    data-maxlength="18" class="frm_input js_can_preview js_maxlength valid"
                                    placeholder="">
                                </span>
                                
                                
                                <p id="sub_titleErrorMsg" class="frm_msg fail" style="display: none;">
                                    <span for="" class="frm_msg_content" >
                                        卡券名称不能为空且长度不超过9个汉字或18个英文字母
                                    </span>
                                </p>
                            </div>
                            
                            <div class="appmsg_edit_item">
                                <label for="" class="frm_label">
                                    <strong class="title">
                                        库存
                                    </strong>
                                </label>
                                <div class="input_submsg">
                                    <span class="frm_input_box">
                                        <input name="quantity" value="" type="text" class="frm_input">
                                    </span>
                                    <span class="input_sub_msg">
                                        份
                                    </span>
                                </div>
                                <p id="quantityErrorMsg"  class="frm_msg fail" >
								    <span for="quantity" class="frm_msg_content" style="display: inline;">
								        库存只能是大于0的数字
								    </span>
								</p>
                            </div>
                            <div class="appmsg_edit_item appmsg_edit_item_label_mult appmsg_input_submsg_item group">
                                <label for="" class="frm_label l">
                                    <strong class="title">
                                        领券限制
                                        <br>
                                        <span class="tips">
                                            (选填)
                                        </span>
                                    </strong>
                                </label>
                                <div class="input_submsg l">
                                    <span class="frm_input_box">
                                        <input name="get_limit" value="" type="text" class="frm_input" placeholder="">
                                    </span>
                                    <span class="input_sub_msg">
                                        张
                                    </span>
                                </div>
                                <p class="frm_tips">
                                    每个用户领券上限，如不填，则默认为1
                                </p>
                                
                            </div>
                            
<!--                             <div class="appmsg_edit_item frm_normal"> -->
<!--                                 <label for="" class="frm_label"> -->
<!--                                     <strong class="title"> -->
<!--                                         操作提示 -->
<!--                                     </strong> -->
<!--                                 </label> -->
<!--                                 <span class="frm_input_box"> -->
                                    <input name="notice" value="使用时向服务员出示此券" type="hidden" data-maxlength="16" target="#js_notice_hint"
                                    class="frm_input js_can_preview js_maxlength">
<!--                                 </span> -->
                                
                                
<%--                                 <p class="frm_tips"> --%>
<!--                                     建议引导用户到店出示卡券，由店员完成核销操作 -->
<%--                                 </p> --%>
                                
<%--                                 <p id="noticeErrorMsg" class="frm_msg fail" style="display: none;"> --%>
<!-- 								    <span for="notice" class="frm_msg_content" style="display: inline;"> -->
<!-- 								        操作提示不能为空且长度不超过16个汉字或32个英文字母 -->
<!-- 								    </span> -->
<%-- 								</p> --%>
                                
<!--                             </div> -->
                            <div class="appmsg_edit_item radio_item">
                                <div class="frm_control_group radio_row">
                                    <label for="" class="frm_label">
                                        有效期
                                    </label>
                                    
                                    <form>
                                    <div  class="frm_controls frm_vertical_lh">
                                        <div  class="radio_control_group group">
                                        
                                            <label radio class="frm_radio_label frm_radio_input " for="checkbox1">
                                                <i class="icon_radio">
                                                </i>
                                                <span class="lbl_content">
                                                    固定日期
                                                </span>
                                                <input name="type" type="radio" value="1" class="frm_radio js_validtime" id="checkbox1"
                                                checked="checked">
                                            </label>
                                            <div class="time_picker">
                                                <div class="input-prepend input-group">
												    <span class="add-on input-group-addon">
												        <i class="glyphicon glyphicon-calendar fa fa-calendar">
												        </i>
												    </span>
												    <input type="text" readonly="readonly" style="width: 200px" name="reservation"
												    id="reservation" class="form-control active" value="${startDate } - ${startDate }">
												</div>
                                            </div>
                                        </div>
                                        <div class="radio_control_group frm_radio_input group">
                                            <label radio class="frm_radio_label" for="checkbox2">
                                                <i class="icon_radio">
                                                </i>
                                                <span class="lbl_content">
                                                    领取后
                                                </span>
                                                <input name="type"  type="radio" value="2" class="frm_radio js_validtime" id="checkbox2">
                                            </label>
                                            
                                            <label class="frm_radio_label" >
	                                            <span id="js_from_day_container" class="dropdown_menu disabled">
	 
	                                                        <div  class="form-group">
																<select  name="_select1" class="form-control">
																 <option value="0">当天</option>
																   <% for(int i=1;i<91;i++){ %> 
																   <option value="<%=i %>"><%=i %>天</option>
																   <%}%> 
																</select>
															</div>
	                                                    
	                                            </span>
                                            
                                            	 <span class="lbl_content">
                                                	生效，有效天数
                                            	 </span>
                                            </label>
                                           
                                            <span id="js_fixed_term_container" class="dropdown_menu disabled">
                                                 
                                                    
                                             		<div class="form-group">
															<select name="_select2"  class="form-control">
																   <% for(int i=1;i<91;i++){ %> 
																   <option value="<%=i %>"><%=i %>天</option>
																   <%}%> 
															</select>
														</div>
                                                    
                                                   
                                                 
                                            </span>
                                        </div>
                                    </div>
                                    </form>
                                    <input type="hidden" name="time_type" id="js_hidden_time_type" value="1">
                                    <input type="hidden" name="begin_time" id="js_hidden_begintime" value="">
                                    <input type="hidden" name="end_time" id="js_hidden_endtime" value="">
                                    <input type="hidden" name="fixed_term" id="js_hidden_fixed_term" value="30">
                                    <input type="hidden" name="fixed_endtime" id="js_hidden_fixed_endtime"
                                    value="">
                                    <input type="hidden" name="from_day" id="js_hidden_from_day" value="1">
                                </div>
                            </div>
                        </div>
                    </div>
                    <i class="arrow arrow_out" style="margin-top: 0px;">
                    </i>
                    <i class="arrow arrow_in" style="margin-top: 0px;">
                    </i>
                </div>
                <div pageId="11"  class="js_edit_content portable_editor to_left appmsg_editor" style="display: none; zoom: 1;">
                    <div class="inner">
                        <div class="editor_section">
<!--                             <h3 class="title"> -->
<!--                                 领券设置 -->
<!--                             </h3> -->
                             
                            <div class="appmsg_edit_item" id="js_share_type">
                                <div class="frm_control_group">
<!--                                     <div class="frm_controls frm_vertical_lh checkbox-list"> -->
                                    
<!--                                     	<label for="can_share"> -->
<!-- 										    <div class="checker"> -->
<!-- 										        <span checkBox class=""> -->
										            <input name="can_share" checked="true" id="can_share" type="hidden">
<!-- 										        </span> -->
<!-- 										    </div> -->
<!-- 										    用户可以分享领券链接 -->
<!-- 										</label> -->
                                        
                                        
<!--                                         <label for="can_give_friend"> -->
<!-- 										    <div class="checker"> -->
<!-- 										        <span checkBox class=""> -->
										            <input name="can_give_friend" checked="true" id="can_give_friend" type="hidden">
<!-- 										        </span> -->
<!-- 										    </div> -->
<!-- 										    用户领券后可转赠其他好友 -->
<!-- 										</label> 
                                        
                                    </div>-->
                                  
                                </div>
                            </div>
                        </div>
                        <div class="editor_section">
                            <h3 class="title">
                                销券设置
                            </h3>
                            <div class="appmsg_edit_item row_item">
                                <div class="frm_control_group radio_row" id="js_destroy_type">
                                    <label for="" class="frm_label">
                                        销券方式
                                    </label>
                                   
                                    <div  class="frm_controls frm_vertical_lh">
<!--                                         <div class="radio_control_group group"> -->
<!--                                             <label radio class="frm_radio_label" for="checkbox3"> -->
<!--                                                 <i class="icon_radio"> -->
<!--                                                 </i> -->
<!--                                                 <span class="lbl_content"> -->
<!--                                                     仅卡券号 -->
<!--                                                 </span> -->
<!--                                                 <input checked data-key="0" type="radio" name="code_type" value="CODE_TYPE_TEXT" class="frm_radio" id="checkbox3"> -->
<%--                                                 <p class="frm_tips"> --%>
<!--                                                     只显示卡券号，验证后可进行销券 -->
<%--                                                 </p> --%>
<!--                                             </label> -->
<!--                                         </div> -->
<!--                                         <div class="radio_control_group group"> -->
<!--                                             <label radio class="frm_radio_label" for="checkbox4"> -->
<!--                                                 <i class="icon_radio"> -->
<!--                                                 </i> -->
<!--                                                 <span class="lbl_content"> -->
<!--                                                     二维码 -->
<!--                                                 </span> -->
<!--                                                 <input data-key="2"  type="radio" name="code_type"  value="CODE_TYPE_QRCODE" class="frm_radio" id="checkbox4"> -->
<%--                                                 <p class="frm_tips"> --%>
<!--                                                     包含卡券信息的二维码，扫描后可进行销券 -->
<%--                                                 </p> --%>
<!--                                             </label> -->
<!--                                         </div> -->
                                        <div class="radio_control_group group">
                                            <label radio class="frm_radio_label" for="checkbox5">
                                                <i class="icon_radio">
                                                </i>
                                                <span class="lbl_content">
                                                    条形码
                                                </span>
                                                <input  data-key="1" checked type="radio" name="code_type"  value="CODE_TYPE_BARCODE" class="frm_radio" id="checkbox5">
                                                <p class="frm_tips">
                                                    包含卡券信息的条形码，扫描后可进行销券
                                                </p>
                                            </label>
                                        </div>
                                  
                                    </div>
                                    <input type="hidden" name="code_type" id="js_hidden_code_type" value="">
                                </div>
                            </div>
                             
                         </div>   
                            	
                        
                    </div>
                    <i class="arrow arrow_out" style="margin-top: 0px;">
                    </i>
                    <i class="arrow arrow_in" style="margin-top: 0px;">
                    </i>
                </div>
                <div pageId="12"  class="js_edit_content portable_editor to_left appmsg_editor" style="display: none; zoom: 1;">
                    <div class="inner">
                        <div class="editor_section">
                            <div class="appmsg_edit_item textarea_item">
                                <label for="" class="frm_label">
                                    <strong class="title">
                                        优惠详情
                                    </strong>
                                </label>
                                <div class="frm_controls">
                                    <div class="frm_textarea_box">
                                        <textarea class="frm_textarea" name="gift" placeholder=""></textarea>
                                    </div>
                                </div>
                                <p id="dgiftErrorMsg" class="frm_msg fail" style="display: none;">
								    <span for="description" class="frm_msg_content" style="display: inline;">
								        使用须知不能为空且长度不超过300个汉字
								    </span>
								</p>
                            </div>
                            <div class="appmsg_edit_item textarea_item">
                                <label for="" class="frm_label">
                                    <strong class="title">
                                        使用须知
                                    </strong>
                                </label>
                                <div class="frm_controls">
                                    <div class="frm_textarea_box">
                                        <textarea class="frm_textarea" name="description" placeholder=""></textarea>
                                    </div>
                                </div>
                                <p id="descriptionErrorMsg" class="frm_msg fail" style="display: none;">
								    <span for="description" class="frm_msg_content" style="display: inline;">
								        使用须知不能为空且长度不超过300个汉字
								    </span>
								</p>
                            </div>
                        </div>
                        <div class="appmsg_edit_item">
                            <label for="" class="frm_label">
                                <strong class="title">
                                    客服电话
                                </strong>
                            </label>
                            <span class="frm_input_box">
                                <input name="service_phone" value="" type="text" class="frm_input valid"
                                placeholder="">
                            </span>
                            <p class="frm_tips">
                                手机或固话
                            </p>
                        </div>
                    </div>
                    <i class="arrow arrow_out" style="margin-top: 0px;">
                    </i>
                    <i class="arrow arrow_in" style="margin-top: 0px;">
                    </i>
                </div>
                <div pageId="13"  class="js_edit_content portable_editor to_left appmsg_editor" style="display: none; zoom: 1;">
                    <div class="inner">
                        <div class="editor_section">
                            <h3 class="title">
                                服务信息
                            </h3>
                            <div class="appmsg_edit_item">
                                <label for="" class="frm_label">
                                    <strong class="title">
                                        适用门店
                                    </strong>
                                </label>
                                <div class="frm_control_group radio_row">
                                    <div class="frm_controls">
<%--                                         <p class="frm_tips frm_tips_top"> --%>
<!--                                             "适用门店"方便帮助用户到店消费。如有门店，请仔细配置。可在"功能"-"门店管理"管理门店信息。 -->
<%--                                         </p> --%>
                                       <select name="shopidlist" class="form-control">
                                        	 <option value="0">所有门店</option>
                                         	<c:forEach items="${storeList }" var="storePojo">
									        	<option value="${storePojo.STORE_CODE }">${storePojo.STORE_NAME }</option>
									         </c:forEach>
									      </select>
									      <div class="checkbox">
									      <label>
									        <input name="tbweixin" type="checkbox"> 同步到微信
									      </label>
									    </div>
                                            </div>
                                            <input type="hidden" value="门店审核中" name="no_store_reason" id="js_hidden_noshop_reason">
                                        </div>
<!--                                         <div class="radio_control_group group"> -->
<!--                                             <label radio class="frm_radio_label selected" for="checkbox8"> -->
<!--                                                 <i class="icon_radio"> -->
<!--                                                 </i> -->
<!--                                                 <span class="lbl_content"> -->
<!--                                                     全部门店适用 -->
<!--                                                 </span> -->
<!--                                                 <input name="xxx" type="radio" value="1" class="frm_radio js_shop_type" id="checkbox8" -->
<!--                                                 checked="checked"> -->
<!--                                             </label> -->
<!--                                         </div> -->
                                    </div>
                                    <input type="hidden" name="shop_type" value="1" id="js_hidden_shop_type">
                                </div>
                            </div>
<!--                             <div class="frm_control_group" id="js_nearby_container" style="display: block;"> -->
<!--                                 <div style="display:none;" class="frm_controls frm_vertical_lh"> -->
<!--                                 	<label for="checkbox12"> -->
<!-- 										    <div class="checker"> -->
<!-- 										        <span checkbox="" class="checked"> -->
<!-- 										            <input id="checkbox12" type="checkbox"> -->
<!-- 										        </span> -->
<!-- 										    </div> -->
<!-- 										   用户可在“附近”领取卡券 -->
										  
<!-- 										</label> -->
                                    	
<!--                                 </div> -->
                                
<%--                                  <p class="frm_tips" id=""> --%>
<!-- 	                                            选择后，若指定的适用门店是优质门店，用户在微信"发现"-"附近"-"附近的门店"可领取本次添加的卡券。 -->
<!-- 	                                            如何成为附近的商户 -->
<!-- 	                                            
<!-- 	                                            <a href="http://mp.weixin.qq.com/s?__biz=MjM5NDAwMTA2MA==&amp;mid=205032311&amp;idx=1&amp;sn=167fa91e8bc7816d3e38d827f19f3b80#rd" -->
<!-- 	                                            target="_blank"> -->
<!-- 	                                                如何成为附近的商户 -->
<!-- 	                                            </a> -->
<!-- 	                                            --> 
<!-- 	                                            <br> -->
<!-- 	                                            可在 -->
<!-- 	                                            门店管理 -->
<!-- 	                                            
<!-- 	                                            <a href="/merchant/entityshop?action=list&amp;lang=zh_CN&amp;token=276543067" -->
<!-- 	                                            target="_blank"> -->
	                                                
<!-- 	                                            </a> -->
<!-- 	                                            --> 
<!-- 	                                            中补充门店信息使其成为优质门店。 -->
<%-- 	                                        </p> --%>
<!--                                 <input type="hidden" name="show_in_nearby" value="1" id="js_show_in_nearby"> -->
<!--                             </div> -->
                        </div>
                        <div class="appmsg_edit_item upload_item" id="js_poi_pic_url" style="display:none;">
                            <label for="" class="frm_label frm_label_top">
                                <strong class="title">
                                    卡券缩略图
                                </strong>
                            </label>
                            <div class="upload_box has_demo" style="width:174px;">
                                <div class="upload_demo demo_card">
                                    <strong>
                                        预览
                                    </strong>
                                    <div class="card_item">
                                        <span class="card_item_name">
                                            卡券
                                        </span>
                                        <span class="card_item_avatar" id="js_preview_logo">
                                            示意图
                                        </span>
                                        <span class="card_item_ic">
                                        </span>
                                    </div>
                                </div>
                                <input type="hidden" class="file_field" value="" name="poi_pic_url" id="poi_pic_url_hidden">
                                <p class="upload_tips">
                                    用于附近门店的商家详情页，请提交与卡券相关的图片，图片建议尺寸150像素x96像素。仅支持.jpg .jpeg .bmp .png格式的照片，大小不超过2M
                                </p>
                                <div class="upload_area">
                                    <a class="btn btn_upload js_select_file" id="poi_pic_url">
                                        上传图片
                                    </a>
                                </div>
                                <div id="poi_pic_url_preview" class="upload_preview">
                                </div>
                            </div>
                        </div>
                    </div>
                    <i class="arrow arrow_out" style="margin-top: 0px;">
                    </i>
                    <i class="arrow arrow_in" style="margin-top: 0px;">
                    </i>
                </div>
            </div>
        </form>
    </div>
    <div class="tool_bar   tc">
        <span id="js_submit" class="btn btn_input btn_primary" data-actionid="9">
            <button>
                提交审核
            </button>
        </span>
         <!-- 
        <span id="js_preview" class="btn btn_input btn_default" data-actionid="8">
            <button>
                预览
            </button>
        </span>
         -->
    </div>
</div>
	</div>
</body>
</html>
 
<script src="/resources/assets/global/plugins/jquery-1.11.0.min.js" type="text/javascript"></script>
<script>
var create_action ="${p:go('','createTicketAction','') }&card_type=GIFT";
Common.init();
</script>
<script type="text/javascript" src="js/moment.min.js"></script>
<script type="text/javascript" src="js/daterangepicker.js"></script>
<script src="js/gift.js?R=2"></script>

<link rel="stylesheet" type="text/css" href="css/ticketManager.css"/>
