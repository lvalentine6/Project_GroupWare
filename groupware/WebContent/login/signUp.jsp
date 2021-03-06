<%@page import="groupware.beans.employeesDto"%>
<%@page import="java.util.List"%>
<%@page import="groupware.beans.employeesDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 

<!-- 잘못 입력하면 바로아래에 제약조건 문구 뜨면서 다시 입력하도록 안내 문구 추가해야 한다.
javaScript로 구현 -->
<%
	employeesDao employeesDao = new employeesDao();
	List<employeesDto> employeesList = employeesDao.list();
%>
     
<jsp:include page="/template/header.jsp"></jsp:include>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script> 
<style>
.select-form{
	width: 400px;
	padding: .8em .5em;
	border: 1px solid #999;
	border-radius: 0px;
	margin-top:10px;
}
.form-input.form-input-underline {
	width:400px;
	border:none;
	border-bottom: 2px solid lightgray;
	margin-top:10px;
}
.input-postNumber{
	width:200px !important;
}
.input-tel{
	width:165px !important;
}
 
</style>
<script>
function confirmId(){//아이디 확인 함수
	
	var regex = /^[0-9a-z]{4,10}$/;
	var inputId = document.querySelector("input[name=empNo]");
	var text = inputId.value;
	var spanId = document.querySelector("input[name=empNo]+ p")
	
		if(regex.test(text)){//정규식으로 확인하고 맞으면 아무것도 안나온다
			spanId.textContent ="";
		}
		else{//정규식으로 확인하고 규격에 맞지않으면 지우고 안내문 나온다.
			spanId.textContent ="아이디는 영소문자,숫자로 4~10자로 입력해주세요."
			inputId.value="";
		}
}
function confirmPw(){//비밀번호 확인 함수
	
	var regex = /^[a-zA-Z0-9]{8,16}$/;
	var inputPw = document.querySelector("input[name=empPw]");
	var text = inputPw.value;
	var spanPw = document.querySelector("input[name=empPw]+ p");

		if(regex.test(text)){//정규식으로 확인하고 맞으면 아무것도 안나온다
			spanPw.textContent = "";
		}
		else{//정규식으로 확인하고 규격에 맞지않으면 지우고 안내문 나온다.
			spanPw.textContent = "비밀번호는 영소대문자,숫자로 8~16자로 입력해주세요";
			inputPw.value ="";
		}
}

function reconfirmPw(){//비밀번호 확인식
	
	var inputPw = document.querySelector("input[name=empPw]");
	var reinputPw = document.querySelector(".rePw");
	var spanPw = document.querySelector(".rePw+ p");
	
	var text = inputPw.value;
	var retext = reinputPw.value;
	
		if(text==retext){//확인 식과 같으면 일치문 나온다.
			spanPw.textContent="비밀번호가 일치합니다.";
			
		}
		else{//확인 식과 다르면 지우고 불일치문 나온다.
			spanPw.textContent="비밀번호가 일치하지 않습니다."
				reinputPw.value ="";
		}
}

function confirmName(){//이름 확인 함수
	
	var regex = /^[가-힣]{2,7}$/;
	var inputName = document.querySelector("input[name=empName]");
	var text = inputName.value;
	var spanName = document.querySelector("input[name=empName]+ p");
	
		if(regex.test(text)){//정규식으로 확인하고 맞으면 아무것도 안나온다
			spanName.textContent="";
		}
		else{//정규식으로 확인하고 규격에 맞지않으면 지우고 안내문 나온다.
			spanName.textContent="한글로 성을 포함하여 2~7자로 작성해 주세요.";
			inputName.value="";
		}
}

function confirmPhone(){//전화번호 확인 함수
	
	var regex = /^\d{4}$/;
	var inputPhone = document.querySelector("input[name=empPhonemid]");
	var text = inputPhone.value;
	var spanPhone = document.querySelector("input[name=empPhonelast]+ p");
	
		if(regex.test(text)){//정규식으로 확인하고 맞으면 아무것도 안나온다
			spanPhone.textContent="";
		}
		else{//정규식으로 확인하고 규격에 맞지않으면 지우고 안내문 나온다.
			spanPhone.textContent="숫자로 4자리씩 입력해주세요";
			inputPhone.value="";
		}
}

function confirmPhone2(){//전화번호 확인 함수
	
	var regex = /^\d{4}$/;
	var inputPhone = document.querySelector("input[name=empPhonelast]");
	var text = inputPhone.value;
	var spanPhone = document.querySelector("input[name=empPhonelast]+ p");
	
		if(regex.test(text)){//정규식으로 확인하고 맞으면 아무것도 안나온다
			spanPhone.textContent="";
		}
		else{//정규식으로 확인하고 규격에 맞지않으면 지우고 안내문 나온다.
			spanPhone.textContent="숫자로 4자리씩 입력해주세요";
			inputPhone.value="";
		}
}
function postNumSearch() {//참고항목을 제외한 다음 주소 api 함수
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
        //    var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
     //       if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                
      //          if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
      //              extraAddr += data.bname;
      //          }
                
                // 건물명이 있고, 공동주택일 경우 추가한다.
                
      //          if(data.buildingName !== '' && data.apartment === 'Y'){
      //              extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
      //          }
                
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                
      //          if(extraAddr !== ''){
      //              extraAddr = ' (' + extraAddr + ')';
      //          }
                
                // 조합된 참고항목을 해당 필드에 넣는다.
      //          document.querySelector("input[name=addressExtra]").value = extraAddr;
            
      //      } 
            
            //else {
          //  document.querySelector("input[name=addressExtra]").value = '';
          //  }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.querySelector('input[name=postNumber]').value = data.zonecode;
            document.querySelector("input[name=addressNum]").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.querySelector("input[name=addressDetail]").focus();
        }
    }).open();
}
</script>
<script>
window.onload = function() {
	document.getElementById('joinDate').valueAsDate = new Date(); //오늘 날짜 기본값으로 설정
}

