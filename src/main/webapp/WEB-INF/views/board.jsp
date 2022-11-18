<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<%@ page session="true" %>
<c:set var="loginId" value="${sessionScope.id}"/>
<c:set var="loginOutLink" value="${loginId=='' ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId=='' ? 'Login' : 'ID='+=loginId}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>fastcampus</title>
  <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
  <style>
    * {
      box-sizing: border-box;
      margin: 0;
      padding: 0;
      font-family: "Noto Sans KR", sans-serif;
    }

    .container {
      width: 50%;
      margin: auto;
    }

    .writing-header {
      position: relative;
      margin: 20px 0 0 0;
      padding-bottom: 10px;
      border-bottom: 1px solid #323232;
    }

    input {
      width: 100%;
      height: 35px;
      margin: 5px 0px 10px 0px;
      border: 1px solid #e9e8e8;
      padding: 8px;
      background: #f8f8f8;
      outline-color: #e6e6e6;
    }

    textarea {
      width: 100%;
      background: #f8f8f8;
      margin: 5px 0px 10px 0px;
      border: 1px solid #e9e8e8;
      resize: none;
      padding: 8px;
      outline-color: #e6e6e6;
    }

    .frm {
      width: 100%;
    }

    .btn {
      background-color: rgb(236, 236, 236); /* Blue background */
      border: none; /* Remove borders */
      color: black; /* White text */
      padding: 6px 12px; /* Some padding */
      font-size: 16px; /* Set a font size */
      cursor: pointer; /* Mouse pointer on hover */
      border-radius: 5px;
    }

    .btn:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>
<div id="menu">
  <ul>
    <li id="logo">fastcampus</li>
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
<div class="container">
  <h2 class="writing-header">게시판 ${mode=="new" ? "글쓰기" : "읽기"}</h2>
  <form id="form" class="frm" action="" method="post">
    <input type="hidden" name="bno" value="${boardDto.bno}">

    <%--**4.08 사용자 입력이 가능한 부분은 c:out 태그로 출력--%>
    <input name="title" type="text" value="<c:out value="${boardDto.title}"/>"
           placeholder="  제목을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}><br>
    <textarea name="content" rows="20" placeholder=" 내용을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}>
      <%--**4.08 사용자 입력이 가능한 부분은 c:out 태그로 출력--%>
      <c:out value="${boardDto.content}" />
    </textarea><br>

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
</div>
<script>
  $(document).ready(function () {
    //**4.08 익명함수를 선언하여 formCheck 변수에 저장
    let formCheck = function () {
      //**4.08 HTML 문서에서 form 아이디를 가진 요소를 form 변수에 저장
      let form = document.getElementById("from");

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
      let isReadonly = $("input[name=title]").attr('readonly');

      // 1. 읽기 상태이면, 수정 상태로 변경
      if (isReadonly == 'readonly') {
        $(".writing-header").html("게시판 수정");
        $("input[name=title]").attr('readonly', false);
        $("textarea").attr('readonly', false);
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
  });
</script>
</body>
</html>