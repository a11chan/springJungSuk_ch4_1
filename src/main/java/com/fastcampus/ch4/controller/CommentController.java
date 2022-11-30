package com.fastcampus.ch4.controller;


import com.fastcampus.ch4.domain.CommentDto;
import com.fastcampus.ch4.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

//@Controller
//@ResponseBody
//**4.11 모든 메서드가 ResponseBody로 응답하는 컨트롤러 애너테이션
@RestController
public class CommentController {
  //**4.11 CommentService 타입 매칭 자동 주입
  @Autowired
  CommentService service;


//  {
//    "pcno":0,
//    "comment":"this is a modify test",
//    "commenter":"asdf"
//  }
  // 댓글을 수정하는 메서드 // PATCH /ch4/comments/2924
  //**4.11 "/comments/cno" 경로의 수정요청을 처리하는 애너테이션
  @PatchMapping("/comments/{cno}")
  //@ResponseBody
  //**4.11 String을 감싸서 Http상태코드를 같이 반환하는 public modify 메서드 선언
    //매개변수로는 dto, cno, session(for 로그인 id 획득)
    //댓글 DTO에 JSON 요청 데이터를 저장, cno에는 URL에 입력된 변수를 그대로 대입하는 애너테이션 붙임
  public ResponseEntity<String> modify(@RequestBody CommentDto dto
      , @PathVariable Integer cno , HttpSession session) {
    String commenter = (String) session.getAttribute("id");
      //View단 완성 시 적용, 일단 테스트를 위해 아래처럼 하드코딩
    //dto의 commenter에 위에서 선언한 commenter 참조변수 대입
    dto.setCommenter(commenter);
    //dto의 cno에 매개변수로 받은 cno 저장
    dto.setCno(cno);
    //dto.soutv
    System.out.println(dto);

    //try문
    try {
      //dto를 통해 댓글 수정 처리한 결과가 1이 아니면 "Modify failed." 메시지를 인수로 하여 Exception 예외 던짐
      if(service.modify(dto)!=1) throw new Exception("Modify failed.");
      // "MOD_OK"와 Http상태코드 200을 반환
      return new ResponseEntity<>("MOD_OK", HttpStatus.OK);

    //Catch문, 잡을 예외는 Exception
    } catch (Exception e) {
      //콘솔에 스택 출력
      e.printStackTrace();
      //"MOD_ERR"와 Http상태코드 400을 반환
      return new ResponseEntity<>("MOD_ERR", HttpStatus.BAD_REQUEST);
    }
  }


  //  {
  //    "pcno":0,
  //    "comment":"this is a test"
  //  }
  // 댓글을 등록하는 메서드 // /ch4/comments?bno=2924
  //**4.11 "/comments"경로의 Post요청을 처리하는 애너테이션
  @PostMapping("/comments")
  //@ResponseBody
  //**4.11 <String>과 응답코드를 반환하는 public write 메서드 선언
    //파라미터로는 dto, bno, session
      //애너테이션을 사용하여 JSON 요청 정보를 dto에 저장되도록 할 것
  public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno
      , HttpSession session) {
    String commenter = (String) session.getAttribute("id");
    //dto의 필드에 위 commenter를 저장
    dto.setCommenter(commenter);
    //dto의 필드에 파라미터로 받은 bno를 저장
    dto.setBno(bno);
    //dto.soutv
    System.out.println("dto = "+dto);

    //try문
    try {
      //만약 dto 등록 처리 결과가 1이 아니면 "Write failed."를 인수로 Exception 예외를 던짐
      if (service.write(dto) != 1)
        throw new Exception("Write failed.");

      //"WRT_OK"와 Http 상태코드 200을 리턴
      return new ResponseEntity<>("WRT_OK", HttpStatus.OK);

    //Exception 예외를 catch
    } catch(Exception e) {
      //콘솔에 스택 트레이스 출력
      e.printStackTrace();
      //"WRT_ERR"와 http 상태코드 400을 리턴
      return new ResponseEntity<>("WRT_ERR", HttpStatus.BAD_REQUEST);
    }
  }


  // DELETE /comments/80?bno=2924 <--삭제할 댓글 번호
  // 지정된 댓글을 삭제하는 메서드
  //**4.11  "/comments/cno" 경로의 delete요청을 처리하는 애너테이션
  //@ResponseBody
  @DeleteMapping("/comments/{cno}")
  //String과 Http상태코드를 반환하는 public remove 메서드 선언
    //파라미터로는 cno, bno, session
    //요청 URL 중 cno 위치에 있는 값을 그대로 cno에 대입하는 애너테이션 붙일 것
  public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session) {
    String commenter = (String) session.getAttribute("id");

    //try문
    try {
      //cno, bno, commenter를 매개변수로 댓글 삭제한 결과를 int rowCnt에 저장
      int rowCnt = service.remove(cno, bno, commenter);
      //만약 rowCnt != 1 -> "Delete Failed"를 인수로 Exception 예외 던짐
      if(rowCnt != 1)
        throw new Exception("Delete Failed");

      //"DEL_OK"와 http 200 코드를 리턴, 제네릭 타입 생략하기
      return new ResponseEntity<>("DEL_OK",HttpStatus.OK);

    //Exception 예외 catch
    } catch (Exception e) {
      //콘솔에 스택 트레이스 출력
      e.printStackTrace();
      //"DEL_ERR"와 http 400코드를 리턴, 제네릭 타입 생략
      return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
    }
  }



  //"/comments" 경로의 get 요청을 처리하는 메서드
  //게시물의 모든 댓글을 가져오는 메서드
  //@ResponseBody
  @GetMapping("/comments")
  //http 상태코드와 List<CommentDto>를 1세트로 반환하는 public list 메서드 선언
    //파라미터로는 bno  //bno가 어떻게 전달되는지는 2.18 참고
  public ResponseEntity<List<CommentDto>> list(Integer bno) {
    //CommentDto를 다루는 List형 list 참조변수 선언 후 null로 초기화
    List<CommentDto> list = null;

    //try문
    try {
      //bno에 해당하는 댓글 전체 목록을 list에 저장
      list = service.getList(bno);
      //list와 http 200 코드를 반환, 제네릭 타입 생략 가능
      return new ResponseEntity<>(list, HttpStatus.OK);
    //Exception 예외를 catch
    } catch (Exception e) {
      //콘솔에 스택 트레이스 출력
      e.printStackTrace();
      //http400 코드 반환, 제네릭 타입 생략 가능
        //생성자의 첫 번째 인수를 생략하면 null로 처리
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}