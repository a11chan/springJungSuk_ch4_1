package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.SearchCondition;

import java.util.List;
import java.util.Map;

public interface BoardDao {
  //namespace+"count"의 결과를 return 하는 count 메서드 선언
  int count() throws Exception;

  //namespace+"delete"의 결과를 반환하는 delete 메서드 선언
  int delete(Integer bno, String writer) throws Exception;

  //namespace+"insert"의 결과를 반환하는 insert 메서드 선언
  int insert(BoardDto boardDto) throws Exception;

  //namespace+"selectAll"의 결과를 반환하는 selectAll 메서드 선언
  List<BoardDto> selectAll() throws Exception;

  //namespace+"select"의 결과를 반환하는 select 메서드 선언
  BoardDto select(Integer bno) throws Exception;

  //namespace+"increaseViewCnt"의 결과를 반환하는 increaseViewCnt 메서드 선언
  int increaseViewCnt(Integer bno) throws Exception;

  //namespace+"selectPge"의 결과를 반환하는 selectPage 메서드 선언
  List<BoardDto> selectPage(Map map) throws Exception;

  //namespace+"update"의 결과를 반환하는 update() 메서드 선언
  int update(BoardDto boardDto) throws Exception;

  //namespace+"deleteAll"의 결과를 반환하는 deleteAll 메서드 선언
  int deleteAll() throws Exception;

  List<BoardDto> searchSelectPage(SearchCondition sc) throws Exception // List<E> selectList(String statement, Object parameter)
  ;

  int searchResultCnt(SearchCondition sc) throws Exception // T selectOne(String statement, Object parameter)
  ;

  int updateCommentCnt(Integer bno, Integer cnt);

}
