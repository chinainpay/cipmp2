var timestamp, nonceStr, signature, jsApiList;
window.onload = function() {
	var url = encodeURIComponent(location.href.split('#')[0]);
	$.ajax({
		type : 'POST',
		url : create_action,
		data : {
			pagePath : url
		},
		dataType : 'json',
		success : function(msg) {
			timestamp = msg.timestamp;
			nonceStr = msg.nonceStr;
			signature = msg.signature;
			jsApiList = msg.jsApiList;
			wx.config({
				debug : false,
				appId : "wx80157e82a942debe",
				nonceStr : nonceStr,
				timestamp : timestamp,
				signature : signature,
				jsApiList : [ 'addCard' ]
			});
		}
	});
};

var addCard_lock = false;
function addCard() {
	if (addCard_lock == true)
		return;
	var getCode = $("#getCode").text();
	$.ajax({
		type : 'POST',
		url : create_action1,
		data : {},
		dataType : 'json',
		success : function(msg) {
			wx.addCard({
				cardList : [ {
					cardId : msg.card_id,
					cardExt : "{\"code\":\"" + getCode + "\",\"timestamp\":\""
							+ msg.timestamp + "\",\"signature\":\""
							+ msg.signature + "\"}"
				} ],
				success : function(res) {
					updateWxState(getCode);
					addCard_lock = true;
				},
				fail : function(res) {
					// alert("error : >>>>> " + JSON.stringify(res));
				}
			});
		}
	});
};

function updateWxState(code) {
	$.ajax({
		type : 'POST',
		url : create_action2 + "&code=" + code,
		data : {},
		dataType : 'json'
	});
}
$(function(){
	$("#batchAddCard").click(function(){
		addCard();
	});
});
