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
								<c:when test="${role=='GUEST' || role==null}">
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
						    <h3 class="panel-title">Thông tin giỏ hàng</h3>
						  </div>
						  <c:if test="${listCarts.size()==0 }">
						  	<div class="alert alert-warning" role="alert">
  									Giỏ Hàng Rỗng
							</div>
						  </c:if>
						  <c:if test="${listCarts.size()!=0 }">
						  <div class="panel-body">
						  	<table class="table table-hover">
								<thead>
									<th>STT</th>
									<th>Tên sản phẩm</th>
									<th>Hình ảnh</th>
									<th>Số lượng</th>
									<th>Thành tiền</th>
									<th>Xóa</th>
								</thead>
								<tbody>
								<%! int i = 1;%>
								<c:forEach var="cart" items="${listCarts }">
								
									<tr>
										<td><% out.print(i++); %></td>
										<td>${cart.batch.batchName}</td>
										<td><img src="${pageContext.servletContext.contextPath}/resources/upload/product/${cart.batch.photo}" class="img-thumbnail" alt="" style="width: 50px;"></td>
										<td><a href="${pageContext.servletContext.contextPath}/user/mycart/${cart.cartId }/sub.htm"> - </a>
										<input disabled="disabled" type="text" value="${cart.amount }" style="width: 30px">
										<a href="${pageContext.servletContext.contextPath}/user/mycart/${cart.cartId }/add.htm"> + </a></td>
										<td>${cart.sumPriceToString() } VNĐ</td>
										<td><a href="${pageContext.servletContext.contextPath}/user/mycart/${cart.cartId }/remove.htm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a></td>
									</tr>
								</c:forEach>
									<tr>
										<td colspan="4">Tổng tiền</td>
										<td>${sumPrice} VNĐ</td>
										<td ><a style="font-weight: bold;color: red" href="${pageContext.servletContext.contextPath}/user/mycart/removeAll.htm">Xóa toàn bộ</a></td>
									</tr>
									<tr>
										<td colspan="5"></td>
										<td><a style="font-weight: bold;color: blue" href="${pageContext.servletContext.contextPath}/user/mycart/buy.htm">Đặt hàng</a></td>
									</tr>
									<% i=1; %>
								</tbody>
							</table>
							
					</div>
						  </c:if>
						  <div class="panel-heading">
						    <a href = "${pageContext.servletContext.contextPath}/user/listBill.htm" class="panel-title" style="font-weight: bold;color: blue">Lịch sử mua hàng</a>
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