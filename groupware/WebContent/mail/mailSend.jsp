<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메일 보내기 화면</title>
<script type="text/javascript">
function sendMail(){
    window.alert("메일이 정상적으로 전송 되었습니다!", "width=370, height=360, resizable=no, scrollbars=no, status=no");
}
</script>
<style>
	.row-mail{
	border:1px dotted gray;
	width:100%;
	margin-top: 10px;
	margin-bottom: 10px;
	text-align: left;
	}
</style>
</head>
<body>
<jsp:include page="/template/header.jsp"></jsp:include>
<jsp:include page="/template/section.jsp"></jsp:include>
<div class="container-900">
	<form action="mailSend.gw" method ="post">
		<div class="row-mail">
		<label>받는사람</label>
		<input type ="text" name="mailRecipient">
		</div>
		<div class="row-mail">
		<label>제목</label>
		<input type ="text" name="mailTitle">
		</div>
	
	<div class="row-mail">
	<label>내용</label>
	<textarea rows="16" class="input" name="mailContent"></textarea>
		</div>
		<div class="row-mail">
		<label>첨부파일(.exe, .jar 파일 전송 불가)</label>
		<input type ="file" name="mailFile">
		</div>		
	<div class = "row text-right">
	<input type="submit" value="보내기" onclick="sendMail()">
	</div>	
	</form>
</div>
<jsp:include page="/template/footer.jsp"></jsp:include>
</body>
</html>