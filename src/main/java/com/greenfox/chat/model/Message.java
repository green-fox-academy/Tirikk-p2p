package com.greenfox.chat.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
public class Message {
  @NotNull
  String username;
  @NotNull
  String text;
  @NotNull
  Timestamp timestamp;
  @NotNull
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

  public Message setUsername(String username) {
    this.username = username;
    return this;
  }

  public Message setText(String text) {
    this.text = text;
    return this;
  }

  public Message setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public Message setId(int id) {
    this.id = id;
    return this;
  }
}
