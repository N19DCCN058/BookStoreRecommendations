<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Đăng Kí</title>

<link href="<c:url value = "/resources/css/bootstrap.min.css"/>" rel="stylesheet">
<link href="<c:url value = "/resources/css/datepicker3.css"/>" rel="stylesheet">
<link href="<c:url value = "/resources/css/styles.css"/>" rel="stylesheet">

<script src="<c:url value="/resources/js/lumino.glyphs.js"/>"></script>
</head>

<body>
	
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.servletContext.contextPath}/trangchu.htm"><span>PTIT </span>BOOKSTORE</a>
			</div>
							
		</div><!-- /.container-fluid -->
	</nav>
		
		<div class="row">
		<div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">Đăng kí tài khoản</div>
				<div class="panel-body">
					<form role="form" action="${pageContext.servletContext.contextPath}/dangki.htm" method="post">
						<fieldset>
							<div class="form-group">
							<p>Username</p>
								<input class="form-control" placeholder="nguyenvana" id = "username" name="username" type="text" >
							</div>
							
							<div class="form-group">
							<p>Email</p>
								<input class="form-control" placeholder="nguyenvana@gmail.com" id = "email" name="email" type="email" >
							</div>
							
							<div class="form-group">
							<p>Full Name</p>
								<input class="form-control" placeholder="Nguyễn Văn A" id = "name" name="name" type="text" >
							</div>
							
							<div class="form-group">
							<p>Số điện thoại</p>
								<input class="form-control" placeholder="0123456789" id = "phone" name="phone" type="number" >
							</div>
							
							<div class="form-group">
							<p>Địa chỉ</p>
								<input class="form-control" placeholder="11/29A, Đình Phong Phú" id = "address" name="address" type="text" >
							</div>
							
							<div class="form-group">
							<p>Mật khẩu</p>
								<input class="form-control" placeholder="********" id = "password" name="password" type="password" >
							</div>
							
							<div>
								<c:choose>
									<c:when test="${status_dangki_username==0}">
										<div class="alert alert-warning" role="alert">Username không hợp lệ</div>
									</c:when>
									<c:when test="${status_dangki_email==0}">
										<div class="alert alert-warning" role="alert">Email không hợp lệ</div>
									</c:when>
									<c:when test="${status_dangki_name==0}">
										<div class="alert alert-warning" role="alert">Họ Và Tên không hợp lệ</div>
									</c:when>
									<c:when test="${status_dangki_address==0}">
										<div class="alert alert-warning" role="alert">Địa chỉ không hợp lệ</div>
									</c:when>
									<c:when test="${status_dangki_phone==0}">
										<div class="alert alert-warning" role="alert">Số điện thoại không hợp lệ</div>
									</c:when>
									<c:when test="${status_dangki_password==0}">
										<div class="alert alert-warning" role="alert">Mật khẩu không hợp lệ</div>
									</c:when>
									<c:when test="${errorUsername==1}">
										<div class="alert alert-danger" role="alert">Username đã được đăng kí</div>
									</c:when>
									<c:when test="${errorEmail==1}">
										<div class="alert alert-danger" role="alert">Email đã được đăng kí</div>
									</c:when>
									<c:when test="${errorPhone==1}">
										<div class="alert alert-danger" role="alert">Số điện thoại đã được đăng kí</div>
									</c:when>
									<c:otherwise>

									</c:otherwise>
								</c:choose>
							</div>
							
							<button type="submit" class="btn btn-primary">Đăng Kí</button>
						</fieldset>
					</form>
				</div>
			</div>
		</div><!-- /.col-->
	</div><!-- /.row -->	

	<script src="<c:url value = "/resources/js/jquery-1.11.1.min.js"/>"></script>
	<script src="<c:url value = "/resources/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value = "/resources/js/chart.min.js"/>"></script>
	<script src="<c:url value = "/resources/js/chart-data.js"/>"></script>
	<script src="<c:url value = "/resources/js/easypiechart.js"/>"></script>
	<script src="<c:url value = "/resources/js/easypiechart-data.js"/>"></script>
	<script src="<c:url value = "/resources/js/bootstrap-datepicker.js"/>"></script>
	<script>
		$('#calendar').datepicker({
		});

		!function ($) {
		    $(document).on("click","ul.nav li.parent > a > span.icon", function(){          
		        $(this).find('em:first').toggleClass("glyphicon-minus");      
		    }); 
		    $(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
		}(window.jQuery);

		$(window).on('resize', function () {
		  if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
		})
		$(window).on('resize', function () {
		  if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
		})
	</script>	
</body>

</html>
