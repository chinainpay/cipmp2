function tip(text,callback,okText){
	art.dialog({
	    width:'75%',
		drag: false,
		resize: false,
		title: '提示',
		content: text,
		fixed: true,
		lock: true,
		okVal:okText,
		ok:function(){
			 if(typeof callback == 'function'){
				 callback();
			 }
		}
	   });

}

var _alert = function(){
	var tpl = '<div class="alert">'+
					'<div  class="alertContent">'+
						'<div  class="alertText" >'+
							'<div alertText class="_text" ></div>'+
						'</div>'+
						'<div alertBtn   class="alertBtn" >'+
							'<div  class="_text" >好</div>'+
						'</div>'+
					'</div>'+
			  '</div>';
	var $alert = null;
	var $alertText = null;
	var $alertBtn = null;
	var init = function(){
			$alert = $(tpl);
			$alertText = $alert.find("[alertText]");
			$alertBtn = $alert.find("[alertBtn]");
			$alertBtn.click(function(){
				hide();
				return false;
			})
			$("body").append($alert);
	}

	var show = function(text){
			if(!$alert)init();
			$alert.css({
				'display' : 'block'
			})

			$alertText.html(text);
	}

	var hide = function(){
			if(!$alert)init();
			$alert.css({
				'display' : 'none'
			})
	}

	return function(text){
		show(text)
	}
}()



var loading = function(){
			var tpl = '<div class="loading">'+
							'<div  class="loadingContent">'+
								'<div  class="_text" >'+
									'<img src="img/10.gif" />'+
								'</div>'+
							'</div>'+
					  '</div>';
			var $el = null; 
			var init = function(){
					$el = $(tpl);  
					$("body").append($el);
			}

			var show = function(text){
					if(!$el)init();
					$el.css({
						'display' : 'block'
					})
 
			}

			var hide = function(){
					if(!$el)init();
					$el.css({
						'display' : 'none'
					})
			}

			return {
				show : show
				,hide : hide
			}
	}() 

	//loading.show();
	//loading.hide();
	
var ajax = function(){

			var ajaxLock = false;

				return function(param,callback){

					if(ajaxLock == true)return false;
							ajaxLock = true;

							$.ajax({
							   type: param['type']||"POST",
							   url: param['action'],
							   data: $.param(param['param']),
							   dataType:'json',
							   success: function(msg){
							      	callback(msg);	 
							   },
							   complete: function(msg){
							    	ajaxLock = false;  	 
							   }
							});
				}

		}();

	var bindAjax = function(){

			

			var get_param = function($el){	

					var res = {};
						$el.find("[ajaxItem]").each(function(){
							//需要验证
							
							if($(this).attr('check') != undefined){

								if(!$(this).val()){
									_alert($(this).attr('data-checkMsg'))
									res = false;
									return false;
								}
							}
							res[$(this).attr('name')] = $(this).val();
						})	

						return res;
			}

			return function($el){
				 

				var action = $el.attr('data-action');
				var callbackName = $el.attr('data-callback');
				var $submit = $el.find("[submit]");
					$submit.click(function(){
						

							var param = get_param($el);
								if(!param)return false;
 								
 								loading.show();
								ajax({
									action : action
									,param : param
								},function(res){
									eval(callbackName+'(res)');	
									 
									loading.hides();
									 
								});
								
								return false;
					})
			}

	}();
	
	$(function(){
		$("[ajax]").each(function(){
			bindAjax($(this));
		})
	})

	//bindAjax($("[ajax]"));
	
/* 页面结构文档 */
	
/*
		    ajax 是最外层
		    data-callback 是提交后的回调函数 xxx 是回调方法名
		    data-action 提交的action
		    凡是带有ajaxItem 的都会被ajax 提交过去
		    带有 check 在提交的时候会被验证 目前是非空
		    data-checkMsg 验证失败时提示的文字
		    name 和 value  还是原有的意思
		    带有 submit 属性会被绑定提交事件
		    
			
			<div ajax data-callback="xxx" data-action="action.xxx">
	              <script type="text/javascript">
	                  var xxx = function(){
	                      alert('xxx');
	                  }
	              </script>
	              <input ajaxItem   name="input_name" value="" />
	
	              <input ajaxItem check data-checkMsg="input1不能为空哦" name="input_name1" value="" />
	
	              <select ajaxItem check data-title="select不能为空哦"  name="select_name">
	                  <option value="1">1</option>
	                  <option value="2">2</option>
	                  <option value="3">3</option>
	              </select>
	
	              <button submit>submit</button>

           </div>
*/
		//Common.init(); 
	
		//Common.init(); 
	function getType() {
			 
			var obj = {
				'pojo.type' : '',
				'pojo.cardNo' :''
			}
			

			$.ajax({
				type : "GET",
				url : actionUrl,
				data : $.param(obj),
				dataType : 'json',
				success: function(msg){
				     if(msg){
				     	if(msg['errorMsg']){ 
				     		alert(res['errorMsg']);
				     		return;
				     	}
				     	
				     	WeixinJSBridge.call('closeWindow');
				     }
				     
				}
			});

		}
		 
		 
		 
