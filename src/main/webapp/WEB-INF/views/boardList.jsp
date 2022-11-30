<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<%--**4.08 페이지 요청 시 세션을 시작하도록 페이지 지시자로 설정 --%>
<%@ page session = "true" %>
<%--**4.08 c:set으로 loginId 변수를 생성하여 세션 객체에서 id키의 속성값을 읽어와 저장--%>
<c:set var="loginId" value="${sessionScope.id}"/>
<%--**4.08 c:set으로 loginOutLink 변수 생성하여 아래의 문자열을 저장
  위에서 정의한 loginId 변수의 값이 ''이면 '/login/login', 그게 아니면 '/login/logout' --%>
<c:set var="loginOutLink" value="${loginId == '' ? '/login/login' : '/login/logout'}"/>
<%--**4.08 c:set으로 loginOut 변수 생성, 위에서 정의한 loginId 변수의 값이 ''이면 'Login', 아니면 'ID='+=loginId 문자열 저장--%>
<c:set var="loginOut" value="${loginId == '' ? 'Login' : 'ID='+=loginId}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>스프링의 정석 실습</title>
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

    a {
      text-decoration: none;
      color: black;
    }

    button,
    input {
      border: none;
      outline: none;
    }

    .board-container {
      width: 60%;
      height: 1200px;
      margin: 0 auto;
      /* border: 1px solid black; */
    }

    .search-container {
      background-color: rgb(253, 253, 250);
      width: 100%;
      height: 110px;
      border: 1px solid #ddd;
      margin-top: 10px;
      margin-bottom: 30px;
      display: flex;
      justify-content: center;
      align-items: center;
    }

    .search-form {
      height: 37px;
      display: flex;
    }

    .search-option {
      width: 100px;
      height: 100%;
      outline: none;
      margin-right: 5px;
      border: 1px solid #ccc;
      color: gray;
    }
    .search-option:focus {
      border: 1px solid var(--main-color);
    }

    .search-option > option {
      text-align: center;
    }

    .search-input {
      color: gray;
      background-color: white;
      border: 1px solid #ccc;
      height: 100%;
      width: 300px;
      font-size: 15px;
      padding: 5px 7px;
    }
    .search-input:focus {
      border: 1px solid var(--main-color);
    }

    .search-input::placeholder {
      color: gray;
    }

    .search-button {
      /* 메뉴바의 검색 버튼 아이콘  */
      width: 20%;
      height: 100%;
      background-color: rgb(22, 22, 22);
      color: rgb(209, 209, 209);
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 15px;
      cursor: pointer;
    }

    .search-button:hover {
      color: rgb(165, 165, 165);
    }

    table {
      border-collapse: collapse;
      width: 100%;
      border-top: 2px solid rgb(39, 39, 39);
    }

    tr:nth-child(even) {
      background-color: #f0f0f070;
    }

    th,
    td {
      width: 300px;
      text-align: center;
      padding: 10px 12px;
      border-bottom: 1px solid #ddd;
    }

    td {
      color: rgb(53, 53, 53);
    }

    .no {
      width: 150px;
    }

    .title {
      width: 50%;
    }

    td.title {
      text-align: left;
    }

    td.writer {
      text-align: left;
    }

    td.viewcnt {
      text-align: right;
    }

    .paging {
      color: black;
      width: 100%;
      align-items: center;
    }

    .page {
      color: black;
      padding: 6px;
      margin-right: 10px;
    }

    .paging-active {
      background-color: rgb(216, 216, 216);
      border-radius: 5px;
      color: rgb(24, 24, 24);
    }

    .paging-container {
      width: 100%;
      height: 70px;
      display: flex;
      margin-top: 50px;
      margin: auto;
    }

    .btn-write {
      background-color: rgb(236, 236, 236); /* Blue background */
      border: none; /* Remove borders */
      color: black; /* White text */
      padding: 6px 12px; /* Some padding */
      font-size: 16px; /* Set a font size */
      cursor: pointer; /* Mouse pointer on hover */
      border-radius: 5px;
      margin-left: 30px;
    }

  </style>
