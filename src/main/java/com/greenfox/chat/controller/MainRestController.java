package com.greenfox.chat.controller;

import com.greenfox.chat.model.ErrorResponse;
import com.greenfox.chat.model.OkResponse;
import com.greenfox.chat.model.ReceivedMessage;
import com.greenfox.chat.repository.MessageRepository;
import com.greenfox.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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
  @CrossOrigin("*")
  public OkResponse receive(@RequestBody() @Valid ReceivedMessage receivedMessage, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      StringBuilder missingFields = new StringBuilder();
      for (FieldError error : bindingResult.getFieldErrors()) {
        missingFields.append(error.getField()).append(", ");
      }
      throw new IllegalArgumentException(missingFields.toString());
    } else {
      messageRepo.save(receivedMessage.getMessage());
      return new OkResponse();
    }
  }

  @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(IllegalArgumentException.class)
  public ErrorResponse handleAllException(IllegalArgumentException e) {
    return new ErrorResponse(String.format("Missing field(s): %s", e.getMessage()));
  }
}
