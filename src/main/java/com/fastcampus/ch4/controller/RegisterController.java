package com.fastcampus.ch4.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import com.fastcampus.ch4.dao.UserDao;
import com.fastcampus.ch4.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 현재 클래스를 원격 프로그램으로 등록하는 애너테이션
// "/register" 경로로 온 요청을 받는 애너테이션(GET, POST 둘 다)
@RequestMapping("/register")
@Controller
public class RegisterController {
  
  //UserDao 객체 선언 및 자동 주입 애너테이션 설정
  // FAIL 이라는 이름의 상수를 0으로 초기화, 클래스 내부에서만 사용할 수 있도록 접근 제어자 설정
  @Autowired
  UserDao userDao;
  final private int FAIL = 0;
  
  //현재 클래스 내에서만 타입을 양방향으로 변환해주는 애너테이션 선언
  //반환타입이 없는 public toDate 메서드 정의
  //View와 서버 간 데이터 변환 및 검증을 담당하는 객체를 파라미터로 설정
  @InitBinder
  public void toDate(WebDataBinder binder) {
    // "yyyy-MM-dd" 형식을 생성자 파라미터로 하는 날자 형식 객체 선언, 참조변수는 df
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    // 다음 정보를 생성자 파라미터로 하는 커스텀 에디터 등록
    // 날짜로 변환, 위에서 정한 날짜 형식을 토대로 Null은 허용되지 않음
    binder.registerCustomEditor(Date.class, new CustomDateEditor(df,false));
    // UserValidator를 WebDataBinder의 로컬 validator로 등록
    binder.setValidator(new UserValidator());
  }

  
  //"add" 경로로 들어오는 GET 요청 처리 애너테이션
  // "registerForm" View 페이지를 반환하는 register 메서드 선언
  @GetMapping("/add")
  public String register() {
    return "registerForm";
  }
  
  //"add" 경로로 들어오는 POST 요청 처리 애너테이션
  //회원가입 처리에 따른 View 페이지를 보여주는 save 메서드 선언
  //파라미터로 User 객체, User 객체를 자동검증하는 애너테이션, 검증 결과를 담는 result 객체
  @PostMapping("/add")
  public String save(@Valid User user, BindingResult result) throws Exception {
    // 데이터 타입 변환, 검증 결과를 콘솔에 출력
    // 회원가입 View에서 받은 User 객체 정보를 콘솔에 출력
    System.out.println("result="+result);
    System.out.println("user="+user);
        
    // 파라미터 result에 에러가 없으면
      // UserDao를 통해 DB에 신규회원 정보를 저장 
      // 저장 결과가 FAIL이 아니면 "registerInfo" View를 리턴
    if(!result.hasErrors()) {
      int rowCnt = userDao.insertUser(user);
      if(rowCnt!=FAIL)
        return "registerInfo";
    }    
    // if문을 다 통과 했다면 "registerForm" View 리턴
    return "registerForm";
  }
}