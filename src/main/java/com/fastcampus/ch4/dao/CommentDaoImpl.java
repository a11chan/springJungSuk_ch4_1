package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.CommentDto;
import org.apache.ibatis.session.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

//**4.10 DB에 직접 접근하는 역할을 하는 @Component임을 나타내는 애너테이션(컴포넌트 스캔 대상인)
@Repository
public class CommentDaoImpl implements CommentDao {

  //SqlSession 자동 주입
  @Autowired
  SqlSession sqlSession;
  //private static 필드로 namespace 선언, 값은 commentMapper.xml 참고 //왜 static인지?
  private static String namespace = "com.fastcampus.ch4.dao.CommentMapper.";

  //return문에서 id가 count인 SQL문 호출
  //메서드 이름은 count, 예외 던짐
  @Override
  public int count(Integer bno) throws Exception {
    return sqlSession.selectOne(namespace + "count", bno);
  }

  //return문에서 id가 deleteAll인 SQL문 호출
  //메서드 이름은 deleteAll, 예외 던짐
  @Override
  public int deleteAll(Integer bno) throws Exception {
    return sqlSession.delete(namespace + "deleteAll", bno);
  }

  //return문에서 id가 delete 인 SQL문 호출
  //메서드 이름은 delete, 예외 던짐
  //Integer cno, String commenter를 매개변수로 받아 Map map으로 전달
  @Override
  public int delete(Integer cno, String commenter) {
    Map map = new HashMap();
    map.put("cno", cno);
    map.put("commenter", commenter);
    return sqlSession.delete(namespace + "delete", map);
  }

  //return문에서 id가 insert 인 SQL문 호출
  //메서드 이름은 insert, 예외 던짐
  @Override
  public int insert(CommentDto commentDto) throws Exception {
    return sqlSession.insert(namespace+"insert",commentDto);
  }

  //return문에서 id가 selectAll 인 SQL문 호출
  //메서드 이름은 selectAll, 예외 던짐
  @Override
  public List<CommentDto> selectAll(Integer bno) throws Exception {
    return sqlSession.selectList(namespace + "selectAll", bno);
  }

  //return문에서 id가 select 인 SQL문 호출
  //메서드 이름은 select, 예외 던짐
  @Override
  public CommentDto select(Integer cno) throws Exception {
    return sqlSession.selectOne(namespace + "select", cno);
  }

  //return문에서 id가 update 인 SQL문 호출
  //메서드 이름은 update, 예외 던짐
  @Override
  public int update(CommentDto commentDto) throws Exception {
    return sqlSession.update(namespace + "update", commentDto);
  }
}