package com.fastcampus.ch4.controller;


import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.PageHandler;
import com.fastcampus.ch4.domain.SearchCondition;
import com.fastcampus.ch4.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

//흐름제어 역할을 하는 객체 생성 애너테이션
@Controller
//"/board" 경로로 GET or POST 요청 시 처리 가능한 애너테이션
@RequestMapping("/board")
//클래스 선언
public class BoardController {

  //BoardService 타입 매칭 자동 주입
  @Autowired
  BoardService boardService;

  //**4.06 - 수정 내용 DB에 등록 구현
  //"/modify" 경로의 post 요청을 처리하는 애너테이션
  @PostMapping("/modify")
  //view 이름을 반환하는 public modify(*) 선언
    //**4.08 매개변수 Integer currentPage, Integer postingListSize를 SearchCondition으로 통합
  public String modify(HttpSession session, BoardDto boardDto
      , RedirectAttributes rattr, SearchCondition sc, Model m) {
    //세션에서 접속 id를 String writer 참조변수에 저장
    String writer = (String) session.getAttribute("id");
    //view에서 받은 게시물 등록정보의 글쓴이를 세션에서 받은 id값으로 저장
    boardDto.setWriter(writer);

    //try문 시작
    try {
      //게시물 dto 객체를 DB에 등록 후 결과를 int rowCnt 변수에 저장
      int rowCnt = boardService.modify(boardDto);
      //만약 rowCnt가 1이 아니면
      if(rowCnt != 1)
        throw new Exception("board modify error");

      //session 객체를 사용한 1회성 메시지로 return할 view에 "MOD_OK" 전달
      rattr.addFlashAttribute("msg", "MOD_OK");

      //**4.08 SearchCondition으로 통합하면서 생략
        //!중요과제!수정 완료후 이전 네비 페이지로 돌아가기 위해 쿼리스트링을 사용하지 않고 View단에 페이지 정보 전달하기
        //rattr.addAttribute("currentPage", currentPage);
        //rattr.addAttribute("postingListSize", postingListSize);

      //redirect문으로 게시글 최신 목록 요청하도록 return
        //**4.08 수정 후 이전 페이지로 돌아가도록 쿼리스트링 첨부
      return "redirect:/board/list"+sc.getQueryString();

    //catch문으로 Exception 예외를 받음
    } catch (Exception e) {
      //콘솔에 스택 트레이스 출력
      e.printStackTrace();
      //게시물 정보를 view단에 넘길 수 있도록 Model 객체에 저장
      m.addAttribute(boardDto);
      //메시지로 리턴할 페이지에 "MOD_ERR"를 전달
      m.addAttribute("msg", "MOD_ERR");

      //rattr.addFlashAttribute("msg","MOD_ERR"); //redirect를 하지 않을 때는 Model 객체에 전달 필요

      //**4.08 SearchCondition으로 통합하면서 생략
        //수정 완료 후 다시 이전 페이지 네비로 돌아가기 위해 정보 Model 객체에 정보 전달
        //m.addAttribute("currentPage", currentPage);
        //m.addAttribute("postingListSize", postingListSize);

      //board.jsp 뷰 출력
      return "board";
      //게시글 작성 화면을 리턴 //redirect:/board/write를 쓰지 않는 이유는? 코드가 길어져서? addFlashAttribute와 같이 redirect문이 필요한 경우가 아니기 때문
        //굳이? return이 되면서 pageContext로 주요 속성값들이 전달된다.
    }
  }