</head>
<body>
<div id="menu">
  <ul>
    <li id="logo">company</li>
    <li><a href="<c:url value='/'/>">Home</a></li>
    <li><a href="<c:url value='/board/list'/>">Board</a></li>
    <li>
      <%--**4.08 <a>의 href 속성값으로 EL변수의 loginOutLink 사용, <a>의 내용으로는 loginOut 변수 사용 --%>
        <a href="<c:url value='${loginOutLink}'/>">${loginOut}</a>
    </li>
    <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
    <li><a href=""><i class="fa fa-search"></i></a></li>
  </ul>
</div>
<script>
  let msg = "${msg}";
  //**4.08 msg 키값이 "LIST_ERR", ,"READ_ERR", "DEL_ERR"와 일치하면 각각 알림창으로 메시지 출력
  if (msg == "LIST_ERR") alert("게시물 목록을 가져오는데 실패했습니다. 다시 시도해 주세요.");
  if (msg == "READ_ERR") alert("삭제되었거나 없는 게시물입니다.");
  if (msg == "DEL_ERR") alert("삭제되었거나 없는 게시물입니다.");

  if (msg == "DEL_OK") alert("성공적으로 삭제되었습니다.");
  if (msg == "WRT_OK") alert("성공적으로 등록되었습니다.");
  if (msg == "MOD_OK") alert("성공적으로 수정되었습니다.");
