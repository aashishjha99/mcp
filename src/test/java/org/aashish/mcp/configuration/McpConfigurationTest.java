//package org.aashish.mcp.configuration;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.mock;
//
//import java.util.Collections;
//import org.junit.jupiter.api.Test;
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.chat.memory.ChatMemory;
//import org.springframework.ai.openai.OpenAiChatModel;
//import org.springframework.ai.tool.ToolCallbackProvider;
//
//class McpConfigurationTest {
//
//  @Test
//  void shouldCreateChatClientWithoutSpring() {
//    OpenAiChatModel model = mock(OpenAiChatModel.class);
//    ChatMemory memory = mock(ChatMemory.class);
//    ToolCallbackProvider provider = mock(ToolCallbackProvider.class);
//
//    McpConfiguration config = new McpConfiguration();
//
//    ChatClient client = config.chatMemoryClient(model, memory, provider);
//
//    assertThat(client).isNotNull();
//  }
//}
