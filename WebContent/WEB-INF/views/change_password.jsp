<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Book Store</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script src="<c:url value="/resources/js/jquery-3.1.1.js"/>"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/bootstrap/css/bootstrap.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/style_site.css"/>">
</head>
<body>
	<div class="container">
		<div class="row" style="margin-top: 3px; height: 125px">
			<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4 clearpadding">
				<a href="${pageContext.servletContext.contextPath}/trangchu.htm"><img width = 800px; style="margin-left: 100%;"
					src="<c:url value="/resources/upload/ptit.webp"/>" alt=""
					class="img-responsive imglogo"></a>
			</div>
		</div>
		<div class="row">
			<nav class="navbar navbar-info re-navbar">
				<div class="container-fluid re-container-fluid">
					<!-- Brand and toggle get grouped for better mobile display -->
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse"
							data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
							<span class="sr-only">Toggle navigation</span> 
							<span class="icon-bar"></span> 
							<span class="icon-bar"></span> 
							<span class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="#">--- Menu ---</a>
					</div>
					<!-- Collect the nav links, forms, and other content for toggling -->
					<div class="collapse navbar-collapse re-navbar-collapse"
						id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav">
							<li class="active"><a href="${pageContext.servletContext.contextPath}/trangchu.htm"><span
									class="glyphicon glyphicon-home" aria-hidden="true"></span>
									HOME<span class="sr-only">(current)</span></a></li>
							<c:forEach var="category" items="${listCategorys }">
								<li><a href="${pageContext.servletContext.contextPath}/${category.categoryNameToURL()}.htm">${category.categoryName}</a></li>
							</c:forEach>
						</ul>
						<ul class="nav navbar-nav navbar-right">
							<li><a href="${pageContext.servletContext.contextPath}/user/mycart.htm"> <span
									class="glyphicon glyphicon-shopping-cart"></span> Giỏ Hàng
							</a></li>
							<c:choose>
								<c:when test="${role=='GUEST'||role==null}">
									<li><a href="${pageContext.servletContext.contextPath}/dangnhap.htm">Đăng nhập</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/dangki.htm">Đăng kí</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.servletContext.contextPath}/user/contact.htm">Thông tin cá nhân</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/dangxuat.htm">Đăng xuất</a></li>
								</c:otherwise>
							</c:choose>
							
						</ul>
					</div>
					<!-- /.navbar-collapse -->

				</div>
				<!-- /.container-fluid -->
			</nav>
		</div>

		<div class="row">
			<div class="panel panel-info">
				
				<div class="panel-body">
				<div class="panel panel-info ">
					  <div class="panel-heading">
					    <h3 class="panel-title text-left">Đổi mật khẩu</h3>
					  </div>
					  <div class="panel-body">
					  	<form class="form-horizontal" action="${pageContext.servletContext.contextPath}/user/changepass.htm" method="post">
						  <div class="form-group">
						    <label for="inputEmail3" class="col-sm-offset-1 col-sm-2 control-label">Mật khẩu cũ</label>
						    <div class="col-sm-8">
						      <input type="password" class="form-control" id="inputEmail3" name = "oldPassword" placeholder="***************">
						    </div>
						  </div>
						  <div class="form-group">
						    <label for="inputEmail3" class="col-sm-offset-1 col-sm-2 control-label">Mật khẩu mới</label>
						    <div class="col-sm-8">
						      <input type="password" class="form-control" id="inputEmail3" name = "newPassword" placeholder="***************">
						    </div>
						  </div>
						  <div class="form-group">
						    <label for="inputEmail3" class="col-sm-offset-1 col-sm-2 control-label">Nhập lại</label>
						    <div class="col-sm-8">
						      <input type="password" class="form-control" id="inputEmail3" name = "rePassword" placeholder="***************">
						    </div>
						  </div>
						  
						  <div>
						  <c:choose>
								<c:when test="${status_update_Pass==0}">
									<div class="alert alert-warning" role="alert">Mật khẩu không hợp lệ</div>
										${status_update_Pass=null }
								</c:when>
								<c:when test="${status_update_Pass==-1}">
									<div class="alert alert-warning" role="alert">Mật khẩu không đúng</div>
										${status_update_Pass=null }
								</c:when>
								<c:when test="${status_update_Pass==-2}">
									<div class="alert alert-warning" role="alert">Mật khẩu không khớp</div>
										${status_update_Pass=null }
								</c:when>
								<c:when test="${status_update_Pass==1}">
									<div class="alert alert-success" role="alert">Đổi mật khẩu thành công</div>
										${status_update_Pass=null }
								</c:when>
								<c:otherwise>

								</c:otherwise>
						</c:choose>
						  </div>
						  
						  <div class="form-group">
						    <div class="col-sm-offset-3 col-sm-7">
						      <button type="submit" class="btn btn-success">Đổi mật khẩu</button>
						    </div>
						  </div>
						</form>				  	
					  </div>
					</div>
				</div>
			</div>
		</div>

		<div class="row" id="footer">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8">
					<address>
						<strong> PTIT BOOK STORE HCM</strong><br> <span
							class="glyphicon glyphicon-home" aria-hidden="true"></span> Địa
						chỉ: 97 Man Thiện, P. Tăng Nhơn Phú A, Quận 9, TP. HCM.<br> <span
							class="glyphicon glyphicon-phone" aria-hidden="true"></span> Điện
						thoại: 0947025004<br>  Copyright ©2022 - Design by minhhiule ---
					</address>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<script
		src="<c:url value="/reesources/bootstrap/js/bootstrap.min.js"/>"></script>
</body>
</html>