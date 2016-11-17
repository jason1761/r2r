<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="sys_code" value="'NewsManage'" />
<s:include value="../header.jsp"></s:include>

<div class="row">
	<div class="col-lg-12">
		<h2 class="page-header">最新消息管理</h2>
	</div>
	<!-- /.col-lg-12 -->
</div>
<div class="row">
	<div class="col-md-12 col-xs-12">
		<div class="panel panel-primary">
			<div class="panel-heading">
				列表
				<div class="pull-right">
					<a class="btn btn-outline btn-default btn-xs" href="newsManage/edit.html?status=add">
						<i class="fa fa-plus"></i> 新增資料
					</a>
				</div>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="table-responsive dataTable_wrapper">
					<table class="table table-striped table-bordered table-hover" id="dataTables">
						<thead>
							<tr>
								<th>系統編號</th>
								<th>標題</th>
								<th>是否置頂</th>
								<th>是否顯示</th>
								<th>顯示日期</th>
								<th>顯示期限</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<s:include value="../footer.jsp"></s:include>
<script>
	$(document).ready(function() {
		$('#dataTables').DataTable({
			responsive : true,
			order : [ [ 0, "desc" ] ]
		});
	});

	
</script>