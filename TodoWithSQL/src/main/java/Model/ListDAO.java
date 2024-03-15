package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ListDAO {
	String url = "jdbc:mysql://localhost/todo";
	String user = "root";
	String password = "password";
	Connection conection = null;

	public void conect() throws Exception {
		// おまじない
		Class.forName("com.mysql.cj.jdbc.Driver");
		// ①DBに接続
		conection = DriverManager.getConnection(url, user, password);
	}

	public ArrayList<HashMap<String, String>> select(String dateSort, String priorSort) throws Exception {
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = null;

		if (dateSort != null || priorSort != null) {
			if (Objects.equals(priorSort, "upSort")) {
				sql = "SELECT * FROM posts where del_flag = 0 ORDER BY prior ASC";
			} else if (Objects.equals(priorSort, "downSort")) {
				sql = "SELECT * FROM posts where del_flag = 0 ORDER BY prior DESC";
			} else if (Objects.equals(dateSort, "upSort")) {
				sql = "SELECT * FROM posts where del_flag = 0 ORDER BY create_time DESC";
			} else {
				sql = "SELECT * FROM posts where del_flag = 0 ORDER BY create_time ASC";
			}
		} else {
			sql = "SELECT * FROM posts where del_flag = 0";
		}

		// DB接続メソッド呼び出し
		conect();
		// ステートメント生成
		statement = conection.prepareStatement(sql);
		// SQL実行
		results = statement.executeQuery();
		// 取得結果を格納

		ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();
		while (results.next()) {
			HashMap<String, String> columns = new HashMap<String, String>();

			String id = results.getString("id");
			columns.put("id", id);

			String task_name = results.getString("title");
			columns.put("task_name", task_name);

			String content = results.getString("content");
			columns.put("content", content);

			String prior = results.getString("prior");
			columns.put("prior", prior);
			rows.add(columns);
		}
		return rows;
	}

	public void insert(String task_name, String content, String prior, String action, String task_id) throws Exception {

		PreparedStatement statement = null;
		String sql = "INSERT INTO posts (title,content,create_time,delete_time,prior) VALUES (?,?,?,?,?)";
		
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		String currentTimestampToString = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(currentTimestamp);
		// DB接続メソッド呼び出し
		conect();
		// ステートメント生成
		statement = conection.prepareStatement(sql);
		
		statement.setString(1, task_name);
		statement.setString(2, content);
		statement.setString(3, currentTimestampToString);
		statement.setString(4, currentTimestampToString);
		statement.setString(5, prior);
		statement.executeUpdate();
	}

	public void delete(String task_id) throws Exception{

		PreparedStatement statement = null;
		
		String sql = "UPDATE posts SET del_flag=1 WHERE id=?";

		conect();
		// ステートメント生成
		statement = conection.prepareStatement(sql);
		
		statement.setString(1, task_id);
		// SQL実行
		statement.executeQuery();
	}
}
