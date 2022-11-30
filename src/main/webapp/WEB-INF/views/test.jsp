<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>CommentTest</title>
  <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>
<h2>CommentTest</h2>
<%--댓글 입력란 제목 "comment: " 을 <label>로 담기 --%>
<label>comment: </label>
<%-- "text" 타입을 "comment"라는 이름으로 입력받는 태그 선언--%>
<input type="text" name="comment">
<br>
<%--id="sendBtn" type="button" 인 <button>, 내부 text는 SEND --%>
<button id="sendBtn" type="button">SEND</button>
<%--id="modBtn" type="button" 인 <button>, 내부 text는 수정완료 --%>
<button id="modBtn" type="button">수정완료</button>

<h2>Data From Server :</h2>
<%--댓글 목록을 표시할 div 컨테이너, id="commentList" 로 선언 --%>
<div id="commentList"></div>

<%-- **4.13 답글 입력폼 생성
  div#replyForm - 인라인 스타일 속성으로 보이지 않게 처리
    text타입의 입력란 요소, name="replyComment"
    button#wrtRepBtn - type="button", 등록 버튼 만들기
 --%>
<div id="replyForm" style="display: none">
  <input type="text" name="replyComment">
  <button id="wrtRepBtn" type="button">등록</button>
</div>

<script>
  // 기본값이 1인 매개변수 value를 가진 익명함수를 선언 후 addZero 변수에 저장
  let addZero = function(value=1){
    // value가 9보다 크면 value, 작으면 문자 0을 value 앞에 붙여서 리턴
    return value > 9 ? value : "0"+value;
  }

  // 기본값이 0인 매개변수 ms를 가진 익명함수를 선언하고 dateToString 변수에 저장
  let dateToString = function(ms=0) {
    // ms를 매개변수로 하여 Date 객체를 생성하고 date 변수에 저장
    let date = new Date(ms);

    //date 변수에서 연도를 추출하여 yyyy 변수에 저장
    let yyyy = date.getFullYear();
    //date 변수에서 '월'을 추출하여 mm 변수에 저장
    let mm = addZero(date.getMonth() + 1);
    //date 변수에서 '일'을 추출하여 dd 변수에 저장
    let dd = addZero(date.getDate());

    //date 변수에서 시간을 추출하여 HH 변수에 저장
    let HH = addZero(date.getHours());
    // date 변수에서 분을 추출하여 MM 변수에 저장
    let MM = addZero(date.getMinutes());
    // date 변수에서 초를 추출하여 ss변수에 저장
    let ss = addZero(date.getSeconds());

    //yyyy, mm, dd, HH, MM, ss 를 적절한 구분자로 연결 후 문자열로 리턴
    return yyyy+"."+mm+"."+dd+ " " + HH + ":" + MM + ":" + ss;
  }


  //서버에서 받은 json 데이터 comments를 인수로 하는 function 선언, 이 함수를 변수 toHtml에 저장
  let toHtml = function (comments) {
    // "<ul>"를 tmp 변수에 저장
    let tmp = "<ul>";

    //comments 객체 내의 각 요소(==comment)에 적용될 수 있게 함수 적용, 함수 매개변수는 comment로 함
    comments.forEach(function (comment) {
      //tmp에 다음 문자열을 누적: li 여는 태그 시작, data-cno 속성값에 comment 객체 cno 대입
      tmp += "<li data-cno=" + comment.cno;
      //tmp에 다음 문자열을 누적: " data-pcno" 속성값에 comment객체의 pcno 대입
      tmp += " data-pcno=" + comment.pcno;
      //tmp에 다음 문자열을 누적: " data-bno" 속성값에 comment객체의 bno 대입 후 li 여는 태그 닫기
      tmp += " data-bno=" + comment.bno + ">";

      // **4.13 답글 앞에 '└>' 표시
      if(comment.pcno != comment.cno) tmp += '└>';

      //comment 객체의 commenter 값을 span.commenter 태그로 감싸서 soutv 형태로 표현
      tmp += 'commenter= <span class="commenter">' + comment.commenter + '</span>';
      //comment 객체의 content 값을 span.comment 태그로 감싸서 soutv 형태로 표현
      tmp += ' comment= <span class="comment">' + comment.comment + '</span>';
      //comment 객체의 up_date 값을 soutv 형태로 표현
      tmp += ' up_date= ' + dateToString(comment.up_date);
      //button.delBtn 으로 삭제버튼 만들기
      tmp += ' <button class="delBtn">삭제</button>';
      //button.modBtn 으로 수정 버튼 만들기
      tmp += ' <button class="modBtn">수정</button>';
      // **4.13 class="replyBtn"인 답글 <button> 추가
      tmp += '<button class="replyBtn">답글</button>';
      // /li태그 닫기
      tmp += "</li>";
    });
    //여태 누적한 tmp에 </ul> 문자열을 더해서 리턴
    return tmp + "</ul>";
  }


  //bno를 인수로 하는 익명함수를 선언하고 변수 showList에 저장
  let showList = function (bno) {
    //ajax 함수 시작
    $.ajax({
      //요청 타입은 GET
      type: "get",
      //요청 URI는 컨트롤러 참고하여 작성
      url: "/ch4/comments?bno=" + bno,
      //서버에서 정상적으로 받은 응답 result를 인수로 하는 익명함수 선언
      success: function (result) {
        //"#commentList" 요소의 html 영역에 result를 인수로 하는 toHtml함수의 결과를 리턴
        $("#commentList").html(toHtml(result));
      },
      //에러 발생 시 익명함수 실행, 매개변수 없음
      error: function () {
        //알림창으로 "error" 출력
        alert("error");
      }
    });
  }


  //댓글을 가져올 게시물 번호 5285를 bno에 저장, 임시 하드코딩 추후 boardList에서 전달받아 EL로 처리
  let bno = 5287;

  //bno를 매개변수로 하는 showList 함수 호출
  showList(bno);

  //문서 로딩 완료 시 jQuery 시작 선언문
  $(function () {
    // #sendBtn 클릭 시 함수 실행 선언문 시작
    $("#sendBtn").on("click", function () {
      //문서 내 name 속성값이 comment인 input 요소의 값을 JS변수 comment에 저장
      let comment = $("input[name=comment]").val();
      //만약 comment의 좌우 공백을 삭제한 결과가 빈문자열이면
      if (comment.trim() == '') {
        //알림창으로 "내용을 입력해주세요." 출력
        alert("내용을 입력해주세요.");
        //문서 내 name속성값이 comment인 input 요소에 커서가 활성화되도록 함수 설정
        $("input[name=comment]").focus();
        //현재 함수를 종료하고 빠져나옴
        return;
      }

      //ajax 함수 시작부분
      $.ajax({
        //요청방식: post
        type: "post",
        //요청 URL: 댓글 등록 시필요한 파라미터는 컨트롤러 참고
        url: "/ch4/comments/?bno=" + bno,
        //요청 헤더로 K-V 객체(전송타입:*json) 설정
        headers: {"content-type": "application/json"},
        //서버로 전송할 데이터, 게시글 번호와 댓글 내용을 K-V 객체로 전달
        //@RequestBody가 있는 컨트롤러의 메서드 파라미터에 전달됨
        data: JSON.stringify({bno: bno, comment: comment}),
        //서버에서 응답을 받았을 때 실행할 함수 선언
        success: function (result) {
          //받은 응답을 알림창에 출력
          alert(result);
          //bno에 해당하는 댓글 목록을 출력하는 메서드 호출
          showList(bno);
        },
        //서버에서 응답을 받지 못했을 때 실행할 함수 선언
        error: function () {
          //알림창에 "error" 출력
          alert("error");
        }
      });
      //댓글 작성 완료 후 입력폼 초기화
      $('input[name="comment"]').val('');
    });


    //$(".delBtn").click(function(){
    //".delBtn"이 생성되기 전에 실행되어 미적용, 클릭 전에 이미 실행됨
    //아래와 같이 이벤트 실행 전후에 상관 없이 고정된 요소에 이벤트 설정 필
    //div.#commentList에 있는 ".delBtn"에 "click"이벤트 발생 시 익명함수 적용
    $("#commentList").on("click", ".delBtn", function () {
      //클릭 이벤트 발생 버튼의 부모 요소의 속성 중 "data-cno"의 값을 변수 cno에 저장
      let cno = $(this).parent().attr("data-cno");
      //클릭 이벤트 발생 버튼의 부모 요소의 속성 중 "data-bno"의 값을 변수 bno에 저장
      let bno = $(this).parent().attr("data-bno");

      //ajax 함수 시작
      $.ajax({
        //요청방법: 'DELETE'
        type: "delete",
        //요청 URL: comment 컨트롤러의 메서드 참고
        url: "/ch4/comments/" + cno + "?bno=" + bno,
        //서버에서 정상적으로 응답 도착 시 실행할 익명함수 선언, 매개변수는 result
        success: function (result) {
          //알림창에 result 출력
          alert(result);
          //bno에 해당하는 댓글 목록 출력
          showList(bno);
        },
        //에러 발생 시 호출될 익명함수, 매개변수 없음
        error: function () {
          //알림창에 "error" 출력
          alert("error");
        }
      });
    });


    //div.#commentList에 있는 요소 중 ".modBtn"에 클릭 이벤트 발생 시 익명함수 호출 선언
    $("#commentList").on("click", ".modBtn", function () {
      //현재 요소의 부모의 속성 중 "data-cno"에 저장된 값을 cno 변수에 저장
      let cno = $(this).parent().attr("data-cno");
      //1. comment의 내용을 input에 출력
      //클릭이 발생한 요소의 부모에 있는 "span.comment"의 text값을 변수 comment에 저장
      let comment = $("span.comment", $(this).parent()).text();

      //문서 내 input 요소 중 name속성값이 comment인 요소의 입력란 값을 위에서 저장한 comment로 대체
      $("input[name=comment]").val(comment);
      //2. 위에서 얻은 cno를 수정완료 버튼 속성에 전달
      $("#modBtn").attr("data-cno", cno);
    });


    //"#modBtn" 클릭시 익명함수 호출 선언
    $("#modBtn").on("click", function () {
      //현재 객체의 "data-cno" 속성값을 cno에 저장
      let cno = $(this).attr("data-cno");
      //name속성이 comment인 input 요소에 입력된 값을 comment 에 저장
      let comment = $('input[name="comment"]').val();

      //만약 comment 변수의 앞뒤 공백을 제거한 결과가 빈문자열이면
      if (comment.trim() == '') {
        //알림창에 "내용을 입력해주세요." 출력
        alert("내용을 입력해주세요.");
        //댓글 입력란에 커서가 활성화되도록 함수 적용
        $('input[name="comment"]').focus();
        //현재 익명함수를 종료
        return;
      }

      //ajax 함수 시작
      $.ajax({
        //요청 타입: 'PATCH'
        type: "patch",
        //요청 URI: 컨트롤러의 메서드 참고하여 작성
        url: "/ch4/comments/" + cno,
        //요청 헤더 K-V {content* : appli*}
        headers: {"content-type": "application/json"},
        // 서버로 전송할 데이터. @RequestBody가 있는 파라미터에 전달됨
        //cno, comment를 각각 K-V로 갖는 객체를 서버에 전달
        data: JSON.stringify({cno: cno, comment: comment}),
        //서버에서 정상적으로 응답을 받았을 때 처리할 익명함수 선언, 매개변수는 result 1개
        success: function (result) {
          //알림창에 result 출력
          alert(result);
          //bno에 해당하는 댓글 목록 출력함수 호출
          showList(bno);
        },
        //에러 발생시 처리할 익명함수 선언, 매개변수 없음
        error: function () {
          //알림창에 "error" 출력
          alert("error");
        }
      });
      // 수정완료 후 댓글 입력란 비움
      $('input[name="comment"]').val('');
    });


    // **4.13 "#commentList" 내 답글 버튼(".replyBtn") 클릭 시, 답글 폼 이동 및 출력
    $("#commentList").on("click", ".replyBtn", function () {
      //답글 입력폼("#replyForm") 이동 - 답글버튼 클릭한 <li> 하위 요소 끝으로 이동
      $("#replyForm").appendTo($(this).parent());
      //답급 입력폼 출력 - css 속성 변경
      $("#replyForm").css("display", "block");
    });

    // **4.13 답글 입력폼의 등록 버튼 클릭 시 대댓글 등록 기능
    // "#wrtRepBtn" 클릭 시 익명함수 실행 선언부 시작
    $("#wrtRepBtn").on("click", function () {
      //input 요소 중 [name='replyComment'] 요소의 value를 comment 변수에 저장
      let comment = $("input[name='replyComment']").val();
      // 댓글 입력 폼의 부모 요소의 속성에서 data-pcno 속성값을 pcno 변수에 저장
      let pcno = $("#replyForm").parent().attr("data-pcno");

      //comment의 앞뒤 공백을 제거한 결과가 빈문자열이면
      if(comment.trim() == '') {
        //알림창으로 '내용을 입력해주세요.' 출력
        alert("내용을 입력해주세요.");
        //답글입력란에 커서가 활성화되도록 처리
        $("input[name='replyComment']").focus();
        //함수 종료
        return;
      }

      //ajax 함수 시작
      $.ajax({
        // post 요청
        type: "post",
        // 컨트롤러 참고하여 요청 URI 작성
        url: "/ch4/comments?bno=" + bno,
        // 요청 헤더 작성
        headers: {"content-type":"application/json"},
        // 서버로 보낼 2가지 데이터, 직렬화하기
        data: JSON.stringify({pcno: pcno, comment: comment}),
        // 서버에서 정상적으로 응답을 받았을 때 실행할 익명함수 선언, 매개변수는 result
        success: function (result) {
          // 매개변수 result를 알림창에 출력
          alert(result);
          // bno에 해당하는 댓글 목록을 view에 출력
          showList(bno);
        },
        // 에러가 발생했을 때 실행할 익명함수, 매개변수 없음
        error: function () {
          // 알림창에 "error" 출력
          alert("error");
        },
      });
      //ajax 요청 후 대댓글 내용 삭제 및 원위치
      //답글 입력폼을 보이지 않게 CSS 속성 변경
      $("#replyForm").css("display", "none");
      //답글 입력란의 내용을 빈문자열로 바꿈
      $('input[name="replyComment"]').val('');
      //답글 입력폼을 body 요소 내 끝으로 붙임
      $("#replyForm").appendTo("body");
    });
  });
</script>
</body>
</html>