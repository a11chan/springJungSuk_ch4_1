package com.fastcampus.ch4.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.ch4.domain.CommentDto;
import com.fastcampus.ch4.service.CommentService;

//@Controller
//@ResponseBody
@RestController
public class CommentController {
  @Autowired
  CommentService service;

  //  {
//    "pcno":0,
//    "comment":"this is a test",
//    "commenter":"asdf"
//  }
  // 댓글을 수정하는 메서드
  @PatchMapping("/comments/{cno}") // PATCH /ch4/comments/2924
//  @ResponseBody
  public ResponseEntity<String> modify(
      @RequestBody CommentDto dto, @PathVariable Integer cno, HttpSession session) {
//	  String commenter = (String) session.getAttribute("id");
    String commenter = "asdf";
    dto.setCommenter(commenter);
    dto.setCno(cno);
    System.out.println("dto = "+dto);

    try {
      if(service.modify(dto) != 1)
        throw new Exception("Modify failed.");

      return new ResponseEntity<String>("MOD_OK",HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<String>("MOD_ERR",HttpStatus.BAD_REQUEST);
    }
  }

//  {
//    "pcno":0,
//    "comment":"this is a test"
//  }
  // 댓글을 등록하는 메서드
  @PostMapping("/comments") // /ch4/comments?bno=2924
//  @ResponseBody
  public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno, HttpSession session) {
//	  String commenter = (String) session.getAttribute("id");
    String commenter = "asdf";
    dto.setCommenter(commenter);
    dto.setBno(bno);
//    System.out.println("dto = "+dto);

    try {
      if(service.write(dto) != 1)
        throw new Exception("Write failed.");

      return new ResponseEntity<String>("WRT_OK",HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<String>("WRT_ERR",HttpStatus.BAD_REQUEST);
    }
  }

  // 지정된 댓글을 삭제하는 메서드
  @DeleteMapping("/comments/{cno}") // DELETE /comments/80?bno=2924 <--삭제할 댓글 번호
//  @ResponseBody
  public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session) {
//    String commenter = (String) session.getAttribute("id");
    String commenter = "asdf";
    try {
      int rowCnt = service.remove(cno, bno, commenter);

      if (rowCnt != 1)
        throw new Exception("Delete Failed");

      return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
    }
  }

  // 게시물의 모든 댓글을 가져오는 메서드
  @GetMapping("/comments")
//  @ResponseBody
  public ResponseEntity<List<CommentDto>> list(Integer bno) { //bno가 어떻게 전달되는지는 2.18 참고
    List<CommentDto> list = null;
    try {
      list = service.getList(bno);
      return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<List<CommentDto>>(HttpStatus.BAD_REQUEST); // 생성자의 첫 번째 인수를 생략하면 null로 처리
    }

  }
}