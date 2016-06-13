$(function(){ 
	$('#js_modifytime_btn').daterangepicker({
		format: 'YYYY-MM-DD'
	}, function(start, end, label) { 
		 
	    $("#js_time_container").text(start.format('YYYY-MM-DD')+'至'+end.format('YYYY-MM-DD'));
	}).on('show.daterangepicker',function(){
		return;
		
		$("#js_time_container").html('<div class="ta_date">'+
							'<span class="date_title" id="js_dateRangeTitle0">2015.06.11-2015.06.18</span>'+
							'<a class="opt_sel" id="js_dateRangeTrigger0" href="#">'+
								'<i class="i_orderd"></i>'+
							'</a>'+
						'</div>')
	})
	//ta_dateTpl
	
	$(window.top.document).find("#iframe").height($("body").height());
})

 
