package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;


@Controller
public class SimpleRestController {

  //"/test" 경로의 get 요청을 처리하는 애너테이션
  @GetMapping("/test")
  // view 이름을 반환하는 public test 메서드 선언부
  public String test() {
    //view 이름 "test"를 반환
    return "test";
  }


  @GetMapping("/test2")
  public String test2() {
    return "comment2";
  }

}