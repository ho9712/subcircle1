<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
${rows.get(1) } 
<%-- ${hotItems } --%>
	<div class="container-fluid">
		<!-- 容器 -->

		<div class="row-fluid" align="center">
			<!--商城导航栏及搜索栏轮播图  (第一列)-->

			<div class="span2">
				<div id="top">
					<ul class="nav nav-list">
						<li class="active"><a href="#">站点首页</a></li>
						<hr>
						<li><a href="#">迷你论坛</a></li>
						<hr>
						<li><a href="#">周边商城</a></li>
						<hr>
						<li><a href="#">个人空间</a></li>
					</ul>
				</div>
				<div id="bom">
					<img class="img-thumbnail"
						src="http://uploadv3.anitoys.com/anitoys/images/test/20190530/k867sby0s4eudifq9dv0wg3rl5i22fk9.jpg"
						width="250" height="250" />
				</div>
			</div>


			<div class="span10">

				<ul class="nav nav-pills">
					<li><a href="#">我的购物车</a></li>
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

				<div>
					<input class="input-medium search-query" type="text"
						style="width: 50%; height: 20px;" />
					<button class="btn btn-default" contenteditable="true"
						type="submit">查找</button>
				</div>


				<!-- 轮播图 -->
				<div class="carousel slide" id="myCarousel" align="center">
					<ol class="carousel-indicators">
						<li class="active" data-slide-to="0" data-target="#myCarousel"></li>
						<li data-slide-to="1" data-target="#myCarousel"></li>
						<li data-slide-to="2" data-target="#myCarousel"></li>
					</ol>
					<div class="carousel-inner">
						<c:forEach items="${hotItems }" var="ins" varStatus="vs">
							<c:choose>
								<c:when test="${vs.count == 1 }">
									<div class="item active">
										<img src="${ins.kkb105 }" style="width: 400px; height: 400px;" />
										<div class="carousel-caption">
											<h4>${ins.kkb102 }</h4>
											<p>${ins.kkb104 }</p>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="item">
										<img src="${ins.kkb105 }" style="width: 400px; height: 400px;" />
										<div class="carousel-caption">
											<h4>${ins.kkb102 }</h4>
											<p>${ins.kkb104 }</p>
										</div>
									</div>	
								</c:otherwise>
							</c:choose>
						</c:forEach>	
					</div>
					<a data-slide="prev" href="#myCarousel"
						class="left carousel-control">‹</a> <a data-slide="next"
						href="#myCarousel" class="right carousel-control">›</a>
				</div>
				<!-- 轮播图结束 -->


				<!-- 商品展示区 -->
				<c:forEach  begin = "1" step = "1" end = "3" varStatus="i"> 
					<ul class="thumbnails">
						<c:forEach items="${rows }" var="ins" begin = "${(i.count-1)*4 }" step = "1" end = "${(i.count-1)*4+3 }">
							<li class="span3">
								<div class="thumbnail">
									<img src="${ins.kkb105 }" width="300" height="300" />
									<div class="caption">
										<h4>${ins.kkb102 }</h4>
										<p>${ins.kkb104 }</p>
										<label><font color="#ff0000" size="2px"> 售价:</font>${ins.kkb103 }
										</label>
										<p>
											<a class="btn btn-success" href="#">收藏</a> <a
												class="btn btn-warning" href="#">加入购物车</a> <a
												class="btn btn-danger" href="#">立即购买</a>
										</p>
									</div>
								</div>
							</li>
						</c:forEach>
					</ul>
				</c:forEach>

				<div class="pagination">
					<!-- 翻页 -->
					<ul class="pagination">
						<li><a href="#">&laquo;</a></li>
						<li class="active"><a href="#">1</a></li>
						<li class="disabled"><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">5</a></li>
						<li><a href="#">&raquo;</a></li>
					</ul>
					<br> <input class="input-medium search-query" type="text"
						style="width: 20px; height: 20px;"></input>
					<button class="btn btn-success" contenteditable="true"
						type="submit">跳转</button>
				</div>
			</div>
			<!-- span10结束 -->
		</div>
		<!-- row结束 -->
	</div>
	<!-- 容器结束 -->

	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>