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

@WebMvcTest(ToolsController.class)
@ActiveProfiles("test")
class ToolsControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean(name = "timeChatClient", answer = Answers.RETURNS_DEEP_STUBS)
  private ChatClient timeChatClient;

  @Test
  void testLocalTime() throws Exception {
    String username = "testUser";
    String message = "What time is it?";
    String mockResponse = "The current local time is 10:00 AM.";

    when(timeChatClient.prompt().advisors(any(Consumer.class)).user(anyString()).call().content())
        .thenReturn(mockResponse);

    mockMvc
        .perform(
            get("/tools/local-time")
                .header("username", username)
                .param("message", message))
        .andExpect(status().isOk())
        .andExpect(content().string(mockResponse));
  }
}
