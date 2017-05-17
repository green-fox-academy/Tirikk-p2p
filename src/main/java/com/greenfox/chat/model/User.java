package com.greenfox.chat.model;

import javax.persistence.*;

@Entity
@Table(name = "chat_user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  int id;
  String name;

  public User() {
  }

  public User(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
