<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
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

    .form {
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
    .title {
      font-size : 50px;
      margin: 40px 0 30px 0;
    }
  </style>
  <title>스프링의 정석 실습</title>
</head>
<body>
 <div class="form">
  <div class="title">Register</div>
  <label for="">아이디</label>
  <input class="input-field" type="text" name="id" value="${user.id}" disabled>
  <label for="">비밀번호</label>
  <input class="input-field" type="text" name="pwd" value="${user.pwd}" disabled>
  <label for="">이름</label>
  <input class="input-field" type="text" name="name" value="${user.name}" disabled>
  <label for="">이메일</label>
  <input class="input-field" type="text" name="email" value="${user.email}" disabled>
  <label for="">생일</label>
<%--  <input class="input-field" type="text" name="birth" value="${user.birth}" disabled>--%>
  <input class="input-field" type="text" name="birth" value="<fmt:formatDate value="${user.birth}" pattern="yyyy-MM-dd" type="date"/>" disabled>
  <label for="">SNS</label>
  <input class="input-field" type="text" name="sns" value="${user.sns}" disabled>
  <button onclick="location.href='<c:url value="/"/>'">처음으로</button>
 </div>

</body>
</html>