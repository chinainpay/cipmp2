// JavaScript Document
	$(function () {
		var locked = false;
	  $("#modify").click(function(){
				  
				 var password_now = $.trim($('#password_now').val());
				 alert(password_now);
				 var password_new = $.trim($('#password_new').val());
				 alert(password_now);
				 var pattern_psw=/\d{6}/;	
				 
				 if (password_now == ""||!pattern_psw.test(password_now)) {
						tip("����ȷ���뵱ǰ����");
						$("#password_now").focus();
						return false;
			         }
			     if (password_new == ""||!pattern_psw.test(password_new)) {
						tip("����ȷ����������");
						$("#password_new").focus();
						return false;
			         }	
			     if(locked == true)return false;
					locked = true;
			     var obj={
			    		 oldPwd:password_now,
			    		 newPwd:password_new
			     }
			     
				 $.ajax({
					   type: "POST",
					   url: "${p:go('','modifyPassword','') }",
					   data: $.param(obj),
					   dataType : 'json',
					   success: function(msg){
					          locked = false;
							 if(msg['result'] === "RZ"){
								 tip("�޸�����ɹ�",function(){
									 window.location.href = '';
								 });
							 }else{
								 tip("��ǰ������д����");
							 }
					   }
			    });
				
		});
		
	});

   