package org.aashish.mcp.controller;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/****
 * Controller for demonstrating Spring AI tool usage.
 *
 */
@RestController
@RequestMapping("/tools")
@Log4j2
public class ToolsController {

  private final ChatClient timeChatClient;

  /** Constructor for ToolsController. */
  public ToolsController(@Qualifier("timeChatClient") ChatClient chatClient) {
    this.timeChatClient = chatClient;
  }

  /**
   * This endpoint uses a custom ChatClient named "timeChatClient" which is configured to use a tool
   * that provides the current local time.
   */
  @GetMapping("/local-time")
  public ResponseEntity<String> localTime(
      @RequestHeader("username") String username, @RequestParam("message") String message) {
    String answer =
        timeChatClient
            .prompt()
            .advisors(a -> a.param(CONVERSATION_ID, username))
            .user(message)
            .call()
            .content();
    return ResponseEntity.ok(answer);
  }
}
