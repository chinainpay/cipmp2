// JavaScript Document
$(function(){


			getData(function(res){
				create_list(res['records']['record']);
		   });
			
 });

var first = false;


var myScroll,
 		pullUpEl, pullUpOffset,
	generatedCount = 0;

	
var create_list = function(d){ 
		      var item_tpl = $("#item_tpl").html(); 	
			  var $item = null;
			  var item = '';
			  for(var i=0;i<d.length;i++){
				
				item = item_tpl;
				if(d[i]['type'] == "扣款"){
					item = item.replace('{type}','话费充值');
				}else{
					item = item.replace('{type}',d[i]['type']);
				}
				item = item.replace('{amount}',d[i]['amount']/100); 
				item = item.replace('{date}',dateFormeat(d[i]['date'],'yyyy-MM-dd'));
				item = item.replace('{status}',d[i]['status']);
				item = item.replace('{orderId}',d[i]['orderId']);
				var merchantName = "高汇通商户";
				if(d[i]['merchantName'].split('-')[1])
					merchantName = d[i]['merchantName'].split('-')[1];
				item = item.replace('{merchantName}',merchantName);
                $item = $(item); 
				
				$("#thelist").append($item);
				
				bind_item($item);
			  }
			  
			  myScroll.refresh();	
			  pullUpEl.querySelector('.pullUpLabel').innerHTML = '下拉刷新';		
}

var bind_item = function($item){
		
		 //$("#arrow_down").hide();
		 $item.find(".tradeRecord_box_right").click(function(){
												 
				$item.find("[_label='arrow_up']").toggle();		
				$item.find("[_label='arrow_down']").toggle();
				$item.find(".tradeRecord_box_details").toggle();
	
		 });
}
var page=1;
var count = function(){
	page++;
	}

function pullUpAction (callback) {
	
	setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
		getData(function(res){
				 create_list(res['records']['record']);
				 callback();
				 count();
                 
		   });
	
			// Remember to refresh when contents are loaded (ie: on ajax completion)
	}, 1000);	// <-- Simulate network congestion, remove setTimeout from production!

	
}

function loaded() {

	pullUpEl = document.getElementById('pullUp');	
	pullUpOffset = pullUpEl.offsetHeight; 
	
	myScroll = new iScroll('wrapper', {
		useTransition: true,
		
		onRefresh: function () {
			
			 if (pullUpEl.className.match('loading')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉刷新';
			}
		},
		onScrollMove: function () {
			
			 if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
				this.maxScrollY = this.maxScrollY;
			} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉刷新';
				this.maxScrollY = pullUpOffset;
			}
		},
		onScrollEnd: function () {
			 if (pullUpEl.className.match('flip')) {
				pullUpEl.className = 'loading';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';				
				pullUpAction(function(){
								  
								  });	// Execute custom function (ajax call?)
			}
		}
	});
	
	setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 800);
}

document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 200); }, false);



function getData(callback){
		
		/*setTimeout(function(){
						var d = {"cardNo":"2326519999000000996","history":"0","page_size":"10","result":"PZ","cause":"查询成功","page":"1","allTotal":"","mac":"kdbUVj9U6MUqzmAXi2MMEKtCu1QqAn/TRBbDhL3WJ5+kUrnWbjvoyORjXfYPbFds/q3O5auVnQFA7xLldG2+faEWRInYYR9Tam8ylS41DEss3I24JXa2hmcHGMy8SyAJwawsSV8AqwG+wjtyOzNI/5yeXETmUWt1Ww0THaSr7ho=","code":"00","records":{"total":"3","record":[{"amount":"1000","status":"成功","merchantName":"102100079970534-充值","date":"20140819194410","type":"充值","orderId":"0000006520413095"},{"amount":"1000","status":"成功","merchantName":"102100079970534-充值","date":"20140819194320","type":"充值","orderId":"0000006520413094"},{"amount":"1000","status":"成功","merchantName":"102100079970534-充值","date":"20140819194243","type":"充值","orderId":"0000006520413093"}]},"msg":"成功"};
						
						callback(d);
							},1000)
	return;
	*/	
		if(first === false){
		   var obj = {};   
		    first = true;
		   	
		   }else{
			   var obj = { 
		            history:1,
					page:page							
					  }
				
		   }
		

					$.ajax({
					   type: "POST",
					   url: "member_record.action",
					   data: $.param(obj),
					   dataType : 'json',
					   success: function(msg){
					   		callback(msg);
					   }
					});


	}
	
