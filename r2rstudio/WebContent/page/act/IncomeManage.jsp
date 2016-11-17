<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="sys_code" value="'incomeManage'" />
<s:include value="../header.jsp"></s:include>

<s:set name="flag" value="qry()"/>

<div class="row">
	<div class="col-lg-12">
		<h2 class="page-header">團費管理</h2>
	</div>
	<!-- /.col-lg-12 -->
</div>
<div class="row">
	<div class="col-lg-4 col-md-4">
		<div class="panel panel-info">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-3">
						<i class="fa fa-plus fa-5x"></i>
					</div>
					<div class="col-xs-9 text-right">
						<div class="huge">
							<s:property value="@com.comm.FmtBean@numSep(total_income,0)" />
						</div>
						<div>收入合計</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-lg-4 col-md-4">
		<div class="panel panel-warning">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-3">
						<i class="fa fa-minus fa-5x"></i>
					</div>
					<div class="col-xs-9 text-right">
						<div class="huge">
							<i class="fa fa-dollar "></i>
							<s:property value="@com.comm.FmtBean@numSep(total_outcome,0)" />
						</div>
						<div>支出合計</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-lg-4 col-md-4">
		<div class="panel panel-danger">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-3">
						<i class="fa fa-exchange fa-5x"></i>
					</div>
					<div class="col-xs-9 text-right">
						<div class="huge">
							<i class="fa fa-dollar "></i>
							<s:property value="@com.comm.FmtBean@numSep(total,0)" />
						</div>
						<div>目前餘額</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-md-12 col-xs-12">
		<div class="panel panel-primary">
			<div class="panel-heading">
				團費明細
				<div class="pull-right">
					<a class="btn btn-outline btn-default btn-xs" href="incomeManage/edit.html?status=add">
						<i class="fa fa-plus"></i> 新增資料
					</a>
				</div>
			</div>
		</div>
		<!-- /.panel-heading -->
		<div class="panel-body">
			<div class="table-responsive dataTable_wrapper">
				<table class="table table-striped table-bordered table-hover" id="dataTables">
					<thead>
						<tr>
							<th>系統編號</th>
							<th>日期</th>
							<th>細項</th>
							<th>收入</th>
							<th>支出</th>
							<th>細項說明</th>
							<th>備註</th>
						</tr>
					</thead>
					<s:if test="#flag && result.size()>0">
						<tbody>
							<s:iterator value="result" var="list">
								<tr onclick="javascript:window.location.href='incomeManage/edit?status=edit&seq=<s:property value="#list.seq"/>'">
									<td><s:property value="#list.seq"/></td>
									<td><s:property value="@com.comm.FmtBean@transDate(#list.getdate,1)" /></td>
									<td><s:property value="#list.subtitle" /></td>
									<td align="right"><s:property value="@com.comm.FmtBean@numSep(#list.income,0)" /></td>
									<td align="right"><s:property value="@com.comm.FmtBean@numSep(#list.outcome,0)" /></td>
									<td><s:property value="#list.submemo" /></td>
									<td><s:property value="#list.memo" /></td>
								</tr>
							</s:iterator>
						</tbody>
					</s:if>
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
			"language" : {
				// 				"url" : "http://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Chinese-traditional.json"
				"url" : "../plugin/datatables-plugins/i18n/Chinese.lang"
			},
			"order" : [ [ 1, "desc" ] ]
		});
	});

	
</script>