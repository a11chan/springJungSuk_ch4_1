package com.fastcampus.ch4.service;


import com.fastcampus.ch4.dao.BoardDao;
import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//서비스 계층의 객체이며 component-scan을 통해 자동 생성되는 객체로 지정하는 애너테이션
@Service
//코드 전체 완성 후 나중에 인터페이스로 추출할 것
//클래스 선언 public class BoardService {
public class BoardServiceImpl implements BoardService {

  //BoardDao 타입 매칭 자동 주입
  @Autowired
  BoardDao boardDao;

  //이하 모든 메서드는 Exception 예외를 던지도록 선언
  //그리고 인수로 있는 *은 필요에 따라 변경할 것

  //boardDao.count(*)의 결과를 반환하는 getCount 메서드 선언
  @Override
  public int getCount() throws Exception {
    return boardDao.count();
  }

  //boardDao.delete(*)의 결과를 반환하는 remove 메서드 선언
  @Override
  public int remove(Integer bno, String writer) throws Exception {
    return boardDao.delete(bno, writer);
  }

  //boardDao.insert(*)의 결과를 반환하는 write 메서드 선언
  @Override
  public int write(BoardDto boardDto) throws Exception {
    return boardDao.insert(boardDto);
//    throw new Exception("board write error");
  }

  //boardDao.selectAll(*)의 결과를 반환하는 getList 메서드 선언
  @Override
  public List<BoardDto> getList() throws Exception {
    return boardDao.selectAll();
  }

  //boardDao.select(*)의 결과를 반환하고 조회수를 1 증가시키는 read(*) 메서드 선언
  @Override
  public BoardDto read(Integer bno) throws Exception {
    BoardDto boardDto = boardDao.select(bno);
    boardDao.increaseViewCnt(bno);

    return boardDto;
  }

  //boardDao.selectPage(*)의 결과를 반환하는 getPage(*) 메서드 선언
  @Override
  public List<BoardDto> getPage(Map map) throws Exception {
    return boardDao.selectPage(map);
  }

  //boardDao.update(*)의 결과를 반환하는 modify(*) 메서드 선언
  @Override
  public int modify(BoardDto boardDto) throws Exception {
    return boardDao.update(boardDto);
//    throw new Exception("update fail");
  }

  //**4.08
  //boardDao.searchResultCnt(*)의 결과를 반환하는 getSearchResultCnt(*) 메서드 선언
  public int getSearchResultCnt(SearchCondition sc) throws Exception {
    return boardDao.searchResultCnt(sc);
  }

  //**4.08
  //boardDao.searchSelectPage(*)의 결과를 반환하는 getSearchResultPage(*) 메서드 선언
  public List<BoardDto> getSearchResultPage(SearchCondition sc) throws Exception {
    return boardDao.searchSelectPage(sc);
  }

}
