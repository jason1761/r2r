<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="row text-center">
	<div class="col-lg-8 col-lg-offset-2 col-md-8 col-sm-8 col-md-offset-2 col-sm-offset-2">
		<h1 data-scroll-reveal="enter from the bottom after 0.1s" class="header-line">團費公開</h1>
		<p data-scroll-reveal="enter from the bottom after 0.3s">台中R2R-2014年4月開創以來，秉持著運動為目的，無門檻不收費用，鼓勵大家一起來跑步，因為有各位跑友的支持與愛護，讓R2R越來越壯大，不管無形的幫忙帶操、分享經驗、拍照錄影以及揪團跑步、團購、報賽事，還是有形的補給、名產、糖果、蛋糕、餅乾、水果以及飲料等等，都來自團友們無私的奉獻，也是社團最大的資源～ヽ(●´ε｀●)ノ
但是未來考量到舉辦活動、團報賽事以及相關行政支出，有團友發心捐獻，團長群就有義務與責任，將收支明細公告以求徵信與團友信任～(ò_óˇ)</p>
	</div>
</div>
<!--/.HEADER LINE END-->
<div class="row set-row-pad">
	<!-- <div class="col-lg-6 col-md-6 col-sm-6 " data-scroll-reveal="enter from the bottom after 0.4s">
		<img src="assets/img/building.jpg" class="img-thumbnail" />
	</div> -->
	<div class="col-lg-10 col-md-10 col-sm-10 col-lg-offset-1 col-md-offset-1 col-sm-offset-1">
		<div class="panel-group" id="accordion">
			<%-- <div class="panel panel-default" data-scroll-reveal="enter from the bottom after 0.9s">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapse3" class="collapsed">
							<strong> 153+</strong> MANAGEMENT COURSES
						</a>
					</h4>
				</div>
				<div id="collapse3" class="panel-collapse collapse" style="height: 0px;">
					<div class="panel-body">
						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sagittis egestas mauris ut vehicula. Cras viverra ac orci ac aliquam. Nulla eget condimentum mauris, eget tincidunt est.</p>
					</div>
				</div>
			</div> --%>
		</div>
		<%-- <div class="alert alert-info" data-scroll-reveal="enter from the bottom after 1.1s">
			<span style="font-size: 40px;">
				<strong> 2500 + </strong>
				<hr />
				Centers
			</span>
		</div> --%>
	</div>
</div>
<script>
	$(function() {
		$.ajax({
			url : "qryIncomeTotalDetail.html",
			dataType : "json",
			type : "post",
			async:false,
			success : function(data) {
// 				console.log(data);
				
				if(data.flag){
					writeData( $("#accordion"), data.result);
				}
			}
		});
	});
	
	
	function writeData($elem,data) {
		var sec = 4;
		var htmlStr = "";
		$(data).each(function(index,element){
			if(index<3){
				htmlStr = "";
				htmlStr+='<div class="panel panel-default" data-scroll-reveal="enter from the bottom after 0.7s"> ';
				htmlStr+='<div class="panel-heading">';
				htmlStr+='<h4 class="panel-title">';
				htmlStr+='<a data-toggle="collapse" data-parent="#accordion" href="#collapse'+index+'" class="collapsed">';
				htmlStr+='<strong>' + element.getmonth + '</strong> 收支明細</a>';
				htmlStr+='<span class="pull-right"><a class="text-primary"><i class="fa fa-plus-circle"></i> '+element.income+'</a>';
				htmlStr+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="text-danger"><i class="fa fa-minus-circle"></i> '+element.outcome+'</a>';
				htmlStr+='</span></h4>';
				htmlStr+='</div>';
				htmlStr+='<div id="collapse'+index+'" class="panel-collapse collapse" style="height: 0px;">';
				htmlStr+='<div class="panel-body">';
				htmlStr+='<div class="table-responsive"><table class="table">';
				
				htmlStr+='<thead><tr><th>#</th><th>日期</th><th>項目</th><th>收入</th><th>支出</th></tr></thead>'
				htmlStr+='<tbody>'+writeDetail(element.detail)+'</tbody></table>';
				htmlStr+='<p></p>';
				htmlStr+='</div>';
				
				htmlStr+='</div></div>';
				htmlStr+='</div>';
				if(index==0) sec += 1;
				else sec +=2;
				$elem.append(htmlStr);
			}
		});
	}
	
	function writeDetail(data){
		var str = '';
		$(data).each(function(index,element){
			str += '<tr>';
			str += '<td>' + (index+1) +'</td>';
			str += '<td>' + element.getdate +'</td>';
			str += '<td>' + element.subtitle +'</td>';
			str += '<td align="right">' + element.income +'</td>';
			str += '<td align="right">' + element.outcome +'</td>';
			str += '</tr>';
		});
		return str;
	}
</script>