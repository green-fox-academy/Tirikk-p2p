package com.greenfox.chat.controller;

import com.greenfox.chat.model.Log;
import com.greenfox.chat.model.User;
import com.greenfox.chat.repository.MessageRepository;
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
  int userId = 1;

  @Autowired
  UserRepository userRepo;

  @Autowired
  MessageRepository messageRepo;

  @RequestMapping("/")
  public String greeting(Model model, HttpServletRequest request) {
    System.err.println(new Log(request.getRequestURI(), request.getMethod(), System.getenv("CHAT_APP_LOGLEVEL"),
            request.getQueryString()));
    if (userRepo.count() == 0) {
      return "redirect:/register";
    } else {
      model.addAttribute("userNotProvided", false);
      return "index";
    }
  }

  @RequestMapping("/enter")
  public String register(Model model) {
    model.addAttribute("userNotProvided", false);
    return "register";
  }

  @RequestMapping(value = "/enter", method = RequestMethod.POST)
  public String registerNew(Model model, @RequestParam(name = "new_user", required = false) String user) {
    if (user.equals("")) {
      model.addAttribute("userNotProvided", true);
      return "register";
    } else {
      userRepo.save(new User(user));
      return "redirect:/";
    }
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public String update(Model model, @RequestParam(name = "user", required = false) String name) {
    if (name.equals("")) {
      model.addAttribute("userNotProvided", true);
      return "index";
    } else {
      User userToChange = userRepo.findOne(1);
      userToChange.setName(name);
      userRepo.save(userToChange);
      return "redirect:/";
    }
  }
}
