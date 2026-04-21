package org.aashish.mcp.controller;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

import java.util.Map;
import org.aashish.mcp.tools.HelpDeskTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the helpdesk chat.
 * @author aashishjha
 */
@RestController
@RequestMapping("/api/helpdesk")
public class HelpDeskController {

  private final ChatClient openAiChatClient;
  private final HelpDeskTools helpDeskTools;

  public HelpDeskController(
      @Qualifier("helpDeskChatClient") ChatClient openAiChatClient, HelpDeskTools helpDeskTools) {
    this.openAiChatClient = openAiChatClient;
    this.helpDeskTools = helpDeskTools;
  }

  /**
   *  Endpoint for the helpdesk chat.
   *
   * @param message
   * @param username
   * @return
   */
  @GetMapping("/chat")
  public ResponseEntity<String> chatOpenAI(
      @RequestParam("message") String message, @RequestHeader("username") String username) {
    return ResponseEntity.ok(
        openAiChatClient
            .prompt()
            .advisors(a -> a.param(CONVERSATION_ID, username))
            .user(message)
            .tools(helpDeskTools)
            .toolContext(Map.of("username", username))
            .call()
            .content());
  }
}
