<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>XXX网络商城</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"
	type="text/javascript"></script>
</head>

<body>
	<!--
            	描述：菜单栏
	-->
	<div class="container-fluid">
		<div class="col-md-4">
			<img src="${pageContext.request.contextPath}/img/logo2.png" />
		</div>
		<div class="col-md-5">
			<img src="${pageContext.request.contextPath}/img/header.png" />
		</div>
		<div class="col-md-3" style="padding-top: 20px">
			<ol class="list-inline">
				<c:if test="${ empty loginUser}">
					<li><a
						href="${pageContext.request.contextPath}/UserServlet?method=loginUI">登录</a></li>
					<li><a
						href="${pageContext.request.contextPath}/UserServlet?method=registUI">注册</a></li>
				</c:if>

				<c:if test="${not empty loginUser}">
					<li><a href="${pageContext.request.contextPath}/jsp/cart.jsp">欢迎,${loginUser.name}</a></li>
					<li><a
						href="${pageContext.request.contextPath}/UserServlet?method=logout">退出</a></li>
					<li><a href="${pageContext.request.contextPath}/jsp/cart.jsp">购物车</a></li>
					<li><a
						href="${pageContext.request.contextPath}/OrderServlet?method=findMyOrderWithPage&num=1">我的订单</a></li>
				</c:if>

			</ol>
		</div>
	</div>
	<!--
            	描述：导航条
	-->
	<div class="container-fluid">
		<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
						aria-expanded="false">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand"
						href="${pageContext.request.contextPath}/IndexServlet?method=execute">首页</a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav" id="menu">

						<%-- 
							<c:forEach  items="${allCategory}" var="category">
								<li value="${category.cid}"><a href="#">${category.cname}</a></li>
							</c:forEach>
						--%>

					</ul>
					<form class="navbar-form navbar-right" role="search">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Search">
						</div>
						<button type="submit" class="btn btn-default">Submit</button>
					</form>

				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>
		<script type="text/javascript">
			$(function() {
				var url = "${pageContext.request.contextPath}/CategoryServlet";
				$.post(url, {
					"method" : "findAllByAjax"
				}, function(data) {
					console.log(data);
					$.each(data, function(i, n) {
						var url ="${pageContext.request.contextPath}/ProductServlet?method=findProByCidWithPage&num=1&cid="+n.cid;
						$("#menu").append(
								"<li value=''><a href='"+url+"'>" + n.cname+"</a></li>");
					});
				});
			});
		</script>
	</div>

</body>

</html>