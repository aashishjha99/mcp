package org.aashish.mcp.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;

@WebMvcTest(ChatController.class)
@ActiveProfiles("test")
class ChatControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean(
      name = "openAiChatClient",
      answer = Answers.RETURNS_DEEP_STUBS)
  private ChatClient openAiChatClient;

  @Test
  void testChatOpenAI() throws Exception {
    String message = "What is the leave policy?";
    String responseMessage = "Our leave policy allows 20 days of paid time off.";

    when(openAiChatClient.prompt().system(anyString()).user(anyString()).call().content())
        .thenReturn(responseMessage);

    mockMvc
        .perform(get("/api/chat/openai").param("message", message))
        .andExpect(status().isOk())
        .andExpect(content().string(responseMessage));
  }

  @Test
  void testStream() throws Exception {
    String message = "Hello";
    Flux<String> fluxResponse = Flux.just("Hi", " ", "there!");

    when(openAiChatClient.prompt().user(anyString()).stream().content()).thenReturn(fluxResponse);

    mockMvc
        .perform(post("/api/chat/stream").param("message", message))
        .andExpect(status().isOk());
  }
}
