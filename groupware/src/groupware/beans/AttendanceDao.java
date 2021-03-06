package groupware.beans;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class AttendanceDao {
	
	public static final String USERNAME = "kh75";
	public static final String PASSWORD = "kh75";
	
	// 출근
	public void attend(AttendanceDto attendanceDto) throws Exception{
		Connection con = jdbcUtils.getConnection();
		
		String sql ="insert into attendance values(to_char(sysdate, 'yyyy-mm-dd'),?, sysdate, null, 0, 0)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,attendanceDto.getEmpNo());
		
		ps.execute();
		con.close();		
	}
	
	// 퇴근
	public boolean leave(AttendanceDto attendanceDto) throws Exception{
		Connection con = jdbcUtils.getConnection();
		
		String sql="update attendance set att_leave=sysdate " 
				+ "where emp_no=? and att_date = to_char(sysdate, 'yyyy-mm-dd')";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,attendanceDto.getEmpNo());
		
		int count = ps.executeUpdate();
		
		
		con.close();
		return count>0;
	}
	
	
	//총 근무시간 계산
	public boolean totaltime(String empNo) throws Exception{
		Connection con = jdbcUtils.getConnection();
		
		//총 근무시간(원래 근무시간 + 추가 근무시간) 계산
		String sql="update attendance set att_totaltime = floor((att_leave-att_attend)*24*60) "
				+ "where emp_no = ? and att_date = to_char(sysdate, 'yyyy-mm-dd')";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,empNo);
		
		int count = ps.executeUpdate();
		
		con.close();
		return count>0;
	}
	//총 근무시간 계산22
	public boolean totaltime(String empNo, String attDate) throws Exception{
		Connection con = jdbcUtils.getConnection();
		
		//총 근무시간(원래 근무시간 + 추가 근무시간) 계산
		String sql="update attendance set att_totaltime = floor((att_leave-att_attend)*24*60) "
				+ "where emp_no = ? and att_date = to_date(?, 'yyyy-mm-dd')";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,empNo);
		ps.setString(2,attDate);
		
		int count = ps.executeUpdate();
		
		con.close();
		return count>0;
	}
	
	//추가 근무 시간 계산 수정
	public boolean overtime(String empNo) throws Exception{
		Connection con = jdbcUtils.getConnection();

		//근무 시간 : 8시간으로 설정
		//추가근무시간 : 총 근무시간 - 8시간
		String sql = "update attendance set att_overtime = GREATEST((att_totaltime/60)-8, 0)"
				+ "where emp_no=? and att_date = to_char(sysdate, 'yyyy-mm-dd')";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, empNo);
		
		int count = ps.executeUpdate();
		
		con.close();
		return count>0;
	}
	
	//추가 근무 시간 계산 수정 222222
	public boolean overtime(String empNo, String attDate) throws Exception{
		Connection con = jdbcUtils.getConnection();

		//근무 시간 : 8시간으로 설정
		//추가근무시간 : 총 근무시간 - 8시간
		String sql = "update attendance set att_overtime = GREATEST((att_totaltime/60)-8, 0)"
				+ "where emp_no=? and att_date = to_date(?, 'yyyy-mm-dd')";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, empNo);
		ps.setString(2,attDate);
		
		int count = ps.executeUpdate();
		
		con.close();
		return count>0;
	}
	
	//한달동안의 추가 근무시간 값 가져오기
	public int getOvertime(String empNo,String year,String month) throws Exception{
		Connection con = jdbcUtils.getConnection();
		
		String sql ="select sum(att_overtime) sumovertime "
				+ "from attendance where emp_no= ? and "
				+ "instr(att_date,'#1-#2')>0";
		
		sql = sql.replaceAll("#1", year);
		sql = sql.replaceAll("#2", month);
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,empNo);
		
		ResultSet rs = ps.executeQuery();
		int sumovertime = 0;
		
		if(rs.next()) {
			sumovertime= rs.getInt("sumovertime");
		}
		
		con.close();
		return sumovertime;
	}
	
	// 이번달 추가 근무시간 값 가져오기 -> 관리자
	public int getOvertime(String empNo) throws Exception{
		Connection con = jdbcUtils.getConnection();
		
		String sql ="SELECT sum(att_overtime) as sumovertime FROM attendance "
				+ "where emp_no=? and "
				+ "att_date between TO_CHAR(TRUNC(sysdate,'MM'),'YYYY-MM-DD') and "
				+ "to_char(LAST_DAY(SYSDATE),'yyyy-mm-dd')";
		
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,empNo);
		
		ResultSet rs = ps.executeQuery();
		
		int sumovertime = 0;
		
		if(rs.next()) {
			sumovertime= rs.getInt("sumovertime");
		}
		
		con.close();
		
		return sumovertime;
	}
	

	
	// 근태목록 보기 
	public List<AttendanceDto> list(String empNo,int startRow, int endRow) throws Exception{
		Connection con = jdbcUtils.getConnection();
		
		String sql ="select * from("
						+ "select rownum rn, TMP.* from("
							+"select"
								+ " A.att_date,A.emp_no,to_char(A.att_attend,'HH24:mi:ss') as att_attend,"
								+ "to_char(A.att_leave,'HH24:mi:ss') as att_leave, A.att_totaltime ,"
								+ " A.att_overtime, E.emp_name"
							+ " from attendance A inner join employees E "
							+ "on E.emp_no = A.emp_no and A.emp_no=? order by att_date desc"
						+")TMP"
					+") where rn between ? and ?";

			PreparedStatement ps =con.prepareStatement(sql);
			ps.setString(1,empNo);
			ps.setInt(2, startRow);
			ps.setInt(3, endRow);
			ResultSet rs = ps.executeQuery();
			
			List<AttendanceDto> attendanceList = new ArrayList<>();
			
			while(rs.next()) {
				AttendanceDto attendanceDto = new AttendanceDto();
				attendanceDto.setAttDate(rs.getString("att_date"));
				attendanceDto.setEmpNo(rs.getString("emp_no"));
				attendanceDto.setEmpName(rs.getString("emp_name"));
				attendanceDto.setAttAttend(rs.getString("att_attend"));
				attendanceDto.setAttLeave(rs.getString("att_leave"));
				attendanceDto.setAttTotaltime(rs.getFloat("att_totaltime"));
				attendanceDto.setAttOvertime(rs.getInt("att_overtime"));
				
				attendanceList.add(attendanceDto);
			}
			con.close();
			
			return attendanceList;
		}
	
	// 근태목록 보기 (관리자)
	public List<AttendanceDto> list(int startRow, int endRow) throws Exception{
		Connection con = jdbcUtils.getConnection();
		
		String sql ="select * from("
						+ "select rownum rn, TMP.* from("
							+"select"
								+ " A.att_date,A.emp_no,to_char(A.att_attend,'HH24:mi:ss') as att_attend,"
								+ "to_char(A.att_leave,'HH24:mi:ss') as att_leave, A.att_totaltime ,"
								+ " A.att_overtime, E.emp_name"
							+ " from attendance A inner join employees E "
							+ "on E.emp_no = A.emp_no order by att_date desc"
						+")TMP"
					+") where rn between ? and ?";

			PreparedStatement ps =con.prepareStatement(sql);
			ps.setInt(1, startRow);
			ps.setInt(2, endRow);
			ResultSet rs = ps.executeQuery();
			
			List<AttendanceDto> attendanceList = new ArrayList<>();
			
			while(rs.next()) {
				AttendanceDto attendanceDto = new AttendanceDto();
				attendanceDto.setAttDate(rs.getString("att_date"));
				attendanceDto.setEmpNo(rs.getString("emp_no"));
				attendanceDto.setEmpName(rs.getString("emp_name"));
				attendanceDto.setAttAttend(rs.getString("att_attend"));
				attendanceDto.setAttLeave(rs.getString("att_leave"));
				attendanceDto.setAttTotaltime(rs.getFloat("att_totaltime"));
				attendanceDto.setAttOvertime(rs.getInt("att_overtime"));
				
				attendanceList.add(attendanceDto);
			}
			con.close();
			
			return attendanceList;
		}
	
	
	// 근태 내역 상세보기 
	public AttendanceDto get(String empNo, String attDate) throws Exception{
		Connection con = jdbcUtils.getConnection();
		
		String sql = "select"
							+ " A.att_date,A.emp_no,to_char(A.att_attend,'HH24:mi:ss') as att_attend, "
							+ "to_char(A.att_leave,'HH24:mi:ss') as att_leave, A.att_totaltime ,"
							+ " A.att_overtime, E.emp_name "
						+ "from attendance A inner join employees E "
				+ "on E.emp_no = A.emp_no and A.emp_no=? and A.att_date=?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, empNo);
		ps.setString(2, attDate);
		ResultSet rs = ps.executeQuery();
		
		AttendanceDto attendanceDto;
		if(rs.next()) {
			attendanceDto = new AttendanceDto();
			attendanceDto.setAttDate(rs.getString("att_date"));
			attendanceDto.setEmpName(rs.getString("emp_name"));
			attendanceDto.setEmpNo(rs.getString("emp_no"));
			attendanceDto.setAttAttend(rs.getString("att_attend"));
			attendanceDto.setAttLeave(rs.getString("att_leave"));
			attendanceDto.setAttTotaltime(rs.getFloat("att_totaltime"));
			attendanceDto.setAttOvertime(rs.getInt("att_overtime"));				
		}
		else {
			attendanceDto = null;
		}
	
		con.close();
		return attendanceDto;
	}
	
	//출퇴근 수정 -> 관리자
	public boolean edit(String empNo, String attDate, String attend, String leave) throws Exception{
		Connection con = jdbcUtils.getConnection();
		
		String sql = "update attendance set att_attend=to_date(?,'hh24:mi:ss'), "
						+ "att_leave=to_date(?,'hh24:mi:ss') "
						+ "where att_date=to_date(?,'yyyy-mm-dd') and emp_no=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,attend);
		ps.setString(2,leave);
		ps.setString(3,attDate);
		ps.setString(4,empNo);
		
		int count = ps.executeUpdate();
		
		con.close();
		return count>0;
	}
	
	//출퇴근 삭제 -> 관리자
	public boolean delete(String empNo, String attDate) throws Exception{
		Connection con = jdbcUtils.getConnection();
		String sql = "delete attendance where emp_no=? and att_date=to_date(?,'yyyy-mm-dd')";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, empNo);
		ps.setString(2, attDate);
		
		int count = ps.executeUpdate();
		
		con.close();
		return count>0;
	}	
	
	//페이지블럭 계산을 위한 카운트 기능(목록/검색)
	public int getCount(String empNo) throws Exception {
		Connection con = jdbcUtils.getConnection();
		String sql = "select count(*) from attendance where emp_no=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, empNo);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		
		con.close();
		
		return count;
	}
	
	//페이지블럭 계산을 위한 카운트 기능(목록/검색) -> 관리자
	public int getCount() throws Exception {
		Connection con = jdbcUtils.getConnection();
		String sql = "select count(*) from attendance";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		
		con.close();
		
		return count;
	}
	
	//이번달에 근무한 내역(있는지) -> 일괄급여
	public int isAttend(String empNo,String attDate) throws Exception {
		Connection con = jdbcUtils.getConnection();
		
		String sql = "select count(*) from attendance where emp_no = ? and instr(att_date,?)>0";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, empNo);
		ps.setString(2, attDate);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int attendCount = rs.getInt(1);
		
		con.close();
		return attendCount;
	}

}
