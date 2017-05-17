package com.greenfox.chat.model;

import com.greenfox.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Message {
  String username;
  String text;
  String timestamp;
  @Id
  int id;

  @Autowired
  private MessageRepository messageRepo;

  public Message(String username, String text, int id) {
    this.username = username;
    this.text = text;
    timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS"));
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public String getText() {
    return text;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public int getId() {
    return id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void generateId() {
    int generatedId = (int)(Math.random() * 8999999) + 1000000;
    if (messageRepo.exists(generatedId)) {
      generateId();
    } else {
      id = generatedId;
    }
  }
}
