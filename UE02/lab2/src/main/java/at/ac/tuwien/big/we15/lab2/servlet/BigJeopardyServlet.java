package at.ac.tuwien.big.we15.lab2.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import at.ac.tuwien.big.we15.lab2.api.Category;
import at.ac.tuwien.big.we15.lab2.api.JeopardyFactory;
import at.ac.tuwien.big.we15.lab2.api.Question;
import at.ac.tuwien.big.we15.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we15.lab2.api.impl.Round;
import at.ac.tuwien.big.we15.lab2.api.impl.ServletJeopardyFactory;
import at.ac.tuwien.big.we15.lab2.api.impl.User;

/**
 * Servlet implementation class BigJeopardyServlet
 */
public class BigJeopardyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static List<Category> categories = null;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void init() {
		ServletContext servletContext = getServletContext();
		JeopardyFactory factory = new ServletJeopardyFactory(servletContext);
		QuestionDataProvider provider = factory.createQuestionDataProvider();
		categories = provider.getCategoryData();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("answer_submit") != null){
			boolean right = false;
			Question q = null;
			Round rnd = null;
			String jsp = "/jeopardy.jsp";
			
			String[] answers = request.getParameterValues("answers");
			HttpSession session = request.getSession(true);
			
			q = (Question) session.getAttribute("question");
			if(q.trueAnswer(answers)){
				((User) session.getAttribute("user1")).setSum(q.getValue());
			
			}
			
			rnd = (Round) session.getAttribute("round");
			if(!rnd.setRound()){
				jsp= "/winner.jsp";
			}
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jsp);
			dispatcher.forward(request, response);
		
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("login") != null) {
			HttpSession session = request.getSession(true);
			User user[] = {new User(), new User()};
			user[0].setUsername(request.getParameter("username"));
			user[1].setUsername("Deadpool");
			for(int i=1;i<=2;i++){
				session.setAttribute("user"+i, user[i-1]);
			}

			
			session.setAttribute("round", new Round());
			session.setAttribute("categories", categories);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jeopardy.jsp");
			dispatcher.forward(request, response);
		}
		
		if(request.getParameter("question_submit") != null) {
			HttpSession session = request.getSession(true);
			int id = Integer.parseInt(request.getParameter("question_selection"));
			
			Question question = null;
			for(Category c : categories) {
				for(Question q : c.getQuestions()) {
					if(q.getId() == id) {
						question = q;
					}
				}
			}
			
			session.setAttribute("question", question);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/question.jsp");
			dispatcher.forward(request, response);
		}
		
	}

}
