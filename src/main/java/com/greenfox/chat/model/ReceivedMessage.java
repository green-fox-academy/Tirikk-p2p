package com.greenfox.chat.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ReceivedMessage {
  @Valid
  @NotNull
  Message message;

  @Valid
  @NotNull
  Client client;

  public Message getMessage() {
    return message;
  }

  public Client getClient() {
    return client;
  }

  public ReceivedMessage setMessage(Message message) {
    this.message = message;
    return this;
  }

  public ReceivedMessage setClient(Client client) {
    this.client = client;
    return this;
  }
}
