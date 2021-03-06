package groupware.login.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import groupware.beans.employeesDao;
import groupware.beans.employeesDto;

@WebServlet(urlPatterns = "/login/signUpEdit.kh")
public class SignUpEditServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.getParameter("UTF-8");
			employeesDto empDto = new employeesDto();
			
			empDto.setDepartment(req.getParameter("department"));
			empDto.setPono(Integer.parseInt(req.getParameter("poNo")));
			empDto.setEmpNo(req.getParameter("empNo"));
			empDto.setEmpName(req.getParameter("empName"));
			empDto.setEmpPhone(req.getParameter("empPhone"));
			empDto.setEmail(req.getParameter("email"));
			empDto.setAddress(req.getParameter("address"));
			


			
			
			employeesDao empDao = new employeesDao();
			empDao.edit(empDto);
			
			
			
			
			resp.sendRedirect(req.getContextPath()+"/address/addressDetail.jsp?empNo="+empDto.getEmpNo());
		
			
		}catch(Exception e) {
			e.printStackTrace();
			resp.sendError(500);
			
		}
	}
}
