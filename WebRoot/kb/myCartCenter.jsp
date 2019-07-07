<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>我的购物车</title>
<jsp:include page="index.jsp" flush="true" /> <!-- 引入导航栏 -->
<style type="text/css">
#tdCenter {
	vertical-align: middle;
}
</style>
</head>
<body>
<%-- ${rows } --%>
	<div class="container-fluid">
		<!-- 容器 -->
		<div class="row-fluid">
			<!-- row -->
			<div class="span10 offset2">
				<!-- col -->
				<table class="table table-hover">
					<thead>
						<tr>
							<th id="tdCenter">
								<input type="checkbox" onclick = "onSelectAll(this.checked)"
								 		name = "selectAll">全选
							</th>
							<th id="tdCenter">商品名</th>
							<th id="tdCenter">单价</th>
							<th id="tdCenter">数量</th>
							<th id="tdCenter">金额</th>
							<th id="tdCenter">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${rows }" var="ins" varStatus="vs">
							<tr>
								<td id="tdCenter">
									<input type="checkbox" name = "idlist" 
									onclick="onSelect(this.checked)" value = "${ins.kkb101 }">  
									<img
										class="img-thumbnail" src="${ins.kkb105 }" width="100"
										height="100" />
								</td>
								<td id="tdCenter">${ins.kkb102 }</td>
								<td id="tdCenter">￥${ins.kkb103 }</td>
								<td id="tdCenter">
									<input type="number" min="1" step="1"
										id = "kkb402" style="width: 60px;" value="${ins.kkb402 }"></td>
								<td id="tdCenter">￥${ins.kkb103*ins.kkb402 }</td>
								<td id="tdCenter"><a class="btn btn-success" href="#">收藏</a>
									<a class="btn btn-danger" href="#" 
										onclick="delFromCart(${ins.kkb101 })">移除</a>
								</td>
							</tr>
						</c:forEach>
						<tr class="success">
							<td id="tdCenter">
								<input type="checkbox" onclick = "onSelectAll(this.checked)"
										name = "selectAll">
								全选
							</td>
							<td></td>
							<td></td>
							<td id="tdCenter">已选<span id="itemCount">n</span>件商品</td>
							<td id="tdCenter">总计元</td>
							<td id="tdCenter">
							<button class="btn btn-warning" href="#" disabled="disabled" id = "pay">
								去结算
							</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- col-END -->
		</div>
		<!-- row-END -->
	</div>
	<!-- 容器END -->
	
	<script type="text/javascript">
	
	
	//idlist的checkbox的调用方法
	
	function onSelect(vsstate)
	{
		var selectAllList = document.getElementsByName("selectAll");
		
		//只要有任意一个checkbox状态为false取消全选
		if(isSelectedAll())
		{
			for(var i = 0; i < selectAllList.length ;i++)
	        {
	        	selectAllList[i].checked = true;
	        }
		}
		else
		{
			for(var i = 0; i < selectAllList.length ;i++)
	        {
	        	selectAllList[i].checked = false;
	        }
		}
		
		//引起去结算按钮状态变更
		if(isSelectOne())
		{
			document.getElementById("pay").disabled = false;
		}
		else
		{
			document.getElementById("pay").disabled = true;
		}		
	}
	
	//选中的idlist的复选框的数目
	function checkedCount()
	{
		var count = 0;
		var checklist = document.getElementsByName("idlist");
		for(var i = 0; i < checklist.length ;i++)
        {
        	if(checklist[i].checked == true)
        	{
        		count++;
        	}
        }
		return count
	}
	
	//所有idlist的复选框都被选中返回true
	function isSelectedAll()
	{
		var checklist = document.getElementsByName("idlist");
		 for(var i = 0; i < checklist.length ;i++)
         {
         	if(checklist[i].checked == false)
         	{
         		return false;
         	}
         }
		 return true;
	}
	
	//至少有一个idlist的复选框被选中返回true
	function isSelectOne() 
	{
		var checklist = document.getElementsByName("idlist");
		for(var i = 0; i < checklist.length ;i++)
        {
        	if(checklist[i].checked == true)
        	{
        		return true;
        	}
        }
		 return false;
	}
	
	// 全部选中和取消
	function onSelectAll(vsstate)
	{
		var checklist = document.getElementsByName("idlist");
		var selectAllList = document.getElementsByName("selectAll");
        if(vsstate)
        {
            for(var i = 0; i < checklist.length ;i++)
            {
            	checklist[i].checked = true;
            }
            document.getElementById("pay").disabled = false;
           
            for(var i = 0; i < selectAllList.length ;i++)
            {
            	selectAllList[i].checked = true;
            }

            document.getElementById("pay").disabled = false;
        }
        else
        {
            for(var i = 0; i < checklist.length ;i++)
            {
            	checklist[i].checked = false;
       		}
            
            for(var i = 0; i < selectAllList.length ;i++)
            {
            	selectAllList[i].checked = false;
            }
            
            document.getElementById("pay").disabled = true;        
		}
	}
	
	
	function delFromCart(kkb101)
	{
		var msg = "您确认移出该商品吗";
	    if (confirm(msg)==true)
	    {
	    	window.location.href="<%=request.getContextPath()%>/kb04DelFromMyCart.html?kkb101="+kkb101;
	    	alert("移除成功");
	    }
	}
	
	</script>
</body>
</html>