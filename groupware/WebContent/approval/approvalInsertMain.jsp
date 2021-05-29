<%@page import="java.util.List"%>
<%@page import="groupware.beans.approvalDto"%>
<%@page import="groupware.beans.approvalDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = (String) session.getAttribute("id");

    approvalDao approvaldao = new approvalDao();
	List<approvalDto> list = approvaldao.approvalList(id);//세션아이디로 작성한 기안서 리스트 출력
     
    %>
<jsp:include page="/template/header.jsp"></jsp:include>

<jsp:include page="/template/section.jsp"></jsp:include>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>

<script>

$(function(){

	////////////////////////////////////////////////////////////////////////
	var _width = '750';
    var _height = '500';
 
    // 팝업을 가운데 위치시키기 위해 아래와 같이 값 구하기
    var _left = Math.ceil(( window.screen.width - _width )/2);
    var _top = Math.ceil(( window.screen.height - _height )/2);
    
var option ='width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top ;
	$(".appinsert").click(function(){
		 window.open("approvalInsert.jsp","draftPopUp",option);
		 
	});
///////////////////////////////////////////////////////////////////////////////

	
});

</script>
<div class = "container-900">
<table class="table table-border table-hover" >
		<thead>
			<tr>
				<th>결제 서류 번호</th>
				<th>제목</th>
				<th>기안자</th>
				<th>기안일</th>
				<th>마감일</th>
				<th>상태</th>
			</tr>
		</thead>
		<tbody><%for(approvalDto approvaldto : list){%>
				<tr>
					<td><%=approvaldto.getAppNo()%></td>
					<td><%=approvaldto.getAppTitle()%></td>
					<td><%=approvaldto.getDrafter()%></td>
					<td><%=approvaldto.getAppDateStart()%></td>
					<td><%=approvaldto.getAppDateEnd()%></td>
					<td><%=approvaldto.getAppState()%></td>
				</tr>
		<%}%>
		</tbody>
</table>
</div>


<input type="button" class = "appinsert" value="기안서 작성">


<jsp:include page="/template/footer.jsp"></jsp:include>