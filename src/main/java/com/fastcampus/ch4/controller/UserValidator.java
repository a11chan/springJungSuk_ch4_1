package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

	public class UserValidator implements Validator {
		@Override
		public boolean supports(Class<?> clazz) {
			// clazz가 User 또는 그 자손인지 확인, 결과가 false 이면 검증 불가, return 문에 함수 바로 작성
		  return User.class.isAssignableFrom(clazz);
		}

		@Override
		public void validate(Object target, Errors errors) {
		  // "UserValidator.validate() is called" 콘솔 출력
			System.out.println("UserValidator.validate() is called");
		  
			// target을 User로 형변환하여 user로 저장
			User user = (User) target;
			
			// id와 pwd를 검증하기 위해 user 객체에서 각각 불러와 String으로 저장
			String id = user.getId();
			String pwd = user.getPwd();
			
			// errors 객체에 id와 pwd가 비어 있거나 빈 문자열이면 "required" 라는 에러코드를 전달하기(관련 메서드 사용)
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "required");

			// 만약, id가 null이거나 길이가 5글자 미만, 12글자를 초과하면 "invalidLength"라는 에러코드를 errors객체에 설정
			if(id==null || id.length() <  5 || id.length() > 12) {
//			errors.reject("invalidAccount"); // 객체 전체에 대한 에러 저장, 로그인 검증 시 활용(ID, PW)
				errors.rejectValue("id", "invalidLength", new String[] {"","5","12"}, null); // defaultMessage를 null로 임시 처리
			}
			// 만약, pwd가 null이거나 길이가 5글자 미만, 12글자를 초과하면 "invalidLength"라는 에러코드를 errors객체에 설정
			if(pwd==null || pwd.length() <  5 || pwd.length() > 12) {
//			errors.reject("invalidAccount"); // 객체 전체에 대한 에러 저장, 로그인 검증 시 활용(ID, PW)
				errors.rejectValue("pwd", "invalidLength", new String[] {"","5","12"}, null);
			}
	}
}