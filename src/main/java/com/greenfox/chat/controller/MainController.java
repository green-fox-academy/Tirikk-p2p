package com.greenfox.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.greenfox.chat.model.*;
import com.greenfox.chat.repository.MessageRepository;
import com.greenfox.chat.repository.UserRepository;;
import com.greenfox.chat.service.IdGenerator;
import com.greenfox.chat.service.Logger;
import com.greenfox.chat.service.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
  @Autowired
  private
  UserRepository userRepo;

  @Autowired
  private
  MessageRepository messageRepo;

  @GetMapping("/")
  public String greeting(Model model, HttpServletRequest request) {
    Logger.log(request);
    if (userRepo.count() == 0) {
      return "redirect:/enter";
    } else {
      Iterable<Message> messages = messageRepo.findAllByOrderByTimestampAsc();
      model.addAttribute("messages", messages);
      model.addAttribute("userNotProvided", false);
      return "index";
    }
  }

  @GetMapping("/enter")
  public String enter(Model model, HttpServletRequest request) {
    Logger.log(request);
    if (userRepo.count() == 0) {
      model.addAttribute("userNotProvided", false);
      return "register";
    } else {
      return "redirect:/";
    }
  }

  @PostMapping(value = "/enterNew")
  public String enterNew(Model model, @RequestParam(name = "new_user", required = false) String user,
                         HttpServletRequest request) {
    Logger.log(request);
    if (user.equals("")) {
      model.addAttribute("userNotProvided", true);
      return "register";
    } else {
      userRepo.save(new User(user));
      return "redirect:/";
    }
  }

  @PostMapping(value = "/update")
  public String update(Model model, @RequestParam(name = "user", required = false) String name, HttpServletRequest
          request) {
    Logger.log(request);
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

  @PostMapping("/addMessage")
  public String addMessage(@RequestParam(name = "message") String message, HttpServletRequest request) throws
          JsonProcessingException {
    Logger.log(request);
    Message messageToSave = new Message(userRepo.findOne(1).getName(), message, IdGenerator.generateId(messageRepo));
    messageRepo.save(messageToSave);
//    MessageSender.sendMessage(messageToSave);
    return "redirect:/";
  }
}
