<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>我的购物车</title>
<jsp:include page="index.jsp" flush="true" />

<style type="text/css">
#tdCenter {
	vertical-align: middle;
}
</style>
</head>
<body>

	<div class="container-fluid">
		<!-- 容器 -->
		<div class="row-fluid">
			<!-- row -->
			<div class="span10 offset2">
				<!-- col -->
				<table class="table table-hover">
					<thead>
						<tr>
							<th id="tdCenter"><input type="checkbox">全选</th>
							<th id="tdCenter">商品名</th>
							<th id="tdCenter">单价</th>
							<th id="tdCenter">数量</th>
							<th id="tdCenter">金额</th>
							<th id="tdCenter">操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td id="tdCenter"><input type="checkbox"> <img
								class="img-thumbnail"
								src="http://uploadv3.anitoys.com/anitoys/images/test/20190530/k867sby0s4eudifq9dv0wg3rl5i22fk9.jpg"
								width="100" height="100" /></td>
							<td id="tdCenter">粘土人</td>
							<td id="tdCenter">999</td>
							<td id="tdCenter"><input type="number" min="1" step="1"
								style="width: 60px;"></td>
							<td id="tdCenter">999</td>
							<td id="tdCenter"><a class="btn btn-success" href="#">收藏</a>
								<a class="btn btn-danger" href="#">删除</a></td>
						</tr>
						<tr class="success">
							<td id="tdCenter"><input type="checkbox">全选</td>
							<td></td>
							<td></td>
							<td id="tdCenter">已选n件商品</td>
							<td id="tdCenter">总计n元</td>
							<td id="tdCenter"><a class="btn btn-success" href="#">去结算</a></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- col-END -->
		</div>
		<!-- row-END -->
	</div>
	<!-- 容器END -->
</body>
</html>