<%@page import="java.util.List"%>
<%@page import="groupware.beans.employeesDao"%>
<%@page import="groupware.beans.employeesDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");

employeesDao empDao = new employeesDao();

List<employeesDto> empList =empDao.list();



//답장하기 기능 :
// 1.파라미터로 보낸사람 이름이 들어온다. 이것을 수신자 이름으로 설정해서 보낸다.
// 2. 파라미터로 원본 글 제목이 들어온다. re: 붙여서 내보냄
String answer_name =request.getParameter("sender");
String answer_title = request.getParameter("m_name");


boolean isSender=answer_name!=null; //답장하기인가?

//주소록을 통해 연동해서 메세지 작성기능:
// 답장기능과 차이를 두어야 한다. 공통점: 수신자이름을 파라미터로 받아와서 보냄. 차이점: ...
// 주소를 통해 사원번호르 받아와서 이를 통해 empName을 가져온다.
String answer_empNo=request.getParameter("empNo");

boolean isAadressSend=answer_empNo!=null; //주소타고 들어온 메세지 작성인가?


if(isAadressSend) {
	String answer_empName=empDao.getName(answer_empNo);
	answer_name=answer_empName;
	} 

%>


<jsp:include page="/template/header.jsp"></jsp:include>


<script>
	$(function(){
		$(".search-btn").click(function(e){
			e.preventDefault();
			
			window.open(this.href, 'new', 'width=600, height=400');
		});
	});
</script>
<style>


	.form-textarea{
		width:100%;
		min-height:300px; 
	
	
	}
	
	
	
	.form-select{
		width:15%;
		padding:0.5rem;
		outline:none;
	}
	
	
	.row{
		text-align:left;
	
	}
	
	


</style>






<div class="container-700">
	<%if(isSender) {%>
	<div class=" text-center">
		<h2>답장하기</h2>
	</div>	
	<%} else{%>
	<div class=" text-center">
		<h2 style="border-bottom: 2px solid rgb(52, 152, 219); padding-bottom: 20px;">메세지 작성</h2>
	</div>
	<%} %>
	
	<div>
		<form action ="massageInsert.kh" method ="post" enctype="multipart/form-data">
			<!-- 제목 : 1. 답장일 때 2. 새로운 massage일때 -->
			<%if(isSender) {%>
			<div class="row">
				<label for="name">제목</label>
				<input class="form-btn" id="name" type ="text" name="m_name" value="re:<%=answer_title %>"required class="form-input" style="background-color: lightgray; border-color: white; color:black">		
			</div>
			<%} else{%>
			<div class="row">
				<label for="name" style="font-weight: bold;">제목</label>
				<input id="name" type ="text" name="m_name" required class="form-input form-btn" style="background-color: lightgray; border-color: white; color:black; ">		
			</div>
			<%} %>
			
			
			<div class="row">
				
			<!-- 수신자 명단 : 1. 답장일 때 || address타고 들어왔을 때  2. 새로운 massage일때 -->
				<%if(isSender||isAadressSend) {%>
					<label style="font-weight: bold;">수신자</label>
					<select name="e2_name" class="form-select">
<!-- 						 수신자 이름 보냄 -->
							<option><%=answer_name%></option>
					</select>
				<%} else{ %>
					<label style="font-weight: bold;">수신자</label>
					<input type="text" name="e2_name" required>
				<%} %>
				
				<%if(!isSender&&!isAadressSend){ %>
				<span> <a class="link-btn search-btn" href="massageInsertList.jsp" onclick="">검색</a></span>
				<%} %>
				
			</div>
			
			<div class="row">
				<label for="content" style="font-weight: bold;">내용</label>
				<textarea  id="content" name="m_content" class="form-textarea"></textarea>
			</div>
			
			<!-- 첨부파일 -->
			<div class="row">
				<label style="font-weight: bold;">첨부파일:</label>
				<input type="file" name="massage_file" class="form-input">
			</div>
			
			<div class="row">
				<input type="submit" value ="보내기" class="form-btn">
			</div>
		</form>
	</div>
</div>



<jsp:include page="/template/footer.jsp"></jsp:include>