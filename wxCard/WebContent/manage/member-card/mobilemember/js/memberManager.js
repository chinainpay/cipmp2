// JavaScript Document$(function () {
	 
$(function () {
		 
		/*
	    $("#cardBack").hide();
		$("#front_paybtn").click(function () {		
			$("#cardFront").hide();
			$("#cardBack").show();
		});
		
		
	
		$("#back_paybtn").click(function () {
			$("#cardBack").hide();
			$("#cardFront").show();
	    });
	    
	    
	    */
	
		 
		
		$("#cardFrontLXL").click(function (){
			window.location.href="member_openMember.action?iss=1";
		});
		$("#cardFrontLXLL").click(function (){
			window.location.href="member_openMember.action";
		});
		
		
		 
		 $("[front_paybtn]").click(function(){    
		        PageTransitions.nextPage(20);  
		        $("#back_paybtn").attr({
		        	'src' : $(this).attr('data-src')
		        })
		        return false;
		  })

		  $("#back_paybtn").click(function(){
		        PageTransitions.nextPage(20);
			  	setTimeout(function(){
			  		window.location.reload();
			  	},500)
		        return false;
		  })
});