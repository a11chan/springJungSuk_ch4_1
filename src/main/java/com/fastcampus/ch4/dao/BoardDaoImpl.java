package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.SearchCondition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//DB에 직접 접근하는 객체임을 의미하는 애너테이션
@Repository
//클래스 선언 : public class BoardDao {
public class BoardDaoImpl implements BoardDao {
  @Autowired
  SqlSession sqlSession;
  // SQL 명령 수행에 필요한 메서드를 제공하는 인터페이스 타입 매칭 자동 주입

  //private static 필드 namespace 설정, 경로는 "com.fastcampus.ch4.dao.BoardMapper."
  private static String namespace = "com.fastcampus.ch4.dao.BoardMapper.";

  //namespace+"count"의 결과를 return 하는 count 메서드 선언
  @Override
  public int count() throws Exception{
    return sqlSession.selectOne(namespace+"count");
  }

  //namespace+"delete"의 결과를 반환하는 delete 메서드 선언
  @Override
  public int delete(Integer bno, String writer) throws Exception {
    Map map = new HashMap();
    map.put("bno",bno);
    map.put("writer",writer);
    return sqlSession.delete(namespace+"delete",map);
  }

  //namespace+"insert"의 결과를 반환하는 insert 메서드 선언
  @Override
  public int insert(BoardDto boardDto) throws Exception {
    return sqlSession.insert(namespace+"insert",boardDto);
  }

  //namespace+"selectAll"의 결과를 반환하는 selectAll 메서드 선언
  @Override
  public List<BoardDto> selectAll() throws Exception {
    return sqlSession.selectList(namespace+"selectAll");
  }

  //namespace+"select"의 결과를 반환하는 select 메서드 선언
  @Override
  public BoardDto select(Integer bno) throws Exception {
    return sqlSession.selectOne(namespace+"select",bno);
  }

  //namespace+"increaseViewCnt"의 결과를 반환하는 increaseViewCnt 메서드 선언
  @Override
  public int increaseViewCnt(Integer bno) throws Exception{
    return sqlSession.update(namespace+"increaseViewCnt",bno);
  }

  //namespace+"selectPge"의 결과를 반환하는 selectPage 메서드 선언
  @Override
  public List<BoardDto> selectPage(Map map) throws Exception {
    return sqlSession.selectList(namespace+"selectPage",map);
  }

  //namespace+"update"의 결과를 반환하는 update() 메서드 선언
  @Override
  public int update(BoardDto boardDto) throws Exception {
    return sqlSession.update(namespace+"update",boardDto);
  }

  //namespace+"deleteAll"의 결과를 반환하는 deleteAll 메서드 선언
  @Override
  public int deleteAll() throws Exception {
    return sqlSession.delete(namespace+"deleteAll");
  }

  // **4.07 namespace+"searchSelectPage"의 결과를 반환하는 searchSelectPage 메서드 선언
  @Override
  public List<BoardDto> searchSelectPage(SearchCondition sc) throws Exception {
    return sqlSession.selectList(namespace+"searchSelectPage",sc);
  }

  // **4.07 namespace+"searchResultCnt"의 결과를 반환하는 searchResultCnt 메서드 선언
  @Override
  public int searchResultCnt(SearchCondition sc) throws Exception {
    return sqlSession.selectOne(namespace+"searchResultCnt",sc);
  }

  // **4.10 게시글 번호 bno에 해당하는 댓글수를 cnt만큼 증가시키는 updateCommentCnt 메서드 선언
  public int updateCommentCnt(Integer bno, Integer cnt) {
    //해시맵 생성 후 Map map에 저장
    Map map = new HashMap();
    //map에 bno, cnt를 저장
    map.put("bno", bno);
    map.put("cnt", cnt);
    //리턴문에서 mapper.xml에 지정된 id의 sql문 호출 및 파라미터 전달
    return sqlSession.update(namespace + "updateCommentCnt", map);
  }

}
