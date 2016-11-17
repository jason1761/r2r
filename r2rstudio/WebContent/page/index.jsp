<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>R2RTC Admin Studio</title>

<!-- Bootstrap Core CSS -->
<link href="${pageContext.request.contextPath}/plugin/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Social Buttons CSS -->
<link href="${pageContext.request.contextPath}/plugin/bootstrap-social/bootstrap-social.css" rel="stylesheet">
<!-- MetisMenu CSS -->
<link href="${pageContext.request.contextPath}/plugin/metisMenu/dist/metisMenu.min.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="${pageContext.request.contextPath}/dist/css/sb-admin-2.css" rel="stylesheet">
<!-- Custom Fonts -->
<link href="${pageContext.request.contextPath}/plugin/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<!-- jQuery -->
<script src="${pageContext.request.contextPath}/plugin/jquery/dist/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="${pageContext.request.contextPath}/plugin/bootstrap/dist/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="${pageContext.request.contextPath}/plugin/metisMenu/dist/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="${pageContext.request.contextPath}/dist/js/sb-admin-2.js"></script>

<!-- Facbook Login SDK -->
<script src="${pageContext.request.contextPath}/js/fb.js"></script>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/logo.ico">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">請先登入</h3>
					</div>
					<div class="panel-body">
						<form role="form">
							<fieldset>
								<!-- <div class="form-group">
									<input class="form-control" placeholder="User ID" name="userid" type="email" autofocus>
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="Password" name="userpwd" type="password" value="">
								</div>
								<div class="checkbox">
									<label> <input name="remember" type="checkbox" value="Remember Me">Remember Me
									</label>
								</div>
								<a href="index.html" class="btn btn-lg btn-success btn-block">登入</a> -->
								<!-- Change this to a button or input when using this as a form -->
								<a class="btn btn-block btn-social btn-facebook" href="#" onclick="facebookLogin()">
									<i class="fa fa-facebook"></i> 使用Facebook登入
								</a>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>