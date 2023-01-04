# Spring MVC 패턴 게시판 구현 프로젝트

>로그인, 회원가입, 게시글, 댓글 CRUD 구현  
>http://43.200.5.248:8080/ch4/

</br>

## 1. 제작 기간
* 2022/09/15 ~ 2022/11/22  
(강의 수강 기간 포함)  

</br>

## 2. 사용 기술
#### `Back-end`
  - Java 11
  - Spring 5.0.7
  - Maven 3.8.1
  - MyBatis 3.5.11
  - MyBatis-Spring 2.0.7
  - MySQL 8.0.28
#### `Front-end`
  - jQuery 1.11.3

</br>

### 2.1 프로젝트 규모
![](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F0f258e48-f75e-4878-9708-be13982c890e%2FUntitled.png?id=8b6ece8a-1cab-46e2-9fb7-17b1f02df066&table=block&spaceId=d45b6527-cc9f-4af9-b7e8-975463d8b2e5&width=2000&userId=504d5d0d-ce4c-40f0-8ccd-2a8fd23e22dc&cache=v2)

</br>

## 3. ERD 설계
![](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Ff129d24f-e486-445d-bc44-297a4d280150%2FUntitled.png?id=f619e8f6-2c9e-4b91-be93-ebb471605512&table=block&spaceId=d45b6527-cc9f-4af9-b7e8-975463d8b2e5&width=2000&userId=504d5d0d-ce4c-40f0-8ccd-2a8fd23e22dc&cache=v2)
[원본 보기🔍](https://www.erdcloud.com/p/FiJ24GFdndCGSCDXG)

</br>

## 4. 핵심 기능
  - 회원가입, 로그인, ID기억  
  - 게시글 조회, 작성, 수정, 삭제  
  - 댓글 조회, 작성, 수정, 삭제  

### 4.1. 전체 흐름  
![](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F1be62653-6a5a-49d1-8e6e-b0d00c39d0d2%2FUntitled.png?table=block&id=1f3c28d4-d0c4-4623-8538-85f75f32e182&spaceId=d45b6527-cc9f-4af9-b7e8-975463d8b2e5&width=2000&userId=504d5d0d-ce4c-40f0-8ccd-2a8fd23e22dc&cache=v2)

</br>

### 4.2. @Controller 목록  
- [LoginController.java](https://github.com/a11chan/springJungSuk_ch4_1/blob/fa79de6844cd94dbfaeebb0e370fee2c779de89c/src/main/java/com/fastcampus/ch4/controller/LoginController.java)
- [RegisterController.java](https://github.com/a11chan/springJungSuk_ch4_1/blob/fa79de6844cd94dbfaeebb0e370fee2c779de89c/src/main/java/com/fastcampus/ch4/controller/RegisterController.java)
- [BoardController.java](https://github.com/a11chan/springJungSuk_ch4_1/blob/fa79de6844cd94dbfaeebb0e370fee2c779de89c/src/main/java/com/fastcampus/ch4/controller/BoardController.java)
- [CommentController.java](https://github.com/a11chan/springJungSuk_ch4_1/blob/fa79de6844cd94dbfaeebb0e370fee2c779de89c/src/main/java/com/fastcampus/ch4/controller/CommentController.java)

</br>

### 4.3. @Service 목록  
- [BoardServiceImpl.java](https://github.com/a11chan/springJungSuk_ch4_1/blob/fa79de6844cd94dbfaeebb0e370fee2c779de89c/src/main/java/com/fastcampus/ch4/service/BoardServiceImpl.java)
- [CommentServiceImpl.java](https://github.com/a11chan/springJungSuk_ch4_1/blob/fa79de6844cd94dbfaeebb0e370fee2c779de89c/src/main/java/com/fastcampus/ch4/service/CommentServiceImpl.java)

</br>

### 4.4. @Repository 목록  
- [UserDaoImpl.java](https://github.com/a11chan/springJungSuk_ch4_1/blob/fa79de6844cd94dbfaeebb0e370fee2c779de89c/src/main/java/com/fastcampus/ch4/dao/UserDaoImpl.java)
- [BoardDaoImpl.java](https://github.com/a11chan/springJungSuk_ch4_1/blob/fa79de6844cd94dbfaeebb0e370fee2c779de89c/src/main/java/com/fastcampus/ch4/dao/BoardDaoImpl.java)
- [CommentDaoImpl.java](https://github.com/a11chan/springJungSuk_ch4_1/blob/fa79de6844cd94dbfaeebb0e370fee2c779de89c/src/main/java/com/fastcampus/ch4/dao/CommentDaoImpl.java)

</br>

### 4.5. DTO 목록  
- [User.java](https://github.com/a11chan/springJungSuk_ch4_1/blob/fa79de6844cd94dbfaeebb0e370fee2c779de89c/src/main/java/com/fastcampus/ch4/domain/User.java)
- [BoardDto.java](https://github.com/a11chan/springJungSuk_ch4_1/blob/fa79de6844cd94dbfaeebb0e370fee2c779de89c/src/main/java/com/fastcampus/ch4/domain/BoardDto.java)
- [CommentDto.java](https://github.com/a11chan/springJungSuk_ch4_1/blob/fa79de6844cd94dbfaeebb0e370fee2c779de89c/src/main/java/com/fastcampus/ch4/domain/CommentDto.java)

</br>

## 5. 트러블 슈팅  
<details>
<summary>
&lt;a&gt;로 작성된 버튼 클릭 시 Cannot read properties of null 에러 발생
</summary>
<div markdown="1">

- &lt;a&gt; 속성에 `href=”#”` 을 추가
- 클릭 이벤트 함수 최상단에 `e.preventDefault()` 추가하여 해결

</div>
</details>

<details>
<summary>
&lt;textarea&gt; 공백 및 내용 출력 문제
</summary>

- &lt;textarea&gt;에 값을 출력할 때는 val() 사용, text()로 하면 새로고침 해야만 값이 보임
- [&lt;textarea&gt;에 공백 제거하는 방법](https://okky.kr/articles/292680)

</details>

<details>
<summary>
XXS 방지를 위한 LUCY 필터는 @RequestBody로 전달되는 JSON 요청을 처리하지 않음
</summary>

- 임시로 &lt;, &gt;를 특수문자로 바꿔 출력하는 함수 추가
  - ```javascript
    function tagEscape(string) {
      string = string.replace(/\</g, "〈");
      string = string.replace(/\>/g, "〉");
      return string;
    }
    ```
  - [참고 사이트 링크](https://oingdaddy.tistory.com/375)

- 추후 적용할 다른 방법([Spring Boot에서 JSON API에 XSS Filter 적용하기](https://jojoldu.tistory.com/470))

</details>
