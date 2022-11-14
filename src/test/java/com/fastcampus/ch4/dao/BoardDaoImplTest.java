package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardDaoImplTest {
  @Autowired
  private BoardDao boardDao;

  //**4.07
  // public void searchSelectPageTest() 테스트 선언 및 테스트 대상으로 지정
  @Test
  public void searchSelectPageTest() throws Exception {
    //게시판의 모든 글 삭제
    boardDao.deleteAll();
    //제목, 내용, 글쓴이 끝에 일련번호가 나오도록 게시글 20개 생성
      //boardDto, boardDao 사용
    for(int i = 1; i <=20; i++) {
      BoardDto boardDto = new BoardDto("title"+i, "content"+i,"asdf"+i);
      boardDao.insert(boardDto);
    }
    //현재 페이지1, 포스팅 목록 크기 10, title2, T를 인수로 하는 SearchCondition 객체 생성
    //생성 후 SearchCondition sc에 저장
    SearchCondition sc = new SearchCondition(1, 10, "title2", "T");
    //위의 검색조건 결과를 List<BoardDto> postingList 에 저장
    List<BoardDto> postingList = boardDao.searchSelectPage(sc);
    //postingList크기가 2이면 테스트 통과
    assertTrue(postingList.size() == 2);

    //**4.08 작성자 검색 테스트 추가
    //현재 페이지1, 포스팅 목록 크기 10, asdf2, W를 인수로 하는 SearchCondition 객체 생성
    //생성 후 SearchCondition sc에 저장
    sc = new SearchCondition(1, 10, "asdf2", "W");
    //위의 검색조건 결과를 List<BoardDto> postingList 에 저장
    postingList = boardDao.searchSelectPage(sc);
    //postingList크기가 2이면 테스트 통과
    assertTrue(postingList.size() == 2);
  }

  //**4.08 작성자 검색 테스트 추가
  //**4.07
  // public void searchSelectPageTest() 테스트 선언 및 테스트 대상으로 지정
  @Test
  public void searchResultCntTest() throws Exception {
    //게시판의 모든 글 삭제
    boardDao.deleteAll();
    //제목, 내용, 글쓴이 끝에 일련번호가 나오도록 게시글 20개 생성
    //boardDto, boardDao 사용
    for(int i = 1; i <=20; i++) {
      BoardDto boardDto = new BoardDto("title"+i, "content"+i,"asdf"+i);
      boardDao.insert(boardDto);
    }
    //현재 페이지1, 포스팅 목록 크기 10, title2, T를 인수로 하는 SearchCondition 객체 생성
    //생성 후 sc에 저장
    SearchCondition sc = new SearchCondition(1, 10, "title2", "T");
    //위의 검색조건 결과수를 rowCnt에 저장
    int rowCnt = boardDao.searchResultCnt(sc);
    //postingList크기가 2이면 테스트 통과
    assertTrue(rowCnt == 2);

    //현재 페이지1, 포스팅 목록 크기 10, asdf2, W를 인수로 하는 SearchCondition 객체 생성
    //생성 후 sc에 저장
    sc = new SearchCondition(1, 10, "asdf2", "W");
    //위의 검색조건 결과수를 rowCnt에 저장
    rowCnt = boardDao.searchResultCnt(sc);
    //postingList크기가 2이면 테스트 통과
    assertTrue(rowCnt == 2);
  }

  @Test
  public void insertTestData() throws Exception {
    boardDao.deleteAll();
    for (int i = 1 ; i<= 220 ; i++) {
      BoardDto boardDto = new BoardDto("title"+i, "no content", "asdf");
      boardDao.insert(boardDto);
    }
  }

  @Test
  public void countTest() throws Exception {
    boardDao.deleteAll();
    assertTrue(boardDao.count()==0);

    BoardDto boardDto = new BoardDto("no title", "no content", "asdf");
    assertTrue(boardDao.insert(boardDto)==1);
    assertTrue(boardDao.count()==1);

    assertTrue(boardDao.insert(boardDto)==1);
    assertTrue(boardDao.count()==2);
  }

  @Test
  public void deleteAllTest() throws Exception {
    boardDao.deleteAll();
    assertTrue(boardDao.count()==0);

    BoardDto boardDto = new BoardDto("no title", "no content", "asdf");
    assertTrue(boardDao.insert(boardDto)==1);
    assertTrue(boardDao.deleteAll()==1);
    assertTrue(boardDao.count()==0);

    boardDto = new BoardDto("no title", "no content", "asdf");
    assertTrue(boardDao.insert(boardDto)==1);
    assertTrue(boardDao.insert(boardDto)==1);
    assertTrue(boardDao.deleteAll()==2);
    assertTrue(boardDao.count()==0);
  }

  @Test
  public void deleteTest() throws Exception {
    boardDao.deleteAll();
    assertTrue(boardDao.count()==0);

    BoardDto boardDto = new BoardDto("no title", "no content", "asdf");
    assertTrue(boardDao.insert(boardDto)==1);
    Integer bno = boardDao.selectAll().get(0).getBno();
    assertTrue(boardDao.delete(bno, boardDto.getWriter())==1);
    assertTrue(boardDao.count()==0);

    assertTrue(boardDao.insert(boardDto)==1);
    bno = boardDao.selectAll().get(0).getBno();
    assertTrue(boardDao.delete(bno, boardDto.getWriter()+"222")==0);
    assertTrue(boardDao.count()==1);

    assertTrue(boardDao.delete(bno, boardDto.getWriter())==1);
    assertTrue(boardDao.count()==0);

    assertTrue(boardDao.insert(boardDto)==1);
    bno = boardDao.selectAll().get(0).getBno();
    assertTrue(boardDao.delete(bno+1, boardDto.getWriter())==0);
    assertTrue(boardDao.count()==1);
  }

  @Test
  public void insertTest() throws Exception {
    boardDao.deleteAll();
    BoardDto boardDto = new BoardDto("no title", "no content", "asdf");
    assertTrue(boardDao.insert(boardDto)==1);

    boardDto = new BoardDto("no title", "no content", "asdf");
    assertTrue(boardDao.insert(boardDto)==1);
    assertTrue(boardDao.count()==2);

    boardDao.deleteAll();
    boardDto = new BoardDto("no title", "no content", "asdf");
    assertTrue(boardDao.insert(boardDto)==1);
    assertTrue(boardDao.count()==1);
  }

  @Test
  public void selectAllTest() throws Exception {
    boardDao.deleteAll();
    assertTrue(boardDao.count()==0);

    List<BoardDto> list = boardDao.selectAll();
    assertTrue(list.size() == 0);

    BoardDto boardDto = new BoardDto("no title", "no content", "asdf");
    assertTrue(boardDao.insert(boardDto)==1);

    list = boardDao.selectAll();
    assertTrue(list.size() == 1);

    assertTrue(boardDao.insert(boardDto)==1);
    list = boardDao.selectAll();
    assertTrue(list.size() == 2);
  }

  @Test
  public void selectTest() throws Exception {
    boardDao.deleteAll();
    assertTrue(boardDao.count()==0);

    BoardDto boardDto = new BoardDto("no title", "no content", "asdf");
    assertTrue(boardDao.insert(boardDto)==1);

    Integer bno = boardDao.selectAll().get(0).getBno();
    boardDto.setBno(bno);
    BoardDto boardDto2 = boardDao.select(bno);
    assertTrue(boardDto.equals(boardDto2));
  }

  @Test
  public void selectPageTest() throws Exception {
    boardDao.deleteAll();

    for (int i = 1; i <= 10; i++) {
      BoardDto boardDto = new BoardDto(""+i, "no content"+i, "asdf");
      boardDao.insert(boardDto);
    }

    Map map = new HashMap();
    map.put("offset", 0);
    map.put("postingListSize", 3);

    List<BoardDto> list = boardDao.selectPage(map);
    assertTrue(list.get(0).getTitle().equals("10"));
    assertTrue(list.get(1).getTitle().equals("9"));
    assertTrue(list.get(2).getTitle().equals("8"));

    map = new HashMap();
    map.put("offset", 0);
    map.put("postingListSize", 1);

    list = boardDao.selectPage(map);
    assertTrue(list.get(0).getTitle().equals("10"));

    map = new HashMap();
    map.put("offset", 7);
    map.put("postingListSize", 3);

    list = boardDao.selectPage(map);
    assertTrue(list.get(0).getTitle().equals("3"));
    assertTrue(list.get(1).getTitle().equals("2"));
    assertTrue(list.get(2).getTitle().equals("1"));
  }

  @Test
  public void updateTest() throws Exception {
    boardDao.deleteAll();
    BoardDto boardDto = new BoardDto("no title", "no content", "asdf");
    assertTrue(boardDao.insert(boardDto)==1);

    Integer bno = boardDao.selectAll().get(0).getBno();
    System.out.println("bno = " + bno);
    boardDto.setBno(bno);
    boardDto.setTitle("yes title");
    assertTrue(boardDao.update(boardDto)==1);

    BoardDto boardDto2 = boardDao.select(bno);
    assertTrue(boardDto.equals(boardDto2));
  }

  @Test
  public void increaseViewCntTest() throws Exception {
    boardDao.deleteAll();
    assertTrue(boardDao.count()==0);

    BoardDto boardDto = new BoardDto("no title", "no content", "asdf");
    assertTrue(boardDao.insert(boardDto)==1);
    assertTrue(boardDao.count()==1);

    Integer bno = boardDao.selectAll().get(0).getBno();
    assertTrue(boardDao.increaseViewCnt(bno)==1);

    boardDto = boardDao.select(bno);
    assertTrue(boardDto!=null);
    assertTrue(boardDto.getView_cnt() == 1);

    assertTrue(boardDao.increaseViewCnt(bno)==1);
    boardDto = boardDao.select(bno);
    assertTrue(boardDto!=null);
    assertTrue(boardDto.getView_cnt() == 2);
  }
}
