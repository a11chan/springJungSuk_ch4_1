<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
  <title>Document</title>
  <link rel="stylesheet" href="<c:url value='/css/comment2.css'/>">
</head>
<body>
<section class="commentArea">
<div id="commentList">
  <ul>
    <li class="comment-item" data-cno="1" data-bno="1070">
                <span class="comment-img">
                    <i class="fa fa-user-circle" aria-hidden="true"></i>
                </span>
      <div class="comment-area">
        <div class="commenter">asdf</div>
        <div class="comment-content">asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf
        </div>
        <div class="comment-bottom">
          <span class="up_date">2022.01.01 23:59:59</span>
          <a href="#" class="btn-write"  data-cno="1" data-bno="1070" data-pcno="">답글쓰기</a>
          <a href="#" class="btn-modify" data-cno="1" data-bno="1070" data-pcno="">수정</a>
          <a href="#" class="btn-delete" data-cno="1" data-bno="1070" data-pcno="">삭제</a>
        </div>
      </div>
    </li>
    <li class="comment-item" data-cno="2" data-bno="1070">
                <span class="comment-img">
                    <i class="fa fa-user-circle" aria-hidden="true"></i>
                </span>
      <div class="comment-area">
        <div class="commenter">qwer</div>
        <div class="comment-content">qwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwerqwer
        </div>
        <div class="comment-bottom">
          <span class="up_date">2022.01.01 23:59:59</span>
          <a href="#" class="btn-write"  data-cno="2" data-bno="1070" data-pcno="">답글쓰기</a>
          <a href="#" class="btn-modify" data-cno="2" data-bno="1070" data-pcno="">수정</a>
          <a href="#" class="btn-delete" data-cno="2" data-bno="1070" data-pcno="">삭제</a>
        </div>
      </div>
    </li>
  </ul>

  <div id="comment-writebox">
    <div class="commenter commenter-writebox">${id}</div>
    <div class="comment-writebox-content">
      <textarea name="" id="" cols="30" rows="3" placeholder="댓글을 남겨보세요"></textarea>
    </div>
    <div id="comment-writebox-bottom">
      <div class="register-box">
        <a href="#" class="btn" id="btn-write-comment">등록</a>
      </div>
    </div>
  </div>
</div>
<div id="reply-writebox">
  <div class="commenter commenter-writebox">${id}</div>
  <div class="reply-writebox-content">
    <textarea name="" id="" cols="30" rows="3" placeholder="댓글을 남겨보세요"></textarea>
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
        <textarea name="" id="" cols="30" rows="5" placeholder="댓글을 남겨보세요"></textarea>
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
<script>
  let id = 'asdf';

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

  $(document).ready(function(){
    $("a.btn-write").click(function(e){
      let target = e.target;
      let cno = target.getAttribute("data-cno")
      let bno = target.getAttribute("data-bno")

      let repForm = $("#reply-writebox");
      repForm.appendTo($("li[data-cno="+cno+"]"));
      repForm.css("display", "block");
      repForm.attr("data-pcno", pcno);
      repForm.attr("data-bno",  bno);
    });

    $("#btn-cancel-reply").click(function(e){
      $("#reply-writebox").css("display", "none");
    });

    $("a.btn-modify").click(function(e){
      let target = e.target;
      let cno = target.getAttribute("data-cno");
      let bno = target.getAttribute("data-bno");
      let pcno = target.getAttribute("data-pcno");
      let li = $("li[data-cno="+cno+"]");
      let commenter = $(".commenter", li).first().text();
      let comment = $(".comment-content", li).first().text();

      $("#modalWin .commenter").text(commenter);
      $("#modalWin textarea").text(comment);
      $("#btn-write-modify").attr("data-cno", cno);
      $("#btn-write-modify").attr("data-pcno", pcno);
      $("#btn-write-modify").attr("data-bno", bno);

      // 팝업창을 열고 내용을 보여준다.
      $("#modalWin").css("display","block");
    });

    $("a.btn-delete").click(function(e){
      alert("delete");
    });

    $("#btn-write-modify").click(function(){
      // 1. 변경된 내용을 서버로 전송
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