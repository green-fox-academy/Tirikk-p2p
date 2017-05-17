package com.greenfox.chat.controller;

import com.greenfox.chat.model.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

  @RequestMapping("/")
  public String greeting() {
    System.err.println(new Log("/", "GET", System.getenv("CHAT_APP_LOGLEVEL"), ""));
    return "index";
  }
}
