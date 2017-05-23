package com.greenfox.chat.service;

import com.greenfox.chat.model.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

public class Logger {

  public static void log(HttpServletRequest request) {
    Enumeration<String> parameterNames = request.getParameterNames();
    StringBuilder params = new StringBuilder();
    while (parameterNames.hasMoreElements()) {
      String param = parameterNames.nextElement();
      params.append(param);
      params.append("=");
      String[] paramValues = request.getParameterValues(param);
      for (String value : Arrays.asList(paramValues)) {
        params.append(value);
      }
      if (parameterNames.hasMoreElements()) {
        params.append("&");
      }
    }
    if (System.getenv("CHAT_APP_LOGLEVEL").equals("INFO")) {
      System.out.println(new Log(request.getRequestURI(), request.getMethod(), System.getenv("CHAT_APP_LOGLEVEL"),
              params.toString()));
    }
  }
}
