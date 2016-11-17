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
	<div class="col-md-12 col-xs-12">
		<div class="panel panel-primary">
			<div class="panel-heading">訂單列表</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="table-responsive dataTable_wrapper">
					<table class="table table-striped table-bordered table-hover" id="dataTables">
						<thead>
							<tr>
								<th>訂單編號</th>
								<th>姓名</th>
								<th>連絡電話</th>
								<th>手環數量</th>
								<th>毛巾數量</th>
								<th>應收金額</th>
								<th>取貨地點</th>
								<th>預定取貨時間</th>
								<th>訂單狀態</th>
							</tr>
						</thead>
						<s:bean name="com.servlet.act.OrderManageAction" id="form" />
						<s:if test="#form.qry() && #form.result.size()>0">
							<tbody>
								<s:iterator value="#form.result" var="list">
									<tr onclick="javascript:window.location.href='orderManage/edit?status=edit&seq=<s:property value="#list.seq"/>'">
										<td><s:property value="#list.seq" /></td>
										<td><s:property value="#list.cname" /></td>
										<td><s:property value="#list.con_tel" /></td>
										<td align="right"><s:property value="#list.order_bracelet" /></td>
										<td align="right"><s:property value="#list.order_towel" /></td>
										<td align="right"><s:property value="#list.total_price" /></td>
										<td><s:property value="#list.get_place" /></td>
										<td><s:property value="@com.comm.FmtBean@transDate(#list.get_date,1)" /></td>
										<td><s:property value="#list.pay_status" /></td>
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
			responsive : true,
			order:[[0,"desc"]]
		});
	});
</script>