</script>
<script>
<% for(employeesDto employee : employeesList){%>
	$(function(){
		$("input[name=empNo]").on("input", function(){
			if($(this).val() == <%=employee.getEmpNo()%>){
				$(this).val("");
				$(this).next("p").text("이미 존재하는 사원번호 입니다.");
			}
		});
	});
<%}
%>
</script>



<div class="row" style="border: none;">
	<h2>사원등록</h2>
</div>

<div class="container-1200" style="border: none; border-top: 2px solid rgb(102, 177, 227); margin-bottom: 10px;">
	<form action="signUpInsert" method="post">
		<div style="margin-top: 10px;">사원번호</div>
		<div>
			<input class="form-input form-input-underline" type="text" name="empNo" pattern="[0-9a-z]{4,10}" required onblur="confirmId();">
			<p class="error"></p><br>
		</div>
		<div>
		비밀번호</div>
		<div>
			<input class="form-input form-input-underline" type="password" name="empPw" pattern="[a-zA-Z0-9]{8,16}" required onblur="confirmPw();">
			<p class="error"></p><br>
		</div>
		<div>비밀번호 확인</div>
		<div>
			<input class="form-input form-input-underline" type ="password" class="rePw" pattern="[a-zA-Z0-9]{8,16}" required onblur="reconfirmPw();">
			<p class="error"></p><br>
		</div>
		<div>직급 </div>
		<div>
			<select name="poNo" class="select-form">
				<option value="">선택하세요</option>
				<option value="1">사장</option>
				<option value="2">부장</option>
				<option value="3">차장</option>
				<option value="4">과장</option>
				<option value="5">대리</option>
				<option value="6">사원</option>
			</select><p></p><br>
		</div>
		<div>이름</div>
		<div>
			<input type="text" class="form-input form-input-underline" name="empName" pattern="[가-힣]{2,7}" required onblur="confirmName();">
			<p class="error"></p><br>
		</div>
		<div>입사일</div>
		<div>
			<input type="date" class="form-input form-input-underline" name="joinDate" id="joinDate" required>
			<p class="error"></p><br><!-- date type 스크립트로 정규식 어떻게 줄까 아니면 select로 년월일 바꿔서 구현? --> 
		</div>
		<div>전화번호</div>
		<div style="margin-top:10px;">
			010  -  <input type="text" name="empPhonemid" pattern="\d{4}" class="form-input form-input-underline input-tel" required onblur="confirmPhone();">  -  <input type="text" name="empPhonelast" pattern="\d{4}" class="form-input form-input-underline input-tel" required onblur="confirmPhone2();">
			<p class="error"></p><br>
		</div>
		<div>이메일</div>
		<div>
			<input type="text" name="emailLocal" required class="form-input form-input-underline">
			@
			<select name="emailDomain" class="select-form">
				<option value="">선택하세요</option>
				<option>gmail.com</option>
				<option>naver.com</option>
				<option>yahoo.co.kr</option>
				<option>empal.com</option>
				<option>nate.com</option>
				<option>dreamwiz.com</option>
				<option>orgio.net</option>
				<option>intizen.com</option>
				<!-- 직접입력 아직 미구현  --> 
			</select>
			<p class="error"></p><br>
		</div>
		<div>부서</div>
		<div>
			<select name = "department" class="select-form"><!-- 회원가입 시 부서 선택 가능하도록 구현  -->
				<option value="">선택하세요</option>
				<option>인사부</option>
				<option>총무부</option>
				<option>회계부</option>
				<option>기획부</option>
				<option>영업부</option>
			</select><br><br>
		</div>
		<div>
			<!-- 다음 api를 활용한 주소 넣기 -->
			<input type="text" name="postNumber" placeholder="우편번호" class="form-input form-input-underline input-postNumber">  
			<input type="button" onclick="postNumSearch();" value="우편번호 찾기" class="form-btn form-btn-inline"><br>
			<input type="text" name="addressNum" placeholder="주소" class="form-input form-input-underline">
			<input type="text" name="addressDetail" placeholder="상세주소" class="form-input form-input-underline"><br><br>
		</div>
		<div>
			<!--<input type="text" name="addressExtra" placeholder="참고항목">  --> 
			<input type="submit" value="회원가입" class="form-btn form-btn-positive">
		</div>
	</form>
</div>



<jsp:include page="/template/footer.jsp"></jsp:include>
