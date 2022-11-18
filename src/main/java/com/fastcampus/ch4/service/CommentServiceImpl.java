package com.fastcampus.ch4.service;


import com.fastcampus.ch4.dao.BoardDao;
import com.fastcampus.ch4.dao.CommentDao;
import com.fastcampus.ch4.domain.CommentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//**4.10 Service 계층임을 나타내는 component 애너테이션 설정
@Service
public class CommentServiceImpl implements CommentService {

  //BoardDao 필드 선언 //@Autowired는 생성자 주입으로 대체
  BoardDao boardDao;
  //CommentDao 필드 선언 //@Autowired는 생성자 주입으로 대체
  CommentDao commentDao;

  //@Autowired //생성자 1개일 때는 생략 가능
  //위에서 선언한 필드를 주입하는 생성자 선언
  public CommentServiceImpl(BoardDao boardDao, CommentDao commentDao) {
    this.boardDao = boardDao;
    this.commentDao = commentDao;
  }

  @Override
  //트랜잭션 적용, Exception 예외 발생 시 롤백되는 애너테이션 설정
  @Transactional(rollbackFor = Exception.class)
  //cno, bno, commenter를 파라미터로 하는 remove 메서드 선언, 예외 던짐
  public int remove(Integer cno, Integer bno, String commenter) throws Exception {
    //bno에 해당하는 게시글의 댓글수를 -1 감소시키고 그 결과를 int rowCnt에 저장
    int rowCnt = boardDao.updateCommentCnt(bno, -1);
    //rowCnt를 soutv
    System.out.println("rowCnt = " + rowCnt);
//      throw new Exception("test error");
    //cno, commenter에 해당하는 댓글을 삭제하고 그 결과를 rowCnt에 저장
    rowCnt = commentDao.delete(cno, commenter);
    //rowCnt.soutv
    System.out.println("rowCnt = " + rowCnt);
    //rowCnt를 반환
    return rowCnt;
  }

  @Override
  //트랜잭션 적용, Exception 예외 발생 시 롤백되는 애너테이션 설정
  @Transactional(rollbackFor = Exception.class)
  //CommentDto가 파라미터인 write 메서드 선언, 예외 던짐
  public int write(CommentDto commentDto) throws Exception {
  //파라미터에서 게시글 번호를 불러와 게시글의 댓글수를 1 증가시킴
    boardDao.updateCommentCnt(commentDto.getBno(),1);
    //throw new Exception("test");
    //return문에서 commentDto 파라미터를 인수로 하여 댓글을 DB에 등록하고 그 결과를 반환
    return commentDao.insert(commentDto);
  }

  @Override
  //게시글 번호 bno에 해당하는 댓글 목록을 List<T>로 반환하는 getList 메서드 선언, 예외 던짐
  public List<CommentDto> getList(Integer bno) throws Exception {
//        throw new Exception("test");
    return commentDao.selectAll(bno);
  }

  @Override
  //게시글 번호 bno에 해당하는 모든 댓글 수를 반환하는 getCount 메서드 선언, 예외 던짐
  public int getCount(Integer bno) throws Exception {
    return commentDao.count(bno);
  }

  @Override
  //댓글 번호 cno에 해당하는 DTO 1개 반환, read 메서드 선언, 예외 던짐
  public CommentDto read(Integer cno) throws Exception {
    return commentDao.select(cno);
  }

  @Override
  //DTO를 파라미터로 하여 댓글 내용을 수정, modify 메서드 선언, 예외 던짐
  public int modify(CommentDto commentDto) throws Exception {
    return commentDao.update(commentDto);
  }
}