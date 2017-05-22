package com.greenfox.chat.service;

import com.greenfox.chat.repository.MessageRepository;

public class IdGenerator {

  public static int generateId(MessageRepository messageRepo) {
    int id = 0;
    boolean idExists = true;
    while (idExists) {
      id = (int) (Math.random() * 8999999) + 1000000;
      if (!messageRepo.exists(id)) {
        idExists = false;
      }
    }
    return id;
  }
}
