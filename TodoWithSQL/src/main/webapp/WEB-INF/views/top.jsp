<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク管理へ、ようこそ</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script> 
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
		<button type="submit" value="upSort" class="btn" name="dateSort">昇順</button>
		<button type="submit" value="downSort" class="btn" name="dateSort">降順</button>
	</form>
	<label>優先度順</label>
	<form action="todo" method="GET">
		<button type="submit" value="upSort" class="btn" name="priorSort">昇順</button>
		<button type="submit" value="downSort" class="btn" name="priorSort">降順</button>
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
			<td><dd id=<%=columns.get("id")%>><%=columns.get("task_name")%></dd></td>
			<td><%=columns.get("content")%></td>
			<td><%=columns.get("prior")%></td>
			<td><button type="submit" value="delete" class="btn"　name="action">削除</button></td> 
			<input type="hidden" name="task_id"　value=<%=columns.get("id")%>>
			<td><button type="submit" value="update" class="btn"　name="action">更新</button></td> 
			<input type="hidden" name="task_id"　value=<%=columns.get("id")%>>
			</form>
		</tr>
		<%}%>
	</table>
</body>
<script>
jQuery(function($){
    $('dd').click(function(){
        //classでonを持っているかチェック
        if(!$(this).hasClass('on')){
            //編集可能時はclassでonをつける
            $(this).addClass('on');
            var txt = $(this).text();
            //テキストをinputのvalueに入れてで置き換え
            $(this).html('<input type="text" value="'+txt+'" />');
            //同時にinputにフォーカスをする
            $('dd > input').focus().blur(function(){
                var inputVal = $(this).val();
                //もし空欄だったら空欄にする前の内容に戻す
                if(inputVal===''){
                    inputVal = this.defaultValue;
                };
                var id = $(this).prop("id");
                //編集が終わったらtextで置き換える
                $(this).parent().removeClass('on').text(inputVal);
                
                //$(this).parent().removeClass('on').getElementById("").setAttribute("value",inputVal); 
            });
        };
    });
});
</script>


</html>