package groupware.holiday.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import groupware.beans.HolidayDao;
import groupware.beans.HolidayDto;
import groupware.beans.employeesDao;

@WebServlet(urlPatterns="/holiday/holidayInsert.gw")
public class HolidayInsertServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			
			req.setCharacterEncoding("UTF-8");
			
			//전달
			HolidayDto holidayDto = new HolidayDto();
			holidayDto.setHolType(req.getParameter("holidayType"));
			holidayDto.setHolStart(req.getParameter("holidayStart"));
			holidayDto.setHolContent(req.getParameter("holidayContent"));
			holidayDto.setHolEnd(req.getParameter("holidayEnd"));
			
			String empNo = (String)req.getSession().getAttribute("id");
			holidayDto.setEmpNo(empNo);

			HolidayDao holidayDao = new HolidayDao();
			
			int holNo = holidayDao.getSequence();//게시글번호(DB시퀀스)
			holidayDto.setHolNo(holNo);
			holidayDao.insert(holidayDto);
			
			int holCount = holidayDao.count(empNo,holNo); //한번 신청한 휴가 일수 가져오기
			employeesDao employeesDao = new employeesDao();
			
			if(employeesDao.holidayMinus(empNo, holCount)) { //제대로 업데이트 됐다면
				resp.sendRedirect("holidayList.jsp");
			}	
			
		}
		catch(Exception e) {
			e.printStackTrace();
			resp.sendError(500);
		}
	}

}
