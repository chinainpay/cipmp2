<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="http://www.cipmp.cn/tld/pagelet"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="chs" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="chs" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="chs">
<!--<![endif]-->
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<script type="text/javascript" src="/resources/own/script/common.js"></script>
<link rel="stylesheet" type="text/css" href="css/memberStatis.css">
</head>
<body>
<div class="page-content">
	<div class="portlet box  ">
		 
		<div class="portlet-body form">

			<div class="form-horizontal">
				<div class="form-body">
					  
					<div class="form-group"> 
						<div class="col-md-3">
							<select class="form-control">
								<option>Option 1</option>
								<option>Option 2</option>
								<option>Option 3</option> 
							</select>
						</div>
						<label class="col-md-1 control-label">
							至
						</label>
							
						<div class="col-md-3">
							<select class="form-control">
								<option>Option 1</option>
								<option>Option 2</option>
								<option>Option 3</option> 
							</select>
						</div>

						<div class="col-md-3">
							<select class="form-control">
								<option>Option 1</option>
								<option>Option 2</option>
								<option>Option 3</option> 
							</select>
						</div>
						<div class="col-md-2">
							<button type="submit" class="btn green">查询</button>
						</div>
							
						
					</div>
				</div>
			</div>

			<div class="statis-item">

				<div class="statis-content">
						<div class="row statis-header">
							<div class="col-md-7">
									会员统计分析
							</div>	

							<div class="col-md-5">
									新增会员565
							</div>
						</div>

						<div class="row statis-content">
							<div class="col-md-7">
								<div id="line"  style="height:400px;"  ></div>

							</div>

							<div class="col-md-5">
									<div class="portlet-body">
										<h4>活动带来的新增会员量</h4>
										<div style="height:400px;" id="pie"  ></div>
									</div>
 
							</div>
						</div>
				</div>

				 
			</div>

 

		</div>
	</div>
	<div class="modal fade" id="msg" class="modal fade" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					<h4 modal-title class="modal-title">修改成功</h4>
				</div>
				<div modal-body class="modal-body">
					
				</div>
				<div class="modal-footer">
					<button modal-submit type="button" data-dismiss="modal" class="btn green"><i class="icon-ok"></i>确认</button>
				</div>
			</div>
		</div>
	</div>
</div>

	
	




<!--ECharts单文件引入 -->
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script> 
<script type="text/javascript">
    // 路径配置
    require.config({
        paths: {
            echarts: 'http://echarts.baidu.com/build/dist'
        }
    });

    require(
        [
            'echarts',
            'echarts/chart/line',
            'echarts/chart/pie' 
        ],
        function (ec) {
            var myPie = ec.init(document.getElementById('pie'));
            var option = {
			    title : {
			        text: '南丁格尔玫瑰图',
			        subtext: '纯属虚构',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        x : 'center',
			        y : 'bottom',
			      show : false,
			        data:['rose1','rose2','rose3','rose4','rose5','rose6','rose7','rose8']
			    },
			    toolbox: {
			        show : false,
			        feature : {
			            mark : {show: false},
			            dataView : {show: false, readOnly: false},
			            magicType : {
			                show: false, 
			                type: ['pie', 'funnel']
			            },
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    series : [
			        {
			            name:'半径模式',
			            type:'pie',
			            radius : [20, 110],
			            center : ['50%', 200],
			            roseType : 'radius',
			            width: '40%',       // for funnel
			            max: 40,            // for funnel
			            itemStyle : {
			               
			               
			            },
			            data:[
			                {value:10, name:'rose1'},
			                {value:5, name:'rose2'},
			                {value:15, name:'rose3'},
			                {value:25, name:'rose4'},
			                {value:20, name:'rose5'},
			                {value:35, name:'rose6'},
			                {value:30, name:'rose7'},
			                {value:40, name:'rose8'}
			            ]
			        }
			    ]
			};
                    
            myPie.setOption(option);


            var myLine = ec.init(document.getElementById('line'));
            var option = {
				    legend: {
				        data:['高度(km)与气温(°C)变化关系']
				    },
				    
				    calculable : true,
				    tooltip : {
				        trigger: 'axis',
				        formatter: "Temperature : <br/>{b}km : {c}°C"
				    },
				    xAxis : [
				        {
				            type : 'value',
				            axisLabel : {
				                formatter: '{value} °C'
				            }
				        }
				    ],
				    yAxis : [
				        {
				            type : 'category',
				            axisLine : {onZero: false},
				            axisLabel : {
				                formatter: '{value} km'
				            },
				            boundaryGap : false,
				            data : ['0', '10', '20', '30', '40', '50', '60', '70', '80']
				        }
				    ],
				    series : [
				        {
				            name:'高度(km)与气温(°C)变化关系',
				            type:'line',
				            smooth:true,
				            itemStyle: {
				                normal: {
				                    lineStyle: {
				                        shadowColor : 'rgba(0,0,0,0.4)'
				                    }
				                }
				            },
				            data:[15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5]
				        }
				    ]
				};
                    

            	myLine.setOption(option);
        }
    );
</script>

<script type="text/javascript"> 

	 
			
			Common.init(); 
			
		 
		
/*
var PLUGINS = [
		 
		"assets/admin/pages/scripts/charts.js",

];
		
		Common.js(PLUGINS);
		
		setTimeout(function(){ 

			  
		   		
			   //Charts.init(); 
			   //Charts.initCharts();	 
			   Charts.initPieCharts();
			   //Charts.initBarCharts();

		},1000)
*/   
		   
		 

	 
</script>
<%if("OK".equals(request.getAttribute("MSG"))){%>
<script type="text/javascript">
showModal('#msg');
</script>
<%}%>
</body>
</html>