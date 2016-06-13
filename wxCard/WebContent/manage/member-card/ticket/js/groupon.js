var pages = {};
var submit_data = {};
$(function(){

	var _$iframe = $(window.top.document).find("#iframe");
		_$iframe.height($("body").height());
		 
	
	
	var submitLock = false;
	
	
		$("#js_submit").click(function(){ 
			
			submit_data = {};
			
			
			if(!(res = pages['10']['save']()))return false;
				jQuery.extend(submit_data,res);
				
			if(!(res = pages['11']['save']()))return false;
				jQuery.extend(submit_data,res);
				
			if(!(res = pages['12']['save']()))return false;
				jQuery.extend(submit_data,res);
				
			if(!(res = pages['13']['save']()))return false;
				jQuery.extend(submit_data,res);
			
			
			
			if(submitLock == true)return false;
				submitLock = true;
			
			
				console.log('submit_data')
				console.log(submit_data);
					$.ajax({
					   type: "POST",
					   url: create_action,
					   data: $.param({
						   'json' : JSON.stringify(submit_data)
					   }),
					   dataType:'json',
					   success: function(msg){
						   if(msg){
							  
							   if(msg['errorMsg']){
								   alert(msg['errorMsg']);
								   return;
							   }
						   }
						   		
						   		window.location.href = msg['url'];
					   },
					   complete : function(){
						   submitLock = false;
					   }
					});
			
			return false;
		})
		
		if(typeof test_data != 'undefined'){
			for(var i in test_data){
				var $el = $("[name='"+i+"']");
					if($el.size() > 0){
						//console.log($el[0].tagName)
						if($el.attr('type') == 'text' || $el[0].tagName == 'TEXTAREA'){
							$el.val(test_data[i]);
						}else{
							
							if($el.attr('type') == 'radio'){
								$el = $el.filter("[value='"+test_data[i]+"']");
								if($el.size() > 0){
									$el[0].checked = true;
								}else{
									console.log([i,test_data[i]].join(' - '));
								}
							}
							
							if($el.attr('type') == 'checkbox'){
								$el[0].checked = true;
							}
						}
						
					}
			}
		}

})


$(function(){
	$('#reservation').daterangepicker(null, function(start, end, label) {
	    console.log(start.toISOString(), end.toISOString(), label);
	});

})




//閸掑洦宕查崣鍏呮櫠
var setRightTop;
$(function(){

	setRightTop = function(actionid){ 
		$item.filter("[data-actionid='"+actionid+"']").click();
	}
	


	var $item = $("#js_preview_area").find("[data-actionid]"); 
		$item.click(function(){
			var self = this;

				  
			$pages.each(function(){ 
 
				if($(this).attr('pageId') == $(self).attr('data-actionid')){
					var top = ($(self).offset().top+($(self).height()/2)) - 130;
						top+=8;
						if(top < 0)top = 0;
					$(this).css({
						'display' : 'block'
						,'margin-top' : top
					})
				}else{
					$(this).css({
						'display' : 'none'
					})
				}
			})
			
			return false;
		})
		
	var $pages = $("[pageId]");

})

$(function(){
	var $radio = $("[radio]");
	

	var initRadio = function($item){
			var $input = $item.find("[type='radio']"); 
				
				$input.change(function(){
					refreshRadio();
				})
			return;
			var $frm_radio_label = $item.find(".frm_radio_label");
				$frm_radio_label.click(function(){ 
					$frm_radio_label.removeClass('selected');
					$(this).addClass('selected');
				})
	
	}
	
	var refreshRadio = function(){
			$radio.each(function(){
				var $input = $(this).find("[type='radio']");
					if($input[0].checked){
						$(this).addClass('selected');
					}else{
						$(this).removeClass('selected');
					}
			})
	}


	refreshRadio();
	
	$radio.each(function(){
		initRadio($(this));
	})

	var $checkBox = $("[checkBox]");
	var initCheckBox = function($item){
			var $input = $item.find("[type='checkbox']");
			var setInput = function(){
					if($input[0].checked){
						$item.addClass('checked');
					}else{
						$item.removeClass('checked');
					}
			}
				
				setInput();

				$input.change(function(){
					setInput();
				})
	}
		
		$checkBox.each(function(){
			initCheckBox($(this))
		})
})


