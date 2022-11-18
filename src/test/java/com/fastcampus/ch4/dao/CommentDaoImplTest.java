package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.CommentDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.stream.events.Comment;
import java.util.List;

import static org.junit.Assert.*;

//**4.10 클래스 전체
//테스트를 위한 ApplicationContext 생성 애너테이션 설정
@RunWith(SpringJUnit4ClassRunner.class)
//context 생성에 필요한 환경설정파일 위치 지정 애너테이션
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class CommentDaoImplTest {

  //CommentDao 타입 매칭 자동 주입
  @Autowired
  CommentDao commentDao;

  //public void count() 테스트, 예외 던짐
  @Test
  public void count() throws Exception {
    //bno 1의 댓글 전부 삭제
    commentDao.deleteAll(1);
    //bno 1에 해당하는 댓글 수가 0과 같은지 검증
    assertTrue(commentDao.count(1)==0);
  }

  //public void delete() 테스트, 예외 던짐
  @Test
  public void delete() throws Exception {
    //bno 1에 해당하는 댓글 전부 삭제
    commentDao.deleteAll(1);
    //bno:1, pcno:0, comment:"comment", commenter:"asdf" 인 CommentDto 객체 생성후 commentDto에 저장
    CommentDto commentDto = new CommentDto(1, 0, "comment", "asdf");
    //commentDto를 DB에 등록하여 그 결과가 1인지 검증
    assertTrue(commentDao.insert(commentDto)==1);
    //bno 1에 해당하는 댓글 수가 1인지 검증
    assertTrue(commentDao.count(1) == 1);

    //bno 1에 해당하는 댓글 중 0번째를 가져와 commentDto에 저장
    commentDto = commentDao.selectAll(1).get(0);
    //commentDto에서 cno, commenter를 가져와 이에 해당하는 댓글 삭제, 그 결과를 int rowCnt에 저장
    int rowCnt = commentDao.delete(commentDto.getCno(), commentDto.getCommenter());
    //rowCnt가 1인지 검증
    assertTrue(rowCnt==1);
  }

  //public void insert() 테스트 선언, 예외 던짐
  @Test
  public void insert() throws Exception {
    //bno 1에 해당하는 댓글 전부 삭제
    commentDao.deleteAll(1);
    //bno:1, pcno:0, comment:"comment", commenter:"asdf" 인 CommentDto 객체 생성후 commentDto에 저장
    CommentDto commentDto = new CommentDto(1, 0, "comment", "asdf");
    //commentDto를 DB에 등록하여 그 결과가 1인지 검증
    assertTrue(commentDao.insert(commentDto)==1);
    //bno 1에 해당하는 댓글 수가 1인지 검증
    assertTrue(commentDao.count(1) == 1);


    //bno:1, pcno:0, comment:"comment", commenter:"asdf" 인 CommentDto 객체 생성후 commentDto에 저장
    commentDto = new CommentDto(1, 0, "comment", "asdf");
    //commentDto를 DB에 등록하여 그 결과가 1인지 검증(성공여부)
    assertTrue(commentDao.insert(commentDto)==1);
    //bno 1에 해당하는 댓글 수가 2인지 검증
    assertTrue(commentDao.count(1)==2);
  }

  //public void selectAll() 테스트 선언, 예외 던짐
  @Test
  public void selectAll() throws Exception {
    //bno 1에 해당하는 댓글 전부 삭제
    commentDao.deleteAll(1);
    //bno:1, pcno:0, comment:"comment", commenter:"asdf" 인 CommentDto 객체 생성후 commentDto에 저장
    CommentDto commentDto = new CommentDto(1, 0, "comment", "asdf");
    //commentDto를 DB에 등록하여 그 결과가 1인지 검증
    assertTrue(commentDao.insert(commentDto)==1);
    //bno 1에 해당하는 댓글 수가 1인지 검증
    assertTrue(commentDao.count(1) == 1);

    //bno 1에 해당하는 모든 댓글 목록을 List<T> list에 저장
    List<CommentDto> list = commentDao.selectAll(1);
    //list의 사이즈가 1인지 검증
    assertTrue(list.size()==1);

    //bno:1, pcno:0, comment:"comment", commenter:"asdf" 인 CommentDto 객체 생성후 commentDto에 저장
    commentDto = new CommentDto(1, 0, "comment", "asdf");
    //commentDto를 DB에 등록하여 그 결과가 1인지 검증
    assertTrue(commentDao.insert(commentDto)==1);
    //bno 1에 해당하는 댓글 수가 2인지 검증
    assertTrue(commentDao.count(1)==2);

    //bno 1에 해당하는 모든 댓글 목록을 list에 저장
    list = commentDao.selectAll(1);
    //list의 사이즈가 2인지 검증
    assertTrue(list.size()==2);
  }

  //public void select() 테스트 선언, 예외 던짐
  @Test
  public void select() throws Exception {
    //bno 1에 해당하는 댓글 전부 삭제
    commentDao.deleteAll(1);
    //bno:1, pcno:0, comment:"comment", commenter:"asdf" 인 CommentDto 객체 생성후 commentDto에 저장
    CommentDto commentDto = new CommentDto(1, 0, "comment", "asdf");
    //commentDto를 DB에 등록하여 그 결과가 1인지 검증
    assertTrue(commentDao.insert(commentDto)==1);
    //bno 1에 해당하는 댓글 수가 1인지 검증
    assertTrue(commentDao.count(1) == 1);


    //bno 1에 해당하는 모든 댓글 목록을 List<T> list에 저장
    List<CommentDto> list = commentDao.selectAll(1);
    //list에서 0번째 댓글을 가져와 comment내용을 String comment에 저장
    String comment = list.get(0).getComment();
    //list에서 0번째 댓글을 가져와 commenter를 String commenter에 저장
    String commenter = list.get(0).getCommenter();
    //comment와 CommentDto의 comment가 같은지 검증
    assertTrue(comment.equals(commentDto.getComment()));
    //commenter와 CommentDto의 commenter가 같은지 검증
    assertTrue(commenter.equals(commentDto.getCommenter()));
  }

  //public void update() 테스트 선언, 예외 던짐
  @Test
  public void update() throws Exception {
    //bno 1에 해당하는 댓글 전부 삭제
    commentDao.deleteAll(1);
    //bno:1, pcno:0, comment:"comment", commenter:"asdf" 인 CommentDto 객체 생성후 commentDto에 저장
    CommentDto commentDto = new CommentDto(1, 0, "comment", "asdf");
    //commentDto를 DB에 등록하여 그 결과가 1인지 검증
    assertTrue(commentDao.insert(commentDto)==1);
    //bno 1에 해당하는 댓글 수가 1인지 검증
    assertTrue(commentDao.count(1) == 1);

    //bno 1에 등록된 모든 댓글을 List<T> list에 저장
    List<CommentDto> list = commentDao.selectAll(1);
    //list에서 0번째 댓글에서 cno를 가져와 commentDto에 저장
    commentDto.setCno(list.get(0).getCno());
    //"comment2"라는 내용의 comment를 commentDto에 저장
    commentDto.setComment("comment2");
    //수정된 commentDto를 DB에 적용하여 그 결과가 1인지 검증
    assertTrue(commentDao.update(commentDto)==1);

    //bno 1에 해당하는 댓글 목록 전체를 list에 저장
    list = commentDao.selectAll(1);
    //list의 0번째 DTO에서 comment를 가져와 String comment에 저장
    String comment = list.get(0).getComment();
    //list의 0번째 DTO에서 commenter를 가져와 String commenter에 저장
    String commenter = list.get(0).getCommenter();
    //comment와 commentDto의 comment가 같은지 검증
    assertTrue(comment.equals(commentDto.getComment()));
    //commenter와 commentDto의 commenter가 같은지 검증
    assertTrue(commenter.equals(commentDto.getCommenter()));
  }
}
