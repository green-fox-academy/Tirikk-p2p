package com.greenfox.chat.service;

import com.greenfox.chat.model.Client;
import com.greenfox.chat.model.Message;
import com.greenfox.chat.model.ReceivedMessage;
import org.springframework.web.client.RestTemplate;

public class MessageSender {

  public static void sendMessage(Message message) {
    Client client = new Client().setId(System.getenv("CHAT_APP_UNIQUE_ID"));
    ReceivedMessage receivedMessage = new ReceivedMessage()
            .setClient(client)
            .setMessage(message);

    RestTemplate rt = new RestTemplate();
    rt.postForObject(System.getenv("CHAT_APP_PEER_ADDRESS") + "/api/message/receive", receivedMessage,
            ReceivedMessage.class);
  }

  public static void sendReceivedMessage(ReceivedMessage receivedMessage) {
    RestTemplate rt = new RestTemplate();
    rt.postForObject(System.getenv("CHAT_APP_PEER_ADDRESS") + "/api/message/receive", receivedMessage,
            ReceivedMessage.class);
  }
}
