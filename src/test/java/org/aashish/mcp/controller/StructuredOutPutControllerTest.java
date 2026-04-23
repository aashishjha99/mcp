package org.aashish.mcp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import org.aashish.mcp.dto.CountryCity;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StructuredOutPutController.class)
@ActiveProfiles("test")
class StructuredOutPutControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean(name = "openAiChatClient", answer = Answers.RETURNS_DEEP_STUBS)
  private ChatClient ollamaChatClient;

  @Test
  void testChatList() throws Exception {
    String message = "List some colors";
    List<String> mockResponse = List.of("Red", "Blue", "Green");

    when(ollamaChatClient
            .prompt()
            .user(anyString())
            .call()
            .entity(any(ListOutputConverter.class)))
        .thenReturn(mockResponse);

    mockMvc
        .perform(get("/api/structured/chat-list").param("message", message))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").value("Red"))
        .andExpect(jsonPath("$[1]").value("Blue"))
        .andExpect(jsonPath("$[2]").value("Green"));
  }

  @Test
  void testChat() throws Exception {
    String message = "List country and capital";
    CountryCity mockCountryCity = new CountryCity();
    List<CountryCity> mockResponse = List.of(mockCountryCity);

    when(ollamaChatClient
            .prompt()
            .user(anyString())
            .call()
            .entity(any(ParameterizedTypeReference.class)))
        .thenReturn(mockResponse);

    mockMvc
        .perform(get("/api/structured/ol").param("message", message))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void testChatMap() throws Exception {
    String message = "Map countries to capitals";
    Map<String, Object> mockResponse = Map.of("France", "Paris", "Japan", "Tokyo");

    when(ollamaChatClient
            .prompt()
            .user(anyString())
            .call()
            .entity(any(MapOutputConverter.class)))
        .thenReturn(mockResponse);

    mockMvc
        .perform(get("/api/structured/chat-map").param("message", message))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.France").value("Paris"))
        .andExpect(jsonPath("$.Japan").value("Tokyo"));
  }
}
