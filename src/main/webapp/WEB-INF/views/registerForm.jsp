<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
      height:600px;
      display : flex;
      flex-direction: column;
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
      margin: 40px 0 30px 0;
    }

    .msg {
      height: 30px;
      text-align:center;
      font-size:16px;
      color:red;
      margin-bottom: 20px;
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
    <% /* ${URLDecoder.decode(param.msg, "utf-8")} */ %>
    <form:errors path="id"/>
    <form:errors path="pwd"/>
  </div>
  <label>아이디</label>
  <input class="input-field" type="text" name="id" placeholder="8~12자리의 영대소문자와 숫자 조합">
  <label>비밀번호</label>
  <input class="input-field" type="password" name="pwd" placeholder="8~12자리의 영대소문자와 숫자 조합">
  <label>이름</label>
  <input class="input-field" type="text" name="name" placeholder="홍길동">
  <label>이메일</label>
  <input class="input-field" type="text" name="email" placeholder="example@fastcampus.co.kr">
  <label>생일</label>
  <input class="input-field" type="text" name="birth" placeholder="2020/12/31">
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

    if(frm.id.value.length<3) {
      setMessage('id의 길이는 3이상이어야 합니다.', frm.id);
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