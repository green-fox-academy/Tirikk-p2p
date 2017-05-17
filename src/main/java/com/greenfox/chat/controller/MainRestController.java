package com.greenfox.chat.controller;

import com.greenfox.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {
  @Autowired
  UserRepository userRepo;

  @RequestMapping("/list")
  @ResponseBody
  public Object list() {
    return userRepo.findAll();
  }
}
