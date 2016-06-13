function cardTouFangDiv() {
	$("#toufang").css('display', 'block');
	$("#fadeCreateVip").css('display', 'block');
}

var $dialog = null;
$(function(){
	
		
		$("#msg").find("[modal-submit]").click(function(){
			if(dialog_submit() === false){
				return false;
			}
		});

		$("#add").click(function(){ 
			$dialog = showModal_2('#msg','选择卡券类型'
					,$("#选择卡券类型").html());
		})	
 
})