package org.aashish.mcp.controller;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat-memory")
public class ChatMemoryController {

  private final ChatClient chatClient;

  public ChatMemoryController(@Qualifier("chatMemoryClient") ChatClient chatClient) {
    this.chatClient = chatClient;
  }

  @GetMapping
  public ResponseEntity<String> chatMemory(
      @RequestParam("message") String message, @RequestHeader("username") String username) {
    return ResponseEntity.ok(
        chatClient
            .prompt()
            .user(message)
            .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username))
            .call()
            .content());
  }

}
