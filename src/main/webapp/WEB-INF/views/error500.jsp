<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value='/css/error.css'/>">
  <title>500 ERROR</title>
</head>
<body>
<section>
<div class="msgBox">
  <h1 class="msgBox__title">500</h1>
  <h2 class="msgBox__subTitle">죄송합니다, 서버 내부 오류입니다.</h2>
  <p class="msgBox__content"> 요청하신 서비스를 처리하는 과정에서 문제가 발생했습니다. 처음부터 다시 시도해주시거나 이메일로 문의 부탁드립니다.</p>
  <a class="msgBox__homeBtn" href="<c:url value='/'/>">처음으로 가기</a>
</div>
</section>
</body>
</html>