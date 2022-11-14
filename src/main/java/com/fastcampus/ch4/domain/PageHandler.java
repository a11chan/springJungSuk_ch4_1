package com.fastcampus.ch4.domain;

import org.springframework.web.util.UriComponentsBuilder;

// 클래스 선언 public class PageHandler {
public class PageHandler {
  //**4.08 아래 4개 필드를 SearchCondition sc로 통합 및 getter/setter 생성 for pagingTest코드 오류 방지
  //  private int currentPage; // 현재 페이지
  //  private int postingListSize; // 화면에 보이는 게시물 수
  //  private String option;
  //  private String keyword;
  private SearchCondition sc;

  private int totalPostings; // 총 게시물 수
  private int pageNaviSize = 10; // 화면에 보이는 페이지 번호 수
  private int totalNaviPages; // 총 페이지 수
  private int beginNaviPage; // 페이지 네비게이션 시작 페이지
  private int endNaviPage; // 페이지 네비게이션 끝 페이지
  private boolean showPrev;
  private boolean showNext;

  // 게시판 목록 구현을 위한 필수 요소는 totalPostings, currentPage, postingListSize	3가지


  //**4.08 (int totalPostings, SearchCondition sc)를 인수로 하는 생성자 선언
  public PageHandler(int totalPostings, SearchCondition sc) {
    //생성자 인수를 통한 필드 초기화
    this.totalPostings = totalPostings;
    this.sc = sc;

    //doPaging 메서드 호출
    doPaging(totalPostings,sc);
  }

  //**4.08 미사용 생성자 삭제
  //public PageHandler(int totalPostings, int currentPage) {


  //**4.08
  //생성자를 메서드로 수정 -> public void doPaging(int totalPostings, SearchCondition sc) {
  public void doPaging(int totalPostings, SearchCondition sc) {
    this.totalPostings = totalPostings;

    totalNaviPages = (int) Math.ceil(totalPostings / (double) pageNaviSize);
    beginNaviPage = (sc.getCurrentPage() - 1) / pageNaviSize * pageNaviSize + 1;
    endNaviPage = Math.min(totalNaviPages, beginNaviPage + pageNaviSize - 1);
    showPrev = sc.getCurrentPage() - pageNaviSize > 0;
    showNext = endNaviPage != totalNaviPages;
  }


  //**4.08 미사용 getter & setter 삭제
  public int getTotalPostings() {
    return totalPostings;
  }

  public void setTotalPostings(int totalPostings) {
    this.totalPostings = totalPostings;
  }

  public int getPageNaviSize() {
    return pageNaviSize;
  }

  public void setPageNaviSize(int pageNaviSize) {
    this.pageNaviSize = pageNaviSize;
  }

  public int getTotalNaviPages() {
    return totalNaviPages;
  }

  public void setTotalNaviPages(int totalNaviPages) {
    this.totalNaviPages = totalNaviPages;
  }

  public int getBeginNaviPage() {
    return beginNaviPage;
  }

  public void setBeginNaviPage(int beginNaviPage) {
    this.beginNaviPage = beginNaviPage;
  }

  public int getEndNaviPage() {
    return endNaviPage;
  }

  public void setEndNaviPage(int endNaviPage) {
    this.endNaviPage = endNaviPage;
  }

  public boolean isShowPrev() {
    return showPrev;
  }

  public void setShowPrev(boolean showPrev) {
    this.showPrev = showPrev;
  }

  public boolean isShowNext() {
    return showNext;
  }

  public void setShowNext(boolean showNext) {
    this.showNext = showNext;
  }

  public SearchCondition getSc() {
    return sc;
  }

  public void setSc(SearchCondition sc) {
    this.sc = sc;
  }

  //**4.08 sc 객체를 이용해야 하는 변수 수정
  //public void print() { //메서드 선언
  //showPrev, showNext, beginNaviPage, endNaviPage, currentPage를 이용해 네비게이션 콘솔에 출력하기
  // 이전, 다음 페이지 버튼은 [PREV], [NEXT]로 표현
  //현재 페이지는 [ ] 대괄호로 감쌀 것
  public void print() {
    System.out.print(showPrev ? "[PREV] " : "");

    for (int pageNumber = beginNaviPage; pageNumber <= endNaviPage; pageNumber++) {
      if (pageNumber == sc.getCurrentPage())
        System.out.print("[" + pageNumber + "] ");
      else
        System.out.print(pageNumber + " ");
    }

    System.out.println(showNext ? "[NEXT]" : "");
  }

  //**4.08 toString 재생성 - 전체 필드 사용
  @Override
  public String toString() {
    return "PageHandler{" +
        "sc=" + sc +
        ", totalPostings=" + totalPostings +
        ", pageNaviSize=" + pageNaviSize +
        ", totalNaviPages=" + totalNaviPages +
        ", beginNaviPage=" + beginNaviPage +
        ", endNaviPage=" + endNaviPage +
        ", showPrev=" + showPrev +
        ", showNext=" + showNext +
        '}';
  }
}
