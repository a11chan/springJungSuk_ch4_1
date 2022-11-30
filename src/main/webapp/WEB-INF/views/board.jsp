<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<%@ page session="true" %>
<c:set var="loginId" value="${sessionScope.id}"/>
<c:set var="loginOutLink" value="${loginId==''||loginId==null ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId==''||loginId==null ? 'Login' : 'ID='+=loginId}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>스프링의 정석 실습</title>
  <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
  <link rel="stylesheet" href="<c:url value='/css/board.css'/>">
  <link rel="stylesheet" href="<c:url value='/css/comment3.css'/>">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>
<div id="menu">
  <ul>
    <li id="logo">company</li>
    <li><a href="<c:url value='/'/>">Home</a></li>
    <li><a href="<c:url value='/board/list'/>">Board</a></li>
    <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
    <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
    <li><a href=""><i class="fa fa-search"></i></a></li>
  </ul>
</div>
<script>
  let msg = "${msg}";
  if (msg == "WRT_ERR") alert("게시물 등록에 실패하였습니다. 다시 시도해 주세요.");
  if (msg == "MOD_ERR") alert("게시물 수정에 실패하였습니다. 다시 시도해 주세요.");
</script>
<main class="container">
  <h2 class="writing-header">게시판 ${mode=="new" ? "글쓰기" : "읽기"}</h2>
  <form id="form" class="frm" action="" method="post">
    <input type="hidden" name="bno" value="${boardDto.bno}">

    <%--**4.08 사용자 입력이 가능한 부분은 c:out 태그로 출력--%>
    <input name="title" type="text" value="<c:out value="${boardDto.title}"/>"
           placeholder="  제목을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}><br>
    <textarea name="content" rows="20" placeholder=" 내용을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}><c:out value="${boardDto.content}" /></textarea><br>

    <%--**4.08 서버에서 받은 mode키의 값이 'new'이면 등록 버튼 표시하는 c:if문 --%>
    <c:if test="${mode eq 'new'}">
      <button type="button" id="writeBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 등록</button>
    </c:if>
    <%--**4.08 서버에서 받은 mode키의 값이 'new'가 아니면 글쓰기 버튼 표시하는 c:if문 --%>
    <c:if test="${mode ne 'new'}">
      <button type="button" id="writeNewBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 글쓰기</button>
    </c:if>
    <%--**4.08 게시물 객체의 작성자가 loginId 키값과 같으면 수정, 삭제 버튼 표시하는 c:if문 --%>
    <c:if test="${boardDto.writer eq loginId}">
      <button type="button" id="modifyBtn" class="btn btn-modify"><i class="fa fa-edit"></i> 수정</button>
      <button type="button" id="removeBtn" class="btn btn-remove"><i class="fa fa-trash"></i> 삭제</button>
    </c:if>
    <button type="button" id="listBtn" class="btn btn-list"><i class="fa fa-bars"></i> 목록</button>
  </form>

  <section class="commentArea">
    <div id="commentList" ${boardDto.bno == 0 || boardDto.bno == null? "style='display:none;'": "" }></div>

    <div id="comment-writebox" ${boardDto.bno == 0 || boardDto.bno == null? "style='display:none;'": "" }>
      <div class="commenter commenter-writebox">${id}</div>
      <div class="comment-writebox-content">
        <textarea name="comment" id="commentInputText" cols="30" rows="3" placeholder="댓글을 남겨보세요"></textarea>
      </div>
      <div id="comment-writebox-bottom">
        <div class="register-box">
          <a href="#" class="btn" id="btn-write-comment">등록</a>
        </div>
      </div>
    </div>

    <div id="reply-writebox">
      <div class="commenter commenter-writebox">${id}</div>
      <div class="reply-writebox-content">
        <textarea name="comment" id="replyInputText" cols="30" rows="3" placeholder="댓글을 남겨보세요"></textarea>
      </div>
      <div id="reply-writebox-bottom">
        <div class="register-box">
          <a href="#" class="btn" id="btn-write-reply">등록</a>
          <a href="#" class="btn" id="btn-cancel-reply">취소</a>
        </div>
      </div>
    </div>

    <div id="modalWin" class="modal">
      <!-- Modal content -->
      <div class="modal-content">
        <span class="close">&times;</span>
        <p>
        <h2> | 댓글 수정</h2>
        <div id="modify-writebox">
          <div class="commenter commenter-writebox"></div>
          <div class="modify-writebox-content">
            <textarea name="comment" id="commentModifyText" cols="30" rows="5" placeholder="댓글을 남겨보세요"></textarea>
          </div>
          <div id="modify-writebox-bottom">
            <div class="register-box">
              <a href="#" class="btn" id="btn-write-modify">등록</a>
            </div>
          </div>
        </div>
        </p>
      </div>
    </div>
  </section>

