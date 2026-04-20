package org.aashish.mcp.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

  private final ChatClient openAiChatClient;

  public ChatController(
      @Qualifier("openAiChatClient") ChatClient openAiChatClient) {
    this.openAiChatClient = openAiChatClient;
  }

  @GetMapping("/openai")
  public String chatOpenAI(@RequestParam("message") String message) {
    return openAiChatClient
        .prompt()
        .system(
            """
            You are an internal HR assistanc ,You have to help with policies
            to help in role.You can help with policies related to leave, attendance, and other HR-related queries.
            You should provide accurate and concise information based on the company's policies.
            If user ask for help anything which is not related to HR, policies,
            Kindly inform them that you can only assist with queries related to HR policies."")
            """)
        .user(message)
        .call()
        .content();
  }


  @GetMapping("/stream")
  public Flux<String> stream(@RequestParam("message") String message) {
    return openAiChatClient.prompt().user(message).stream().content();
  }
}
