package org.aashish.mcp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ChatMemoryController.class)
@ActiveProfiles("test")
class ChatMemoryControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean(name = "chatMemoryClient", answer = Answers.RETURNS_DEEP_STUBS)
  private ChatClient chatClient;

  @Test
  void testChatMemory() throws Exception {
    String username = "testUser";
    String message = "Do you remember my previous question?";
    String mockResponse = "Yes, you asked about the weather.";

    when(chatClient.prompt().user(anyString()).advisors(any(Consumer.class)).call().content())
        .thenReturn(mockResponse);

    mockMvc
        .perform(
            get("/chat-memory")
                .header("username", username)
                .param("message", message))
        .andExpect(status().isOk())
        .andExpect(content().string(mockResponse));
  }
}
