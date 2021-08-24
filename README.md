Project GroupWare
================

 ### 프로젝트 링크 : [GroupWare](https://bit.ly/3k7dwT1)



* 계정 정보
   * ID : 1032
   * PW : qwer1000
------------------------------

사용 기술
-----------------
* Language : Java(8), JavaScript(ES5), HTML5, CSS3     
* Library : jQuery      
* Server : Tomcat 9.0      
* DBMS : Oracle       
* Tool : Git / GIthub         

</br>

개발 기간 & 참여 인원  
-----------------
* 개발 기간 : 2021-05-24 ~ 2021-06-04         
* 팀 프로젝트 (5명)

</br>


ERD 설계
-----------------
[![ERD.png](https://i.postimg.cc/dQhqCS8r/ERD.png)](https://postimg.cc/grPFFst2)

</br>

프로젝트 기능
-----------------

* <h3>공통 기능</h3>
 
  * 로그인 화면
  * 메인 페이지

* <h3>세부 기능</h3>

  * 공지 메일     
   
  * 일정 목록          
  * 주소록     
  * 메시지          
     * 발신함         
     * 수신함        
  * 전자 결재     
    * 기안서 작성    
    * 내 결재 관리      
  * 근태 관리    
     * 출퇴근 목록      
     * 휴가 목록      
     * 출퇴근 관리(관리자만)    
  * 게시판 (댓글기능, 이전-다음글, 조회수 기능, 목록 댓글 개수 표시)    
    * 전체 게시판          
     * 공지 게시판      
     * 질문 게시판        
     * 자유 게시판        
     * 내 작성글    
  * 마이페이지     
    * 나의 정보     
    * 나의 급여      
    * 관리자 페이지(관리자만)      
 
 </br>
 
 담당 기능
 -----------
 * __UI 디자인__    
<details>
   <summary>공지 메일</summary>
   <div markdown="1">
   <br>
   
* 받는 사람의 이메일 형식 검사를 수행하고 올바른 이메일 형식이 아닐시 에러 메시지를 표시합니다.   
* 정상적으로 메일이 발송되면 알림창을 표시합니다.    
* 이후 구글 메일 API를 통해 메일을 발송합니다.   
    
* **코드확인** :pushpin:  
    * [구글 SMTP 인증](https://github.com/lvalentine6/Project_GroupWare/blob/master/groupware/src/groupware/beans/MailLogin.java)    
    * [메일 전송](https://github.com/lvalentine6/Project_GroupWare/blob/master/groupware/src/groupware/mail/servlet/MailSendServlet.java)
   <br>
    
    
   [![2.png](https://i.postimg.cc/DzRnG40Q/2.png)](https://postimg.cc/SXLBFsWj)

  </div>
  </details> 

<details>
   <summary>게시판, 댓글</summary>
   <div markdown="1">
   <br>

   [![1.png](https://i.postimg.cc/y8QVVbzb/1.png)](https://postimg.cc/LJPdDv5t)
* 타인 게시글만 조회수 증가
    [타인 게시글](https://github.com/lvalentine6/Project_GroupWare/blob/master/groupware/WebContent/board/boardDetail.jsp#L24)
  </div>
  </details> 

  </br>

  느낀점
  ----------
  >[국비학원 세미 프로젝트가 끝나고](https://bit.ly/2VwlLOs)
