package org.aashish.mcp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RagController.class)
@ActiveProfiles("test")
class RagControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean(name = "chatMemoryClient", answer = Answers.RETURNS_DEEP_STUBS)
  private ChatClient chatClient;

  @MockBean private VectorStore vectorStore;

  @Test
  void testRandomChat() throws Exception {
    String username = "testUser";
    String message = "Hello, what is the meaning of life?";
    String mockResponse = "42";

    when(chatClient.prompt().user(anyString()).advisors(any(Consumer.class)).call().content())
        .thenReturn(mockResponse);

    mockMvc
        .perform(
            post("/rag/randome/chat")
                .header("username", username)
                .param("message", message))
        .andExpect(status().isOk())
        .andExpect(content().string(mockResponse));
  }
}
