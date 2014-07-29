function draw(){
	var version = new Array();	// 版本
	var create_bug = new Array();	// 新建的bug
	var sum_bug = new Array();	// bug总数
	var open_bug = new Array();	// 打开的bug（未关闭的bug）
	var open_sum_bug = new Array();	// 打开的bug的累计（未关闭的bug）
	
	var sum_tmp=0;
	
	var i=0,k=0;
	
	$("tbody").find("tr").each(function(){	
		var j=0;	
		var input=$("tbody").find("tr")[i].getElementsByTagName("input")[0];
		if(input.checked){
			$(this).find("td").each(function(){
				switch(j)
				{
				case 0:
					version[k]=$(this).text();
					break;
				case 1:
					sum_bug[k]=parseInt($(this).text());
					break;
				case 2:
					open_bug[k]=parseInt($(this).text());
					sum_tmp=sum_tmp+open_bug[k];
					open_sum_bug[k]=sum_tmp;
					k++;
					break;
				default:
					break;
				}
				j++;
			});
		}
		i++;
	});
	
	function show_sheet(){
		var lineChartData = {
			labels : version,
			bezierCurve:0,
			animation : false,
			datasets : [
				// bug总数
				{
					fillColor : "rgba(0,0,0,0)",
					strokeColor : "rgba(50,50,50,1)",
					pointColor : "rgba(50,50,50,1)",
					pointStrokeColor : "#fff",
					data : sum_bug
				},
				// 打开的bug
				{
					fillColor : "rgba(100,0,0,0)",
					strokeColor : "rgba(0,0,250,1)",
					pointColor : "rgba(0,0,255,1)",
					pointStrokeColor : "#fff",
					data : open_bug
				},
				// 打开的bug累计
				{
					fillColor : "rgba(100,0,0,0)",
					strokeColor : "rgba(255,0,0,1)",
					pointColor : "rgba(255,0,0,1)",
					pointStrokeColor : "#fff",
					data : open_sum_bug
				}
			]
			
		};
	
		var myLine = new Chart(document.getElementById("canvas").getContext("2d")).Line(lineChartData);
	}
	
	// 绑定事件
	document.addEventListener("EventName",function() {});
	
	show_sheet();
}