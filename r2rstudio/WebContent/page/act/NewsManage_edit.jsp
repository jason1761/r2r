<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="sys_code" value="'IncomeManage'" />
<s:include value="../header.jsp"></s:include>
<div class="row">
	<div class="col-lg-12">
		<h2 class="page-header">團費管理</h2>
	</div>
	<!-- /.col-lg-12 -->
</div>
<div class="row">
	<div id="msgtxt" style="display: none">
		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">X</button>
	</div>
</div>
<div class="row">
	<div class="col-lg-12">
		<form role="form" id="form" action="updAction.html" method="post" >
			<s:token />
			<input type="hidden" name="seq" value="${seq}" />
			<input type="hidden" name="status" value="${status}" />
			<div class="panel panel-green">
				<div class="panel-heading">資料管理</div>
				<div class="panel-body">
					<div class="form-group">
						<div class="col-md-6 col-xs-6">
							<label>系統編號</label>
							<input type="text" class="form-control" value="${seq }" placeholder="自動編號" disabled />
						</div>
						<div class="col-md-6 col-xs-6">
							<label>發生日期</label>
							<div id="getdate_picker" class="form-group input-group">
								<input data-format="yyyy/MM/dd" type="text" class="form-control" required="required" name="getdate" value="<s:property value='@com.comm.FmtBean@transDate(getdate,1)'/>"></input>
								<span class="add-on input-group-addon">
									<i data-time-icon="icon-time" data-date-icon="icon-calendar" class="fa fa-calendar"> </i>
								</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-12 col-xs-12">
							<label>細項</label>
							<input type="text" name="subtitle" required="required" class="form-control" value="${subtitle}"/>
						</div>
					</div>
					<div class="form-group ">
						<div class="col-md-4 col-xs-4">
							<label>收入金額</label>
							<input type="text" class="form-control text-right"  required="required" name="income" value="${income}"  onkeyup="this.value=this.value.replace(/[^\d]/g,'')"  onfocus="this.value=''"/>
						</div>
						<div class="col-md-4 col-xs-4">
							<label>支出金額</label>
							<input type="text" class="form-control text-right"  required="required" name="outcome"  value="${outcome}"  onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onfocus="this.value=''" />
						</div>
						<div class="col-md-4 col-xs-4">
							<label>合計金額</label>
							<h4 class="text-danger" id="totalcnt"><stong><i class="fa fa-dollar"></i> ${income-outcome}</stong></h4>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-12 col-xs-12">
							<label>細項說明</label>
							<textarea class="form-control" name="submemo">${submemo }</textarea>
						</div>
					</div>
				</div>
				<div class="panel-footer">
					<button type="submit" class="btn btn-success">更新資料</button>
<!-- 					<button type="button" class="btn btn-warning" onclick="submitDel()">刪除資料</button> -->
					<button type="reset" class="btn btn-default">重寫</button>
					<button type="button" class="btn btn-default" onclick="javascript:window.location.href='../incomeManage.html'">回前頁</button>
				</div>
			</div>
		</form>
	</div>
</div>
<s:include value="../footer.jsp"></s:include>

<script>
	$('#getdate_picker').datetimepicker({
		language : 'zh_TW',
		pickTime : false
	});

	$(function() {
		var msgext = "<s:property value='msg_ext'/>";
		if (msgext == 'upd_success') {
			$("#msgtxt").html("更新成功。");
			$("#msgtxt").removeClass();
			$("#msgtxt").addClass("alert alert-success alert-dismissable");
			$("#msgtxt").show();
		} else if (msgext == 'upd_error') {
			$("#msgtxt").html("更新失敗。");
			$("#msgtxt").removeClass();
			$("#msgtxt").addClass("alert alert-danger alert-dismissable");
			$("#msgtxt").show();
		} else {
			$("#msgtxt").hide();
		}
	});
	
	function submitDel(){
		bootbox.confirm("資料一經刪除即無法回復，確定刪除？",function(result){
			if(result){
				
			}else{
				return false;
			}
		});
	}
</script>