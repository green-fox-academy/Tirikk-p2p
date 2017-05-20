package com.greenfox.chat.model;

import javax.validation.constraints.NotNull;

public class Client {
  @NotNull
  String id;

  public String getId() {
    return id;
  }

  public Client setId(String id) {
    this.id = id;
    return this;
  }
}
