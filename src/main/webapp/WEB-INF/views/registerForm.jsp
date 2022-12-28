<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
  <style>
    @import url("<c:url value='/css/color.css'/>");

    * { box-sizing:border-box; }

    form {
      width:400px;
      height:fit-content;
      display : flex;
      flex-direction: column;
      justify-content: space-evenly;
      align-items:center;
      position : absolute;
      top:50%;
      left:50%;
      transform: translate(-50%, -50%) ;
      border: 1px solid var(--main-color);
      border-radius: 10px;
    }

    .input-field {
      width: 300px;
      height: 40px;
      border : 1px solid var(--main-color);
      border-radius:5px;
      padding: 0 10px;
      margin-bottom: 10px;
    }
    .input-field:focus {
      outline : 1px solid var(--main-color);
    }
    label {
      width:300px;
      height:30px;
      margin-top :4px;
    }

    button {
      background-color: var(--point-color);
      color : white;
      width:300px;
      height:50px;
      font-size: 17px;
      border : none;
      border-radius: 5px;
      margin : 20px 0 30px 0;
      cursor:pointer;
    }
    input[type="checkbox"] {
      accent-color: var(--main-color);
    }
    .title {
      font-size : 50px;
      margin: 30px 0 20px 0;
    }

    .msg {
      height: fit-content;
      text-align:center;
      font-size:16px;
      color:red;
    }

    .msg span {
      display:block;
      font-size: 0.9em;
    }

    .sns-chk {
      margin-top : 5px;
    }
    .sns-chk > label {
      cursor: pointer;
    }
  </style>
  <title>스프링의 정석 실습</title>
</head>
<body>
<%-- <form action="<c:url value="/register/save"/>" method="POST" onsubmit="return formCheck(this)"> --%>
<form:form modelAttribute="user"> <!-- @Valid User user -->
  <div class="title">Register</div>
  <div id="msg" class="msg">
    <%--${URLDecoder.decode(param.msg, "utf-8")}--%>
    <form:errors path="id"/>
    <form:errors path="pwd"/>
  </div>
  <label>아이디</label>
  <input class="input-field" type="text" name="id" required placeholder="5~12자리의 영대소문자와 숫자 조합" value="<c:out value='${user.id}'/>">
  <label>비밀번호</label>
  <input class="input-field" type="password" name="pwd" required placeholder="5~12자리의 영대소문자와 숫자 조합" value="<c:out value='${user.pwd}'/>">
  <label>이름</label>
  <input class="input-field" type="text" name="name" required placeholder="홍길동" value="<c:out value='${user.name}'/>">
  <label>이메일</label>
  <input class="input-field" type="text" name="email" required placeholder="example@fastcampus.co.kr" value="<c:out value='${user.email}'/>">
  <label>생일</label>
  <input class="input-field" type="text" name="birth" required placeholder="2020-12-31" value='<fmt:formatDate value="${user.birth}" pattern="yyyy-MM-dd" type="date"/>'>
  <label>SNS</label>
  <div class="sns-chk">
    <label><input type="checkbox" name="sns" value="facebook"/>페이스북</label>
    <label><input type="checkbox" name="sns" value="kakaotalk"/>카카오톡</label>
    <label><input type="checkbox" name="sns" value="instagram"/>인스타그램</label>
  </div>
  <button>회원 가입</button>
  <%-- </form> --%>
</form:form>
<script>
  function formCheck(frm) {
    var msg ='';

    if(frm.id.value.length<5 || frm.id.value == "" || frm.id.value === null ) {
      setMessage('아이디의 길이는 5~12 이내여야 합니다.', frm.id);
      return false;
    }

    if(frm.pwd.value.length<5 || frm.pwd.value == "" || frm.pwd.value === null ) {
      setMessage('비밀번호의 길이는 5~12 이내여야 합니다.', frm.pwd);
      return false;
    }

    return true;
  }

  function setMessage(msg, element){
    document.getElementById("msg").innerHTML = `<i class="fa fa-exclamation-circle"> ${'${msg}'}</i>`;

    if(element) {
      element.select();
    }
  }
</script>
</body>
</html>