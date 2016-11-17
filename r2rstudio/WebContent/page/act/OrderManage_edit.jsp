<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="sys_code" value="'OrderManage'" />
<s:include value="../header.jsp"></s:include>
<div class="row">
	<div class="col-lg-12">
		<h2 class="page-header">毛巾手環訂單管理</h2>
	</div>
	<!-- /.col-lg-12 -->
</div>
<div class="row">
	<div id="msgtxt" style="display: none">
		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">X</button>
	</div>
</div>
<form role="form" id="form" action="updAction.html" method="post">
	<div class="row">
		<s:token />
		<input type="hidden" name="seq" value="${seq}" />
		<div class="col-lg-12">
			<div class="panel panel-red">
				<div class="panel-heading">訂購者資料</div>
				<div class="panel-body">
					<div class="form-group">
						<div class="col-md-6 col-xs-6">
							<label>訂購人</label>
							<p><img src="<s:property value='fb_pic'/>" class="img-circle" />&nbsp;&nbsp; <a href="<s:property value='fb_link'/>" target="_blank">
									<s:property value='cname' />
								</a></p>

						</div>
						<div class="col-md-6 col-xs-6">
							<label>連絡電話</label>
							<p class="text-primary"><s:property value='con_tel' /></p>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6 col-xs-6">
							<label>Email</label>
							<p class="text-primary"><s:property value='con_mail' /></p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-green">
				<div class="panel-heading">資料管理</div>
				<div class="panel-body">
					<div class="form-group">
						<div class="col-md-6 col-xs-6">
							<label>訂單編號</label>
							<p class="text-primary"><s:property value='seq' /></p>
						</div>
						<div class="col-md-6 col-xs-6">
							<label>建立日期</label>
							<p class="text-primary"><s:property value='@com.comm.FmtBean@transDate(order_date,1)' /></p>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-xs-4">
							<label>手環數量</label>
							<p class="text-primary"><s:property value='order_bracelet' /></p>
						</div>
						<div class="col-md-4 col-xs-4">
							<label>毛巾數量</label>
							<p class="text-primary"><s:property value='order_towel' /></p>
						</div>
						<div class="col-md-4 col-xs-4">
							<label>應付金額</label>
							<p class="text-primary"><i class="fa fa-dollar"></i> <s:property value='total_price' /></p>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6 col-xs-6">
							<label>取貨地點</label>
							<s:select class="form-control" name="get_place" list="@com.comm.FmtBean@getSysCDMap('place')" listKey="key" listValue="value" value="get_place" />
						</div>
						<div class="col-md-6 col-xs-6">
							<label>取貨時間</label>
							<div id="get_date_picker" class="form-group input-group">
								<input data-format="yyyy/MM/dd" type="text" class="form-control" id="get_date" name="get_date" value="<s:property value='@com.comm.FmtBean@transDate(get_date,1)'/>"></input>
								<span class="add-on input-group-addon">
									<i data-time-icon="icon-time" data-date-icon="icon-calendar" class="fa fa-calendar"> </i>
								</span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-4 col-xs-4">
							<label>訂單確認</label>
							<s:select class="form-control" name="check_yn" list="tfList" listKey="key" listValue="value" value="check_yn" />
						</div>
						<div class="col-md-4 col-xs-4">
							<label>取貨狀態</label>
							<s:select class="form-control" name="pay_status" list="@com.comm.FmtBean@getSysCDMap('paysts')" listKey="key" listValue="value" value="pay_status" />
						</div>
						<div class="col-md-4 col-xs-4">
							<label>取貨日期</label>
							<div id="datetimepicker" class="form-group input-group">
								<input data-format="yyyy/MM/dd" type="text" class="form-control" id="rel_get_date" name="rel_get_date" value="<s:property value='@com.comm.FmtBean@transDate(rel_get_date,1)'/>"></input>
								<span class="add-on input-group-addon">
									<i data-time-icon="icon-time" data-date-icon="icon-calendar" class="fa fa-calendar"> </i>
								</span>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-footer">
					<button type="submit" class="btn btn-success">更新資料</button>
					<button type="button" class="btn btn-default" onclick="javascript:window.location.href='../orderManage.html'">回前頁</button>
				</div>
			</div>
		</div>
	</div>
</form>
<s:include value="../footer.jsp"></s:include>

<script>
	$('#datetimepicker').datetimepicker({
		language : 'zh_TW',
		pickTime : false
	});

	$('#get_date_picker').datetimepicker({
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

	
</script>