<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title>Market</title>
<link href="css/bootstrap.css" rel="stylesheet">
<style type="text/css">
#bom {
	position: fixed;
	left: 0px;
	bottom: 0;
	_position: absolute;
	_top: expression(document.documentElement.clientHeight +  
		 document.documentElement.scrollTop - this.offsetHeight);
}

#top {
	position: fixed;
	top: 15%;
	width: 15%;
	_position: absolute;
	_bottom: expression(document.documentElement.clientHeight +  
		 document.documentElement.scrollTop - this.offsetHeight);
}
</style>
</head>
<body>

	<div class="row-fluid" align="center">
		<!--站点导航栏-商城导航栏 ()-->

		<div class="span2">
			<!-- 站点导航栏 -->
			<div id = "top">
				<ul class="nav nav-list">
					<li><a href="#">站点首页</a></li>
					<hr>
					<li><a href="#">迷你论坛</a></li>
					<hr>
					<li class="active"><a href="#">周边商城</a></li>
					<hr>
					<li><a href="#">个人空间</a></li>
				</ul>
			</div>
			<div id="bom">
				<img class="img-thumbnail"
					src="http://uploadv3.anitoys.com/anitoys/images/test/20190530/k867sby0s4eudifq9dv0wg3rl5i22fk9.jpg"
					width="200" height="200" />
			</div>
		</div>
		<!-- 站点导航栏结束 -->


		<div class="span10">
			<!-- 商城模块导航栏 -->
			<ul class="nav nav-pills">
				<li><a href="#" onclick="ToMyCart()">我的购物车</a></li>
				<li><a href="#">我的订单</a></li>
				<li class="dropdown"><a href="#" data-toggle="dropdown"
					class="dropdown-toggle">求购 <strong class="caret"></strong></a>
					<ul class="dropdown-menu">
						<li><a href="#">求购信息列表</a></li>
						<li><a href="#">我的求购列表</a></li>
						<li><a href="#">我的响应列表</a></li>
						<li><a href="#">待处理列表</a></li>
					</ul></li>
			</ul>
		</div>
		<!-- 商城模块导航栏 结束-->
	</div>
	<!-- 导航栏结束 -->
<script type="text/javascript">

	function ToMyCart() 
	{
		window.location.href="<%=request.getContextPath()%>/Kb04MyCartCenter.html";
	}
	
</script>

</body>