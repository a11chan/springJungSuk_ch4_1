package com.fastcampus.ch4.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class PageHandlerTest {

  //테스트 대상 메서드 지정 애너테이션
  //반환값, 매개변수가 없는 test1 메서드 선언
  @Test
  public void test1() {

    SearchCondition sc = new SearchCondition(1,10,"","");
    //전체 게시물 수: 250, 현재 페이지: 1일 경우 페이지 네비게이션 출력
    //PageHandler 클래스의 생성자를 통해 만든 객체를 ph 참조변수에 대입
    PageHandler ph = new PageHandler(250, sc);
    //현재 페이지를 콘솔에 출력
    System.out.println("ph.currentPage = " + ph.getSc().getCurrentPage());
    //네비게이션 전체 출력
    ph.print();
    //ph 참조변수에 있는 필드값 전체 출력
    System.out.println("ph.toString() = " + ph);

    //시작 페이지가 1인지 검증
    assertTrue(ph.getBeginNaviPage() ==1);
    //페이지 네비게이션의 마지막 페이지가 10인지 검증
    assertTrue(ph.getEndNaviPage() ==10);
  }


  //테스트 대상 메서드 지정 애너테이션
  //반환값, 매개변수가 없는 test2 메서드 선언
  @Test
  public void test2() {
    SearchCondition sc = new SearchCondition(15,10,"","");
    // 매개변수가 2개인 PageHandler 클래스의 생성자에
    // 전체 게시물 수 : 250, 현재 페이지 : 15를 대입하여 생성한 객체를 참조변수 ph에 대입
    PageHandler ph = new PageHandler(250,sc);
    //현재 페이지 번호를 콘솔에 출력
    System.out.println("ph.currentPage = " + ph.getSc().getCurrentPage());
    //네비게이션 전체 출력
    ph.print();
    //ph의 모든 필드를 콘솔에 출력
    System.out.println("ph.toString() = " + ph);

    //시작 페이지가 11인지 검증
    //마지막 페이지가 20인지 검증
    assertTrue(ph.getBeginNaviPage() ==11);
    assertTrue(ph.getEndNaviPage() ==20);
  }


  @Test
  public void test3() {
    SearchCondition sc = new SearchCondition(26,10,"","");
    //전체 페이지 수가 255, 현재 페이지가 26일 때의 PageHandler 객체 생성 후 참조변수 ph에 대입
    PageHandler ph = new PageHandler(255,sc);
    //현재 페이지 출력
    System.out.println("ph.currentPage = " + ph.getSc().getCurrentPage());
    //전체 페이지 네비게이션 출력 메서드 호출
    ph.print();
    //ph에 있는 모든 필드 출력
    System.out.println("ph.toString() = " + ph);

    //시작 페이지가 21인지 검증
    assertTrue(ph.getBeginNaviPage() ==21);
    //마지막 페이지가 26인지 검증
    assertTrue(ph.getEndNaviPage() ==26);
  }

  @Test
  public void test4() {
    SearchCondition sc = new SearchCondition(20,10,"","");
    //전체 게시물 수: 255, 현재 페이지: 20 을 인수로 PageHandler 생성자 호출 후 ph 참조변수에 생성된 객체 주소 저장
    PageHandler ph = new PageHandler(255,sc);
    //현재 페이지 콘솔에 출력
    System.out.println("ph.currentPage = " + ph.getSc().getCurrentPage());
    //페이지 네비게이션 콘솔에 출력하는 메서드 호출
    ph.print();
    //ph의 모든 필드 콘솔에 출력
    System.out.println("ph.toString() = " + ph);

    //시작 페이지가 11인지 검증
    assertTrue(ph.getBeginNaviPage() ==11);
    //마지막 페이지가 20인지 검증
    assertTrue(ph.getEndNaviPage() ==20);
  }

}
