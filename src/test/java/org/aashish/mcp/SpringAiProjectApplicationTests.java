package org.aashish.mcp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.tool.ToolCallbackProvider; // Import the new dependency
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {"spring.ai.openai.api-key=test-key","app.mcp.enabled=false"})
@ActiveProfiles("test")
class SpringAiProjectApplicationTests {

  @MockBean private OpenAiChatModel openAiChatModel;

  @MockBean private VectorStore vectorStore;

  @MockBean // Mock the ToolCallbackProvider
  private ToolCallbackProvider toolCallbackProvider;

  @Test
  void contextLoads() {}
}
