package com.greenfox.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.greenfox.chat.model.ErrorResponse;
import com.greenfox.chat.model.OkResponse;
import com.greenfox.chat.model.ReceivedMessage;
import com.greenfox.chat.repository.MessageRepository;
import com.greenfox.chat.service.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
      StringBuilder missingFields = new StringBuilder();
      for (FieldError error : bindingResult.getFieldErrors()) {
        missingFields.append(error.getField()).append(", ");
      }
      throw new NullPointerException(missingFields.toString());
    } else {
      if (!receivedMessage.getClient().getId().equals(System.getenv("CHAT_APP_UNIQUE_ID"))) {
        messageRepo.save(receivedMessage.getMessage());
        sendMessageWS();
        MessageSender.sendReceivedMessage(receivedMessage);
      }
      return new OkResponse();
    }
  }

  @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(NullPointerException.class)
  public ErrorResponse handleIllegalArgsException(NullPointerException e) {
    return new ErrorResponse(String.format("Missing field(s): %s", e.getMessage()));
  }

  private void sendMessageWS() {
    template.convertAndSend("/topic/messages", "proba");
  }
}
