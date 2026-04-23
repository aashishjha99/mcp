package org.aashish.mcp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.function.Consumer;
import org.aashish.mcp.tools.HelpDeskTools;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HelpDeskController.class)
@ActiveProfiles("test")
class HelpDeskControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean(name = "helpDeskChatClient", answer = Answers.RETURNS_DEEP_STUBS)
  private ChatClient openAiChatClient;

  @MockBean private HelpDeskTools helpDeskTools;

  @Test
  void testChatOpenAI() throws Exception {
    String username = "testUser";
    String message = "Create a ticket for my broken monitor.";
    String mockResponse = "Ticket #123 has been created.";

    when(openAiChatClient
            .prompt()
            .advisors(any(Consumer.class))
            .user(anyString())
            .tools((Object) any())
            .toolContext(anyMap())
            .call()
            .content())
        .thenReturn(mockResponse);

    mockMvc
        .perform(
            get("/api/helpdesk/chat")
                .header("username", username)
                .param("message", message))
        .andExpect(status().isOk())
        .andExpect(content().string(mockResponse));
  }
}