  //**4.06 - 글쓰기 내용 DB에 등록 및 최신 naviPage로 이동
  //"/write" 경로의 post 요청을 처리하는 애너테이션
  @PostMapping("/write")
  //view 이름을 반환하는 public write(*) 선언
  public String write(HttpSession session, BoardDto boardDto, RedirectAttributes rattr, Model m) {

    //세션에서 접속 id를 String writer 참조변수에 저장
    String writer = (String) session.getAttribute("id");
    //view에서 받은 게시물 등록정보의 글쓴이를 세션에서 받은 id값으로 저장
    boardDto.setWriter(writer);

    //try문 시작
    try {
      //게시물 dto 객체를 DB에 등록 후 결과를 int rowCnt 변수에 저장
      int rowCnt = boardService.write(boardDto);
      //만약 rowCnt가 1이 아니면
        //Exception("board write error") 예외를 던짐
      if(rowCnt!=1) throw new Exception("board write error");

      //session 객체를 사용한 1회성 메시지로 return할 view에 "WRT_OK" 전달
      rattr.addFlashAttribute("msg","WRT_OK");

      //최신 게시판 목록 화면을 보여주도록 redirect문 리턴
      return "redirect:/board/list";
      //catch문으로 Exception 예외를 받음
    } catch (Exception e) {
      //콘솔에 스택 트레이스 출력
      e.printStackTrace();

      //게시물 정보를 view단에 넘길 수 있도록 Model 객체에 저장
      m.addAttribute(boardDto);
      //다시 글을 작성할 수 있게 mode 키에 new 값을 뷰로 전달
      m.addAttribute("mode","new");
      //메시지로 리턴할 페이지에 "WRT_ERR"를 전달
      m.addAttribute("msg","WRT_ERR");
      //❌rattr.addFlashAttribute("msg","WRT_ERR"); //redirect를 하지 않으므로 Model 객체에 전달 필요
      //게시글 작성 화면을 리턴 //redirect:/board/write를 쓰지 않는 이유는? 코드가 길어져서?
      return "board";
    }
  }

  //**4.06 - 글쓰기 화면 불러오기
  // "/write" 경로의 get 요청을 처리하는 애너테이션
  @GetMapping("/write")
  //board라는 뷰 이름을 반환하는 String write(*) 메서드 선언
  public String write(Model m) {
    //mode 키에 new 값을 뷰로 전달
    m.addAttribute("mode","new");
    //board 뷰 이름 반환
    return "board";
  }


  //"/remove" 경로의 Post요청을 처리하는 애너테이션
  @PostMapping("/remove")
  //View이름을 반환하는 public remove(*) 메서드 선언
  //Model 객체를 파라미터에 넣지 않아도 자동으로 추가될까?
    //RedirectAttributes 객체에 자동으로 추가되는 걸까?
  public String remove(Integer bno, SearchCondition sc
      , RedirectAttributes rattr, HttpSession session){

    //session에서 id키의 속성값을 String writer 참조변수에 저장
    String writer = (String) session.getAttribute("id");
    //rattr에 보낼 메시지 저장, 기본값으로 "DEL_OK"
    String msg = "DEL_OK";

    //try문 시작
    try {
      //서비스 계층의 remove(*) 메서드를 호출하고 그 결과를 int rowCnt에 저장
      int rowCnt = boardService.remove(bno, writer);
      //rowCnt가 1이 아니면 "board remove error"를 인수로 하여 Exception 예외를 던짐
      if(rowCnt != 1) throw new Exception("board remove error");

    // catch문 시작, Exception 예외를 인수로 함
    } catch(Exception e) {
      //에러 발생 시 콘솔에 스택 트레이스 출력
      e.printStackTrace();
      //msg에 "DEL_ERR" 저장
      msg = "DEL_ERR";
    }
    //redirect문 끝에 쿼리스트링으로 자동 삽입될 수 있게 currentPage, postingListSize 변수를 RedirectAttributes 객체에 저장
    //rattr.addAttribute("currentPage", currentPage);
    //rattr.addAttribute("postingListSize", postingListSize);

    //msg를 키로 하고 "DEL_OK"를 값으로 하는 1회성 메시지가 View단으로 전달될 수 있게 저장
    rattr.addFlashAttribute("msg",msg);

    //"/board/List" 경로를 요청하는 redirect문을 return
    //**4.08 삭제 이전 페이지로 돌아가는 쿼리스트링 붙이기(검색조건 포함)
    return "redirect:/board/list"+sc.getQueryString();
  }

