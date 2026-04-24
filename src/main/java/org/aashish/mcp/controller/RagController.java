package org.aashish.mcp.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  @Value("classpath:static/systemPromptRandomDataTemplate.st")
  private Resource fileResource;

  public RagController(
      @Qualifier("chatMemoryClient") ChatClient chatClient, VectorStore vectorStore) {
    this.chatClient = chatClient;
    this.vectorStore = vectorStore;
  }

  /**
   * API for random chat
   * @param username
   * @param message
   * @return
   * @throws IOException
   */
  @PostMapping("/randome/chat")
  public ResponseEntity<String> randomChat(
      @RequestHeader("username") String username, @RequestParam("message") String message)
      throws IOException {
    String ans =
        chatClient
            .prompt()
            .user(message)
            .advisors(advisorSpec -> advisorSpec.param("conversationId", username))
            .call()
            .content();
    return ResponseEntity.ok(ans);
  }
}
