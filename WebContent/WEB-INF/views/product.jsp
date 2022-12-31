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
				<div class="panel panel-info " style="margin-bottom: 15px">
						  <div class="panel-heading">
						    <h3 class="panel-title">Xem chi tiết sản phẩm</h3>
						  </div>
						  <div class="panel-body">
					  		<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 text-center">
				                <a href="${pageContext.servletContext.contextPath}/resources/upload/product/${batch.photo }"" class="jqzoom" rel="gal1" title="triumph">
						            <img src="${pageContext.servletContext.contextPath}/resources/upload/product/${batch.photo }" alt="" style="width:100%">
						        </a>
						  	</div>
						  	<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
						  		<h1 style="font-size: 25px;text-transform:uppercase;color: red;font-weight:bold;">${batch.batchName}</h1>
						  		<p>${batch.describe}</p>
							<c:choose>
								<c:when test="${batch.discount!=0}">
									<p>Giá cũ: <strong><del>${batch.priceToString()} VNĐ</del></strong></p>
						  		<p>Giá khuyến mại: <span style="font-weight: bold;color: green">${batch.priceDiscountToString()} VNĐ</span></p>
								</c:when>
								<c:otherwise>
									<p>Giá: <span style="font-weight: bold;color: green">${batch.priceDiscountToString()} VNĐ</span></p>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${batch.status==1}">
									<a href="${pageContext.servletContext.contextPath}/user/mycart/addCart/${batch.batchNameToURL()}.htm"><button class='btn btn-info'>
											<span class="glyphicon glyphicon-shopping-cart"
												aria-hidden="true"></span> Thêm giỏ hàng
										</button></a>
								</c:when>
								<c:when test="${batch.status==2}">
									<p><span style="font-weight: bold;color: green">HẾT HÀNG</span></p>
								</c:when>
								<c:otherwise>
									<p><span style="font-weight: bold;color: red">NGỪNG KINH DOANH</span></p>
								</c:otherwise>
							</c:choose>
							
						  		
						  	</div>
						  	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center">
						  		<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
						  			<img src="<c:url value="/resources/upload/icon/services.png"/>" alt="">
						  			<p style="color:red">Phục vụ chu đáo</p>
						  		</div>
						  		<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
						  			<img src="<c:url value="/resources/upload/icon/ship.png"/>" alt="">
						  			<p style="color:red">Trao hàng đúng hẹn</p>
						  		</div>
						  		<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
						  			<img src="<c:url value="/resources/upload/icon/services.png"/>" alt="">
						  			<p style="color:red">Đổi hàng trong 24h</p>
						  		</div>
						  	</div>
						  </div>
						</div>
						
					<div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
						<h1 style="font-size: 25px;text-transform:uppercase;color: red;font-weight:bold;">Recommendation system</h1>
							<div class="product_item" style="height: 450px">
								<p>
									<a href="${pageContext.servletContext.contextPath}/product/${batch_recommendation.batchNameToURL()}.htm" class="product_name">${batch_recommendation.batchName}</a>
								</p>
								<div class="product-image">
									<a href="${pageContext.servletContext.contextPath}/product/${batch_recommendation.batchNameToURL()}.htm"><img
										src="${pageContext.servletContext.contextPath}/resources/upload/product/${batch_recommendation.photo}"
										alt="" class=""></a>
								</div>
								<p>
									
									<c:choose>
										<c:when test="${batch_recommendation.discount!=0}">
											<span class='price text-right'>${batch_recommendation.priceDiscountToString()} VNĐ</span>
				   										<del class="product-discount">${batch.priceToString()} VNĐ</del>
													</c:when>
										<c:otherwise>
											<span class='price text-right'>${batch.priceDiscountToString()} VNĐ</span>
													</c:otherwise>
									</c:choose>
									
								</p>
								<c:choose>
									<c:when test="${batch_recommendation.status==1}">
										<a href="${pageContext.servletContext.contextPath}/user/mycart/addCartRC/${batch_recommendation.batchNameToURL()}.htm"><button class='btn btn-info'>
												<span class="glyphicon glyphicon-shopping-cart"
													aria-hidden="true"></span> Thêm giỏ hàng
											</button></a>
									</c:when>
									<c:when test="${batch_recommendation.status==2}">
										<p><span style="font-weight: bold;color: green">HẾT HÀNG</span></p>
									</c:when>
									<c:otherwise>
										<p><span style="font-weight: bold;color: red">NGỪNG KINH DOANH</span></p>
									</c:otherwise>
								</c:choose>
								
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
						thoại: 099999999<br> 
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