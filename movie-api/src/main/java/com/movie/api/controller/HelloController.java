package com.movie.api.controller;

import com.movie.api.request.UserRegisterRequest;
import com.service.user.UserService;
import com.service.vo.UserVO;
import com.utils.validator.MobileNumberValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/home")
@Slf4j
public class HelloController {
  @Autowired
  private UserService userService;

  @RequestMapping("/hello")
  public String index() {
    log.info("hello world every one!");
    return "Hello World";
  }

  @PostMapping("/register")
  public String register(@RequestBody UserRegisterRequest request) {
    String mobileNumber = MobileNumberValidator.validate(request.mobile);
    if (mobileNumber == null) {
      return "注册失败";
    }

    UserVO userVO = userService.findByMobile(mobileNumber);
    if (userVO != null) {
      return "当前手机号已被注册啦～注册人为" + userVO.name;
    }

    UserVO registerUser = userService.register(mobileNumber, request.name);
    return "注册成功，欢迎您，" + registerUser.name;
  }
}
