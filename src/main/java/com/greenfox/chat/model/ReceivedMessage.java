package com.greenfox.chat.model;

import javax.validation.Valid;

public class ReceivedMessage {
  @Valid
  Message message;

  @Valid
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
