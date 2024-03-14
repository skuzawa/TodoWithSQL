package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TodoController
 */
@WebServlet("/todo")
public class AddServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String url = "jdbc:mysql://localhost/todo";
		String user = "root";
		String password = "password";

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "SELECT * FROM posts where del_flag = 0";
		String sortType = request.getParameter("sortType");
		String priorType = request.getParameter("priorType");
		
		if(sortType == null) {
			;
		}else if(sortType.equals("upSort")){
			sql = "SELECT * FROM posts where del_flag = 0 ORDER BY create_time DESC";
		}else if(sortType.equals("downSort")) {
			sql = "SELECT * FROM posts where del_flag = 0 ORDER BY create_time ASC";
		}else{
			;
		}
		
		if(priorType == null) {
			;
		}else if(priorType.equals("upSort")){
			sql = "SELECT * FROM posts where del_flag = 0 ORDER BY prior ASC";
		}else if(priorType.equals("downSort")) {
			sql = "SELECT * FROM posts where del_flag = 0 ORDER BY prior DESC";
		}else{
			;
		}
		
        try (Connection connection = DriverManager.getConnection
        (url,user,password);
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet results = statement.executeQuery()){
            ArrayList<HashMap<String,String>> rows = new ArrayList<HashMap<String,String>>();

            while(results.next()){
                HashMap<String,String>columns = new HashMap<String,String>();

                String id = results.getString("id");
                columns.put("id",id);
                
                String task_name = results.getString("title");
                columns.put("task_name",task_name);

                String content = results.getString("content");
                columns.put("content",content);
                
                String prior = results.getString("prior");
                columns.put("prior",prior);

                rows.add(columns);
            }
            request.setAttribute("rows",rows);
        }catch(Exception e){
            request.setAttribute("message","Exception:"+ e.getMessage());
		}

		String view = "/WEB-INF/views/top.jsp";
		request.getRequestDispatcher(view).forward(request, response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		//todo.jsp→入力値
		String task_name = request.getParameter("task_name");
		String content = request.getParameter("content");
		String prior =  request.getParameter("prior");
		String action = request.getParameter("action");
		String task_id = request.getParameter("task_id");
		
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		String currentTimestampToString = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(currentTimestamp);
		
		String url = "jdbc:mysql://localhost/todo";
		String user = "root";
		String password = "password";

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(action.equals("delete")){
			String sql = "UPDATE posts SET del_flag=1 WHERE id=?";
			Connection con = null;
			PreparedStatement ps = null;
			try {
				con = DriverManager.getConnection(url, user, password);
				ps = con.prepareStatement(sql);
				ps.setString(1, task_id);
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					//⑤リソースを解放
					if (ps != null)
						ps.close();
					if (con != null)
						con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else {
			String sql = "INSERT INTO posts (title,content,create_time,delete_time,prior) VALUES (?,?,?,?,?)";
			Connection con = null;
			PreparedStatement ps = null;
			try {
				con = DriverManager.getConnection(url, user, password);
				ps = con.prepareStatement(sql);
				ps.setString(1, task_name);
				ps.setString(2, content);
				ps.setString(3, currentTimestampToString);
				ps.setString(4, currentTimestampToString);
				ps.setString(5, prior);
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					//⑤リソースを解放
					if (ps != null)
						ps.close();
					if (con != null)
						con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		doGet(request, response);
	}
}
