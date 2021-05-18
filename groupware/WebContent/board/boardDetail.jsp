<%@page import="java.util.List"%>
<%@page import="groupware.beans.BoardCommentsDao"%>
<%@page import="groupware.beans.BoardCommentsDto"%>
<%@page import="groupware.beans.employeesDto"%>
<%@page import="groupware.beans.employeesDao"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@page import="groupware.beans.boardDto"%>
<%@page import="groupware.beans.boardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
int boardNo = Integer.parseInt(request.getParameter("boardNo"));

boardDao boarddao = new boardDao();
boardDto boarddto = boarddao.detail(boardNo);

// 타인의 게시글에만 조회수 증가
String empNo = (String)session.getAttribute("id");
Set<Integer> boardNoSet;
if(session.getAttribute("boardNoSet") != null){
	boardNoSet = (Set<Integer>)session.getAttribute("boardNoSet");
}
else{
	boardNoSet = new HashSet<>();
}
if(boardNoSet.add(boardNo)){
	boarddao.boCount(boardNo, empNo);
}

	String loginId = (String)session.getAttribute("id");
	String writerId = boarddto.getEmpNo();
//--작성자 아이디와 접속한 아이디 비교하기 위한 변수 loginId(접속 아이디) writerId(게시글 작성자 아이디)

int authoritylevel = ((Integer)(session.getAttribute("authorityLevel"))).intValue();

//session int 로 변환해야 한다.

//댓글 목록 불러오기
	BoardCommentsDao boardCommentsDao = new BoardCommentsDao();
	List<BoardCommentsDto> boardCommentsList = boardCommentsDao.list(boardNo);
%>    
    
<jsp:include page="/template/header.jsp"></jsp:include>
<jsp:include page="/template/section.jsp"></jsp:include>
<div class ="row text-center">
<%=boarddto.getBoardNo() %><br>
<%=boarddto.getBoTitle() %><br>
<%=boarddto.getBoType() %><br>
<%=boarddto.getBoContent() %><br>
<%=boarddto.getBoCount() %><br>
<%=boarddto.getBoDate() %><br>
<%=boarddto.getEmpName() %><br>
</div>

<div class = "row text-right">
<%if(loginId.equals(writerId)||authoritylevel==1){ %><!-- 접속한 아이디와 작성자 아이디가 같으면 수정과 삭제 가능
														 or 권한레벨이 1이면 게시글 수정 삭제 가능  -->
	<a href = "boardEdit.jsp?boardNo=<%=boarddto.getBoardNo()%>">수정하기</a>
	<a href = "boardmain.jsp">목록보기</a>
	<form action="boardDelete.gw" method="post">
		<input type="hidden" name="boardNo" value="<%=boarddto.getBoardNo()%>">
		<input type="submit" value="삭제하기">
	</form>
<%}else{ }%>	
	
</div>
<div class="row text-left">
		<h4>댓글 목록</h4>
	</div>
	<%for(BoardCommentsDto boardCommentsDto : boardCommentsList) { %>
	<div class="row text-left" style="border:1px solid gray;">
		<div class="float-container">
			<div class="left"><%=boardCommentsDto.getEmpNo()%></div>
			
			<%if(loginId == writerId){ %>
			<div class="right">
				<a class="reply-edit-btn">수정</a> 
				| 
				<a class="reply-delete-btn" href="replyDelete.kh?replyNo=<%=boardCommentsDto.getComNo()%>&replyOrigin=<%=boardNo%>">삭제</a>
			</div>
			<%} %>
		</div>
<div class="com-display-area">
			<pre><%=boardCommentsDto.getComContent()%></pre>
		</div>
		
		<%if(loginId == writerId){ %>
		<div class="com-edit-area">
			<form action="comEdit.gw" method="post">
				<input type="hidden" name="comNo" value="<%=boardCommentsDto.getComNo()%>">
				<input type="hidden" name="empNo" value="<%=boardCommentsDto.getEmpNo()%>">
				
				<textarea name="comContent" required><%=boardCommentsDto.getComContent()%></textarea>
				<input type="submit" value="댓글 수정">		
				<input type="button" value="작성 취소" class="reply-edit-cancel-btn">		
			</form>
		</div> 
		<%} %>
		<div><%=boardCommentsDto.getDate().toLocaleString()%></div>
	</div>
	<%} %>

<form action="comInsert.gw" method="post"> 
<input type="hidden" name="empNo">
	<div class="row">
		<textarea rows="4" class="form-input" required name="comContent" placeholder="댓글 입력"></textarea>
	</div>
	<div class="row">
		<input type="submit" value="댓글 작성" class="form-btn form-btn-normal">
	</div>
</form>

<jsp:include page="/template/footer.jsp"></jsp:include>