  //"/read" 경로의 GET요청을 처리하는 애너테이션
  @GetMapping("/read")
  //View이름을 반환하는 public read(*) 메서드 선언
  //**4.08 currentPage, postingListSize를 삭제하고 SearchCondition sc로 통합
    //boardList.jsp에서 넘어온 sc 객체 전체를 받기 위함
  public String read(Integer bno, SearchCondition sc, Model m
      , RedirectAttributes rattr) {
    //try문 시작
    try {
      //bno에 해당하는 게시물 정보를 BoardDto형에 저장
      BoardDto boardDto = boardService.read(bno);
      //View단으로 boardDto 객체를 전달
      m.addAttribute(boardDto);

    //try문 종료, catch문 시작, Exception 예외 받음
    } catch(Exception e) {
      //지정된 예외 발생 시 스택 트레이스 콘솔에 출력
      e.printStackTrace();
      //**4.08 예외 발생 시 세션을 사용한 1회성 메시지로 다음 K-V 전달 "msg"-"READ_ERR"
      rattr.addFlashAttribute("msg", "READ_ERR");
      //**4.08 이전 페이지에서 얻은 sc 객체 정보로 queryString을 만들고
      // /board/list 경로에 붙여 redirect 처리
      return "redirect:/board/list"+sc.getQueryString();
    }

    //board 라는 view 이름을 return
    return "board";
  }

  //**4.08
  //"/board"의 하위 경로인 "/list"로 GET 요청 시 처리 가능한 애너테이션
  @GetMapping("/list")
  //View 페이지명을 반환하는 public list 메서드 선언
  //파라미터로는 SearchCondition sc, Model m, HttpServletRequest request
  public String list(SearchCondition sc , Model m
      , HttpServletRequest request) {
    //request를 매개변수로 하는 loginCheck 메서드의 결과가 false이면
    if (!loginCheck(request))
      //메서드에서 빠져나와 아래 경로를 반환
      //request객체에서 원래 가고자 했던 URL 값을 toURL 쿼리 스트링에 담아 /login/login 경로로 redirect
      return "redirect:/login/login?toURL=" + request.getRequestURL();

    //**4.08
    //try문 시작
    try {
      //총게시물 수를 가져와 int totalPostings에 저장하고 Model 객체에 추가
      int totalPostings = boardService.getSearchResultCnt(sc);
      m.addAttribute("totalPostings",totalPostings);

    //총 게시물 수와 sc를 이용하여 PageHandler 객체 생성 후 PageHandler pageHandler에 저장
      PageHandler pageHandler = new PageHandler(totalPostings, sc);
    //sc를 인수로 하여 게시물 목록을 가져와 List<*> postingList에 저장
      List<BoardDto> postingList = boardService.getSearchResultPage(sc);
    //Model 객체에 1. 게시물 리스트와 2. 페이지 핸들러 객체(ph)를 K-V 형태로 저장
      m.addAttribute("postingList",postingList);
      m.addAttribute("ph", pageHandler);

    //**4.08
    //타임존 없이 현재 날짜를 시스템에서 얻고 타임존을 시스템 기본값에서 가져와 적용, 타임스탬프로 변환 후 Instant startOfToday에 저장
      Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
    //Model 객체에 "startOfToday"를 키로 startOfToday를 밀리초로 변환한 타임스탬프를 키값으로 추가
      m.addAttribute("startOfToday", startOfToday.toEpochMilli());
    //catch문 시작, 인수는 Exception e로 함
    } catch (Exception e) {
      //예외 발생 시 스택 트레이스 출력
      e.printStackTrace();
      //**4.08
      //msg 키에 LIST_ERR 키값을 Model 객체에 추가
      m.addAttribute("msg", "LIST_ERR");
      //totalPostings 키에 0 키값을 Model 객체에 추가
      m.addAttribute("totalPostings", 0);
      // try-catch문 종료
    }
      //"boardList"라는 뷰 이름을 반환
    return "boardList";
  }
  // 메서드 간 경계 //

  //boolean을 반환하는 private loginCheck 메서드 선언
  //세션을 얻을 수 있는 객체를 인수로 함
  private boolean loginCheck(HttpServletRequest request) {
    // 1. 세션을 얻어서 Httpsession session 참조변수에 저장
    HttpSession session = request.getSession();
    // 2. 세션에서 "id"란 이름의 키값이 null이 아니면 true를 반환
    return session.getAttribute("id") != null;
  }
}
