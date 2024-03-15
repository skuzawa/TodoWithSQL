package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.ListDAO;

/**
 * Servlet implementation class TodoController
 */
@WebServlet("/todo")
public class AddServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//時系列順に並べ替え（default:null）
		String dateSort = request.getParameter("dateSort");
		//優先度順に並べ替え（default:null）
		String priorSort = request.getParameter("priorSort");

		try {
			ListDAO dao = new ListDAO();
			//データの一覧表示
			request.setAttribute("rows", dao.select(dateSort, priorSort));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// リクエストスコープへのデータ格納（リストデータの格納）
			request.setAttribute("message", "例外が発生しました");
			String view = "WEB-INF/view/error.jsp";
			// 転送オブジェクトを取得
			RequestDispatcher dispatcher = request.getRequestDispatcher(view);
			// 転送
			dispatcher.forward(request, response);
		}
		String view = "/WEB-INF/views/top.jsp";
		request.getRequestDispatcher(view).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
	
		String task_name = request.getParameter("task_name");
		String content = request.getParameter("content");
		String prior = request.getParameter("prior");
		String action = request.getParameter("action");
		String task_id = request.getParameter("task_id");
		
		//削除ボタンか保存ボタンのリクエストか条件分岐
		if (Objects.equals(action,"delete")) {
			try {
				ListDAO dao = new ListDAO();
				dao.delete(task_id);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// リクエストスコープへのデータ格納（リストデータの格納）
				request.setAttribute("message", "例外が発生しました");
				String view = "WEB-INF/view/error.jsp";
				// 転送オブジェクトを取得
				RequestDispatcher dispatcher = request.getRequestDispatcher(view);
				// 転送
				dispatcher.forward(request, response);
			}
		} else {
			//保存ボタンからリクエスト
			try {
				ListDAO dao = new ListDAO();
				dao.insert(task_name, content, prior,action, task_id);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// リクエストスコープへのデータ格納（リストデータの格納）
				request.setAttribute("message", "例外が発生しました");
				String view = "WEB-INF/view/error.jsp";
				// 転送オブジェクトを取得
				RequestDispatcher dispatcher = request.getRequestDispatcher(view);
				// 転送
				dispatcher.forward(request, response);
			}
		}
		doGet(request, response);
	}
}
