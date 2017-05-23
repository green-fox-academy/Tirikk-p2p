package com.greenfox.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.greenfox.chat.model.ErrorResponse;
import com.greenfox.chat.model.Message;
import com.greenfox.chat.model.OkResponse;
import com.greenfox.chat.model.ReceivedMessage;
import com.greenfox.chat.repository.MessageRepository;
import com.greenfox.chat.service.MessageSender;
import com.greenfox.chat.service.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MainRestController {
  @Autowired
  private
  MessageRepository messageRepo;

  @Autowired
  private
  SimpMessagingTemplate template;

  @PostMapping("/api/message/receive")
  @CrossOrigin("*")
  public OkResponse receive(@RequestBody() @Valid ReceivedMessage receivedMessage, BindingResult bindingResult)
          throws JsonProcessingException {
    if (bindingResult.hasErrors()) {
      throw new NullPointerException(Validator.getMissingFields(bindingResult));
    } else {
      if (!receivedMessage.getClient().getId().equals(System.getenv("CHAT_APP_UNIQUE_ID")) && !messageRepo.exists
              (receivedMessage.getMessage().getId())) {
        messageRepo.save(receivedMessage.getMessage());
        sendMessageWS(receivedMessage.getMessage());
//        MessageSender.sendReceivedMessage(receivedMessage);
      }
      return new OkResponse();
    }
  }

  @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(NullPointerException.class)
  public ErrorResponse handleIllegalArgsException(NullPointerException e) {
    return new ErrorResponse(String.format("Missing field(s): %s", e.getMessage()));
  }

  private void sendMessageWS(Message message) {
    template.convertAndSend("/topic/messages", message);
  }
}
