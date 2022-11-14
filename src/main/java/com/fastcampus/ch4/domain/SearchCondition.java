package com.fastcampus.ch4.domain;

import org.springframework.web.util.UriComponentsBuilder;

//**4.07 public class SearchCondition { 선언
  //currentPage=1, postingListSize=10, offset=0, keyword="", option="" 필드 생성 및 초기화
  //**4.08 offset 필드 삭제, getter만 남김
public class SearchCondition {
  private Integer currentPage = 1;
  private Integer postingListSize = 10;
  private String keyword = "";
  private String option = "";

  //기본 생성자 선언
  public SearchCondition(){}

  //currentPage, postingListSize, keyword, option을 인수로하는 생성자 선언
  public SearchCondition(Integer currentPage, Integer postingListSize, String keyword, String option) {
    this.currentPage = currentPage;
    this.postingListSize = postingListSize;
    this.keyword = keyword;
    this.option = option;
  }


  // ?page=1&pageSize=10&option=T&keyword="title" 를 생성하는 메서드
  //**4.08 쿼리스트링 처리 메서드 선언
  //public String getQueryString() {
  //currentPage 파라미터가 없으면 현재 클래스에 있는 필드를 사용하여 메서드 오버로드
  public String getQueryString() {
    return getQueryString(currentPage);
  }

  // ?page=1&pageSize=10&option=T&keyword="title" 를 생성하는 메서드
  //**4.08 쿼리스트링 처리 메서드 선언
  //특정 페이지 요청 시에도 페이징이 가능하게 파라미터 추가
  //public String getQueryString(Integer currentPage) {
  public String getQueryString(Integer currentPage) {
    //return문에 UriComponentsBuilder의 객체를 생성하고 메서드 체이닝으로 다음 변수의 쿼리스트링 생성
    //currentPage, postingListSize, option, keyword
    //!!currentPage는 메서드 파라미터 사용
    return UriComponentsBuilder.newInstance()
        .queryParam("currentPage",currentPage)
        .queryParam("postingListSize",postingListSize)
        .queryParam("option",option)
        .queryParam("keyword",keyword)
        .build().toString();
  }


  //모든 필드에 대해 getters/setters 선언
  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  public Integer getPostingListSize() {
    return postingListSize;
  }

  public void setPostingListSize(Integer postingListSize) {
    this.postingListSize = postingListSize;
  }

  //**4.08 리턴문으로 offset 수식 결과를 리턴
  public Integer getOffset() {
    return (currentPage - 1)*postingListSize;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public String getOption() {
    return option;
  }

  public void setOption(String option) {
    this.option = option;
  }

  //**4.08 toString 생성 - 모든 필드
  @Override
  public String toString() {
    return "SearchCondition{" +
        "currentPage=" + currentPage +
        ", postingListSize=" + postingListSize +
        ", offset=" + getOffset() +
        ", keyword='" + keyword + '\'' +
        ", option='" + option + '\'' +
        '}';
  }
}
