package groupware.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class employeesDao {

	public static final String USERNAME= "kh75";
	public static final String PASSWORD= "kh75";
	
	public boolean login(employeesDto employeesdto)throws Exception{
		
		Connection con = jdbcUtils.getConnection();
		
		String sql = "select * from employees"
				+ " where emp_no = ? and emp_pw = ?";
				
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, employeesdto.getEmpNo());
		ps.setString(2, employeesdto.getEmpPw());
		
		ResultSet rs = ps.executeQuery();
		
		boolean login;
		
		if(rs.next()) login = true;
		else login = false;
		
		con.close();
		return login;
	}
	
	public void regist(employeesDto employeesdto)throws Exception{
		
		Connection con = jdbcUtils.getConnection();
		
		String sql = "insert into employees values"
				+ " (?,?,?,?,?,?,?,?,?,?)";
				
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, employeesdto.getEmpNo());
		ps.setString(2, employeesdto.getEmpPw());
		ps.setInt(3, employeesdto.getPono());
		ps.setString(4, employeesdto.getEmpName());
		ps.setString(5, employeesdto.getJoinDate());
		ps.setString(6, employeesdto.getEmpPhone());
		ps.setString(7, employeesdto.getEmail());
		ps.setString(8, employeesdto.getAddress());
		ps.setString(9, employeesdto.getDepartment());

		//직급별휴가 일수 설정
		ps.setInt(10, employeesdto.getHolidayCount());
		ps.executeUpdate();
		con.close();
	}
	
	//단일 조회
	public employeesDto loginInfo(String empNo)throws Exception{
		
		Connection con = jdbcUtils.getConnection();
		
		String sql = "select * from employees where emp_no=?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, empNo);
		
		ResultSet rs = ps.executeQuery();
		
		employeesDto employeesdto ;
		if(rs.next()) {
			employeesdto = new employeesDto();
			
			employeesdto.setEmpNo(rs.getString(1));
			employeesdto.setEmpPw(rs.getString(2));
			employeesdto.setPono(rs.getInt(3));
			employeesdto.setEmpName(rs.getString(4));
			employeesdto.setJoinDate(rs.getString(5));
			employeesdto.setEmpPhone(rs.getString(6));
			employeesdto.setEmail(rs.getString(7));
			employeesdto.setAddress(rs.getString(8));
			employeesdto.setHolidayCount(rs.getInt("holiday_count"));
			employeesdto.setDepartment(rs.getString("department"));
			
		}
		else {
			employeesdto=null;
			
		}
		con.close();
		return employeesdto;
	}

	public String position(String empNo)throws Exception{
		Connection con = jdbcUtils.getConnection();
		
		String sql = "select E.emp_no,E.emp_name,P.posi"
				+ " from employees E inner join"
				+ " position P on E.po_no=P.po_no"
				+ " where emp_no=?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, empNo);
		ResultSet rs = ps.executeQuery();
		String posi;
		if(rs.next()) {
		posi = rs.getString("posi");
		}
		else {
			posi=null;
		}
		con.close();
		return posi;
	}
	
	public void loginInfoEdit(employeesDto employeesdto)throws Exception{//회원정보 수정 Dao
		Connection con  = jdbcUtils.getConnection();
		
		String sql = "update employees set"
					+" emp_pw=?,"
					+" emp_name=?,"
					+" emp_phone=?,"
					+" email=?,"
					+" address=?"
					+" where emp_no = ?";//비밀번호 이름 핸드폰 이메일 주소
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, employeesdto.getEmpPw());
		ps.setString(2, employeesdto.getEmpName());
		ps.setString(3, employeesdto.getEmpPhone());
		ps.setString(4, employeesdto.getEmail());
		ps.setString(5, employeesdto.getAddress());
		ps.setString(6, employeesdto.getEmpNo());
		ps.executeUpdate();

		con.close();
	}
	
	//윤준하 
	/// 회원목록 전체 : massageInsert에서 목록 호출때 사용
	public List<employeesDto> list() throws Exception {
		Connection con = jdbcUtils.getConnection();
		
		String sql ="select*from employees";
		PreparedStatement ps =con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		List<employeesDto> list = new ArrayList<>();
		
		
		while(rs.next()) {
		employeesDto empDto = new employeesDto();
		
		empDto.setEmpNo(rs.getString("emp_no"));
		empDto.setEmpPw(rs.getString("emp_pw"));
		empDto.setPono(rs.getInt("po_no"));
		empDto.setEmpName(rs.getString("emp_name"));	
		empDto.setJoinDate(rs.getString("join_date"));
		empDto.setEmpPhone(rs.getString("emp_phone"));
		empDto.setEmail(rs.getString("email"));
		empDto.setAddress(rs.getString("address"));
		empDto.setDepartment(rs.getString("department"));//부서 추가
		list.add(empDto);	
		}
		con.close();
		return list;
		
		
	}
	//윤준하  
	//수신자 번호 가져오는 메소드 : 수신자 이름을 통해 번호를 가져오는 메소드이다. 수신자 목록(list)에서 사용됨
	public employeesDto getReceiver_no (String e2_name) throws Exception{
		Connection con = jdbcUtils.getConnection();
		
		String sql = "select emp_no from employees where emp_name=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, e2_name);
		
		ResultSet rs = ps.executeQuery();
		employeesDto  empDto; 
		
		if(rs.next()) {
			empDto= new employeesDto();

			empDto.setEmpNo(rs.getString("emp_no"));

			
		}else {
			empDto=null;
		}
		con.close();
		return empDto;
	
	}
	
	//노승연,오하영
	//휴가 사용시 휴가 일수 차감하도록 update
	public boolean holidayMinus(String empNo, int holCount) throws Exception {
		Connection con = jdbcUtils.getConnection();
		
		String sql = "update employees set holiday_count= holiday_count-? where emp_no=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, holCount);
		ps.setString(2, empNo);
		
		int count = ps.executeUpdate();
		
		con.close();
		return count>0;
	}
	
	public boolean holidayPlus(String empNo, int holCount) throws Exception {
		Connection con = jdbcUtils.getConnection();
		
		String sql = "update employees set holiday_count= holiday_count+? where emp_no=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, holCount);
		ps.setString(2, empNo);
		
		int count = ps.executeUpdate();
		
		con.close();
		return count>0;
	}


	//윤준하
	//empNo->empName 불러오는 메소드
	public String getName(String empNo)throws Exception {
		Connection con =jdbcUtils.getConnection();
		
		String sql ="select emp_name from employees where emp_no=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, empNo);
		
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		String empName =rs.getString("emp_name");
		
		con.close();
		return empName;
		
		
	}

	public List<employeesDto> list(String depart) throws Exception {//부서별 사원 리스트 
		Connection con = jdbcUtils.getConnection();
		
		String sql ="select E.*,P.posi from employees E inner join"
				+ " position P on E.po_no = P.po_no"
				+ " where department = ? ";
		PreparedStatement ps =con.prepareStatement(sql);
		ps.setString(1, depart);
		ResultSet rs = ps.executeQuery();
		
		List<employeesDto> list = new ArrayList<>();
		
		
		while(rs.next()) {
		employeesDto empDto = new employeesDto();
		
		empDto.setEmpNo(rs.getString("emp_no"));
		empDto.setEmpPw(rs.getString("emp_pw"));
		empDto.setPono(rs.getInt("po_no"));
		empDto.setEmpName(rs.getString("emp_name"));	
		empDto.setJoinDate(rs.getString("join_date"));
		empDto.setEmpPhone(rs.getString("emp_phone"));
		empDto.setEmail(rs.getString("email"));
		empDto.setAddress(rs.getString("address"));
		empDto.setDepartment(rs.getString("department"));
		empDto.setPoSi(rs.getString("posi"));
		list.add(empDto);	
		}
		con.close();
		return list;
		
		
	}
	//윤준하
	//관리자의 사원정보 수정기능
	public boolean edit (employeesDto empDto) throws Exception {
		Connection con = jdbcUtils.getConnection();
		
		String sql ="update employees set  department=?, po_no=?, emp_name=?, emp_phone=?, email=?, address=? where emp_no=?";
		
		PreparedStatement ps =con.prepareStatement(sql);
		ps.setString(1, empDto.getDepartment());
		ps.setInt(2, empDto.getPono());
		ps.setString(3, empDto.getEmpName());
		ps.setString(4, empDto.getEmpPhone());
		ps.setString(5, empDto.getEmail());
		ps.setString(6, empDto.getAddress());
		ps.setString(7, empDto.getEmpNo());
		
		int count = ps.executeUpdate();
		
		con.close();
		return count>0;
		
	}
	

}
