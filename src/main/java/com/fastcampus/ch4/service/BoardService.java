package com.fastcampus.ch4.service;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.SearchCondition;

import java.util.List;
import java.util.Map;

public interface BoardService {
  //boardDao.count(*)의 결과를 반환하는 getCount 메서드 선언
  int getCount() throws Exception;

  //boardDao.delete(*)의 결과를 반환하는 remove 메서드 선언
  int remove(Integer bno, String writer) throws Exception;

  //boardDao.insert(*)의 결과를 반환하는 write 메서드 선언
  int write(BoardDto boardDto) throws Exception;

  //boardDao.selectAll(*)의 결과를 반환하는 getList 메서드 선언
  List<BoardDto> getList() throws Exception;

  //boardDao.select(*)의 결과를 반환하고 조회수를 1 증가시키는 read(*) 메서드 선언
  BoardDto read(Integer bno) throws Exception;

  //boardDao.selectPage(*)의 결과를 반환하는 getPage(*) 메서드 선언
  List<BoardDto> getPage(Map map) throws Exception;

  //boardDao.update(*)의 결과를 반환하는 modify(*) 메서드 선언
  int modify(BoardDto boardDto) throws Exception;

  int getSearchResultCnt(SearchCondition sc) throws Exception;

  List<BoardDto> getSearchResultPage(SearchCondition sc) throws Exception;

}
