package com.greenfox.chat.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Message {
  String username;
  String text;
  Timestamp timestamp;
  @Id
  int id;

  public Message() {
  }

  public Message(String username, String text, int id) {
    this.username = username;
    this.text = text;
    timestamp = new Timestamp(System.currentTimeMillis());
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public String getText() {
    return text;
  }

  public Timestamp getTimestamp() {
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

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public void setId(int id) {
    this.id = id;
  }
}
