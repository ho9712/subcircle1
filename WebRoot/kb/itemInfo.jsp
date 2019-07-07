<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>商品${ins.kkb102 }详情</title>
<link href="css/bootstrap.css" rel="stylesheet">
<jsp:include page="index.jsp" flush="true"/>	<!-- 引入导航栏 -->
</head>
<body>
${msg }
	<div class="container-fluid">
		<!-- 容器 -->
		<div class="row-fluid" height="400px">
			<!-- row1 商品展示区-->
			<div class="span3 offset2" align="center">
				<!-- row1左--商品图片区-->
				<img class="img-thumbnail" src="${ins.kkb105 }" width="300"
					height="300" />
			</div>
			<!-- row1左边结束--商品图片区-->

			<div class="span7" align="left">
				<!-- row1右边--商品信息区-->
				<h3>${ins.kkb102 }</h3>
				<table class="table">
					<!-- <thead>
						<tr>
							<th>编号</th>
							<th>产品</th>
							<th>交付时间</th>
							<th>状态</th>
						</tr>
					</thead> -->
					<tbody>
						<tr>
							<td>售价</td>
							<td>${ins.kkb103 }</td>
						</tr>
						<tr class="success">
							<td>商品类型</td>
							<td>${ins.kkb106 }</td>
						</tr>
						<tr class="error">
							<td>关联作品</td>
							<td>${ins.kkb107 }</td>
						</tr>
						<tr class="warning">
							<td>商品描述</td>
							<td>${ins.kkb104 }</td>
						</tr>
						<tr class="info">
							<td>商品库存</td>
							<td>${ins.kkb108 }</td>
						</tr>
					</tbody>
				</table>
				<label> 数量: <input type="number" step="1" min="1" value="1"
							style="width: 60px;" id="kkb402">
				</label>	
				<div class="btn-group btn-group-sm">
					<button type="button" class="btn btn-success"
						onclick="onCollect(${ins.kkb101 })">收藏</button>
					<button type="button" class="btn btn-warning"
							onclick="onAddToCart(${ins.kkb101 })">加入购物车</button>
					<button type="button" class="btn btn-danger">立即购买</button>
				</div>
			</div>
			<!-- row1右边结束--商品信息区-->
		</div>
		<!-- row1结束 -->
		<hr>

		<div class="row-fluid">
			<!-- row2 商品评分评价区-->
			<div class="span10 offset2">
				<!-- row2第一行-->
				<h3>购买者评价:</h3>
				<table class="table">
					<tbody>
						<tr class="error">
							<td>评分</td>
							<td>5</td>
						</tr>
						<c:forEach begin="1" step="1" end="15" varStatus="vs">
							<tr class="info">
								<td>用户${vs.count }</td>
								<td>干得漂亮${vs.count }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<!-- row2 商品评分评价区   结束-->
	</div>
	<!-- 容器结束 -->
<script type="text/javascript">

	//根据商品id加入用户收藏列表
	function onCollect(kkb101)
	{
		window.location.href="<%=request.getContextPath()%>/kb03CollectItem.html?"
					+"kkb101="+kkb101;
		alert("收藏成功");
	}
	
	//根据商品id以及数量加入用户购物车中
	function onAddToCart(kkb101)
	{
		var kkb402 = document.getElementById('kkb402').value
		window.location.href="<%=request.getContextPath()%>/kb04AddToMyCart.html?"
				+"kkb101="+kkb101
				+"&kkb402="+kkb402;
		alert("加入购物车成功");
	}

</script>

<script src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>

</body>
</html>