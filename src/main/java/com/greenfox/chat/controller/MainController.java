package com.greenfox.chat.controller;

import com.greenfox.chat.model.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

  @RequestMapping("/")
  public String greeting(HttpServletRequest request) {
    System.err.println(new Log(request.getRequestURI(), request.getMethod(), System.getenv("CHAT_APP_LOGLEVEL"),
            request.getQueryString()));
    return "index";
  }
}
