package org.aashish.mcp.controller;

import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rag")
@Log4j2
public class RagController {

  private final ChatClient chatClient;
  private final VectorStore vectorStore;

  public RagController(
      @Qualifier("chatMemoryClient") ChatClient chatClient, VectorStore vectorStore) {
    this.chatClient = chatClient;
    this.vectorStore = vectorStore;
  }

  /**
   * API for RAG chat with memory
   * @param username identifier for conversation history
   * @param message user query
   * @return AI response with retrieved context
   */
  @PostMapping("/chat")
  public ResponseEntity<String> ragChat(
      @RequestHeader("username") String username, @RequestParam("message") String message) {
    String response =
        chatClient
            .prompt()
            .user(message)
            .advisors(advisorSpec -> advisorSpec.param("chat_memory_conversation_id", username))
            .call()
            .content();
    return ResponseEntity.ok(response);
  }
}