</script>
<div style="text-align:center">
  <div class="board-container">
    <div class="search-container">
      <%--**4.08 검색조건을 "/board/list"로 GET방식으로 보내는 <form> 정의 --%>
      <form action="<c:url value="/board/list"/> " method="get" class="search-form" >
        <%--**4.08 목록상자에서 선택한 검색조건이 서버로 전송될 수 있게 name 속성값 설정 --%>
        <select class="search-option" name="option">
          <%--**4.08 기존 option값이 'A'이거나 ""이면 기본값으로 선택되도록 태그 속성값 지정,
          ph.sc가 아닌 sc로 해도 되지 않을까? 실험 해보니 안 됨. ph.sc 해야 나옴
          아마 SearchCondition 클래스에 @Controller 기능이 있는 애너테이션이 없어서 그런 듯
          즉, 객체 생성이 안 되었기 때문. ph 객체는 컨트롤러에서 직접 객체생성을 해줬기 때문에
          컨트롤러 기능을 하는 애너테이션 없이도 참조 가능한 걸로 보임--%>
          <option value="A" ${ph.sc.option == 'A' || ph.sc.option =='' ? "selected" : ""}>제목+내용</option>
          <%--**4.08 기존 option값이 'T'이면 기본값으로 선택되도록 태그 속성값 지정--%>
          <option value="T" ${ph.sc.option == 'T' ? "selected" : ""}>제목만</option>
          <%--**4.08 기존 option값이 'W'이면 기본값으로 선택되도록 태그 속성값 지정--%>
          <option value="W" ${ph.sc.option == 'W' ? "selected" : ""}}>작성자</option>
        </select>

        <%--**4.08 검색 후 검색란에 검색어 남아있을 수 있도록 value값 불러오기--%>
        <input type="text" name="keyword" class="search-input" type="text" value="${ph.sc.keyword}"
               placeholder="검색어를 입력해주세요">

        <%--**4.08 input으로 submit 역할을 하는 '검색'버튼 생성--%>
        <input type="submit" class="search-button" value="검색">
      </form>
      <button id="writeBtn" class="btn-write" onclick="location.href='<c:url value="/board/write"/>'"><i
          class="fa fa-pencil"></i> 글쓰기
      </button>
    </div>

    <table>
      <tr>
        <th class="no">번호</th>
        <th class="title">제목</th>
        <th class="writer">이름</th>
        <th class="regdate">등록일</th>
        <th class="viewcnt">조회수</th>
      </tr>
      <c:forEach var="boardDto" items="${postingList}">
        <tr>
          <td class="no">${boardDto.bno}</td>
          <td class="title">
            <%-- ph.sc에 queryString 필드가 없으면 getQueryString으로 자동 호출하는 것 같음 --%>
            <a href="<c:url value='/board/read'/>${ph.sc.queryString}&bno=${boardDto.bno}">
              <%--**4.08 <a>안에 들어갈 게시물 제목을 c:out 태그로 내용 표현--%>
              <c:out value="${boardDto.title}"/> <span>(<c:out value="${boardDto.comment_cnt}"/>)</span>
            </a>
          </td>
          <td class="writer">${boardDto.writer}</td>
          <%--**4.08
            게시물 등록 시간이 오늘날짜 0시보다 크면
            <td class="regdate"> 안에 현재 시각을 HH:mm 형식으로 표현
            게시물 등록 시간이 어제이면
            <td class="regdate"> 안에 등록된 날짜를 "yyyy-MM-dd" 형식으로 표현
            c:choose, c:when, c:otherwise, fmt:formatDate를 사용
          --%>
          <c:choose>
            <c:when test="${boardDto.reg_date.time > startOfToday}">
              <td class="regdate">
                <fmt:formatDate value="${boardDto.reg_date}" pattern="HH:mm" type="time"/>
              </td>
            </c:when>
            <c:otherwise>
              <td class="regdate">
                <fmt:formatDate value="${boardDto.reg_date}" pattern="yyyy-MM-dd" type="date"/>
              </td>
            </c:otherwise>
          </c:choose>
          <td class="viewcnt">${boardDto.view_cnt}</td>
        </tr>
      </c:forEach>
    </table>
    <br>
    <div class="paging-container">
      <div class="paging">
        <%--**4.08
          서버에서 총게시글 수가 null 또는 0이면 다음 내용 출력, c:if 사용
          <div> 게시물이 없습니다.</div>
        --%>
        <c:if test="${totalPostings == null || totalPostings==0}">
          <div> 게시물이 없습니다.</div>
        </c:if>
        <%--**4.08
          총 게시글 수가 null이 아니고 0도 아니면 페이지 네비게이션 출력
        --%>
        <c:if test="${totalPostings != null && totalPostings != 0}">
          <c:if test="${ph.showPrev}">
            <%--**4.08
            쿼리스트링빌더로 만들어진 주소를 c:url을 사용해 이전 네비 페이지로 갈 수 있게 넣기 --%>
            <a class="page" href="<c:url value='/board/list${ph.sc.getQueryString(ph.beginNaviPage - 1)}'/>"/>">&lt;</a>
          </c:if>
          <c:forEach var="i" begin="${ph.beginNaviPage}" end="${ph.endNaviPage}">
            <%--**4.08 현재 페이지가 페이징 번호와 일치할 때 삼항연산자로
            <a>의 클래스에 "paging-active" 추가, 같지 않을 때는 빈문자열--%>
            <a class="page ${i eq ph.sc.currentPage ? 'paging-active' : ''}"
              <%--**4.08 쿼리스트링빌더로 만들어진 네비 페이지 주소를
              href의 속성값에 c:url 태그로 링크 설정 --%>
              href="<c:url value='/board/list${ph.sc.getQueryString(i)}'/>">${i}</a>
          </c:forEach>
          <c:if test="${ph.showNext}">
            <%--**4.08
            쿼리스트링빌더로 만들어진 주소를 c:url을 사용해 다음 네비 페이지로 이동할 수 있게 하기 --%>
            <a class="page" href="<c:url value='/board/list${ph.sc.getQueryString(ph.endNaviPage + 1)}'/>">&gt;</a>
          </c:if>
        </c:if>
      </div>
    </div>
  </div>
</div>
</body>
</html>