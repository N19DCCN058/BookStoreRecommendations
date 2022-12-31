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
			<li><a
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
				<li class="active">Edit Batch of Product</li>
			</ol>
		</div>
		<!--/.row-->

		<div>
			<c:choose>
				<c:when test="${status_update_batch==0}">
					<div class="alert alert-danger" role="alert">Chỉnh sửa sản phẩm thất bại</div>
				</c:when>
				<c:when test="${status_update_batch==2}">
					<div class="alert alert-warning" role="alert">Tên sản phẩm đã được đặt tên cho lô sản phẩm khác</div>
				</c:when>
				<c:when test="${status_update_batch==3}">
					<div class="alert alert-warning" role="alert">Các trường sản phẩm không được trống</div>
				</c:when>
				<c:when test="${status_update_batch==1}">
					<div class="alert alert-success" role="alert">Sửa thông tin thành công</div>
				</c:when>
				<c:when test="${status_update_batch_photo==0}">
					<div class="alert alert-warning" role="alert">File không hợp
						lệ</div>
				</c:when>
				<c:otherwise>

				</c:otherwise>
			</c:choose>
		</div>

		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">Thông tin lô sản phẩm</div>
					<div class="panel-body">
						<div class="panel panel-info " style="margin-bottom: 15px">
							<div class="panel-body">
								<form role="form"
									action="${pageContext.servletContext.contextPath}/admin/editbatch/${batch.batchId }.htm"
									enctype="multipart/form-data" method="post">
									<fieldset>
										<div class="form-group">
											<p>Tên sản phẩm</p>
											<input class="form-control" placeholder="Samsung galaxy Y"
												id="batchName" name="batchName" type="text"
												value="${batch.batchName }">
										</div>

										<div class="form-group">
											<p>Danh mục sản phẩm</p>
											<select name="category" class="form-control">
												<c:forEach var="category" items="${categorys }">
													<c:choose>
														<c:when
															test="${category.categoryId==batch.category.categoryId }">
															<option value="${category.categoryId }"
																selected="selected">${category.categoryName }</option>
														</c:when>
														<c:otherwise>
															<option value="${category.categoryId }">${category.categoryName }</option>
														</c:otherwise>
													</c:choose>


												</c:forEach>
											</select>
										</div>

										<div class="form-group">
											<p>Giá sản phẩm</p>
											<input class="form-control" placeholder="10.000.000 VND"
												id="price" name="price" type="number"
												value="${batch.price }" disabled="disabled">
										</div>

										<div class="form-group">
											<p>Số lượng nhập kho</p>
											<input class="form-control" placeholder="100" id="amount"
												name="amount" type="number" value="${batch.amount }" disabled="disabled">
										</div>

										<div class="form-group">
											<p>Hình ảnh sản phẩm</p>
											<input class="form-control" id="photo" name="photo"
												type="file">
										</div>

										<div class="form-group">
											<p>Mô tả sản phẩm</p>
											<textarea class="form-control" rows="3" name="describe"
												id="describe">${batch.describe }</textarea>
										</div>

										<div class="form-group">
											<p>Discount</p>
											<input class="form-control" placeholder="100" id="discount"
												name="discount" type="number" value="${batch.discount }">
										</div>

										<button type="submit" class="btn btn-primary">Edit</button>
										<c:choose>
											<c:when
												test="${batch.status == 2 }">
												<a class="btn btn-error"><span style="font-weight: bold;color: red">Ngừng Kinh Doanh</span></a>
											</c:when>
											<c:when
												test="${batch.status == 0 }">
												<a class="btn btn-error"><span style="font-weight: bold;color: red">Hết Hàng</span></a>
											</c:when>
											<c:otherwise>
												<a
											href="${pageContext.servletContext.contextPath}/admin/disablebatch/${batch.batchId }.htm"
											class="btn btn-warning">Ngưng Kinh Doanh</a>
											</c:otherwise>
										</c:choose>
										
									</fieldset>
								</form>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--/.row-->

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
