$(function() {
		   
       var pricemoney = $("#pricemoney").val() || 50;
	    var $app_radio_a = $(".radio li a");
		$app_radio_a.each(function(){
					 
					if(pricemoney == +$(this).attr('data-value')){
						$(this).parent().addClass("checked").siblings(".checked").removeClass("checked");
						
            			$(this).trigger("app.click.radio", this);
					}
		 });
		
		$(document).on("app.click.radio", ".radio li a", function(e, element) {
				e.preventDefault();
				$("#pricemoney").val($(element).attr("data-value"));
		 });	
		
		
    $(document).on("click", ".radio li a", function(e) {
        e.preventDefault();
        var $this = $(this);
        if (! $this.parent().hasClass("disabled")) {
            $this.parent().addClass("checked").siblings(".checked").removeClass("checked");
            $this.trigger("app.click.radio", this);
        }
        return false;
    });


    $(document).on("keyup", ".form-mod input", function(e) {
        var $this = $(this);
        $this.parent().toggleClass("on", $this.val().length > 0);
    });

    $(document).on("click", ".form-mod .btn-delete", function(e) {
        e.preventDefault();
        $(this).parent().removeClass("on");
        $(this).siblings("input").val("").focus();
        return false;
    });

    $(".form-mod input").filter(function() {
        return this.value;
    }).parent().addClass("on");
	
	
	$("#otherPrice").click(function(){
					$(".checked").removeClass("checked");

	})
	
						
	$("li a").click(function(){
				$("#otherPrice").val("");
	})
	
	$("#otherPrice").blur(function(){
	    var other=$("#otherPrice").val();
		if(other!=""){
			$("#pricemoney").val(other);
			}
	})
	
//	$("#weixinpay").click(function(){
//					var other=$("#otherPrice").val();
//					var pattern = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
//					if(!pattern.test(other)&& other!=""){
//						tip("Please enter a number");
//						return false;
//					}			   
//	 })


});