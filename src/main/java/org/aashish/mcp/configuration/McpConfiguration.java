package org.aashish.mcp.configuration;

import java.util.List;
import org.aashish.mcp.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ajha
 */
@Configuration
public class McpConfiguration {

  /**
   * Chat client bean with memory advisor added to the advisor chain. This will ensure that the
   * conversation history is maintained across multiple interactions.
   *
   * @param aiChatModel
   * @param chatMemory
   * @param advisor
   * @param toolCallbackProvider
   * @return
   */
  @Bean("mcpChatClient")
  @ConditionalOnProperty(
      name = "app.mcp.enabled",
      havingValue = "true",
      matchIfMissing = true
  )
  public ChatClient chatMemoryClient(
      OpenAiChatModel aiChatModel,
      ChatMemory chatMemory,
      ToolCallbackProvider toolCallbackProvider) {
    Advisor loggerAdvisor = new SimpleLoggerAdvisor();
    Advisor tokenAdvisor = new TokenUsageAuditAdvisor();
    Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
    return ChatClient.builder(aiChatModel).defaultAdvisors(
            List.of(loggerAdvisor, tokenAdvisor, memoryAdvisor))
        .defaultToolCallbacks(toolCallbackProvider)
        .build();
  }

}