//pageid = 10
$(function(){

	var $page = $("[pageid='10']");
	var $el = $("#js_colorpicker");
	var $dropdownSwitch = $el.find("[dropdownSwitch]");
	var $jsDropdownList = $el.find("[jsDropdownList]");
	var $jsDropdownItem = $jsDropdownList.find("[jsDropdownItem]");
	var $js_color_preview = $("[js_color_preview]");
	var $discount = $page.find("[name='discount']");
	var $title = $page.find("[name='title']");
	var $sub_title = $page.find("[name='sub_title']");
	var $type = $page.find("[name='type']");
	var $select1 = $page.find("[name='_select1']");
	var $select2 = $page.find("[name='_select2']");
	var $js_title_preview = $("#js_title_preview");
	var $js_sub_title_preview = $("#js_sub_title_preview");
	var $reservation = $page.find("[name='reservation']");
	var $quantity = $page.find("[name='quantity']"); 
	var $get_limit = $page.find("[name='get_limit']");
	var $notice = $page.find("[name='notice']");
		 
		$dropdownSwitch.click(function(){
			$jsDropdownList.css({
				'display' : 'block'
			})
			return false;
		}) 
		
		//disabled
		
	var setDisabled = function(){
			if($page.find("#checkbox2")[0].checked){
				$select1.removeAttr('disabled');
				$select2.removeAttr('disabled');
			}else{
				$select1.attr({
					disabled : true
				})
				
				$select2.attr({
					disabled : true
				})
			}
		}
		
		 
		
		setInterval(function(){
			setDisabled();
		},100)
		
		
		
		$jsDropdownItem.click(function(){
			$js_color_preview.css({
				'background-color' : $(this).attr('data-value')
			}).attr({
				'data-color' : $(this).attr('data-name')
			})
		})
		
		
		$(document).click(function(){
			$jsDropdownList.css({
				'display' : 'none'
			})
		})
	

	var checks = {
		 	discount : function(){

		 		if($discount.val() <= 0.1 || $discount.val() >= 10){

			 		$("#discountErrorMsg").css({
			 				'display' : 'block'
			 		})

			 		return false;
			 	}

			 		$("#discountErrorMsg").css({
			 				'display' : 'none'
			 		})

			 		return true;
 
			}  
			,title : function(){

		 		if(!$title.val()){

			 		$("#titleErrorMsg").css({
			 				'display' : 'block'
			 		})

			 		return false;
			 	}

			 		$("#titleErrorMsg").css({
			 				'display' : 'none'
			 		})

			 		return true;
 
			}  
			,sub_title : function(){
				
				var v = $sub_title.val()||'';

		 		if(!v || v.length > 18){

			 		$("#sub_titleErrorMsg").css({
			 				'display' : 'block'
			 		})

			 		return false;
			 	}

			 		$("#sub_titleErrorMsg").css({
			 				'display' : 'none'
			 		})

			 		return true;
 
			}  
			,quantity : function(){

		 		if(!$quantity.val()){

			 		$("#quantityErrorMsg").css({
			 				'display' : 'block'
			 		})

			 		return false;
			 	}

			 		$("#quantityErrorMsg").css({
			 				'display' : 'none'
			 		})

			 		return true;


			}
			,notice : function(){

				if(!$notice.val()){
				 
			 		$("#noticeErrorMsg").css({
			 				'display' : 'block'
			 		})

			 		return false;
			 	}

			 		$("#noticeErrorMsg").css({
			 				'display' : 'none'
			 		})
			 		return true;
				 
			}
	}	

	$discount.blur(function(){
		$(this).val(($(this).val() || '').replace(/[^\d\.]*/ig,''))
		checks['discount']();
	})

	$title.blur(function(){
		checks['title']();
	}) 
	
	
	$sub_title.blur(function(){
		checks['sub_title']();
	}) 
	
	$quantity.blur(function(){
		checks['quantity']();
	})
	
	$notice.blur(function(){
		checks['notice']();
	})
	
	//js_title_preview
	
	setInterval(function(){
		$js_title_preview.text($title.val());
	},100)
	
	setInterval(function(){
		$js_sub_title_preview.text($sub_title.val());
	},100)
	//js_sub_title_preview

	
	var save = function(){
			var res = {};
				res['color'] = $js_color_preview.attr('data-color');
				res['discount'] = $discount.val();
				res['title'] = $title.val();
				res['sub_title'] = $sub_title.val();
				res['quantity'] = $quantity.val();  
				res['get_limit'] = $get_limit.val();
				res['notice'] = $notice.val();
				//res['reservation'] = $reservation.val();
				

				for(var i in checks){
			 		if(!checks[i]()){
			 			setRightTop(10);
			 			return false;
			 		}
			 	}

				if($type.filter(":checked").val() == 1){
					
					var timestamp = $reservation.val(); 
						timestamp = timestamp.split(' - ');
					
					res['date_info'] = {
							type : 1
							,begin_timestamp : timestamp[0]
							,end_timestamp : timestamp[1]
					}
				}else{
					
					res['date_info'] = {
							type : 2
							,fixed_begin_term : $page.find("[name='_select1']").val()
							,fixed_term : $page.find("[name='_select2']").val()
					}
					 
				}
				
				 

				return res;
	}

	pages['10'] = {
		'save' : save
	}
})



