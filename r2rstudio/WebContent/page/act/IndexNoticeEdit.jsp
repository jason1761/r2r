<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="sys_code" value="'IndexNotice'" />
<s:include value="../header.jsp"></s:include>
<div class="row">
	<div class="col-lg-12">
		<h2 class="page-header">首頁設定</h2>
	</div>
	<!-- /.col-lg-12 -->
</div>
<div class="row">
	<div id="msgtxt" style="display: none">
		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">X</button>
	</div>
</div>
<form role="form" id="form" action="./indexNoticeMangage/updAction.html" method="post">
	<s:token></s:token>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-primary">
				<div class="panel-heading">首頁參數</div>
				<div class="panel-body">
				<div class="form-group">
						<div class="col-md-12 col-xs-12">
							<label>首頁橫幅</label>
							<input type="text" name="index_info" class="form-control" value="${index_info }" required="required"/>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-primary">
			<div class="panel-heading">起跑須知</div>
				<div class="panel-body">
					<textarea id="editor1" name="editor1" rows="10" cols="80" required="required"><s:property value="editor1" /></textarea>
				</div>
				<div class="panel-footer">
					<button type="submit" class="btn btn-primary">更新資料</button>
					<button type="reset" class="btn btn-default">重寫</button>
				</div>
			</div>

		</div>
	</div>
</form>
<s:include value="../footer.jsp"></s:include>
<script>
	$(function() {
		//bootstrap WYSIHTML5 - text editor
		CKEDITOR.replace('editor1');
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