</main>
<script>
  function tagEscape(string) {
    string = string.replace(/\</g, "〈");
    string = string.replace(/\>/g, "〉");
    return string;
  }

  let bno = ${boardDto.bno == null ? 0 : boardDto.bno};

  $(document).ready(function () {
    // 이하 게시글 영역 관련 코드
    let formCheck = function () {
      //**4.08 HTML 문서에서 form 아이디를 가진 요소를 form 변수에 저장
      let form = document.getElementById("form");

      //**4.08 form변수의 title속성의 value값이 빈문자열이면
      if(form.title.value == "") {
        //**4.08 알림창으로 "제목을 입력해 주세요." 메시지 출력
        alert("제목을 입력해 주세요.");
        //**4.08 form변수의 title에 커서가 포커스되도록 함수호출
        form.title.focus();
        //**4.08 form 요소에서 action이 발동되지 않도록 하는 boolean 반환
        return false;
      }

      //**4.08 만약 form속성의 content속성값이 빈문자열이면
      if(form.content == '') {
        //**4.08 알림창에 "내용을 입력해 주세요." 출력
        alert("내용을 입력해 주세요.");
        //**4.08 form변수의 content속성에 커서가 활성화되도록 함수 호출
        form.content.focus();
        //**4.08 form 요소에서 action이 발동되지 않도록 하는 boolean 반환
        return false;
      }

      //**4.08formCheck 함수의 리턴값으로 true 지정
      return true;
    };

    $("#writeNewBtn").on("click", function () {
      location.href = "<c:url value='/board/write'/>";
    });

    $("#writeBtn").on("click", function () {
      let form = $("#form");
      form.attr("action", "<c:url value='/board/write'/>");
      form.attr("method", "post");

      //**4.08 만약, formCheck() 함수의 값이 true이면 form 요소의 action 기능 발동하는 함수 호출
      if(formCheck()) form.submit();
    });

    $("#modifyBtn").on("click", function () {
      let form = $("#form");
      let isReadonly = $("#form input[name=title]").attr('readonly');

      // 1. 읽기 상태이면, 수정 상태로 변경
      if (isReadonly == 'readonly') {
        $(".writing-header").html("게시판 수정");
        $("#form input[name=title]").attr('readonly', false);
        $("#form textarea").attr('readonly', false);
        $("#modifyBtn").html("<i class='fa fa-pencil'></i> 등록");
        return;
      }

      // 2. 읽기전용 상태가 아니면, 수정된 내용을 서버로 전송, if문 안 씀
      //**4.08 form 변수의 action 속성값을 c:url 태그를 이용해 다음과 같이 지정
      //queryString 변수 사용해 modify 요청 주소 생성(검색조건 및 검색어 포함된)
      form.attr("action", "<c:url value='/board/modify'/>${searchCondition.queryString}");
      form.attr("method", "post");
      if (formCheck()) form.submit();
    });

    $("#removeBtn").on("click", function () {
      if (!confirm("정말로 삭제하시겠습니까?")) return;

      let form = $("#form");
      //**4.08 SearchCondition 객체 내 queryString 생성 메서드 사용해 remove 요청 주소 생성
      form.attr("action", "<c:url value='/board/remove'/>${searchCondition.queryString}");
      form.attr("method", "post");
      form.submit();
    });

    $("#listBtn").on("click", function () {
      //**4.08 SearchCondition 객체 내 queryString 생성 메서드 사용해 modify 요청 주소 생성
      location.href = "<c:url value='/board/list'/>${searchCondition.queryString}";
    });


    // 이하 댓글 영역 관련 코드

    // 00:00 형식 만들기
    let addZero = function(value=1){
      return value > 9 ? value : "0"+value;
    }

    let dateToString = function(ms=0) {
      let date = new Date(ms);

      let yyyy = date.getFullYear();
      let mm = addZero(date.getMonth() + 1);
      let dd = addZero(date.getDate());

      let HH = addZero(date.getHours());
      let MM = addZero(date.getMinutes());
      let ss = addZero(date.getSeconds());

      return yyyy+"."+mm+"."+dd+ " " + HH + ":" + MM + ":" + ss;
    }

    //댓글 목록 생성
    let toHtml = function (comments) {
      let tmp = "<ul>";

      comments.forEach(function (comment) {
        tmp += "<li data-cno=" + comment.cno;
        tmp += " data-pcno=" + comment.pcno;
        tmp += " data-bno=" + comment.bno;
        if(comment.pcno != comment.cno) tmp += ' class="reply"';
        tmp += ">";
        tmp += '<span class="comment-img"><i class="fa fa-user-circle" aria-hidden="true"></i></span>';
        tmp += '<div class="comment-area">';
        tmp += '<div class="commenter">'+ tagEscape(comment.commenter)+'</div>'
        tmp += '<div class="comment-content">'+ tagEscape(comment.comment) +'</div>';
        tmp += '<div class="comment-bottom">';
        tmp += '<span class="up_date">'+dateToString(comment.up_date)+'</span>';
        tmp += '<a href="#" class="btn-write-reply" data-cno="' +comment.cno+ '" data-bno="'+comment.bno+'" data-pcno="'+comment.pcno+'">답글쓰기</a>';
        tmp += '<a href="#" class="btn-modify-comment" data-cno="' +comment.cno+ '" data-bno="'+comment.bno+'" data-pcno="'+comment.pcno+'">수정</a>';
        tmp += '<a href="#" class="btn-delete" data-cno="' +comment.cno+ '" data-bno="'+comment.bno+'" data-pcno="'+comment.pcno+'">삭제</a>';
        tmp += '</div>';
        tmp += '</div>';
        tmp += "</li>";
      });
      return tmp + "</ul>";
    }

    let showList = function (bno) {
      $.ajax({
        type: "get",
        url: "/ch4/comments?bno=" + bno,
        success: function (result) {
          $("#commentList").html(toHtml(result));
        },
        error: function () {
          alert("오류가 발생했습니다. 다시 확인 후 시도하시거나 관리자에게 문의 부탁드립니다.");
        }
      });
    }

    showList(bno);

    // 댓글 등록
    $("#btn-write-comment").on("click", function (e) {
      e.preventDefault();
      let comment = tagEscape($("#commentInputText").val());
      if (comment.trim() == '') {
        alert("내용을 입력해주세요.");
        $("input[name=comment]").focus();
        return;
      }

      $.ajax({
        type: "post",
        url: "/ch4/comments/?bno=" + bno,
        headers: {"content-type": "application/json"},
        data: JSON.stringify({bno: bno, comment: comment}),
        success: function (result) {
          alert(result+": 성공적으로 등록되었습니다.");
          showList(bno);
        },
        error: function () {
          alert("오류가 발생했습니다. 처음부터 다시 시도하시거나 관리자에게 문의 부탁드립니다.");
        }
      });
      //댓글 작성 완료 후 입력폼 초기화
      $('#commentInputText').val('');

    });

    // 댓글 삭제
    $("#commentList").on("click",".btn-delete",function(e){
      e.preventDefault();
      let target = e.target;
      let cno = target.getAttribute("data-cno");
      let bno = target.getAttribute("data-bno");

      $.ajax({
        type: "delete",
        url: "/ch4/comments/" + cno + "?bno=" + bno,
        success: function (result) {
          alert(result+": 성공적으로 삭제되었습니다.");
          showList(bno);
        },
        error: function () {
          alert("오류가 발생했습니다. 다시 확인 후 시도하시거나 관리자에게 문의 부탁드립니다.");
        }
      });

    });


    // 답글 등록 준비
    $("#commentList").on("click", ".btn-write-reply", function (e) {
      e.preventDefault();
      let target = e.target;
      let cno = target.getAttribute("data-cno");
      let bno = target.getAttribute("data-bno");
      let pcno = target.getAttribute("data-pcno");

      let repForm = $("#reply-writebox");
      repForm.appendTo($("li[data-cno=" + cno + "]"));
      $("#btn-write-reply").attr("data-pcno", pcno);
      $("#btn-write-reply").attr("data-bno",  bno);
      repForm.css("display", "block");
    });

    //답글 등록
    $("#btn-write-reply").on("click", function (e) {
      e.preventDefault();
      let comment = tagEscape($("#replyInputText").val());
      if(comment.trim() == '') {
        alert("내용을 입력해주세요.");
        $("#replyInputText").focus();
        return;
      }

      let pcno = $(this).attr("data-pcno");
      let bno = $(this).attr("data-bno");

      $.ajax({
        type: "post",
        url: "/ch4/comments?bno=" + bno,
        headers: {"content-type":"application/json"},
        data: JSON.stringify({pcno: pcno, comment: comment}),
        success: function (result) {
          alert(result+": 정상적으로 등록되었습니다.");
          showList(bno);
        },
        error: function () {
          alert("오류가 발생했습니다. 다시 확인 후 시도하시거나 관리자에게 문의 부탁드립니다.");
        }
      });
      $("#replyInputText").val('');
      $("#reply-writebox").css("display", "none");
      $("#reply-writebox").appendTo("body");

    });

    // 답글 등록 취소
    $("#btn-cancel-reply").click(function(){
      $("#reply-writebox").css("display", "none");
    });


    // 댓글 수정 준비
    $("#commentList").on("click",".btn-modify-comment",function(e){
      e.preventDefault();
      let target = e.target;
      let cno = target.getAttribute("data-cno");
      let bno = target.getAttribute("data-bno");
      let li = $("li[data-cno="+cno+"]");
      let commenter = $(".commenter", li).text();
      let comment = $(".comment-content", li).text();

      $("#modalWin .commenter").text(commenter);
      $("#modalWin textarea").val(comment);
      $("#btn-write-modify").attr("data-cno", cno);
      $("#btn-write-modify").attr("data-bno", bno);

      $("#modalWin").css("display","block");
    });

    // 댓글 수정
    $("#btn-write-modify").click(function(e){
      e.preventDefault();
      let target = e.target;
      let cno = target.getAttribute("data-cno");
      let bno = target.getAttribute("data-bno");
      let comment = tagEscape($("#commentModifyText").val());

      if (comment.trim() == '') {
        alert("내용을 입력해주세요.");
        $("#commentModifyText").focus();
        return;
      }

      $.ajax({
        type: "patch",
        url: "/ch4/comments/" + cno,
        headers: {"content-type": "application/json"},
        data: JSON.stringify({cno: cno, comment: comment}),
        success: function (result) {
          alert(result+": 성공적으로 등록되었습니다.");
          showList(bno);
        },
        error: function () {
          alert("오류가 발생했습니다. 다시 확인 후 시도하시거나 관리자에게 문의 부탁드립니다.");
        }
      });

      $("#commentModifyText").val('');

      // 2. 모달 창을 닫는다.
      $(".close").trigger("click");
    });

    $(".close").click(function(){
      $("#modalWin").css("display","none");
    });



  });
</script>

</body>
</html>