$(function () {
			
			
			    var locked = false;
				if(locked == true)return false;
					locked = true;
					sendMessage(function(){
									locked = false;
										 });
				$("#SendCode").click(function(){
					if(locked == true)return false;
					locked = true;
					var obj={
						bindPhone:phone,
						what:"findpwd"
					};
					var url="SendMessage.action"
					send(function(msg){
								  
								  },url,obj);
					sendMessage(function(){
									locked = false;
										 });
				 })
				
				var obj={
						bindPhone:phone,
						what:"findpwd"
					};
				var url="SendMessage.action"
				send(function(msg){
							  
							  },url,obj);

				$("#confirm").click(function(){

						 var code=$("#code").val();
						 var pwd_new=$("#pwd_new").val();
						 var pattern_psw=/\d{6}/;
						 if(code==''||!pattern_psw.test(code)){
							 tip("����ȷ������֤��");
							 $("#code").focus();
							 return false;
									 }
						 if(pwd_new==''||!pattern_psw.test(pwd_new)){
							 tip("����ȷ��������");
							 $("#pwd_new").focus();
							 return false;
									 }
									 
						 var obj = { 
  					  		messageNum :code,
							pwd:pwd_new
					     }

						 $.ajax({
							   type: "POST",
							   url:'member_resetPwd.action',
							   data: $.param(obj),
							   dataType : 'json',
							   success: function(msg){
									//tip(msg);
							  		if(msg['msg'] === "00"){
							  			tip("�������óɹ���",function(){
							  				window.location.href = 'member_isVipMember.action';
							  			});
										$(".aui_close").hide();
							  		}
							  		if(msg['msg'] === "01"){
							  			tip("��������ʧ�ܣ�");
							  		}
							  		if(msg['msg'] === "02"){
							  			tip("��֤���������");
							  		}
							   }
					     });
						 
				         $("#confirm").attr("disabled","disabled");
				 });		
				
});

var InterValObj; //timer����������ʱ��
var count = 60; //���������1��ִ��
var curCount;//��ǰʣ������

function sendMessage(callback) {
	curCount = count;
           //timer������
           function SetRemainTime() {
                     if (curCount == 0) {                
                         window.clearInterval(InterValObj);//ֹͣ��ʱ��
                         $("#SendCode").val("���·���");
                         callback();
                        }
                     else {
                         curCount--;
                         $("#SendCode").val("�ط�("+curCount+")" );
                        }
           }
		   
           $("#SendCode").val( "�ط�("+curCount+")" );
           InterValObj = window.setInterval(SetRemainTime,1000);//������ʱ����1��ִ��һ��
}


function send(callback,url,obj){
					$.ajax({
					   type: "POST",
					   url: url,
					   data: $.param(obj),
					   dataType : 'json',
					   success: function(msg){
					   		callback(msg);
					   }
					});

	}