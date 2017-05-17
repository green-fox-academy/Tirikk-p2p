package com.greenfox.chat.controller;

import com.greenfox.chat.model.Log;
import com.greenfox.chat.model.User;
import com.greenfox.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
  @Autowired
  UserRepository userRepo;

  @RequestMapping("/")
  public String greeting(HttpServletRequest request) {
    System.err.println(new Log(request.getRequestURI(), request.getMethod(), System.getenv("CHAT_APP_LOGLEVEL"),
            request.getQueryString()));
    return "index";
  }

  @RequestMapping("/register")
  public String register(Model model) {
    model.addAttribute("userNotProvided", false);
    return "register";
  }

  @RequestMapping(value = "/registerNew", method = RequestMethod.POST)
  public String registerNew(Model model, @RequestParam(name = "new_user", required = false) String user) {
    if (user.equals("")) {
      model.addAttribute("userNotProvided", true);
      return "register";
    } else {
      userRepo.save(new User(user));
      return "redirect:/";
    }
  }
}
