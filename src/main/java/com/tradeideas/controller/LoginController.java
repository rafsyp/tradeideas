package com.tradeideas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.tradeideas.domain.User;
import com.tradeideas.service.UserService;


@Controller
public class LoginController extends BaseController {
	
  @Autowired
  private UserService userService;
  
  @GetMapping("/login")
  public String login() {
	logger.info("> logging in");
    return "login";
  }
  
  @GetMapping("/register")
  public String register (ModelMap model) {
    model.put("user", new User());
    return "register";
  }
  
  @PostMapping("/register")
  public String registerPost (User user) {
	logger.info("> registered");
    userService.save(user);
    return "redirect:/login";
  }
  
}