//pageid = 11
$(function(){
	var $page = $("[pageid='11']");
	 
	var $items = $("#js_destroy_type_preview").children('div');
	var $radioInput = $page.find("[radio] input");
	 
	var $can_share = $page.find("[name='can_share']");
	var $can_give_friend = $page.find("[name='can_give_friend']");
	var $code_type = $page.find("[name='code_type']");
	var $code_num = $page.find("[name='code_num']");
	 

	var checks = {
		 	 
	}

	 

	var save = function(){
			var res = {}; 
				 
				res['code_type'] = $code_type.filter(":checked").val();
				
				if($can_share.filter(":checked")){
					res['can_share'] = true;
				}else{
					res['can_share'] = false;
				}
				
				if($can_give_friend.filter(":checked")){
					res['can_give_friend'] = true;
				}else{
					res['can_give_friend'] = false;
				}
				
				if(res['code_type'] == 'CODE_TYPE_BARCODE'){
					if($code_num.val()){
						res['code_type'] = 'CODE_TYPE_ONLY_BARCODE';
					}
				}
				
				if(res['code_type'] == 'CODE_TYPE_QRCODE'){
					if($code_num.val()){
						res['code_type'] = 'CODE_TYPE_ONLY_QRCODE';
					}
				}
				
				//console.log('res');
				//console.log(res);
				//code_type
				
				 
			 	for(var i in checks){
			 		if(!checks[i]()){
			 			setRightTop(11);
			 			return false;
			 		}
			 	}
			 	 

				return res;
	}


	var refreshItems = function(){
			var v = $radioInput.filter(":checked").attr('data-key');
				if(typeof v == 'undefined')return;

				$("#js_destroy_title").css({
					'display' : 'none'
				})
				$items.css({
					'display' : 'none'
				})

				$items.filter(".js_code_preview_"+v).css({
					'display' : 'block'
				})
	}
		//filter
		refreshItems();
		$radioInput.change(function(){
			refreshItems();
		})

	//js_destroy_type_preview

	pages['11'] = {
		'save' : save
	}
})

//pageid = 12
$(function(){
	var $page = $("[pageid='12']");
	
	
	var $detail = $page.find("[name='detail']");
	var $description = $page.find("[name='description']");
	var $service_phone = $page.find("[name='service_phone']"); 
	var $js_nearby = $("#js_nearby");

		var checks = {
		 	description : function(){

		 		if(!$description.val()){

			 		$("#descriptionErrorMsg").css({
			 				'display' : 'block'
			 		})

			 		return false;
			 	}

			 		$("#descriptionErrorMsg").css({
			 				'display' : 'none'
			 		})

			 		return true;  
			} 
			,detail : function(){
				
				if(!$detail.val()){

			 		$("#detailErrorMsg").css({
			 				'display' : 'block'
			 		})

			 		return false;
			 	}

			 		$("#detailErrorMsg").css({
			 				'display' : 'none'
			 		})

			 		return true;  
			}
		}


		$description.blur(function(){
			checks['description']();
		})
		
		$detail.blur(function(){
			checks['detail']();
		})
		
		
		
	var save = function(){
			var res = {}; 
			 
				res['detail'] = $detail.val(); 
				res['description'] = $description.val(); 
				res['service_phone'] = $service_phone.val(); 
			 	
			 	for(var i in checks){
			 		if(!checks[i]()){
			 			setRightTop(12);
			 			return false;
			 		}
			 	}

				return res;
	}
  
	pages['12'] = {
		'save' : save
	}
})

//pageid = 13
$(function(){
	var $page = $("[pageid='13']");
	var $shopidlist = $page.find("[name='shopidlist']"); 
	var $tbweixin = $page.find("[name='tbweixin']"); 
	
	
	var save = function(){
			var res = {}; 
				 
			    res['shopidlist'] = $shopidlist.val(); 
			    res['tbweixin'] = $tbweixin[0].checked;

				return res;
	}
  
	pages['13'] = {
		'save' : save
	}
})



/*
var test_data = {
	
		can_give_friend: true,
		can_share: true,
		code_type: "CODE_TYPE_QRCODE",
		date_info: {
			begin_timestamp: "2014-05-12",
			end_timestamp: "2014-05-12",
			type: 1
		},
		begin_timestamp: "2014-05-12",
		end_timestamp: "2014-05-12",
		type: 1 ,
		description: "娴ｈ法鏁ゆい鑽ょ叀",
		discount: "1",
		get_limit: "2",
		notice: "閹垮秳缍旈幓鎰仛",
		quantity: "1",
		service_phone: "12321323",
		sub_title: "閸擃垱鐖ｆ０锟�
		title: "閹舵ɑ澧搁崚鍛婄垼妫帮拷
	
}
*/