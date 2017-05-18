package com.greenfox.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfox.chat.model.ErrorResponse;
import com.greenfox.chat.model.Message;
import com.greenfox.chat.model.OkResponse;
import com.greenfox.chat.model.ReceivedMessage;
import com.greenfox.chat.repository.MessageRepository;
import com.greenfox.chat.repository.UserRepository;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
  public Object receive(@RequestBody() ReceivedMessage receivedMessage) throws IllegalAccessException, IOException {
        StringBuilder message = new StringBuilder();
        for (Field f : receivedMessage.getClass().getFields()) {
          f.setAccessible(true);
          if (f.get(receivedMessage) == null) {
            message.append(f.toString());
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
          }
        }
//    List<Field> fields = Arrays.asList(ReceivedMessage.class.getFields());
//    StringBuilder message = new StringBuilder();
//    for (Object o : fields) {
//      if (!receivedMessage.has(o.toString())) {
//        message.append(o.toString());
//      }
//    }
    if (message.length() != 0) {
      throw new NullPointerException(message.toString());
    } else {
//      ObjectMapper mapper = new ObjectMapper();
//      ReceivedMessage mmessage = mapper.readValue(receivedMessage.toString(), ReceivedMessage.class);
      messageRepo.save(receivedMessage.getMessage());
      return new OkResponse();
    }
  }

  @ExceptionHandler(Exception.class)
  public void handleAllException(Exception e) {
    System.out.println("####################################\n" + e.getMessage());
  }
}
