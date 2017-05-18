package com.greenfox.chat.model;

public class ReceivedMessage {
  Message message;
  Client client;

  public Message getMessage() {
    return message;
  }

  public Client getClient() {
    return client;
  }

  public void setMessage(Message message) {
    this.message = message;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
