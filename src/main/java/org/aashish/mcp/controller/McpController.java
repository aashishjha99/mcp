package org.aashish.mcp.controller;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.c;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mcp")
public class McpController {
  private final ChatClient chatClient;

  public McpController(@Qualifier("mcpChatClient") ChatClient chatClientBuilder) {
    this.chatClient = chatClientBuilder;
  }

  /**
   *
   * @param message
   * @return
   */
  @GetMapping("/chat")
  public String chat(@RequestParam(value = "message") String message) {
    return chatClient.prompt().user(message).call().content();
  }
}
