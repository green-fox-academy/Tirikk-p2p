package com.greenfox.chat.controller;

//import com.greenfox.chat.model.Log;
import com.greenfox.chat.model.Log;
import com.greenfox.chat.model.Message;
import com.greenfox.chat.model.User;
import com.greenfox.chat.repository.MessageRepository;
import com.greenfox.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
  @Autowired
  UserRepository userRepo;

  @Autowired
  MessageRepository messageRepo;

  @RequestMapping("/")
  public String greeting(Model model, HttpServletRequest request) {
    if (System.getenv("CHAT_APP_LOGLEVEL").equals("INFO")) {
      System.out.println(new Log(request.getRequestURI(), request.getMethod(), System.getenv("CHAT_APP_LOGLEVEL")));
    }
    if (userRepo.count() == 0) {
      return "redirect:/enter";
    } else {
      Iterable<Message> messages = messageRepo.findAllByOrderByTimestampAsc();
      model.addAttribute("messages", messages);
      model.addAttribute("userNotProvided", false);
      return "index";
    }
  }

  @RequestMapping("/enter")
  public String enter(Model model, HttpServletRequest request) {
    if (System.getenv("CHAT_APP_LOGLEVEL").equals("INFO")) {
      System.out.println(new Log(request.getRequestURI(), request.getMethod(), System.getenv("CHAT_APP_LOGLEVEL")));
    }
    if (userRepo.count() == 0) {
      model.addAttribute("userNotProvided", false);
      return "register";
    } else {
      return "redirect:/";
    }
  }

  @RequestMapping(value = "/enterNew")
  public String enterNew(Model model, @RequestParam(name = "new_user", required = false) String user, HttpServletRequest request) {
    if (System.getenv("CHAT_APP_LOGLEVEL").equals("INFO")) {
      System.out.println(new Log(request.getRequestURI(), request.getMethod(), System.getenv("CHAT_APP_LOGLEVEL"),
              request.getParameter("new_user")));
    }
    if (user.equals("")) {
      model.addAttribute("userNotProvided", true);
      return "register";
    } else {
      userRepo.save(new User(user));
      return "redirect:/";
    }
  }

  @RequestMapping(value = "/update")
  public String update(Model model, @RequestParam(name = "user", required = false) String name, HttpServletRequest request) {
    if (System.getenv("CHAT_APP_LOGLEVEL").equals("INFO")) {
      System.out.println(new Log(request.getRequestURI(), request.getMethod(), System.getenv("CHAT_APP_LOGLEVEL"),
              request.getParameter("user")));
    }
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
  public String addMessage(@RequestParam(name = "message") String message, HttpServletRequest request) {
    if (System.getenv("CHAT_APP_LOGLEVEL").equals("INFO")) {
      System.out.println(new Log(request.getRequestURI(), request.getMethod(), System.getenv("CHAT_APP_LOGLEVEL"),
              request.getParameter("message")));
    }
    int id = 0;
    boolean idExists = true;
    while (idExists) {
      id = (int)(Math.random() * 8999999) + 1000000;
      if (!messageRepo.exists(id)) {
        idExists = false;
      }
    }
    messageRepo.save(new Message(userRepo.findOne(1).getName(), message, id));
    return "redirect:/";
  }
}
