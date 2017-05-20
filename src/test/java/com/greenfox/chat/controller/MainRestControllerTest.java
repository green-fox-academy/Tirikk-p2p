package com.greenfox.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfox.chat.ChatApplication;
import com.greenfox.chat.model.Client;
import com.greenfox.chat.model.Message;
import com.greenfox.chat.model.ReceivedMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatApplication.class)
@WebAppConfiguration
@EnableWebMvc
public class MainRestControllerTest {
  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
          MediaType.APPLICATION_JSON.getSubtype(),
          Charset.forName("utf8"));

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void testReceive_withCorrectInput() throws Exception {
    Message message = new Message()
            .setId(7655482)
            .setUsername("EggDice")
            .setText("How you doin'?")
            .setTimestamp(new Timestamp(1322018752992L));
    Client client = new Client().setId("EggDice");
    ReceivedMessage receivedMessage = new ReceivedMessage()
            .setMessage(message)
            .setClient(client);
    ObjectMapper mapper = new ObjectMapper();
    String jsonInput = mapper.writeValueAsString(receivedMessage);

    mockMvc.perform(post("/api/message/receive")
            .contentType(contentType)
            .content(jsonInput))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.status", is("ok")));
  }

  @Test
  public void testReceive_withoutMessageText() throws Exception {
    Message message = new Message()
            .setId(7655482)
            .setUsername("EggDice")
            .setTimestamp(new Timestamp(1322018752992L));
    Client client = new Client().setId("EggDice");
    ReceivedMessage receivedMessage = new ReceivedMessage()
            .setMessage(message)
            .setClient(client);
    ObjectMapper mapper = new ObjectMapper();
    String jsonInput = mapper.writeValueAsString(receivedMessage);

    mockMvc.perform(post("/api/message/receive")
            .contentType(contentType)
            .content(jsonInput))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", containsString("message.text")));
  }

  @Test
  public void testReceive_withoutClient() throws Exception {
    Message message = new Message()
            .setId(7655482)
            .setUsername("EggDice")
            .setText("How you doin'?")
            .setTimestamp(new Timestamp(1322018752992L));
    ReceivedMessage receivedMessage = new ReceivedMessage()
            .setMessage(message);
    ObjectMapper mapper = new ObjectMapper();
    String jsonInput = mapper.writeValueAsString(receivedMessage);

    mockMvc.perform(post("/api/message/receive")
            .contentType(contentType)
            .content(jsonInput))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", containsString("client")));
  }
}