package com.greenfox.chat.controller;

//import com.greenfox.chat.model.Log;
import com.greenfox.chat.model.Message;
import com.greenfox.chat.model.User;
import com.greenfox.chat.repository.MessageRepository;
import com.greenfox.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MainController {
  @Autowired
  UserRepository userRepo;

  @Autowired
  MessageRepository messageRepo;

  @RequestMapping("/")
  public String greeting(Model model, HttpServletRequest request) {
//    System.err.println(new Log(request.getRequestURI(), request.getMethod(), System.getenv("CHAT_APP_LOGLEVEL"),
//            request.getQueryString()));
    if (userRepo.count() == 0) {
      return "redirect:/enter";
    } else {
      Iterable<Message> messages = messageRepo.findAll();
      model.addAttribute("messages", messages);
      model.addAttribute("userNotProvided", false);
      return "index";
    }
  }

  @RequestMapping("/enter")
  public String enter(Model model) {
    model.addAttribute("userNotProvided", false);
    return "register";
  }

  @RequestMapping(value = "/enterNew")
  public String enterNew(Model model, @RequestParam(name = "new_user", required = false) String user) {
    if (user.equals("")) {
      model.addAttribute("userNotProvided", true);
      return "register";
    } else {
      userRepo.save(new User(user));
      return "redirect:/";
    }
  }

  @RequestMapping(value = "/update")
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

  @RequestMapping("/addMessage")
  public String addMessage(@RequestParam(name = "message") String message) {
    messageRepo.save(new Message(userRepo.findOne(1).getName(), message));
    return "redirect:/";
  }
}
