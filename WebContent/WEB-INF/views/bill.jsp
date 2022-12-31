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
					class="img-responsive"></a>
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
						<div class="panel panel-info " style="margin-bottom: 15px">
						  <div class="panel-heading">
						    <h3 class="panel-title">Chi tiết đơn hàng</h3>
						  </div>
						  <div class="panel-body">
						  	<table class="table table-hover">
								<thead>
									<th>STT</th>
									<th>Tên sản phẩm</th>
									<th>Hình ảnh</th>
									<th>Số lượng</th>
									<th>Thành tiền</th>
								</thead>
								<tbody>
								<%! int i = 1;%>
								<c:forEach var="detailBill" items="${detailsBill }">
								
									<tr>
										<td><% out.print(i++); %></td>
										<td>${detailBill.nameProduct}</td>
										<td><img src="${pageContext.servletContext.contextPath}/resources/upload/product/${detailBill.photo}" class="img-thumbnail" alt="" style="width: 50px;"></td>
										<td> ${detailBill.soluong}</td>
										<td>${detailBill.thanhtienToString()} VNĐ</td>
									</tr>
								</c:forEach>
									<tr>
										<td colspan="4">Tổng tiền</td>
										<td>${sumPrice} VNĐ</td>
									</tr>
									<% i=1; %>
								</tbody>
							</table>
					</div>
				</div>
				
				<div class="panel panel-info ">
					  <div class="panel-heading">
					    <h3 class="panel-title text-left">Thông tin liên hệ</h3>
					  </div>
					  <div class="panel-body">
					  	<form class="form-horizontal">
						  <div class="form-group">
						    <label for="inputEmail3" class="col-sm-offset-1 col-sm-2 control-label">Họ tên</label>
						    <div class="col-sm-8">
						      <p class="form-control">${bill.customerName }</p>
						    </div>
						  </div>
						  <div class="form-group">
						    <label for="inputEmail3" class="col-sm-offset-1 col-sm-2 control-label">Email</label>
						    <div class="col-sm-8">
						      <p class="form-control">${bill.customerEmail }</p>
						    </div>
						  </div>
						  <div class="form-group">
						    <label for="inputEmail3" class="col-sm-offset-1 col-sm-2 control-label">Địa chỉ</label>
						    <div class="col-sm-8">
						      <p class="form-control">${bill.customerAddress }</p>
						    </div>
						  </div>
						  <div class="form-group">
						    <label for="inputEmail3" class="col-sm-offset-1 col-sm-2 control-label">Số điện thoại</label>
						    <div class="col-sm-8">
						      <p class="form-control">${bill.customerPhone }</p>
						    </div>
						  </div>
						  <div class="form-group">
						    <label for="inputEmail3" class="col-sm-offset-1 col-sm-2 control-label">Nội dung</label>
						    <div class="col-sm-8">
						      <textarea class="form-control" disabled="disabled" rows="3" id="note">${bill.note }</textarea>
						    </div>
						  </div>
							<div class="form-group">
						    <label for="inputEmail3" class="col-sm-offset-1 col-sm-2 control-label">Hình thức thanh toán</label>
						    <div class="col-sm-8">
						      <p class="form-control">${bill.payment_Methods.paymentName }</p>
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
						chỉ:  97 Man Thiện, P. Tăng Nhơn Phú A, Quận 9, TP. HCM.<br> <span
							class="glyphicon glyphicon-phone" aria-hidden="true"></span> Điện
						thoại: 0947025004<br> Copyright ©2022 - Design by minhhiule ---
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