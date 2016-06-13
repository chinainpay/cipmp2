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
							 tip("请正确输入验证码");
							 $("#code").focus();
							 return false;
									 }
						 if(pwd_new==''||!pattern_psw.test(pwd_new)){
							 tip("请正确输入密码");
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
							  			tip("密码重置成功！",function(){
							  				window.location.href = 'member_isVipMember.action';
							  			});
										$(".aui_close").hide();
							  		}
							  		if(msg['msg'] === "01"){
							  			tip("密码重置失败！");
							  		}
							  		if(msg['msg'] === "02"){
							  			tip("验证码输入错误！");
							  		}
							   }
					     });
						 
				         $("#confirm").attr("disabled","disabled");
				 });		
				
});

var InterValObj; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount;//当前剩余秒数

function sendMessage(callback) {
	curCount = count;
           //timer处理函数
           function SetRemainTime() {
                     if (curCount == 0) {                
                         window.clearInterval(InterValObj);//停止计时器
                         $("#SendCode").val("重新发送");
                         callback();
                        }
                     else {
                         curCount--;
                         $("#SendCode").val("重发("+curCount+")" );
                        }
           }
		   
           $("#SendCode").val( "重发("+curCount+")" );
           InterValObj = window.setInterval(SetRemainTime,1000);//启动计时器，1秒执行一次
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