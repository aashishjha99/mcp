package org.aashish.mcp.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

  private final ChatClient openAiChatClient;

  public ChatController(@Qualifier("openAiChatClient") ChatClient openAiChatClient) {
    this.openAiChatClient = openAiChatClient;
  }

  /**
   * Chat with OpenAI
   * @param message
   * @return
   */
  @GetMapping("/openai")
  public String chatOpenAI(@RequestParam("message") String message) {
    return openAiChatClient
        .prompt()
        .system(
            """
            You are an internal HR assistant. You have to help with policies
            related to leave, attendance, and other HR-related queries.
            You should provide accurate and concise information based on the company's policies.
            If a user asks for help with anything not related to HR policies,
            kindly inform them that you can only assist with queries related to HR policies.
            """)
        .user(message)
        .call()
        .content();
  }

  /**
   * Chat with memory API
   *
   * @param message
   * @return
   */
  @PostMapping("/stream")
  public Flux<String> stream(@RequestParam("message") String message) {
    return openAiChatClient.prompt().user(message).stream().content();
  }
}
