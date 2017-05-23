package com.greenfox.chat.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
  private String path;
  private String method;
  private String dateTime;
  private String logLevel;
  private String requestData;

  public Log() {
  }

  public Log(String path, String method, String logLevel, String requestData) {
    this.path = path;
    this.method = method;
    this.logLevel = logLevel;
    this.requestData = requestData;
    dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS"));
  }

  public String getPath() {
    return path;
  }

  public String getMethod() {
    return method;
  }

  public String getDateTime() {
    return dateTime;
  }

  public String getLogLevel() {
    return logLevel;
  }

  public String getRequestData() {
    return requestData;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }

  public void setLogLevel(String logLevel) {
    this.logLevel = logLevel;
  }

  public void setRequestData(String requestData) {
    this.requestData = requestData;
  }

  @Override
  public String toString() {
    return String.format("%s %s Request %s %s %s", dateTime, logLevel, path, method, requestData);
  }
}
