<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<li><a href="${pageContext.request.contextPath}/main">
		<i class="fa fa-home fa-fw"></i> 主頁
	</a></li>
<li><a href="#">
		<i class="fa fa-desktop fa-fw"></i> 前台管理
		<span class="fa arrow"></span>
	</a>
	<ul class="nav nav-second-level">
		<li><a href="${pageContext.request.contextPath}/act/indexNoticeMangage">首頁設定</a></li>
		<%-- <li><a href="${pageContext.request.contextPath}/act/incomeManage">首頁活動編輯</a></li>
		<li><a href="${pageContext.request.contextPath}/act/incomeManage">團跑資訊編輯</a></li> --%>
		<li><a href="${pageContext.request.contextPath}/act/newsManage">最新資訊管理</a></li>
	</ul> <!-- /.nav-second-level --></li>
<li><a href="#">
		<i class="fa fa-list-alt fa-fw"></i> 團務管理
		<span class="fa arrow"></span>
	</a>
	<ul class="nav nav-second-level">
		<li><a href="${pageContext.request.contextPath}/act/orderManage">毛巾/手環訂單管理</a></li>
		<%-- <li><a href="${pageContext.request.contextPath}/act/orderManage">毛巾/手環進銷存</a></li> --%>
		<li><a href="${pageContext.request.contextPath}/act/incomeManage">團費管理</a></li>
		</ul>
<li><a href="#">
		<i class="fa fa-gear fa-fw"></i> 系統管理
		<span class="fa arrow"></span>
	</a>
	<ul class="nav nav-second-level">
		<li><a href="${pageContext.request.contextPath}/sys/userManage">使用者管理</a></li>
	</ul> <!-- /.nav-second-level --></li>
<%-- <li><a href="#">
		<i class="fa fa-bar-chart-o fa-fw"></i> 活動管理
		<span class="fa arrow"></span>
	</a>
	<ul class="nav nav-second-level">
		<li><a href="flot.html">訂單管理</a></li>
	</ul> <!-- /.nav-second-level --></li>
 --%>
<li><a href="http://yohogame.myyoho.com/R2RTC" target="_blank">
		<i class="fa fa-sign-out fa-fw"></i> 前台網站
	</a></li>
<li><a href="http://admin-official.line.me" target="_blank">
		<i class="fa fa-sign-out fa-fw"></i> LINE官方帳號管理
	</a></li>


