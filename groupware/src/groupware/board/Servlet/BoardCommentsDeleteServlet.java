package groupware.board.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import groupware.beans.BoardCommentsDao;
import groupware.beans.BoardCommentsDto;

@WebServlet(urlPatterns = "/board/comDelete.gw")
public class BoardCommentsDeleteServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setCharacterEncoding("UTF-8"); 
			BoardCommentsDto boardCommentsDto = new BoardCommentsDto();
			boardCommentsDto.setComNo(Integer.parseInt(req.getParameter("comNo")));
			boardCommentsDto.setBoardNo(Integer.parseInt(req.getParameter("boardNo")));
			boardCommentsDto.setEmpNo(req.getParameter("empNo"));
			
			//처리
			BoardCommentsDao boardCommentsDao = new BoardCommentsDao();
			boardCommentsDao.delete(boardCommentsDto);
			
			//출력
			resp.sendRedirect("boardDetail.jsp?boardNo="+boardCommentsDto.getEmpNo());
		}
		catch(Exception e) {
			e.printStackTrace();
			resp.sendError(500);
		}
	}
}
