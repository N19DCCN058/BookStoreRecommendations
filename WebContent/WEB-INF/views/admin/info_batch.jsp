<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Dashboard</title>

<link href="<c:url value = "/resources/css/bootstrap.min.css"/>"
	rel="stylesheet">
<link href="<c:url value = "/resources/css/datepicker3.css"/>"
	rel="stylesheet">
<link href="<c:url value = "/resources/css/styles.css"/>"
	rel="stylesheet">

<script src="<c:url value="/resources/js/lumino.glyphs.js"/>"></script>
</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#sidebar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand"
					href="${pageContext.servletContext.contextPath}/admin/index.htm"><span>BOOKSTORE</span>Admin</a>
				<ul class="user-menu">
					<li class="dropdown pull-right"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"><svg
								class="glyph stroked male-user">
								<use xlink:href="#stroked-male-user"></use></svg> ${fullname} <span
							class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a
								href="${pageContext.servletContext.contextPath}/admin/profile.htm"><svg
										class="glyph stroked male-user">
										<use xlink:href="#stroked-male-user"></use></svg> Profile</a></li>
							<li><a
								href="${pageContext.servletContext.contextPath}/admin/editprofile.htm"><svg
										class="glyph stroked gear">
										<use xlink:href="#stroked-gear"></use></svg> Settings</a></li>
							<li><a
								href="${pageContext.servletContext.contextPath}/dangxuat.htm"><svg
										class="glyph stroked cancel">
										<use xlink:href="#stroked-cancel"></use></svg> Logout</a></li>
						</ul></li>
				</ul>
			</div>

		</div>
		<!-- /.container-fluid -->
	</nav>

	<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
		<form role="search">
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Search">
			</div>
		</form>
		<ul class="nav menu">
			<li><a
				href="${pageContext.servletContext.contextPath}/admin/index.htm"><svg
						class="glyph stroked dashboard-dial">
						<use xlink:href="#stroked-dashboard-dial"></use></svg> Dashboard</a></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/admin/listbill.htm"><svg
						class="glyph stroked bag">
						<use xlink:href="#stroked-bag"></use></svg> Order List</a></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/admin/listuser.htm"><svg
						class="glyph stroked male user ">
						<use xlink:href="#stroked-male-user" /></svg> User List</a></li>
			<li class="active"><a
				href="${pageContext.servletContext.contextPath}/admin/listproduct.htm"><svg
						class="glyph stroked mobile device">
						<use xlink:href="#stroked-mobile-device" /></svg> Product List</a></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/admin/newproduct.htm"><svg
						class="glyph stroked pencil">
						<use xlink:href="#stroked-pencil"></use></svg> New Batch of Products</a></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/admin/newaccount.htm"><svg
						class="glyph stroked app-window">
						<use xlink:href="#stroked-app-window"></use></svg> New Account Employee</a></li>
			<li class="parent "><a
				href="${pageContext.servletContext.contextPath}/admin/profile.htm">
					<span data-toggle="collapse" href="#sub-item-1"><svg
							class="glyph stroked chevron-down">
							<use xlink:href="#stroked-chevron-down"></use></svg></span> ${fullname }
			</a>
				<ul class="children collapse" id="sub-item-1">

					<li><a class=""
						href="${pageContext.servletContext.contextPath}/admin/editprofile.htm">
							<svg class="glyph stroked chevron-right">
								<use xlink:href="#stroked-chevron-right"></use></svg> Setting
					</a></li>
					<li><a class=""
						href="${pageContext.servletContext.contextPath}/dangxuat.htm">
							<svg class="glyph stroked chevron-right">
								<use xlink:href="#stroked-chevron-right"></use></svg> Logout
					</a></li>
				</ul></li>
			<li role="presentation" class="divider"></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/dangxuat.htm"><svg
						class="glyph stroked male-user">
						<use xlink:href="#stroked-male-user"></use></svg> Đăng Xuất</a></li>
		</ul>
		<div class="attribution">
			Template by <a
				href="http://www.medialoot.com/item/lumino-admin-bootstrap-template/">Medialoot</a><br />
			<a href="http://www.glyphs.co" style="color: #333;">Icons by
				Glyphs</a>
		</div>
	</div>
	<!--/.sidebar-->

	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="row">
			<ol class="breadcrumb">
				<li><a
					href="${pageContext.servletContext.contextPath}/admin/index.htm"><svg
							class="glyph stroked home">
							<use xlink:href="#stroked-home"></use></svg></a></li>
				<li class="active">Information Product</li>
			</ol>
		</div>
		<!--/.row-->
		
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
									<p><span style="font-weight: bold;color: green">Còn hàng: ${batch.amount - batch.sold } Chiếc</span></p>
								</c:when>
								<c:when test="${batch.status==2}">
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
		</div>
	</div>
	<!--/.main-->



	<script src="<c:url value = "/resources/js/jquery-1.11.1.min.js"/>"></script>
	<script src="<c:url value = "/resources/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value = "/resources/js/chart.min.js"/>"></script>
	<script src="<c:url value = "/resources/js/chart-data.js"/>"></script>
	<script src="<c:url value = "/resources/js/easypiechart.js"/>"></script>
	<script src="<c:url value = "/resources/js/easypiechart-data.js"/>"></script>
	<script src="<c:url value = "/resources/js/bootstrap-datepicker.js"/>"></script>
	<script>
		$('#calendar').datepicker({});

		!function($) {
			$(document)
					.on(
							"click",
							"ul.nav li.parent > a > span.icon",
							function() {
								$(this).find('em:first').toggleClass(
										"glyphicon-minus");
							});
			$(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
		}(window.jQuery);

		$(window).on('resize', function() {
			if ($(window).width() > 768)
				$('#sidebar-collapse').collapse('show')
		})
		$(window).on('resize', function() {
			if ($(window).width() <= 767)
				$('#sidebar-collapse').collapse('hide')
		})
	</script>
</body>

</html>
