package com.greenfox.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfox.chat.model.ErrorResponse;
import com.greenfox.chat.model.OkResponse;
import com.greenfox.chat.model.ReceivedMessage;
import com.greenfox.chat.repository.MessageRepository;
import com.greenfox.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
//import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
public class MainRestController {
  @Autowired
  UserRepository userRepo;

  @Autowired
  MessageRepository messageRepo;

  @RequestMapping("/list")
  @ResponseBody
  public Object list() {
    return userRepo.findAll();
  }

  @RequestMapping("/listMessages")
  @ResponseBody
  public Object listMessages() {
    return messageRepo.findAll();
  }

  @RequestMapping("/api/message/receive")
//  @SendTo("/index/messages")
  @CrossOrigin("*")
  public OkResponse receive(@RequestBody() @Valid ReceivedMessage receivedMessage, BindingResult bindingResult) throws JsonProcessingException {
    if (bindingResult.hasErrors()) {
      StringBuilder missingFields = new StringBuilder();
      for (FieldError error : bindingResult.getFieldErrors()) {
        missingFields.append(error.getField()).append(", ");
      }
      throw new NullPointerException(missingFields.toString());
    } else {
      if (!receivedMessage.getClient().getId().equals(System.getenv("CHAT_APP_UNIQUE_ID"))) {
        messageRepo.save(receivedMessage.getMessage());

        ObjectMapper mapper = new ObjectMapper();
        String jsonOutput = mapper.writeValueAsString(receivedMessage);

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<String> entity = new HttpEntity<>(jsonOutput);
        RestTemplate rt = new RestTemplate();
        rt.postForObject(System.getenv("CHAT_APP_PEER_ADDRESS")  + "/api/message/receive", entity, OkResponse.class);
      }
      return new OkResponse();
    }
  }

  @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(NullPointerException.class)
  public ErrorResponse handleIllegalArgsException(NullPointerException e) {
    return new ErrorResponse(String.format("Missing field(s): %s", e.getMessage()));
  }
}
