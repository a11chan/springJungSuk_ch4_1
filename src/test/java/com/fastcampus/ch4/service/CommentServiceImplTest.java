package com.fastcampus.ch4.service;


import com.fastcampus.ch4.dao.*;
import com.fastcampus.ch4.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.assertTrue;

//**4.10 전체 클래스
//테스트를 위한 ApplicationContext 생성 애너테이션 설정
@RunWith(SpringJUnit4ClassRunner.class)
//context 생성에 필요한 환경설정파일 위치 지정 애너테이션
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class CommentServiceImplTest {

  //CommentService, CommentDao, BoardDao 타입 매칭 자동 주입
  @Autowired
  CommentService commentService;
  @Autowired
  CommentDao commentDao;
  @Autowired
  BoardDao boardDao;

  @Test
  public void remove() throws Exception {
    //boardDao를 통해 게시글 전체 삭제
    boardDao.deleteAll();

    //title:"hello", content:"hello", writer:"asdf"을 인수로 하는 BoardDto 객체 생성 후 boardDto에 저장
    BoardDto boardDto = new BoardDto("hello","content","asdf");
    //boardDao를 통해 DB에 등록한 결과가 1임을 검증
    assertTrue(boardDao.insert(boardDto)==1);
    //모든 게시글을 select한 결과 중 0번째 게시글에서 bno를 가져와 Integer bno에 저장
    Integer bno = boardDao.selectAll().get(0).getBno();

    //bno.soutv
    System.out.println("bno = " + bno);

    //위에서 가져온 bno에 해당하는 모든 댓글 삭제
    commentDao.deleteAll(bno);
    //위에서 얻은 bno, pcno:0, comment:"hi", commenter:"qwer"을 인수로 하는 CommentDto 객체를 생성하여 commentDto에 저장
    CommentDto commentDto = new CommentDto(bno, 0, "hi", "qwer");
    //boardDao를 이용하여 위에서 얻은 bno에 해당하는 게시물 1개의 댓글수가 0임을 검증
    assertTrue(boardDao.select(bno).getComment_cnt()==0);
    //commentService를 통해 위에서 만든 commentDto를 토대로 댓글을 DB에 등록한 결과가 1임을 검증
    assertTrue(commentService.write(commentDto)==1);
    //boardDao를 통해 위에서 얻은 bno에 해당하는 게시글 1개의 댓글수가 1임을 검증
    assertTrue(boardDao.select(bno).getComment_cnt()==1);

    //commentDao를 통해 위에서 얻은 bno에 해당하는 모든 댓글 중에 0번째의 cno를 Integer cno에 저장
    Integer cno = commentDao.selectAll(bno).get(0).getCno();
      //일부러 예외를 발생시키고 Tx가 취소되는지 확인해야.
    //commentService를 통해 위에서 얻은 cno, bno, commentDto의 작성자에 해당되는 댓글을 삭제한 결과를 int rowCnt에 저장
    int rowCnt = commentService.remove(cno, bno, commentDto.getCommenter());
    //rowCnt가 1임을 검증
    assertTrue(rowCnt==1);
    //commentService를 통해 위에서 얻은 bno에 해당하는 게시글 1개에 달린 댓글 수가 0임을 검증
    assertTrue(commentService.getCount(bno)==0);
  }

  @Test
  public void write() throws  Exception {
    //게시글 전부 삭제
    boardDao.deleteAll();

    //title:"hello", content:"hello", writer:"asdf"을 인수로 하는 BoardDto 객체 생성 후 boardDto에 저장
    BoardDto boardDto = new BoardDto("hello","content","asdf");
    //boardDao를 통해 DB에 등록한 결과가 1임을 검증
    assertTrue(boardDao.insert(boardDto)==1);
    //게시글 전체 목록을 불러와 0번째 게시글에서 bno을 얻어 Integer bno에 저장
    Integer bno = boardDao.selectAll().get(0).getBno();
    //bno.soutv
    System.out.println("bno = " + bno);

    //bno에 해당하는 모든 댓글 삭제
    commentDao.deleteAll(bno);
    //위에서 얻은 bno, pcno:0, comment:"hi", commenter:"qwer"을 인수로 하는 CommentDto 객체를 생성하여 commentDto에 저장
    CommentDto commentDto = new CommentDto(bno,0,"hi","qwer");
    //boardDao를 통해 위에서 얻은 bno에 해당하는 게시글 1개에 대한 댓글 수가 0임을 검증
    assertTrue(boardDao.select(bno).getComment_cnt()==0);
    //commentService를 통해 위에서 만든 commentDto를 DB에 등록(댓글등록)한 결과가 1임을 검증
    assertTrue(commentService.write(commentDto)==1);
    //boardDao를 통해 위에서 얻은 bno의 댓글수가 1임을 검증
    assertTrue(boardDao.select(bno).getComment_cnt()==1);
  }
}