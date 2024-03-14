<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク管理へ、ようこそ</title>
</head>
<body>
	<form action="todo" method="POST">
		<label>タスク名：</label><input type="text" name="task_name" value=""
			required="required"> <label>タスク内容：</label><input type="text"
			name="content" value="" required="required"> <label>優先度：</label>
		<select name="prior" required="required">
			<option value="1">高</option>
			<option value="2">中</option>
			<option value="3">低</option>
		</select>
		</div>
		<button type="submit" value="save" class="btn" name="action">保存</button>
	</form>
	<label>時間順</label>
	<form action="todo" method="GET">
		<button type="submit" value="upSort" class="btn" name="sortType">昇順</button>
		<button type="submit" value="downSort" class="btn" name="sortType">降順</button>
	</form>
	<label>優先度順</label>
	<form action="todo" method="GET">
		<button type="submit" value="upSort" class="btn" name="priorType">昇順</button>
		<button type="submit" value="downSort" class="btn" name="priorType">降順</button>
	</form>
	<%
	ArrayList<HashMap<String, String>> rows = (ArrayList<HashMap<String, String>>) request.getAttribute("rows");
	%>
	<table border="1">
		<tr>
			<th>タスク名</th>
			<th>タスク内容</th>
			<th>重要度</th>
		</tr>
		<%
		for (HashMap<String, String> columns : rows) {
		%>
		<tr>
		<form action="todo" method="POST">
			<td><%=columns.get("task_name")%></td>
			<td><%=columns.get("content")%></td>
			<td><%=columns.get("prior")%></td>
			
				<td><button type="submit" value="delete" class="btn"
						name="action">削除</button></td> <input type="hidden" name="task_id"
					value=<%=columns.get("id")%>>
			</form>
		</tr>
		<%}%>
	</table>
</body>
</html>