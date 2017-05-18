package com.greenfox.chat.controller;

import com.greenfox.chat.model.ErrorResponse;
import com.greenfox.chat.model.Message;
import com.greenfox.chat.model.OkResponse;
import com.greenfox.chat.model.ReceivedMessage;
import com.greenfox.chat.repository.MessageRepository;
import com.greenfox.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainRestController {
  @Autowired
  UserRepository userRepo;

  @Autowired
  MessageRepository messageRepo;

  @RequestMapping("/list")
  @ResponseBody
  public Object list() {
    return userRepo.findAll();
  }

  @RequestMapping("/listMessages")
  @ResponseBody
  public Object listMessages() {
    return messageRepo.findAll();
  }

  @RequestMapping("/api/message/receive")
  @CrossOrigin("*")
  public Object receive(@RequestBody() ReceivedMessage receivedMessage) throws Exception {
    messageRepo.save(receivedMessage.getMessage());
    return new OkResponse();
  }

  @ExceptionHandler(Exception.class)
  public void handleAllException(Exception e) {
      System.out.println("####################################\n" + e.getMessage());;
  }
}
