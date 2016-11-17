<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:if test="#session.userid == null">
	<s:set name="userid" value="" scope="session" />
	<script>
		location.href = "${pageContext.request.contextPath}/index.html";
	</script>
</s:if>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>R2RTC Admin</title>

<!-- Bootstrap Core CSS -->
<link href="${pageContext.request.contextPath}/plugin/bootstrap/dist/css/bootstrap.css" rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="${pageContext.request.contextPath}/plugin/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

<!-- DataTables CSS -->
<link href="${pageContext.request.contextPath}/plugin/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">

<!-- DateTimePicker CSS -->
<link href="${pageContext.request.contextPath}/plugin/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="${pageContext.request.contextPath}/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="${pageContext.request.contextPath}/plugin/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">


<!-- bootstrap wysihtml5 - text editor -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/js/plugin/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">

<!-- DataTables Responsive CSS -->
<%-- <link href="${pageContext.request.contextPath}/plugin/datatables-responsive/css/responsive.dataTables.scss" rel="stylesheet"> --%>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.ico">
</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<img src="${pageContext.request.contextPath}/img/r2rlogo.png" class="navbar-brand" style="padding: 5px" />
				<a class="navbar-brand" href="${pageContext.request.contextPath}/main">R2RTC Admin 2.0</a>


			</div>
			<!-- /.navbar-header -->
			<ul class="nav navbar-top-links navbar-right">
				<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
					</a>
					<ul class="dropdown-menu dropdown-user">
						<li><a href="#">
								<i class="fa fa-user fa-fw"></i> 用戶資料
							</a></li>
						<!-- <li><a href="#">
								<i class="fa fa-gear fa-fw"></i> Settings
							</a></li>
						<li class="divider"></li> -->
						<li><a href="logoutAction.html">
								<i class="fa fa-sign-out fa-fw"></i> 登出
							</a></li>
					</ul> <!-- /.dropdown-user --></li>
				<!-- /.dropdown -->
			</ul>
			<!-- /.navbar-top-links -->

			<div class="navbar-default sidebar" role="navigation">
				<div class="sidebar-nav navbar-collapse">
					<ul class="nav" id="side-menu">
						<%-- <li class="sidebar-search">
							<div class="input-group custom-search-form">
								<input type="text" class="form-control" placeholder="Search...">
								<span class="input-group-btn">
									<button class="btn btn-default" type="button">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div> <!-- /input-group -->
						</li> --%>
						<s:include value="leftmenu.jsp"></s:include>
					</ul>
				</div>
				<!-- /.sidebar-collapse -->
			</div>
			<!-- /.navbar-static-side -->
		</nav>

		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">